package bean;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;

import db.interfaces.IAppointmentDAO;
import entities.Appointment;
import entities.Patient;

@Stateful
@Scope(ScopeType.CONVERSATION)
@Name("EditAppointment")
public class EditAppointmentBean implements EditAppointment {

	@Logger
	private Log log;

	@In(required = true)
	private Appointment appointment;

	@SuppressWarnings("unused")
	@In(required = false, scope = ScopeType.CONVERSATION)
	private Patient patient;

	/**
	 * Page from where the request origins
	 */
	@RequestParameter
	private String origin;

	private String requestParm;

	/**
	 * Date and time of of the appointment. Prevents overriding each other.
	 */
	private Date date;
	private Date time;

	@In
	private IAppointmentDAO appointmentDAO;

	@In(create = true)
	private FacesMessages facesMessages;

	public void init() {
		log.info("Initializing request parm and date");
		// set the request source if it has not been set
		if (requestParm == null) {
			requestParm = origin != null ? new String(origin)
					: "viewAppointments";
			log.info("request parameter for page is #0", requestParm);
		}

		if (appointment.getStartDate() != null) {
			date = (Date) appointment.getStartDate().clone();
			time = (Date) appointment.getStartDate().clone();
		} else {
			date = null;
			time = null;
		}
	}

	/**
	 * Note: By default, the conversation will not actually be destroyed until
	 * after any redirect has occurred. Setting beforeRedirect=true specifies
	 * that the conversation should be destroyed at the end of the current
	 * request, and that the redirect will be processed in a new temporary
	 * conversation context.
	 */
	@End(beforeRedirect = true)
	public String save() {
		if (date == null || time == null) {
			facesMessages.add("dateOrTime", Severity.ERROR,
					"Date or time may not be null");
			return null;
		} else {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(time);

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date);
			cal2.set(Calendar.HOUR_OF_DAY, cal1.get(Calendar.HOUR_OF_DAY));
			cal2.set(Calendar.MINUTE, cal1.get(Calendar.MINUTE));

			appointment.setStartDate(cal2.getTime());
			log.info("Setting date and time for appointment to #0", cal2
					.getTime());
		}

		appointmentDAO.merge(appointment);
		return requestParm;
	}

	@End(beforeRedirect = true)
	public String cancel() {
		return requestParm;
	}

	@End(beforeRedirect = true)
	public String delete() {
		log.info("Removing appointment with id #0", appointment.getId());
		if (appointment.getId() != null) {
			appointmentDAO.remove(appointment);
		}
		return requestParm;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getTime() {
		return time;
	}

	@Remove
	@Destroy
	public void destroy() {
		log.info("destroyed...");
	}
}
