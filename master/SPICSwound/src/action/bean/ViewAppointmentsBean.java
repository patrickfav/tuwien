package bean;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.log.Log;

import util.JSFNavigationConstants;
import db.interfaces.IAppointmentDAO;
import entities.Appointment;
import entities.Patient;
import entities.Trial;
import entities.User;

@Stateful
@Scope(ScopeType.CONVERSATION)
@Name("ViewAppointments")
public class ViewAppointmentsBean implements ViewAppointments {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;

	@EJB
	private IAppointmentDAO appointmentsDAO;

	/**
	 * Appointments table viewer.
	 */
	@SuppressWarnings("unused")
	@DataModel("appointmentsDataModel")
	private List<Appointment> appointments;

	@DataModelSelection("appointmentsDataModel")
	@Out(required = false)
	private Appointment appointment;

	private String searchString;

	private Trial trial;

	@In
	private User user;

	@In
	private SessionInfo sessionInfo;

	@Out(required = false)
	private Patient patient;

	/**
	 * Data scroller
	 */
	private int page;
	private static final int MAX_PAGES = 20;
	private static final int ROW_COUNT = 15;

	@Factory("appointmentsDataModel")
	public String findAppointments() {
		this.trial = sessionInfo.getTrial();
		log.info("Searching appointments for user #0,"
				+ " trial #1, searchString #2", user.getUsername(), trial
				.getId(), searchString);

		appointments = appointmentsDAO.findByTitle(trial, user, searchString,
				MAX_PAGES * ROW_COUNT);
		page = 1;

		return JSFNavigationConstants.RELOADPAGE;
	}

	@Begin(join = true)
	public String createAppointment() {
		reset();
		log.info("Creating new appointment for user #0, trial #1", user
				.getUsername(), trial.getId());

		appointment = new Appointment();
		appointment.setUser(user);
		appointment.setTrial(trial);

		return JSFNavigationConstants.EDITAPPOINTMENT;
	}

	@Begin(join = true)
	public String editAppointment() {
		reset();
		log.info("Editing appointment with id #0", appointment.getId());

		return JSFNavigationConstants.EDITAPPOINTMENT;
	}

	public void resetSearchString() {
		searchString = "";
		findAppointments();
	}

	public void reset() {
		searchString = null;
		appointments = null;
		page = 1;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getMAX_PAGES() {
		return MAX_PAGES;
	}

	public int getROW_COUNT() {
		return ROW_COUNT;
	}

	@Destroy
	@Remove
	public void destroy() {
		reset();
	}

	public String selectPatient() {
		patient = appointment.getPatient();
		if (patient == null) {
			log.warn("trying to edit patient but outjection parameter is null");
		} else {
			log.info("editing patient #0", patient.getKennnummer());
		}

		reset();
		return JSFNavigationConstants.EDITPATIENTTRIALDATA;
	}
}
