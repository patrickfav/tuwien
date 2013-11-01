package util.charting;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import util.RuntimeConfiguration;
import entities.Attribute;
import entities.AttributeGroup;
import entities.TrialForm;

@Scope(ScopeType.APPLICATION)
@Name("chartRepository")
public class ChartRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String CHART_PKG = "util.charting.charts.";

	private Map<String, Map<String, SpicsChart>> chartMap = new HashMap<String, Map<String, SpicsChart>>();

	@In(create = false, required = true, value = "RuntimeConfiguration")
	private RuntimeConfiguration runtimeConfiguration;

	@Logger
	private Log log;

	/**
	 * Load all spics charts in util.charting.charts at creation time.
	 */
	@Create
	public void loadCharts() {

		// split fully qualified chart names
		String[] charts = runtimeConfiguration.getActiveCharts().split(";;");

		// try to load classes
		for (String s : charts) {

			String[] sParts = s.split(";");
			if (sParts.length != 2) {
				log.warn("failed to parse: #0", s);
				continue;
			}

			String className = CHART_PKG + sParts[1];
			String documentationName = sParts[0];

			Class<?> c = null;

			try {
				c = this.getClass().getClassLoader().loadClass(className);
			} catch (ClassNotFoundException e) {
				log.warn("Chart #0 was not found", className);
				continue;
			}

			if (c.isInterface()) {
				log.warn("Chart #0 is an interface", className);
				continue;
			}

			Object o = null;
			try {
				o = c.newInstance();
			} catch (Exception e) {
				log.warn("could not create instance of #0", className);
				continue;
			}

			if (o instanceof SpicsChart == false) {
				log
						.warn(
								"class #0 does not implement the spics chart interface",
								className);
				continue;
			}

			SpicsChart sc = (SpicsChart) o;
			sc.setDocumentationName(documentationName);
			addToMap(documentationName, sc.getName(), sc);

			try {
				sc.init(this);
			} catch (ChartingException e) {
				log.warn("failed to init chart #0", sc.getName());
			}
			log.info("added spics chart #0", sc.getName());
		}
	}

	@SuppressWarnings("unchecked")
	public TrialForm findTrialForm(String trialName, String trialFormName,
			NatAttributeIdentifier... keys) throws ChartingException {

		// XXX: solve with hql?

		List<TrialForm> matches = ((EntityManager) Component
				.getInstance("entityManager"))
				.createQuery(
						"from TrialForm tf where tf.name = :trialFormName and tf.trial.name = :trialName")
				.setParameter("trialFormName", trialFormName).setParameter(
						"trialName", trialName).getResultList();

		LinkedList<TrialForm> removeList = new LinkedList<TrialForm>();

		boolean foundKey;

		for (TrialForm tf : matches) {
			for (NatAttributeIdentifier key : keys) {

				foundKey = false;

				for (AttributeGroup group : tf.getAttributeGroups()) {
					if (group.getName().equals(key.groupName) == false) {
						continue;
					}

					for (Attribute atr : group.getAttributes()) {
						if (atr.getName().equals(key.attributeName) == true) {
							key.id = atr.getId();
							foundKey = true;
							break;
						}
					}
				}

				if (foundKey == false)
					removeList.add(tf);
			}
		}

		matches.removeAll(removeList);

		if (matches.size() == 1) {
			return matches.get(0);
		} else if (matches.size() == 0) {
			throw new ChartingException("Couldn't find TrialForm "
					+ trialFormName);
		} else {
			log.warn("multiple TrialForms found for name #0", trialFormName);
			return matches.get(0);
		}
	}

	public Map<String, Map<String, SpicsChart>> getChartMap() {
		return chartMap;
	}

	private void addToMap(String docName, String chartName, SpicsChart chart) {
		Map<String, SpicsChart> map = this.chartMap.get(docName);

		if (map == null) {
			map = new HashMap<String, SpicsChart>();
		}

		map.put(chartName, chart);

		this.chartMap.put(docName, map);
	}
}
