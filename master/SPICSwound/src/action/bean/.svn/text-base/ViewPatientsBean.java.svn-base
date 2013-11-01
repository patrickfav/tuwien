package bean;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import util.BeanResetter;
import util.JSFNavigationConstants;
import util.SpicsPermissions;
import bean.action.PatientAction;
import db.interfaces.IAppointmentDAO;
import db.interfaces.IPatientDAO;
import entities.OrgGroup;
import entities.Patient;
import entities.Trial;
import entities.User;

@Stateful
@Scope(ScopeType.SESSION)
@Name("ViewPatients")
public class ViewPatientsBean implements ViewPatients {

	private static final long serialVersionUID = 1L;

	public static final String BEANNAME = "ViewPatients";

	@In
	private IPatientDAO patientDAO;
	@In
	private PatientAction patientAction;

	@Logger
	private Log log;

	@DataModel("patientsDataModel")
	private List<Patient> patients;
	
	@DataModelSelection("patientsDataModel")
	@Out(required = false)
	private Patient patient;

	private String searchString;
	private String addPatientID;

	@In
	private OrgGroup group;

	private Trial trial;
	@In
	private User user;

	@In
	private SessionInfo sessionInfo;

	@In(required = false)
	private BeanResetter beanResetter;

	@SuppressWarnings("unused")
	@Out(required = false)
	private Boolean resetTDViewer;

	// for rich:dataScroller
	private int page;
	private static final int MAX_PAGES = 20;
	private static final int ROW_COUNT = 15;
	
	/**
	 * Patients with appointments
	 */
	private Set<Long> patientsAppointment;
	
	@In
	private IAppointmentDAO appointmentDAO;
	
	@Factory("patientsDataModel")
	public String findPatient() {

		beanResetter.addGridBean(this);

		this.trial = sessionInfo.getTrial();

		log.info("ViewPatientsBean: findPatients called, searchString: "
				+ searchString);

		if (StringUtils.isBlank(searchString)) { // no search String entered
			patients = patientDAO.getPatientsByTrial(trial, "", MAX_PAGES * ROW_COUNT);
		} else {
			patients = patientDAO.getPatientsByTrial(trial,	searchString, MAX_PAGES * ROW_COUNT);
			
			if (patients.size() == 1
					&& patients.get(0).getKennnummer().equals(searchString)) {
				// only if one patient found, and his kennnummer is the whole
				// search string (kennnummer)
				patient = patients.get(0);

				// exactly one patient with the specified id found! forward if
				// we can complete trialforms
				if (sessionInfo.getTrial().isFillable())
					return editPatientTrialData();
			}
		}
		
		Identity.instance().filterByPermission(patients, SpicsPermissions.VIEW_PATIENT);
		
		return JSFNavigationConstants.RELOADPAGE;
	}
	
	public void resetSearchString() {
		searchString = "";
		findPatient();
	}

	public String createPatient() {

		if (StringUtils.isBlank(addPatientID)) {
			log.warn("not creating empty patient");
			FacesMessages.instance().addFromResourceBundle(Severity.ERROR,
					"error.noemptypatient");
			return JSFNavigationConstants.RELOADPAGE;
		}

		if (patientDAO.findByUserAndKennnummer(user, addPatientID) != null) {
			log.warn("cannot create patient, id already exists!");
			FacesMessages.instance().addFromResourceBundle(Severity.ERROR,
					"error.patientexists");
			return JSFNavigationConstants.RELOADPAGE;
		}

		this.patient = patientAction.createPatient(addPatientID);

		searchString = addPatientID;
		addPatientID = null;
		findPatient();

		FacesMessages.instance().addFromResourceBundle(Severity.INFO,
				"patientcreated.info");
		return JSFNavigationConstants.RELOADPAGE;

	}

	@RaiseEvent("patientChanged")
	public String editPatientTrialData() {
		if (patient == null)
			log.warn("Trying to edit patient but outjection parameter is null");
		else
			log.info("Editing patient: " + patient.getKennnummer());

		reset();
		resetTDViewer = true;
		searchString = null;
		addPatientID = null;
		return JSFNavigationConstants.EDITPATIENTTRIALDATA;
	}

	public void reset() {
		searchString = null;
		patients = null;
		addPatientID = null;
		page = 1;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getAddPatientID() {
		return addPatientID;
	}

	public void setAddPatientID(String addPatientID) {
		this.addPatientID = addPatientID;
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
	
	public void loadPatientsAppointment() {
		// load patients with upcoming appointment
		log.info("loading patient id's with appointments...");
		patientsAppointment = new HashSet<Long>();
		patientsAppointment.addAll(
				appointmentDAO.getPatientsWithAppointment(
						trial, user, new Date(), DateUtils.addDays(new Date(), 1)));
	}
	
	public boolean hasPatientAppointment(Long patientId) {
		return patientsAppointment != null 
			&& patientsAppointment.contains(patientId);
	}
}
