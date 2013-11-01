package util.webservice;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.wsf.spi.annotation.WebContext;

import util.webservice.common.IWebserviceConstants;
import bean.action.PatientAction;
import entities.Patient;

/**
 * @author inso
 * @author inso
 */
@Stateless
@WebContext(contextRoot = "/SPICSWebservice")
@WebService(name = "PatientService", serviceName = "PatientService")
@Name("PatientService")
public class PatientServiceImpl extends AbstractService implements
		IPatientService {

	@In
	private PatientAction patientAction;

	@WebMethod
	public Long createPatient(@WebParam(name = "username") String username,
			@WebParam(name = "patientId") String kennnummer,
			@WebParam(name = "documentationId") Long trialId)
			throws SpicsWebserviceException {
		preValidateUser();
		if (username == null || kennnummer == null || trialId == null) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION,
					"username, patientID or trialId may not be empty");
		}

		if (!patientAction.userExists(username)) {
			throw new SpicsWebserviceException(IWebserviceConstants.USER_NOT_FOUND,
					"The user {0} can not be found", new String[] { username });
		}

		if (!patientAction.participationExists(username, trialId)) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.USER_PARTICIPATION_NOT_FOUND,
					"The participation for user {0} and trial {1} can not be found",
					username, trialId.toString());
		}

		if (patientAction.patientExists(username, kennnummer)) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.PATIENT_ALREADY_EXISTS,
					"The patient with id {0} for user {1} already exists",
					kennnummer, username);
		}

		Patient p = patientAction.createPatient(kennnummer, username, trialId);
		return p != null ? p.getId() : null;
	}

	@WebMethod
	public Long findPatient(@WebParam(name = "username") String username,
			@WebParam(name = "patientId") String kennnummer)
			throws SpicsWebserviceException {
		preValidateUser();
		if (username == null || kennnummer == null) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION,
					"username or patientID may not be empty");
		}

		return patientAction.findPatientId(username, kennnummer);
	}
}
