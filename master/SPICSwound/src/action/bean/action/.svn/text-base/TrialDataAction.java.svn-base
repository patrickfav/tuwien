package bean.action;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import util.SpicsPermissions;
import db.interfaces.IPatientDAO;
import db.interfaces.ITrialDataDAO;
import db.interfaces.ITrialFormDAO;
import entities.Patient;
import entities.TrialData;
import entities.TrialForm;
import entities.User;

/**
 * @author inso
 * @author inso
 */
@Name("trialDataAction")
@Scope(ScopeType.STATELESS)
@AutoCreate
public class TrialDataAction {

	@Logger
	private Log log;

	@In
	private User user;

	@In(create = true)
	private ITrialDataDAO trialDataDAO;

	@In
	private ITrialFormDAO trialFormDAO;

	@In
	private IPatientDAO patientDAO;

	/**
	 * Creates the trial data for the patient and trial form.
	 * @param patientId
	 *            The patient id
	 * @param trialFormId
	 *            The trial form id
	 * @return The id of the created trial data
	 */
	public Long createTrialData(Long patientId, Long trialFormId) {
		log.info("creating new TrialData with patientID #0 and trialFormID #1",
				patientId, trialFormId);

		Patient patient = patientDAO.findByID(patientId);
		if (patient == null) {
			return null;
		}

		TrialForm trialForm = trialFormDAO.findByID(trialFormId);
		if (trialForm == null) {
			return null;
		}

		// check if we are allowed to add the trial data
		if (!canAddTrialData(trialForm, patient)) {
			return null;
		}

		TrialData trialData = new TrialData();
		trialData.setSavedBy(user);
		trialData.setLastModifiedBy(user);
		trialData.setPatient(patient);
		trialData.setLastModified(new Date(System.currentTimeMillis()));
		trialData.setSavedOn(new Date(System.currentTimeMillis()));
		trialData.setTrialform(trialForm);

		trialForm.getTrialDatas().add(trialData);
		trialDataDAO.persist(trialData);

		patient.getTrialdatas().add(trialData);

		return trialData.getId();
	}

	/**
	 * Checks if a trial data for the specified id exists.
	 * @param trialDataId
	 *            The trial data id
	 * @return True if a trial data exists, otherwise false
	 */
	public boolean trialDataExists(Long trialDataId) {
		return getTrialData(trialDataId) != null;
	}

	/**
	 * Get the trial data for the specified id.
	 * @param trialDataId
	 *            The trial data id
	 * @return The found trial data, or null if no trial data for the specified
	 *         id exists.
	 */
	public TrialData getTrialData(Long trialDataId) {
		return trialDataDAO.findByID(trialDataId);
	}

	/**
	 * Checks if it is possible to create the trial data.
	 * @param tf
	 * @param patient
	 * @return
	 */
	public boolean canAddTrialData(TrialForm tf, Patient patient) {
		try {
			validateAddTrialData(tf, patient);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Validates if it is possible to create the trial data. Throws an exception
	 * if any constraint is violated.
	 * @param tf
	 * @param patient
	 * @throws Exception
	 */
	public void validateAddTrialData(TrialForm tf, Patient patient)
			throws Exception {
		// log.info("validating add trial data: fillable=#0", tf.isFillable());
		if (!tf.isFillable()) {
			// trial form can not be filled
			throw new Exception("Form is not fillable");
		}

		// log.info("validating patient user: patient.savedUser=#0, user=#1",
		// patient.getSavedBy().getUsername(), user.getUsername());
		if (!(patient.getSavedBy().getUsername().equals(user.getUsername()) || Identity
				.instance().hasPermission(tf.getTrial(),
						SpicsPermissions.EDIT_TRIAL_DATA))) {
			// patient has been created from a different user or no permissions
			throw new Exception(
					"Patient has not been created from current user"
							+ " or current user has insufficient permissions");
		}

		Boolean canFill = trialFormDAO.canFillTrialForm(tf.getId(), patient
				.getKennnummer());
		// log.info("validating can fill trial form: canFill=#0", canFill);
		if (!canFill) {
			// trial form can only be filled once
			throw new Exception("Form can only filled once");
		}
	}
}
