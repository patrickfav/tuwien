package bean;

import java.util.List;

import javax.ejb.Local;
import javax.faces.component.UISelectItems;

import util.Resettable;
import util.plugin.IPageFragment;
import entities.Patient;
import entities.TrialData;
import entities.TrialForm;

@Local
public interface ViewTrialData extends Resettable {
	
	public static final String PAGE_ID = "viewTrialData.xhtml";
	
	public void setUp();

	public void destroy();
	
	public String editTrialData(TrialData current);
	
	public String createTrialData();
	
	public String importTrialData();
	
	public String back();
	
	public String undo(TrialData td);
	
	public boolean canAddTrialData(TrialForm tf);
	
	public Patient getPatient();

	public void setPatient(Patient patient);
	
	public String showChart();
	
	public String updatePatientMetaData();
	
	public UISelectItems getChartSelectItems();
	
	public void setChartSelectItems(UISelectItems selectItems);
	
	public List<IPageFragment> getTrialDataFragments();

	/**
	 * Loads all appointments for the current patient.
	 */
	public void loadAppointments();
	
	/**
	 * Edits the selected appointment.
	 * @return The redirected page.
	 */
	public String editAppointment();
	
	/**
	 * Creates a new appointment for the current patient.
	 * @return The redirected page.
	 */
	public String createAppointment();
}
