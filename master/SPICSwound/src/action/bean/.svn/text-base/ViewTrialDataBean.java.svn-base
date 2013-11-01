package bean;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
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
import util.charting.ChartRepository;
import util.charting.SpicsChart;
import util.plugin.IPageFragment;
import util.plugin.IPluginRegistry;
import db.interfaces.IAppointmentDAO;
import db.interfaces.IPatientDAO;
import db.interfaces.ITrialDataDAO;
import db.interfaces.ITrialFormDAO;
import entities.Appointment;
import entities.Patient;
import entities.Trial;
import entities.TrialData;
import entities.TrialForm;
import entities.User;

@Stateful
@Scope(ScopeType.SESSION)
@Name("ViewTrialData")
public class ViewTrialDataBean implements ViewTrialData {

	private static final long serialVersionUID = 1L;
	public static final String BEANNAME = "ViewTrialData";

	@EJB
	private ITrialDataDAO trialDataDAO;

	@EJB
	private ITrialFormDAO trialFormDAO;
	
	@EJB
	private IPatientDAO patientDAO;

	@In
	private IAppointmentDAO appointmentDAO;

	@Logger
	private Log log;

	@In(required = false)
	@Out(required = false)
	private Patient patient;

	@In
	private User user;
	
	@In("PluginRegistry")
	private IPluginRegistry pluginRegistry;

	private Trial trial;

	@In
	private SessionInfo sessionInfo;

	@In
	private BeanResetter beanResetter;
	
	@SuppressWarnings("unused")
	@Out(required = false)
	private Long undoTDId;

	@Out(required = false)
	private Long editTrialDataID;

	@DataModel("trialFormsView")
	private List<TrialForm> trialFormsView;

	@DataModelSelection("trialFormsView")
	private TrialForm tf;

	@SuppressWarnings("unused")
	@Out(required = false)
	private Boolean resetTDEditor;

	/**
	 * Appointments table viewer.
	 */
	@SuppressWarnings("unused")
	@DataModel("patientAppointmentsDataModel")
	private List<Appointment> appointments;

	@DataModelSelection("patientAppointmentsDataModel")
	@Out(required = false)
	private Appointment appointment;
	
	@Factory("trialFormsView")
	public void setUp() {
		log.info("setup called... setting up trialdataviewer for trail #0 and patient #1", sessionInfo.getTrialID(), patient.getKennnummer());
		trial = sessionInfo.getTrial();
		
		trialFormsView = new LinkedList<TrialForm>();

		for (TrialForm tmpTF : trial.getTrialForms()) { // needed so we can
			// access only the
			// patients trialdata
			tmpTF.setWrapperPatient(patient);
			log.info("trialform #0 has #1 trialdatas", tmpTF.getName(), tmpTF
					.getPatientTrialData().size());
			if (tmpTF.getTrialSpecific())
				continue;	// ignore - just for legacy support!
			else {
				trialFormsView.add(tmpTF);
			}
		}

		Collections.sort(trialFormsView);

		beanResetter.addGridBean(this); // register to be reset by mainMenu

	}

	@Remove
	@Destroy
	public void destroy() {
		log.info("ViewTrialDataBean destroyed...");
	}

	@End
	public String editTrialData(TrialData current) {
		this.editTrialDataID = current.getId();
		log.info("Editing trialdata with id #0", editTrialDataID);
		resetTDEditor = true;
		trialFormsView = null;
		return JSFNavigationConstants.EDITTRIALDATA;
	}

	public String createTrialData() {
		log.info("creating new trialData for " + tf.getName());

		this.editTrialDataID = createTrialData(tf).getId();
		log.info("new trialdata got id #0", editTrialDataID);

		resetTDEditor = true;
		trialFormsView = null;
		return JSFNavigationConstants.EDITTRIALDATA;
	}

	@End
	public String importTrialData() {
		log.info("import new trialData for " + tf.getName());

		this.editTrialDataID = createTrialData(tf).getId();
		log.info("new trialdata got id #0", editTrialDataID);

		resetTDEditor = true;
		trialFormsView = null;
		return JSFNavigationConstants.IMPORTTRIALDATA;
	}


	public void reset() {
		log.info("resetting...");
		trialFormsView = null;
	}

