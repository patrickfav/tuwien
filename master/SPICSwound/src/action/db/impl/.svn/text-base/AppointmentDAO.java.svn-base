package db.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.IAppointmentDAO;
import entities.Appointment;
import entities.Patient;
import entities.Trial;
import entities.User;

/**
 * @author inso
 */
@Stateless
@Name("appointmentDAO")
@AutoCreate
public class AppointmentDAO implements IAppointmentDAO {

	@In
	private EntityManager entityManager;

	public Appointment findByID(Long id) {
		return entityManager.find(Appointment.class, id);
	}

	public Appointment merge(Appointment element) {
		return entityManager.merge(element);
	}

	public void persist(Appointment element) {
		entityManager.persist(element);
	}

	public void refresh(Appointment element) {
		entityManager.refresh(element);
	}

	public void remove(Appointment element) {
		entityManager.remove(element);
	}

	@SuppressWarnings("unchecked")
	public List<Appointment> findByTitle(Trial trial, User user,
			String searchString, int maxResults) {
		searchString = searchString == null || searchString.length() == 0 ?
				"%" : "%" + searchString.toLowerCase() + "%";
		
		Query q = entityManager.createQuery("FROM Appointment a " +
				" WHERE LOWER(a.title) LIKE :searchString" +
				" AND a.trial = :trial " +
				" AND a.user = :user ");
		
		q.setParameter("searchString", searchString);
		q.setParameter("trial", trial);
		q.setParameter("user", user);
		
		q.setMaxResults(maxResults);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Appointment> getPatientAppointments(Trial trial, User user,
			Patient patient, int maxResults) {
		Query q = entityManager.createQuery("FROM Appointment a " +
				" WHERE a.trial = :trial " +
				" AND a.user = :user " +
				" AND a.patient = :patient ");
		
		q.setParameter("trial", trial);
		q.setParameter("user", user);
		q.setParameter("patient", patient);
		
		q.setMaxResults(maxResults);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Long> getPatientsWithAppointment(Trial trial, User user,
			Date startDate, Date endDate) {
		Query q = entityManager.createQuery(
				" SELECT a.patient.id FROM Appointment AS a " +
				" WHERE a.trial = :trial " +
				" AND a.user = :user " +
				" AND a.startDate >= :startDate" +
				" AND a.startDate <= :endDate " +
				" GROUP BY a.patient.id" );
		
		q.setParameter("trial", trial);
		q.setParameter("user", user);
		q.setParameter("startDate", startDate);
		q.setParameter("endDate", endDate);
		
		return q.getResultList();
	}
}
