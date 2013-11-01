package util.webservice;

import javax.ejb.Remote;

import util.webservice.common.IWebserviceConstants;

/**
 * @author inso
 * @author inso
 */
@Remote
public interface IDocumentationDataImportService {

	/**
	 * Initializes the system to a concrete Trial - necessary to perform the
	 * lookup operations.
	 * @param trialId
	 *            The trialId used for the lookup actions.
	 * @throws SpicsWebserviceException
	 *             Exception with code
	 *             {@link IWebserviceConstants.NOT_LOGGED_IN_EXCEPTION} if the
	 *             user is not logged in. Exception with code
	 *             {@link IWebserviceConstants.INSUFFICIENT_PRIVILEGES_EXCEPTION}
	 *             if the user has insufficient privileges to perform this
	 *             action. Exception with code
	 *             {@link IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION} if
	 *             trialId is null. Exception with code
	 *             {@link IWebserviceConstants.TRIAL_NOT_FOUND} if the trial
	 *             with the specified id does not exist.
	 */
	public void setDocumentationId(Long trialId)
			throws SpicsWebserviceException;

	/**
	 * This method performs the lookup operation for a TrialForm. Given its name
	 * it returns a systemwide unique ID. It <b>assumes</b> the names of the
	 * forms of one Trial to be unique.
	 * @param trialFormName
	 *            The name of the Form
	 * @return The ID of the Form
	 * @throws SpicsWebserviceException
	 *             Exception with code
	 *             {@link IWebserviceConstants.NOT_LOGGED_IN_EXCEPTION} if the
	 *             user is not logged in. Exception with code
	 *             {@link IWebserviceConstants.INSUFFICIENT_PRIVILEGES_EXCEPTION}
	 *             if the user has insufficient privileges to perform this
	 *             action. Exception with code
	 *             {@link IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION} if
	 *             trialFormName is null.
	 */
	public Long lookupDocumentationFormId(String trialFormName)
			throws SpicsWebserviceException;

	/**
	 * Performs the lookup for a specific Attribute given the ID of the form
	 * that contains the Attribute as well as the Names of the Attribute itself
	 * and the AttributeGroup that contains it.
	 * @param trialFormId
	 *            The ID of the form
	 * @param attributeGroupName
	 *            The name of the AttributeGroup
	 * @param attributeName
	 *            The name of the Attribute
	 * @return the ID of the Attribute, if found
	 * @throws SpicsWebserviceException
	 *             Exception with code
	 *             {@link IWebserviceConstants.NOT_LOGGED_IN_EXCEPTION} if the
	 *             user is not logged in. Exception with code
	 *             {@link IWebserviceConstants.INSUFFICIENT_PRIVILEGES_EXCEPTION}
	 *             if the user has insufficient privileges to perform this
	 *             action. Exception with code
	 *             {@link IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION} if
	 *             trialFormId, attributeGroupName or attributeName is null.
	 *             Exception with code
	 *             {@link IWebserviceConstants.TRIAL_FORM_NOT_FOUND} if the
	 *             trial form with the specified id does not exist.
	 */
	public Long lookupAtrributeId(Long trialFormId, String attributeGroupName,
			String attributeName) throws SpicsWebserviceException;

	/**
	 * Creates a new TrialData instance, associates it with the given patient
	 * and trialform and persists it to the database. It assumes that the user
	 * that should be associated with it should be the same as the creator of
	 * the patient. Both Patient and Form have to exist.
	 * @param patientId
	 *            The patient id
	 * @param trialFormId
	 *            The trial form id
	 * @return The ID of the newly created TrialData
	 * @throws SpicsWebserviceException
	 *             Exception with code
	 *             {@link IWebserviceConstants.NOT_LOGGED_IN_EXCEPTION} if the
	 *             user is not logged in. Exception with code
	 *             {@link IWebserviceConstants.INSUFFICIENT_PRIVILEGES_EXCEPTION}
	 *             if the user has insufficient privileges to perform this
	 *             action. Exception with code
	 *             {@link IWebserviceConstants.PATIENT_NOT_FOUND} if the patient
	 *             with the specified id does not exist. Exception with code
	 *             {@link IWebserviceConstants.TRIAL_FORM_NOT_FOUND} if the
	 *             trial form with the specified id does not exist. Exception
	 *             with code
	 *             {@link IWebserviceConstants.CREATE_TRIAL_DATA_NOT_ALLOWED} if
	 *             it is not allowed to create the trial data.
	 */
	public Long createDocumentationData(Long patientId, Long trialFormId)
			throws SpicsWebserviceException;

	/**
	 * This is a convenience method that in addition to performing the creation
	 * of the TrialData a patient lookup is performed. Otherwise this method is
	 * identically to the combination of findPatient(username, kennnummer) and
	 * createTrialData(patientId, trialFormId) <br>
	 * <i>Note: method renamed because of bug JBWS-1799</i>
	 * @param username
	 *            The username
	 * @param kennnummer
	 *            The kennnummer of the patient
	 * @param trialFormId
	 *            The trial form id
	 * @return The ID of the newly created TrialData
	 * @throws SpicsWebserviceException
	 *             {@link IDocumentationDataImportService.createDocumentationData}
	 */
	public Long createDocumentationDataPatient(String username,
			String kennnummer, Long trialFormId)
			throws SpicsWebserviceException;

	/**
	 * Adds a value to the trial.
	 * @param trialDataId
	 *            The trial data id.
	 * @param attributeId
	 *            The attribute id.
	 * @param value
	 *            The serializable value object.
	 * @throws SpicsWebserviceException
	 *             Exception with code
	 *             {@link IWebserviceConstants.NOT_LOGGED_IN_EXCEPTION} if the
	 *             user is not logged in. Exception with code
	 *             {@link IWebserviceConstants.INSUFFICIENT_PRIVILEGES_EXCEPTION}
	 *             if the user has insufficient privileges to perform this
	 *             action. Exception with code
	 *             {@link IWebserviceConstants.DOCU_DATA_NOT_FOUND} if the trial
	 *             data with the specified id does not exist. Exception with
	 *             code {@link IWebserviceConstants.ATTRIBUTE_NOT_FOUND} if the
	 *             attribute with the specified id does not exist. Exception
	 *             with code {@link IWebserviceConstants.VALUE_ALREADY_EXISTS}
	 *             if a value for the specified documentation data and attribute
	 *             already exists. Exception with code
	 *             {@link IWebserviceConstants.VALUE_NOT_SERIALIZABLE} if value
	 *             does not implement the Serializable interface.
	 */
	public void addValue(Long trialDataId, Long attributeId, Object value)
			throws SpicsWebserviceException;

}
