package util.charting.charts;

import java.awt.Dimension;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import util.charting.ChartProcessor;
import util.charting.ChartRepository;
import util.charting.ChartingException;
import util.charting.NatAttributeIdentifier;
import util.charting.SpicsChart;
import entities.Attribute;
import entities.AttributeGroup;
import entities.Patient;
import entities.TrialData;
import entities.TrialForm;

public class TestChart3 implements SpicsChart {

	private static final long serialVersionUID = 1L;

	private Patient patient = null;

	private boolean init = false;

	private Long trialFormId = null;

	private Long attrId = null;

	private String docName = "Beispielstudie zum Thema Wundheilung";

	public JFreeChart getChart(ChartProcessor chartProcessor) {

		if (init == false || patient == null)
			return null;

		TrialForm tf = chartProcessor.getEntityManager().find(TrialForm.class,
				trialFormId);

		List<TrialData> datas = tf.getTrialDatasByPatient(patient);

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (TrialData td : datas) {
			double val = ((Integer) td.getValueByAttributeId(attrId)
					.getValueAsObject()).doubleValue();
			dataset.addValue(val, "Systolisch", td.getSavedOn());
		}

		// create a chart...
		return ChartFactory.createLineChart("Systolisch", // chart title
				"Date", // domain axis label
				"mmHg", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				false, // include legend
				true, // tooltips
				false // urls
				);
	}

	public String getDescription() {
		return "descr Testchart3";
	}

	public String getName() {
		return "Testchart3";
	}

	public void init(ChartRepository chartRepository) throws ChartingException {

		TrialForm tf = chartRepository.findTrialForm(
				docName, "Teststudienblatt",
				new NatAttributeIdentifier("Blutdruck", "Systolisch"));

		for (AttributeGroup ag : tf.getAttributeGroups()) {
			for (Attribute a : ag.getAttributes()) {
				if (a.getName().equals("Systolisch")) {
					attrId = a.getId();
					break;
				}
			}
		}

		if (attrId == null)
			throw new ChartingException("Couldn't find attribute systolisch");

		trialFormId = tf.getId();

		init = true;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Dimension getPreferredDimension() {
		return null;
	}

	public void setDocumentationName(String name) {
		this.docName = name;

	}

}
