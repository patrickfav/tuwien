package util.webservice;

import javax.ejb.Remote;

import util.webservice.common.IWebserviceConstants;

/**
 * @author inso
 * @author inso
 */
@Remote
public interface IPatientService {

	/**
	 * Looks up a patient by the unique combination of the users username and
	 * the character based id
	 * @param username
	 *            The username of the user that created the patient
	 * @param kennnummer
	 *            The characterbased ID of the patient
	 * @return Unique patient ID - the database ID of the patient - systemwide
	 *         unqiue. 0, if no patient has been found.
	 * @throws SpicsWebserviceException
	 *             Exception with code
	 *             {@link IWebserviceConstants.NOT_LOGGED_IN_EXCEPTION} if the
	 *             user is not logged in. Exception with code
	 *             {@link IWebserviceConstants.INSUFFICIENT_PRIVILEGES_EXCEPTION}
	 *             if the user has insufficient privileges to perform this
	 *             action. Exception with code
	 *             {@link IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION} if
	 *             the username or patientId is null.
	 */
	public Long findPatient(String username, String kennnummer)
			throws SpicsWebserviceException;

	/**
	 * Creates a patient for the user with the given character based ID and
	 * associates it with the trial. The combination of username and patientID
	 * has to be unique.
	 * @param username
	 *            Username of the user that should be saved as the patients
	 *            creator
	 * @param kennnummer
	 *            The character based ID the patient should have
	 * @param trialId
	 *            TrialId of the trial the patient should be associated with
	 * @return the systemwide unique ID of the patient
	 * @throws SpicsWebserviceException
	 *             Exception with code
	 *             {@link IWebserviceConstants.NOT_LOGGED_IN_EXCEPTION} if the
	 *             user is not logged in. Exception with code
	 *             {@link IWebserviceConstants.INSUFFICIENT_PRIVILEGES_EXCEPTION}
	 *             if the user has insufficient privileges to perform this
	 *             action. Exception with code
	 *             {@link IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION} if
	 *             the username, patientID or trialId is null. Exception with
	 *             code {@link IWebserviceConstants.USER_NOT_FOUND} if a user
	 *             for the specified username does not exist. Exception with
	 *             code
	 *             {@link IWebserviceConstants.USER_PARTICIPATION_NOT_FOUND} if
	 *             the user is not participated in the trial. Exception with
	 *             code {@link IWebserviceConstants.PATIENT_ALREADY_EXISTS} if a
	 *             patient with the specified patientID already exists.
	 */
	public Long createPatient(String username, String kennnummer, Long trialId)
			throws SpicsWebserviceException;
}
