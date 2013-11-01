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
import bean.SessionInfo;
import db.interfaces.IParticipationDAO;
import db.interfaces.IPatientDAO;
import db.interfaces.IUserDAO;
import entities.OrgGroup;
import entities.Participation;
import entities.Patient;
import entities.User;

/**
 * @author inso
 * @author inso
 */
@Name("patientAction")
@Scope(ScopeType.STATELESS)
@AutoCreate
public class PatientAction {

	@In
	private IPatientDAO patientDAO;

	@In
	private IUserDAO userDAO;

	@In
	private IParticipationDAO participationDAO;

	@In(required = false)
	private OrgGroup group;

	@In(required = false)
	private User user;

	@In(required = false)
	private SessionInfo sessionInfo;

	@Logger
	private Log log;

	public Patient createPatient(String patientID) {
		return createPatient(patientID, user.getUsername(), sessionInfo
				.getTrialID());
	}

	/**
	 * Creates a new patient with the specified patientID, username and trialId.
	 * @param patientID
	 *            The id for the new patient.
	 * @param username
	 *            The username for which the patient should be created.
	 * @param trialId
	 *            The id of the trial for which the patient should be added.
	 * @return The new created patient, or null if the username or trialId was
	 *         wrong, or the patient already exists.
	 */
	public Patient createPatient(String patientID, String username, Long trialId) {
		log.info("creating patient with id #0 for user #1 and trial #2",
				patientID, username, trialId);

		this.user = getUser(username);
		// no user found
		if (user == null) {
			log.info("username #0 not found", username);
			return null;
		}

		this.group = user.getGroups().get(0);

		// check if patient already exists
		if (patientExists(username, patientID)) {
			log.info(
					"patient with patientId #0 and username #1 already exists",
					patientID, username);
			return null;
		}

		Participation part = getParticipation(username, trialId);
		// no participation found
		if (part == null) {
			log.info("no participation for username #0 and trialId #1 found",
					username, trialId);
			return null;
		}

		Patient p = new Patient();
		p.setKennnummer(patientID);
		p.setRegistrationDate(new Date(System.currentTimeMillis()));
		p.setSavedBy(user);
		p.getGroups().add(group);
		p.setParticipation(part);
		part.getPatients().add(p);

		patientDAO.persist(p);

		group.getPatients().add(p);

		SpicsPermissions.instance().grantPatientOwner(p,
				Identity.instance().getPrincipal());

		log.info("successfully created patient with database id #0", p.getId());

		return p;
	}

	/**
	 * Get the user with the specified id.
	 * @param username
	 * @return
	 */
	public User getUser(String username) {
		return userDAO.findByID(username);
	}

	/**
	 * Checks if the user with the specified id exists.
	 * @param username
	 * @return
	 */
	public boolean userExists(String username) {
		return getUser(username) != null;
	}

	/**
	 * Get the patient with the specified username for the user.
	 * @param username
	 * @param kennnummer
	 * @return
	 */
	public Patient getPatient(String username, String kennnummer) {
		return patientDAO.findByUsernameAndKennnummer(username, kennnummer);
	}

	/**
	 * Get the patient with the specified id.
	 * @param patientId
	 * @return
	 */
	public Patient getPatient(Long patientId) {
		return patientDAO.findByID(patientId);
	}

	/**
	 * Checks if the patient already exists.
	 * @param username
	 * @param kennnummer
	 * @return
	 */
	public boolean patientExists(String username, String kennnummer) {
		return getPatient(username, kennnummer) != null;
	}

	/**
	 * Get the participation for the user.
	 * @param username
	 * @param trialId
	 * @return
	 */
	public Participation getParticipation(String username, Long trialId) {
		return participationDAO.findByUsernameAndTrialId(username, trialId);
	}

	/**
	 * Checks if a participation for the user exists.
	 * @param username
	 * @param trialId
	 * @return
	 */
	public boolean participationExists(String username, Long trialId) {
		return getParticipation(username, trialId) != null;
	}

	/**
	 * Get the patient with the specified id.
	 * @param username
	 * @param kennnummer
	 * @return
	 */
	public Long findPatientId(String username, String kennnummer) {
		log.info("find patientID called for kennnummer #0 and username #1",
				kennnummer, username);

		Patient p = patientDAO
				.findByUsernameAndKennnummer(username, kennnummer);
		return p != null ? p.getId() : null;
	}
}
