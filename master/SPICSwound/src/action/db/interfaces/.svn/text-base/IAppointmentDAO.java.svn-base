package db.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import entities.Appointment;
import entities.Patient;
import entities.Trial;
import entities.User;

/**
 * @author inso
 */
@Local
public interface IAppointmentDAO extends IGenericDAO<Appointment, Long> {

	/**
	 * Find all appointments containing the specified search string within the
	 * title.
	 * @param trial
	 *            The trial of the appointments.
	 * @param user
	 *            The user of the appointments.
	 * @param searchString
	 *            The search string which has to be contained in the title.
	 * @param maxResults
	 *            Maximum number of found appointments.
	 * @return A list of found appointments.
	 */
	public List<Appointment> findByTitle(Trial trial, User user,
			String searchString, int maxResults);

	/**
	 * Get all appointments for the specified user.
	 * @param trial
	 *            The trial of the appointments.
	 * @param user
	 *            The user of the appointments.
	 * @param patient
	 *            The patient of the appointments.
	 * @param maxResults
	 *            Maximum number of found appointments.
	 * @return A list of appointments.
	 */
	public List<Appointment> getPatientAppointments(Trial trial, User user,
			Patient patient, int maxResults);

	/**
	 * Get a list of patient id's (not the character based id) for which an
	 * appointment within the specified start- and end-date exists.
	 * @param trial
	 *            The trial of the appointments.
	 * @param user
	 *            The user of the appointments.
	 * @param startDate
	 *            The start date.
	 * @param endDate
	 *            The end date.
	 * @return List of patient id's
	 */
	public List<Long> getPatientsWithAppointment(Trial trial, User user,
			Date startDate, Date endDate);
}
