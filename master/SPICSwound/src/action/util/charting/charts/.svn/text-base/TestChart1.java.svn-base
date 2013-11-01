package util.charting.charts;

import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import util.charting.ChartProcessor;
import util.charting.ChartRepository;
import util.charting.SpicsChart;
import entities.Patient;

public class TestChart1 implements SpicsChart {

	private static final long serialVersionUID = 1L;
	
	public JFreeChart getChart(ChartProcessor chartProcessor) {
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("Category 1", 43.2);
		data.setValue("Category 2", 27.9);
		data.setValue("Category 3", 79.5);

		// create a chart...
		return ChartFactory.createPieChart(getName(), data, true, // legend?
				true, // tooltips?
				false // URLs?
				);
	}

	public String getDescription() {
		return "descr Testchart1";
	}

	public String getName() {
		return "Testchart1";
	}

	public void init(ChartRepository arg0) {
	}

	public void setPatient(Patient patient) {	
	}

	public Dimension getPreferredDimension() {
		return null;
	}

	public void setDocumentationName(String name) {
		// TODO Auto-generated method stub
		
	}
}
