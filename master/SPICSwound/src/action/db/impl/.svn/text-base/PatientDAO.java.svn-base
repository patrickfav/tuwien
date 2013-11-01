package db.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.IParticipationDAO;
import db.interfaces.IPatientDAO;
import entities.Patient;
import entities.Trial;
import entities.User;

@Stateless
@Name("patientDAO")
@AutoCreate
public class PatientDAO implements IPatientDAO {

	@In
	private EntityManager entityManager;

	@EJB IParticipationDAO participationDAO;

	public void persist(Patient p) {
		entityManager.persist(p);
	}

	public Patient merge(Patient patient) {
		return entityManager.merge(patient);
	}

	public Patient findByID(Long id) {
		return entityManager.find(Patient.class, id);
	}

	public void refresh(Patient p) {
		entityManager.refresh(p);
	}

	public void remove(Patient p) {
		entityManager.remove(p);
	}

	@SuppressWarnings("unchecked")
	public List<Patient> getPatientsByTrialAndUser(Trial trial, User user,
			String searchString) {

		String prepared;
		if("".equals(searchString))
			prepared = "%";
		else
			prepared = "%" + searchString + "%";

		Query q =  entityManager
				.createQuery(
						"select p from Patient as p" +
						" where p.participation.trial.id = :trial_id " +
						"AND p.hidden = false " +
						"AND p.kennnummer LIKE :searchString")
				.setParameter("trial_id", trial.getId())
				.setParameter("searchString",prepared);

		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Patient> getPatientsByTrial(Trial trial,
			String searchString, int maxResults) {

		String prepared;
		if("".equals(searchString))
			prepared = "%";
		else
			prepared = "%" + searchString.toLowerCase() + "%";

		Query q =  entityManager
				.createQuery(
						"select p from Patient as p" +
						" where p.participation.trial.id = :trial_id " +
						"AND p.hidden = false " +
						"AND lower(p.kennnummer) LIKE :searchString")
				.setParameter("trial_id", trial.getId())
				.setParameter("searchString",prepared)
				.setMaxResults(maxResults);

		return q.getResultList();
	}

	public Patient findByUserAndKennnummer(User user, String kennnummer) {
		return findByUsernameAndKennnummer(user.getUsername(), kennnummer);
	}
	

	public Patient findByUsernameAndKennnummer(String username,
			String kennnummer) {
		Query q = entityManager.createQuery("SELECT p from Patient p where p.savedBy.username = :username AND p.kennnummer = :kennnr");
		q.setParameter("username", username);
		q.setParameter("kennnr", kennnummer);
		try {
			return (Patient)q.getSingleResult();
		}catch(NoResultException e) {
			// expected result
			return null;
		}
	}

	public Date getLastModified(Patient p) {
		Query q = entityManager.createQuery("SELECT MAX(td.lastModified) FROM TrialData td WHERE td.patient.id = :patient_id");
		q.setParameter("patient_id", p.getId());

		Date result = (Date)q.getSingleResult();
		
		return result == null ? p.getRegistrationDate() : result;
	}

}
