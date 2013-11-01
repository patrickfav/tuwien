package util.webservice;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.wsf.spi.annotation.WebContext;

import util.webservice.common.IWebserviceConstants;
import bean.action.FormLookupAction;
import bean.action.PatientAction;
import bean.action.TrialDataAction;
import bean.action.ValueAction;
import entities.Patient;
import entities.TrialForm;

/**
 * @author inso
 * @author inso
 */
@Stateless
@WebContext(contextRoot = "/SPICSWebservice")
@WebService(name = "DocumentationDataImportService", serviceName = "DocumentationDataImportService")
@Name("DocumentationDataImportService")
public class DocumentationDataImportServiceImpl extends AbstractService
		implements IDocumentationDataImportService {

	@In(create = true)
	private FormLookupAction formLookupAction;

	@In(create = true)
	private TrialDataAction trialDataAction;

	@In(create = true)
	private PatientAction patientAction;

	@In(create = true)
	private ValueAction valueAction;

	@WebMethod
	public void setDocumentationId(
			@WebParam(name = "documentationId") Long trialId)
			throws SpicsWebserviceException {
		preValidateUser();
		if (trialId == null) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION,
					"documentationId may not be empty");
		}
		if (!formLookupAction.trialExists(trialId)) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.DOCU_NOT_FOUND,
					"The documentation with id {0} can not be found", trialId
							.toString());
		}

		formLookupAction.setTrialId(trialId);
	}

	@WebMethod
	public Long lookupDocumentationFormId(
			@WebParam(name = "documentationFormName") String trialFormName)
			throws SpicsWebserviceException {
		preValidateUser();
		if (trialFormName == null) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION,
					"documentationFormName may not be empty");
		}

		return formLookupAction.lookupTrialForm(trialFormName);
	}

	@WebMethod
	public Long lookupAtrributeId(
			@WebParam(name = "documentationFormId") Long trialFormId,
			@WebParam(name = "attributeGroupName") String attributeGroupName,
			@WebParam(name = "attributeName") String attributeName)
			throws SpicsWebserviceException {
		preValidateUser();
		if (trialFormId == null || attributeGroupName == null
				|| attributeName == null) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION,
					"documentationFormId, attributeGroupName and attributeName may not be empty");
		}
		if (!formLookupAction.trialFormExists(trialFormId)) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.DOCU_FORM_NOT_FOUND,
					"The documentationForm with id {0} can not be found",
					trialFormId.toString());
		}

		return formLookupAction.lookupAttribute(trialFormId,
				attributeGroupName, attributeName);
	}

	@WebMethod(operationName = "createDocumentationDataPatientId")
	public Long createDocumentationData(
			@WebParam(name = "patientId") Long patientId,
			@WebParam(name = "documentationFormId") Long trialFormId)
			throws SpicsWebserviceException {
		preValidateUser();
		if (patientId == null || trialFormId == null) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION,
					"patientId and documentationFormId may not be empty");
		}
		validateCreateTrialData(patientId, trialFormId);

		return trialDataAction.createTrialData(patientId, trialFormId);
	}

	@WebMethod(operationName = "createDocumentationDataUserPatient")
	public Long createDocumentationDataPatient(
			@WebParam(name = "username") String username,
			@WebParam(name = "patientId") String kennnummer,
			@WebParam(name = "documentationFormId") Long trialFormId)
			throws SpicsWebserviceException {
		preValidateUser();
		if (username == null || kennnummer == null || trialFormId == null) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION,
					"username, patientId and documentationFormId may not be empty");
		}

		Long patientId = patientAction.findPatientId(username, kennnummer);
		if (patientId == null) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.PATIENT_NOT_FOUND,
					"The patient with id {0} can not be found for the user with id {1}",
					kennnummer, username);
		}
		validateCreateTrialData(patientId, trialFormId);

		return trialDataAction.createTrialData(patientId, trialFormId);
	}

	private void validateCreateTrialData(Long patientId, Long trialFormId)
			throws SpicsWebserviceException {
		Patient patient = patientAction.getPatient(patientId);
		if (patient == null) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.PATIENT_NOT_FOUND,
					"The patient with id {0} can not be found", patientId
							.toString());
		}
		TrialForm trialForm = formLookupAction.getTrialForm(trialFormId);
		if (trialForm == null) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.DOCU_FORM_NOT_FOUND,
					"The documentationForm with id {0} can not be found",
					trialFormId.toString());
		}
		try {
			trialDataAction.validateAddTrialData(trialForm, patient);
		} catch (Exception e) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.CREATE_DOCU_DATA_NOT_ALLOWED, e
							.getMessage());
		}
	}

	@WebMethod
	public void addValue(
			@WebParam(name = "documentationDataId") Long trialDataId,
			@WebParam(name = "attributeId") Long attributeId,
			@WebParam(name = "value") Object value)
			throws SpicsWebserviceException {
		preValidateUser();
		if (trialDataId == null || attributeId == null || value == null) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION,
					"documentationDataId, attributeId and value may not be empty");
		}
		if (!trialDataAction.trialDataExists(trialDataId)) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.DOCU_DATA_NOT_FOUND,
					"The documentationData with id {0} can not be found",
					trialDataId.toString());
		}
		if (!formLookupAction.attributeExists(attributeId)) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.ATTRIBUTE_NOT_FOUND,
					"The attribute with id {0} can not be found", attributeId
							.toString());
		}
		if (valueAction.valueExists(attributeId, trialDataId)) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.VALUE_ALREADY_EXISTS,
					"There exists already a value for the attribute with id {0} and documentation data with id {1}",
					attributeId.toString(), trialDataId.toString());
		}
		if (!(value instanceof Serializable)) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.VALUE_NOT_SERIALIZABLE,
					"Only serializable values are allowed");
		}

		// workaround as JAXB does not support interface parameter types!
		Serializable serialValue = (Serializable) value;
		valueAction
				.createAndPersistValue(attributeId, trialDataId, serialValue);
	}
}
