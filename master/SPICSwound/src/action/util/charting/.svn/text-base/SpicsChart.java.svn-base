package util.charting;

import java.awt.Dimension;
import java.io.Serializable;

import org.jfree.chart.JFreeChart;

import entities.Patient;

public interface SpicsChart extends Serializable {

	/**
	 * Generate the chart
	 * 
	 * @return
	 */
	public JFreeChart getChart(ChartProcessor chartProcessor);

	/**
	 * The short name of the chart
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * The long description of the chart
	 * 
	 * @return
	 */
	public String getDescription();
	
	public void setDocumentationName(String name);

	public void setPatient(Patient patient);

	/**
	 * This method must perform all the initialization work. It will be called
	 * by the charting repository at startup time.
	 * 
	 * @param chartRepository
	 *            reference to the chart repository. this reference allows the
	 *            loaded chart to access methods of the repository (like
	 *            'findTrialForm')
	 * @throws ChartingException
	 *             if the initialization wasn't successful
	 */
	public void init(ChartRepository chartRepository) throws ChartingException;
	
	public Dimension getPreferredDimension();
}
