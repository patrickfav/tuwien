package bean;

import java.io.Serializable;

import javax.ejb.Local;

import util.Resettable;

@Local
public interface ViewPatients extends Resettable, Serializable {

	public String editPatientTrialData();

	public String findPatient();

	public void destroy();

	public void resetSearchString();

	public String getSearchString();

	public void setSearchString(String searchString);

	public String createPatient();

	public String getAddPatientID();

	public void setAddPatientID(String addPatientID);

	public int getPage();

	public void setPage(int page);

	public int getMAX_PAGES();

	public int getROW_COUNT();

	/**
	 * Load the patients with appointments in the next time.
	 */
	public void loadPatientsAppointment();

	/**
	 * Get if the patient has an appointment in the next time.
	 * @param patientId
	 *            The id of the patient.
	 * @return True if the patient has an appointment in the next time,
	 *         otherwise false.
	 */
	public boolean hasPatientAppointment(Long patientId);
}