	@End
	public String back() {
		trialFormsView = null;
		return JSFNavigationConstants.BACK2VIEWPATIENTS;
	}

	@End
	public String undo(TrialData td) {
		undoTDId = td.getId();
		resetTDEditor = true;
		trialFormsView = null;
		return JSFNavigationConstants.UNDOTRIALDATA;
	}

	private TrialData createTrialData(TrialForm tf) {
		TrialData td = new TrialData();
		td.setSavedBy(user);
		td.setLastModifiedBy(user);
		td.setPatient(patient);
		td.setLastModified(new Date(System.currentTimeMillis()));
		td.setSavedOn(new Date(System.currentTimeMillis()));
		td.setTrialform(tf);

		tf.getTrialDatas().add(td);

		trialDataDAO.persist(td);

		patient.getTrialdatas().add(td);

		return td;
	}

	public boolean canAddTrialData(TrialForm tf) {
		if (!tf.isFillable())
			return false;

		if (!(patient.getSavedBy().getUsername().equals(user.getUsername()) || Identity
				.instance().hasPermission(tf.getTrial(),
						SpicsPermissions.EDIT_TRIAL_DATA))) {
			return false;
		}

		if (!tf.getFillOnce())
			return true;

		return trialFormDAO.canFillTrialForm(tf.getId(), patient
				.getKennnummer());
	}
	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		log.info("Patient set to #1", patient.getKennnummer());
		this.patient = patient;
	}
	
	@End
	public String showChart() {
		trialFormsView = null;
		return JSFNavigationConstants.SHOWCHART;
	}
	
		
	private String getUid() {
		return FacesContext.getCurrentInstance().getViewRoot().createUniqueId();
	}

	public UISelectItems getChartSelectItems() {
		UISelectItems items = new UISelectItems();
		items.setId(getUid());
		LinkedList<SelectItem> selectList = new LinkedList<SelectItem>();

		ChartRepository cr = (ChartRepository) Component.getInstance("chartRepository");
		Map<String, SpicsChart> chartMap =  cr.getChartMap().get(sessionInfo.getTrial().getName());
		
		if (chartMap == null || chartMap.size() == 0) {
			//SelectItem item = new SelectItem("");
			//selectList.add(item);
		} else {

			for (String c : chartMap.keySet()) {
				SelectItem item = new SelectItem();
				item.setLabel(c);
				item.setValue(c);
				selectList.add(item);
			}
		}
		items.setValue(selectList);
		return items;
	}

	// required by JSF 1.2
	public void setChartSelectItems(UISelectItems selectItems) {
	}
	
	public String updatePatientMetaData() {
		log.info("merging patient meta data");
		patientDAO.merge(this.patient);
		FacesMessages.instance().addFromResourceBundle(Severity.INFO, "patientEdit.info");
		return JSFNavigationConstants.RELOADPAGE;
	}
	
	public List<IPageFragment> getTrialDataFragments() {
		return pluginRegistry.getPageFragments(PAGE_ID);
	}
	
	@Observer("patientChanged") // necessary hack to get patient change notification
	@Factory("patientAppointmentsDataModel")
	public void loadAppointments() {
		Trial trial = sessionInfo.getTrial();
		log.info("Searching appointments for user #0,"
				+ " trial #1, patient #2", user != null ? user.getUsername() : null, 
				trial != null ? trial.getId() : null, 
				patient != null ? patient.getKennnummer() : null);

		appointments = appointmentDAO.getPatientAppointments(trial, user, patient,
				Integer.MAX_VALUE);
	}

	@Begin(join = true)
	public String editAppointment() {
		appointments = null; // force reload
		log.info("Editing appointment with id #0", appointment.getId());

		return JSFNavigationConstants.EDITAPPOINTMENT;
	}

	@Begin(join = true)
	public String createAppointment() {
		appointments = null; // force reload
		Trial trial = sessionInfo.getTrial();
		log.info("Creating new appointment for user #0, trial #1, patient #2", user
				.getUsername(), trial.getId(), patient.getKennnummer());

		appointment = new Appointment();
		appointment.setUser(user);
		appointment.setTrial(trial);
		appointment.setPatient(patient);
		
		return JSFNavigationConstants.EDITAPPOINTMENT;
	}
}
