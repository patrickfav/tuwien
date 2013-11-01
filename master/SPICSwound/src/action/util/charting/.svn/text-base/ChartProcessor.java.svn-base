package util.charting;

import java.awt.Dimension;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import bean.SessionInfo;

import util.JSFNavigationConstants;
import entities.Patient;
import entities.TrialData;

@Scope(ScopeType.CONVERSATION)
@Name("chartProcessor")
public class ChartProcessor implements Serializable {

	private static final long serialVersionUID = 1L;

	private byte[] chartImage = null; // chart image (.png) as a byte array

	private JFreeChart chart;

	private Dimension dimension;

	private String selectedChartName;

	@In(required = false)
	private Patient patient;

	@In(create = false)
	private ChartRepository chartRepository;

	@In
	private EntityManager entityManager;

	@In
	private SessionInfo sessionInfo;

	@Logger
	private Log log;

	@SuppressWarnings("unused")
	@Out(required = false, scope=ScopeType.SESSION)
	private Boolean resetTDViewer;

	public String getSelectedChartName() {
		return selectedChartName;
	}

	public void setSelectedChartName(String selectedChartName) {
		this.selectedChartName = selectedChartName;

	}

	public void createChart() {
		if(patient == null || selectedChartName == null)
			log.warn("no patient injected! patient: #0, chartname #1", patient, selectedChartName);
		
		SpicsChart spc = chartRepository.getChartMap().get(
				sessionInfo.getTrial().getName()).get(selectedChartName);
		spc.setPatient(patient);
		chart = spc.getChart(this);
		dimension = spc.getPreferredDimension();
		int height = (dimension == null) ? 400 : dimension.height;
		int width = (dimension == null) ? 600 : dimension.width;

		try {
			this.chartImage = ChartUtilities.encodeAsPNG(this.chart
					.createBufferedImage(width, height));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public byte[] getChartImage() {

		createChart();

		return chartImage;
	}

	public void setChartImage(byte[] chartImage) {
		this.chartImage = chartImage;
	}

	@End(root=true)
	public String back() {
		resetTDViewer = true;
		return JSFNavigationConstants.FINISHEDEDITINGTRIALDATA;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public ChartRepository getChartRepository() {
		return chartRepository;
	}

	@SuppressWarnings("unchecked")
	public List<TrialData> searchTrialData(Long tfId, Patient patient) {

		Query q = entityManager
				.createQuery("from TrialData td where td.trialform.id=:tfId and td.patient=:p");

		q.setParameter("tfId", tfId);
		q.setParameter("p", patient);

		return q.getResultList();
	}
}
