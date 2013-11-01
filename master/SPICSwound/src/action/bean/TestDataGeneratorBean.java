package bean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.management.PasswordHash;

import util.DateUtils;
import util.RuntimeConfiguration;
import util.SpicsPermissions;
import util.excel.ExcelImportExportException;
import util.xml.IXMLImportExport;
import util.xml.XMLImportExport;
import bean.action.TrialDataImportAction;
import db.interfaces.IAppointmentDAO;
import db.interfaces.IGroupDAO;
import db.interfaces.IParticipationDAO;
import db.interfaces.IPatientDAO;
import db.interfaces.IPropertyDAO;
import db.interfaces.IRoleDAO;
import db.interfaces.ITrialDAO;
import db.interfaces.ITrialDataDAO;
import db.interfaces.ITrialFormDAO;
import db.interfaces.IUserDAO;
import db.interfaces.IUserPermissionDAO;
import db.interfaces.IValueDAO;
import entities.Appointment;
import entities.Attribute;
import entities.AttributeGroup;
import entities.OrgGroup;
import entities.Participation;
import entities.Patient;
import entities.Property;
import entities.TRIALSTATUS;
import entities.Trial;
import entities.TrialData;
import entities.TrialForm;
import entities.User;
import entities.UserPermission;
import entities.UserRole;
import entities.constraint.IntegerConstraint;
import entities.event.EVENTTYPE;
import entities.event.TrialCreateEvent;
import entities.event.ValueChangeEvent;
import entities.event.ValueCreateEvent;
import entities.event.ValueDeleteEvent;
import entities.formelement.DATATYPE;
import entities.formelement.FPRSScale;
import entities.formelement.FileUpload;
import entities.formelement.TextArea;
import entities.formelement.TextField;
import entities.formelement.VASScale;
import entities.formelement.VancouverScarScale;
import entities.value.IntegerValue;
import entities.value.Value;

@Stateful
@Name("TestDataGenerator")
@Scope(ScopeType.APPLICATION)
@Startup
public class TestDataGeneratorBean implements TestDataGenerator {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;

	@EJB
	private ITrialDataDAO trialDataDAO;

	@EJB
	private IValueDAO valueDAO;

	@EJB
	private ITrialFormDAO trialFormDAO;

	@EJB
	private IPatientDAO patientDAO;

	@EJB
	private ITrialDAO trialDAO;

	@EJB
	private IUserDAO userDAO;

	@EJB
	private IRoleDAO roleDAO;

	@EJB
	private IParticipationDAO participationDAO;

	@EJB
	private IGroupDAO groupDAO;

	@EJB
	private IUserPermissionDAO userPermissionDAO;

	@EJB
	private IPropertyDAO propertyDAO;

	@EJB
	private IAppointmentDAO appointmentDAO;

	@EJB
	private EventManager eventManager;

	@In
	private TrialDataImportAction trialDataImportAction;

	private User user;
	private SpicsPermissions spicsPermissions;

	// @Create
	public void generateTestForm(boolean soulTestData) {

		Property prop1 = new Property(RuntimeConfiguration.PROP_LOGO_URL,
				"graphics/design/logo.png", true);
		propertyDAO.persist(prop1);
		Property prop2 = new Property(RuntimeConfiguration.PROP_SUPPORT_EMAIL,
				"swwe@inso.tuwien.ac.at", true);
		prop2.setRegex(".+@.+\\.[a-z]+");
		propertyDAO.persist(prop2);
		Property prop3 = new Property(RuntimeConfiguration.PROP_RICHFACES_SKIN,
				"classic", true);
		prop3.setRegex("blueSky|classic|ruby");
		propertyDAO.persist(prop3);

		Property prop4 = new Property(RuntimeConfiguration.PROP_STYLESHEET_URL,
				"graphics/stylesheet.css", true);
		propertyDAO.persist(prop4);
		Property prop5 = new Property(RuntimeConfiguration.PROP_PLUGIN_LIST,
				"SoulMedicinePlugin", false);
		propertyDAO.persist(prop5);
		Property prop6 = new Property(RuntimeConfiguration.PROP_ACTIVE_CHARTS,
				"Soulstudie;SoulChart;;foo;TestChart1", false);
		propertyDAO.persist(prop6);

		this.log.info("Aufruf von generateTestForm");

		// TrialForm tsForm = generateTrialSpecific();

		/* ******************************************** */

		TrialForm form = new TrialForm();

		LinkedList<AttributeGroup> atGroups = new LinkedList<AttributeGroup>();
		form.setSort(0);
		form.setAttributeGroups(atGroups);
		form.setName("Teststudienblatt");
		form
				.setDescription("Mit dieser Studie wird getestet ob alle formularelemente korrekt angezeigt werden koennen");
		form.setLastModified(new Date(System.currentTimeMillis()));

		AttributeGroup blutdruck = new AttributeGroup();
		atGroups.add(blutdruck);

		blutdruck.setName("Blutdruck");
		blutdruck.setTrialForm(form);
		blutdruck.setSort(0);

		LinkedList<Attribute> blutdruckAG = new LinkedList<Attribute>();
		blutdruck.setAttributes(blutdruckAG);

		Attribute systolisch = new Attribute();
		systolisch.setName("Systolisch");
		systolisch.setAttributeGroup(blutdruck);
		systolisch.setRecommended(true);
		systolisch.setSort(0);
		TextField tf4 = new TextField();
		tf4.setDataType(DATATYPE.INTEGER);
		systolisch.setFormElement(tf4);

		systolisch.setUnit("mmHg");
		blutdruckAG.add(systolisch);

		Attribute diastolisch = new Attribute();
		diastolisch.setName("Diastolisch");
		diastolisch.setAttributeGroup(blutdruck);
		diastolisch.setRecommended(true);
		diastolisch.setSort(1);
		TextField tf5 = new TextField();
		tf5.setDataType(DATATYPE.INTEGER);
		diastolisch.setFormElement(tf5);
		diastolisch.setUnit("mmHg");
		blutdruckAG.add(diastolisch);

		IntegerConstraint diastConstr = new IntegerConstraint();
		diastConstr.setMax(300);
		diastConstr.setMin(50);
		tf5.setConstraint(diastConstr);

		Attribute pulsamplitude = new Attribute();
		pulsamplitude.setName("Pulsamplitude");
		pulsamplitude.setAttributeGroup(blutdruck);
		pulsamplitude.setRecommended(false);
		pulsamplitude.setSort(2);
		TextField tf6 = new TextField();
		tf6.setDataType(DATATYPE.INTEGER);
		pulsamplitude.setFormElement(tf6);

		pulsamplitude.setUnit("Anzahl/minute");
		blutdruckAG.add(pulsamplitude);

		/* ****************************** */

		AttributeGroup testgruppe = new AttributeGroup();
		atGroups.add(testgruppe);

		testgruppe.setName("TestAG");
		testgruppe.setTrialForm(form);
		testgruppe.setSort(1);
		testgruppe
				.setDescription("Dies ist eine AttributeGroup die primär dazu dient die sonst noch nicht verwendeten Elemente zu testen.");

		LinkedList<Attribute> testgruppeAG = new LinkedList<Attribute>();
		testgruppe.setAttributes(testgruppeAG);

		Attribute testAttr = new Attribute();
		testAttr.setName("Testwert(Float)");
		testAttr.setAttributeGroup(testgruppe);
		testAttr.setRecommended(true);
		testAttr.setSort(0);
		TextField tf7 = new TextField();
		tf7.setDataType(DATATYPE.DECIMAL);
		testAttr.setFormElement(tf7);
		testgruppeAG.add(testAttr);

		Attribute listAttr = new Attribute();
		listAttr.setName("Testliste");
		listAttr.setAttributeGroup(testgruppe);
		listAttr.setRecommended(false);
		listAttr.setSort(1);
		entities.formelement.List testList = new entities.formelement.List();
		testList.setDataType(DATATYPE.STR);
		listAttr.setFormElement(testList);
		testList.getValues().add("eins");
		testList.getValues().add("zwei");
		testList.getValues().add("drei");
		testList.getValues().add("vier");
		testgruppeAG.add(listAttr);

		Attribute cbAttr = new Attribute();
		cbAttr.setName("Testcheckbox");
		cbAttr.setAttributeGroup(testgruppe);
		cbAttr.setRecommended(true);
		cbAttr.setSort(2);
		entities.formelement.CheckBox testCB = new entities.formelement.CheckBox();
		testCB.setDataType(DATATYPE.BOOLEAN);
		cbAttr.setFormElement(testCB);
		testgruppeAG.add(cbAttr);

		Attribute rbAttr = new Attribute();
		rbAttr.setName("Testradiobuttons");
		rbAttr.setAttributeGroup(testgruppe);
		rbAttr.setSort(3);
		entities.formelement.RadioButtons testRB = new entities.formelement.RadioButtons();
		testRB.setDataType(DATATYPE.STR);
		rbAttr.setFormElement(testRB);
		testRB.getValues().add("rb1");
		testRB.getValues().add("rb2");
		testRB.getValues().add("rb3");
		testRB.getValues().add("rb4");
		testgruppeAG.add(rbAttr);

		Attribute dateAttr = new Attribute();
		dateAttr.setName("TestDate");
		dateAttr.setAttributeGroup(testgruppe);
		dateAttr.setSort(4);
		entities.formelement.DatePicker testDP = new entities.formelement.DatePicker();
		testDP.setDataType(DATATYPE.DATE);
		dateAttr.setFormElement(testDP);
		testgruppeAG.add(dateAttr);

		Attribute smilieAttr = new Attribute();
		smilieAttr.setName("TestFPRSScale");
		smilieAttr.setAttributeGroup(testgruppe);
		smilieAttr.setSort(5);
		entities.formelement.FPRSScale fs = new FPRSScale();
		fs.setDataType(DATATYPE.INTEGER);
		smilieAttr.setFormElement(fs);
		testgruppeAG.add(smilieAttr);

		Attribute smilie2Attr = new Attribute();
		smilie2Attr.setName("TestVASScale");
		smilie2Attr.setAttributeGroup(testgruppe);
		smilie2Attr.setSort(6);
		entities.formelement.VASScale vs = new VASScale();
		vs.setDataType(DATATYPE.INTEGER);
		smilie2Attr.setFormElement(vs);
		testgruppeAG.add(smilie2Attr);

		Attribute fileAttr = new Attribute();
		fileAttr.setName("TestDatenupload");
		fileAttr.setAttributeGroup(testgruppe);
		fileAttr.setSort(7);
		entities.formelement.FileUpload fu = new FileUpload();
		fu.setDataType(DATATYPE.FILE);
		fileAttr.setFormElement(fu);
		testgruppeAG.add(fileAttr);

		Attribute vssAttr = new Attribute();
		vssAttr.setName("TestVancouverScarScale");
		vssAttr.setAttributeGroup(testgruppe);
		vssAttr.setSort(8);
		VancouverScarScale vss = new VancouverScarScale();
		vss.setDataType(DATATYPE.INTEGERSET);
		vssAttr.setFormElement(vss);
		testgruppeAG.add(vssAttr);

		Attribute taAttr = new Attribute();
		taAttr.setName("TestTextarea");
		taAttr.setAttributeGroup(testgruppe);
		taAttr.setSort(9);
		TextArea ta = new TextArea();
		ta.setDataType(DATATYPE.LONGSTR);
		taAttr.setFormElement(ta);
		testgruppeAG.add(taAttr);

		/* trialdata */
		// UserRole reviewerRole = new UserRole();
		// reviewerRole.setRolename("reviewer");
		// roleDAO.persist(reviewerRole);
		UserRole contributorRole = new UserRole();
		contributorRole.setRolename("contributor");
		roleDAO.persist(contributorRole);

		UserRole creatorRole = new UserRole();
		creatorRole.setRolename("creator");
		creatorRole.setGroups(new HashSet<UserRole>());
		creatorRole.getGroups().add(contributorRole);
		roleDAO.persist(creatorRole);

		UserRole adminRole = new UserRole();
		adminRole.setRolename("admin");
		adminRole.setGroups(new HashSet<UserRole>());
		adminRole.getGroups().add(creatorRole);
		// adminRole.getGroups().add(reviewerRole);
		roleDAO.persist(adminRole);

		User userWithoutRights = new User();
		userWithoutRights.setFirstname("Bart");
		userWithoutRights.setTitle("Bsc.");
		userWithoutRights.setLastname("Simpson");
		userWithoutRights.setOrganisation("inso");
		userWithoutRights.setUsername("bart");
		userWithoutRights.setScreenname("bart");
		userWithoutRights.setPasswordHash(PasswordHash.instance()
				.generateSaltedHash("abc", "bart"));
		userWithoutRights.setEmail("test_b@inso.tuwien.ac.at");
		userWithoutRights
				.setPreferredLocale(java.util.Locale.GERMAN.toString());
		userWithoutRights.setNotifyOnEvents(false);
		userWithoutRights.setNotifyEvents(EVENTTYPE
				.getMailableNonAdminEventNames());
		userDAO.persist(userWithoutRights);

		user = new User();

		user.setFirstname("Hans");
		user.setTitle("Dr.");
		user.setLastname("Muster");
		user.setOrganisation("Musterkrankenhaus");
		user.setUsername("MusterKH");
		user.setScreenname("Dr. Muster");
		user.setPasswordHash(PasswordHash.instance().generateSaltedHash(
				"secret", "MusterKH"));
		user.setEmail("test_1@inso.tuwien.ac.at");
		user.setPreferredLocale(java.util.Locale.GERMAN.toString());
		user.setRoles(new HashSet<UserRole>());
		user.getRoles().add(adminRole);
		user.setNotifyOnEvents(false);
		user.setNotifyEvents(EVENTTYPE.getMailableNonAdminEventNames());

		User otherUser = new User();
		otherUser.setTitle("Dr.");
		otherUser.setFirstname("Max");
		otherUser.setLastname("Spics");
		otherUser.setOrganisation("MusterUKH");
		otherUser.setUsername("maxSpics58");
		otherUser.setPasswordHash(PasswordHash.instance().generateSaltedHash(
				"Stiegl", "maxSpics58"));
		otherUser.setEmail("test_2@inso.tuwien.ac.at");
		otherUser.setScreenname("Dr. M. Spics");
		otherUser.setPreferredLocale(java.util.Locale.GERMAN.toString());
		otherUser.setRoles(new HashSet<UserRole>());
		otherUser.getRoles().add(contributorRole);
		otherUser.setNotifyOnEvents(false);
		otherUser.setNotifyEvents(EVENTTYPE.getMailableNonAdminEventNames());

		userDAO.persist(user);
		userDAO.persist(otherUser);

		spicsPermissions = SpicsPermissions.instance();

		Patient patient = new Patient();
		patient.setKennnummer("1234ABC456");
		patient.setSavedBy(user);
		patient.setRegistrationDate(new Date(System.currentTimeMillis()));

		Patient patient2 = new Patient();
		patient2.setKennnummer("KNR2537AZ");
		patient2.setSavedBy(user);
		patient2.setRegistrationDate(new Date(System.currentTimeMillis()));

		Patient patient3 = new Patient();
		patient3.setKennnummer("GHF783412");
		patient3.setSavedBy(otherUser);
		patient3.setRegistrationDate(new Date(System.currentTimeMillis()));

		Patient patientNotInTrial = new Patient();
		patientNotInTrial.setKennnummer("NOT_IN_TRIAL");
		patientNotInTrial.setSavedBy(user);
		patientNotInTrial.setRegistrationDate(new Date(System
				.currentTimeMillis()));

		Patient patientNotInGroup = new Patient();
		patientNotInGroup.setKennnummer("NOT_IN_GROUP");
		patientNotInGroup.setSavedBy(otherUser);
		patientNotInGroup.setRegistrationDate(new Date(System
				.currentTimeMillis()));

		patientDAO.persist(patient);
		patientDAO.persist(patient2);
		patientDAO.persist(patient3);
		patientDAO.persist(patientNotInTrial);
		patientDAO.persist(patientNotInGroup);

		UserPermission patientPermission = new UserPermission(user
				.getUsername(), "Patient:" + patient.getId(), spicsPermissions
				.getPermissionString(spicsPermissions
						.getPatientOwnerPermission(null, null)), "user");

		UserPermission patient2Permission = new UserPermission(user
				.getUsername(), "Patient:" + patient2.getId(), spicsPermissions
				.getPermissionString(spicsPermissions
						.getPatientOwnerPermission(null, null)), "user");

		UserPermission patient3Permission = new UserPermission(otherUser
				.getUsername(), "Patient:" + patient3.getId(), spicsPermissions
				.getPermissionString(spicsPermissions
						.getPatientOwnerPermission(null, null)), "user");

		UserPermission patientNotInTrialPermission = new UserPermission(user
				.getUsername(), "Patient:" + patientNotInTrial.getId(),
				spicsPermissions.getPermissionString(spicsPermissions
						.getPatientOwnerPermission(null, null)), "user");

		UserPermission patientNotInGroupPermission = new UserPermission(
				otherUser.getUsername(),
				"Patient:" + patientNotInGroup.getId(), spicsPermissions
						.getPermissionString(spicsPermissions
								.getPatientOwnerPermission(null, null)), "user");

		userPermissionDAO.persist(patientPermission);
		userPermissionDAO.persist(patient2Permission);
		userPermissionDAO.persist(patient3Permission);
		userPermissionDAO.persist(patientNotInTrialPermission);
		userPermissionDAO.persist(patientNotInGroupPermission);

		Trial trial = new Trial();
		trial.setName("Beispielstudie zum Thema Wundheilung");
		trial
				.setFullName("Beispielstudie zum Thema Wundheilung unter Berücksichtigung von gewissen Elementen");
		trial.setStatus(TRIALSTATUS.EXECUTE);
		trial.setBeginDate(new GregorianCalendar(2007, 4, 21).getTime());
		trial.setEndDate(new GregorianCalendar(2007, 12, 1).getTime());
		trial
				.setDescription("Das hier ist ein Beispieleintrag einer Studie, die den Überblick über eine Studie geben soll. In diesem Bereich können in der Regel allgemeine Informationen über die Studie in Erfahrung gebracht werden. Dieser Text sollte die Problemstellung, die Methodik, sowie das Ziel der Studie vor Augen führen. Im Bereich Felderdefinitionen können die Felder und Werte definiert werden, die bei jedem Patienten gemessen werden sollen und in dieses Portal eingetragen werden sollen. Im Bereich Studienpartner finden Sie die Namen aller Ärzte, die an dieser Studie teilnehmen. Im nächsten Punkt 'Studienpatienten' können die Namen der Patienten angezeigt werden, sowie Daten für diese eingetragen werden. Die Werte, die eingetragen werden sollten, müssen natürlich davor vom Ersteller der Studie in 'Felderdefinitionen' kreiert und verwaltet werden.");
		trial.setSupervisor("Dr. Supervisor");
		trial.getTrialForms().add(form);
		trial.setUser(user);

		user.getTrials().add(trial);
		form.setTrial(trial);

		trialFormDAO.persist(form);
		trialDAO.persist(trial);

		TrialCreateEvent ev = new TrialCreateEvent();
		ev.setUser(user);
		ev.setTrial(trial);
		eventManager.registerEvent(ev);

		UserPermission ownerPermission = new UserPermission(user.getUsername(),
				"trial:" + trial.getId(), spicsPermissions
						.getPermissionString(spicsPermissions
								.getOwnerPermissions(null, null)), "user");
		userPermissionDAO.persist(ownerPermission);

		Participation user2trial2patient = new Participation();
		user2trial2patient.setParticipatingSince(new Date(System
				.currentTimeMillis()));
		user2trial2patient.getPatients().add(patient);
		user2trial2patient.getPatients().add(patient2);
		user2trial2patient.getPatients().add(patientNotInGroup);
		user2trial2patient.setTrial(trial);
		user2trial2patient.setUser(user);
		user2trial2patient.setCanViewAllPatients(true);

		participationDAO.persist(user2trial2patient);

		Participation otherUser2trial = new Participation();
		otherUser2trial.setParticipatingSince(new Date(System
				.currentTimeMillis()));
		otherUser2trial.getPatients().add(patient3);
		otherUser2trial.setTrial(trial);
		otherUser2trial.setUser(otherUser);

		participationDAO.persist(otherUser2trial);

		UserPermission participatioPermission = new UserPermission(otherUser
				.getUsername(), "trial:" + trial.getId(), spicsPermissions
				.getPermissionString(spicsPermissions.getPartnerPermissions(
						null, null)), "user");
		userPermissionDAO.persist(participatioPermission);

		patient3.setParticipation(otherUser2trial);
		patient.setParticipation(user2trial2patient);
		patient2.setParticipation(user2trial2patient);

		OrgGroup aGroup = new OrgGroup();
		aGroup.setName("Chirurgie");
		aGroup.getPatients().add(patient);
		aGroup.getPatients().add(patient2);
		aGroup.getPatients().add(patient3);
		aGroup.getUsers().add(user);
		aGroup.getUsers().add(otherUser);

		groupDAO.persist(aGroup);

		user.getGroups().add(aGroup);
		otherUser.getGroups().add(aGroup);
		patient.getGroups().add(aGroup);
		patient2.getGroups().add(aGroup);
		patient3.getGroups().add(aGroup);
		patientNotInTrial.getGroups().add(aGroup);

		// initial TD

		TrialData trialData = new TrialData();
		trialData.setPatient(patient);
		trialData.setLastModified(new Date(System.currentTimeMillis()));
		trialData.setSavedOn(new Date(System.currentTimeMillis()));
		trialData.setValues(new HashSet<Value>());
		trialData.setTrialform(form);
		trialData.setSavedBy(user);
		trialData.setLastModifiedBy(user);

		trialDataDAO.persist(trialData);

		IntegerValue systolischValue = new IntegerValue();
		systolischValue.setAttribute(systolisch);
		systolischValue.setTrialData(trialData);
		systolischValue.setValue(123);
		trialData.getValues().add(systolischValue);
		valueDAO.persist(systolischValue);

		ValueCreateEvent vce4 = new ValueCreateEvent(user, systolischValue);
		eventManager.registerEvent(vce4, true);

		IntegerValue diastolischValue = new IntegerValue();
		diastolischValue.setAttribute(diastolisch);
		diastolischValue.setTrialData(trialData);
		diastolischValue.setValue(456);
		trialData.getValues().add(diastolischValue);
		valueDAO.persist(diastolischValue);

		ValueCreateEvent vce5 = new ValueCreateEvent(user, diastolischValue);
		eventManager.registerEvent(vce5, true);

		IntegerValue pulsamplValue = new IntegerValue();
		pulsamplValue.setAttribute(pulsamplitude);
		pulsamplValue.setTrialData(trialData);
		pulsamplValue.setValue(80);
		trialData.getValues().add(pulsamplValue);
		valueDAO.persist(pulsamplValue);

		ValueCreateEvent vce6 = new ValueCreateEvent(user, pulsamplValue);
		eventManager.registerEvent(vce6, true);

		TrialData secondTime = new TrialData();
		secondTime.setPatient(patient);
		secondTime.setLastModified(new Date(System.currentTimeMillis()));
		secondTime.setSavedOn(new Date(System.currentTimeMillis()));
		secondTime.setValues(new HashSet<Value>());
		secondTime.setTrialform(form);
		secondTime.setSavedBy(otherUser);
		secondTime.setLastModifiedBy(otherUser);

		trialDataDAO.persist(secondTime);

		IntegerValue systolischValue2 = new IntegerValue();
		systolischValue2.setAttribute(systolisch);
		systolischValue2.setTrialData(secondTime);
		systolischValue2.setValue(111);
		secondTime.getValues().add(systolischValue2);
		valueDAO.persist(systolischValue2);

		ValueCreateEvent vce7 = new ValueCreateEvent(otherUser,
				systolischValue2);
		eventManager.registerEvent(vce7, true);

		IntegerValue diastolischValue2 = new IntegerValue();
		diastolischValue2.setAttribute(diastolisch);
		diastolischValue2.setTrialData(secondTime);
		diastolischValue2.setValue(222);
		secondTime.getValues().add(diastolischValue2);
		valueDAO.persist(diastolischValue2);

		ValueCreateEvent vce8 = new ValueCreateEvent(otherUser,
				diastolischValue2);
		eventManager.registerEvent(vce8, true);

		IntegerValue pulsamplValue2 = new IntegerValue();
		pulsamplValue2.setAttribute(pulsamplitude);
		pulsamplValue2.setTrialData(secondTime);
		pulsamplValue2.setValue(80);
		secondTime.getValues().add(pulsamplValue2);
		valueDAO.persist(pulsamplValue2);

		ValueCreateEvent vce9 = new ValueCreateEvent(otherUser, pulsamplValue2);
		eventManager.registerEvent(vce9, true);

		TrialData thirdTime = new TrialData();
		thirdTime.setPatient(patient);
		thirdTime
				.setLastModified(new GregorianCalendar(2007, 10, 10).getTime());
		thirdTime.setSavedOn(new Date(System.currentTimeMillis()));
		thirdTime.setValues(new HashSet<Value>());
		thirdTime.setTrialform(form);
		thirdTime.setSavedBy(user);
		thirdTime.setLastModifiedBy(user);

		trialDataDAO.persist(thirdTime);

		IntegerValue systolischValue3 = new IntegerValue();
		systolischValue3.setAttribute(systolisch);
		systolischValue3.setTrialData(thirdTime);
		systolischValue3.setValue(784);
		thirdTime.getValues().add(systolischValue3);
		valueDAO.persist(systolischValue3);

		ValueCreateEvent vce10 = new ValueCreateEvent(user, systolischValue3);
		eventManager.registerEvent(vce10, true);

		IntegerValue diastolischValue3 = new IntegerValue();
		diastolischValue3.setAttribute(diastolisch);
		diastolischValue3.setTrialData(thirdTime);
		diastolischValue3.setValue(654);
		thirdTime.getValues().add(diastolischValue3);
		valueDAO.persist(diastolischValue3);

		ValueCreateEvent vce11 = new ValueCreateEvent(user, diastolischValue3);
		eventManager.registerEvent(vce11, true);

		// test valuechange and valuedelete

		IntegerValue pulsamplValue3 = new IntegerValue();
		pulsamplValue3.setAttribute(pulsamplitude);
		pulsamplValue3.setTrialData(thirdTime);
		pulsamplValue3.setValue(80);
		thirdTime.getValues().add(pulsamplValue3);
		valueDAO.persist(pulsamplValue3);

		ValueCreateEvent vce12 = new ValueCreateEvent(user, pulsamplValue3);
		eventManager.registerEvent(vce12, true);

		Serializable oldVal = pulsamplValue3.getValue();
		pulsamplValue3.setValue(99);

		ValueChangeEvent vChangeEv = new ValueChangeEvent(user, oldVal,
				pulsamplValue3);
		eventManager.registerEvent(vChangeEv, true);

		valueDAO.remove(pulsamplValue3);

		ValueDeleteEvent vde = new ValueDeleteEvent(user, pulsamplValue3);
		eventManager.registerEvent(vde, true);

		/* calendar test data */
		Appointment app1 = new Appointment();
		app1.setStartDate(DateUtils.newDateTime(2008, 11, 3, 14, 15));
		app1.setTitle("Allgemeine Untersuchung");
		app1.setDescription("Untersuchung der Diskushernie durch Dr. Fletcher");
		app1.setTrial(trial);
		app1.setUser(user);

		Appointment app2 = new Appointment();
		app2.setStartDate(DateUtils.newDateTime(2009, 1, 13, 8, 55));
		app2.setTitle("Termin für Patient #AD18181");
		app2.setDescription("Kontrolle Burn-out Syndrom Herr Marx");
		app2.setTrial(trial);
		app2.setUser(user);
		app2.setPatient(patient);

		appointmentDAO.persist(app1);
		appointmentDAO.persist(app2);

		/* Testdata for htmlunit tests */

		User testuser = new User();
		testuser.setUsername("testuser");
		testuser.setFirstname("Test");
		testuser.setLastname("User");
		testuser.setOrganisation("Testorganisation");
		testuser.setPasswordHash(PasswordHash.instance().generateSaltedHash(
				"testpass", "testuser"));
		testuser.setEmail("test_3@inso.tuwien.ac.at");
		testuser.setPreferredLocale("de");
		testuser.setTitle("DDr");
		testuser.setScreenname("Dr. User");
		testuser.setNotifyOnEvents(false);
		testuser.setNotifyEvents(EVENTTYPE.getMailableNonAdminEventNames());

		userDAO.persist(testuser);

		OrgGroup testgroup = new OrgGroup();
		testgroup.setName("testgroup");
		testgroup.getUsers().add(testuser);
		testgroup.getUsers().add(userWithoutRights);

		groupDAO.persist(testgroup);

		testuser.getGroups().add(testgroup);
		userWithoutRights.getGroups().add(testgroup);

		if (soulTestData)
			generateSoulTestdata(aGroup);

		generateWHATTestdata(aGroup);
	}

	private void generateSoulTestdata(OrgGroup group) {
		log.info("Beginne Soulstudie-Erstellung");

		ClassLoader myClassLoader = this.getClass().getClassLoader();
		IXMLImportExport trialReader = new XMLImportExport();

		InputStream stammdatenIS = myClassLoader
				.getResourceAsStream("trialforms/Stammdaten_2.zip");
		InputStream medikationIS = myClassLoader
				.getResourceAsStream("trialforms/Medikation_3.zip");
		InputStream untersuchungIS = myClassLoader
				.getResourceAsStream("trialforms/Untersuchung_2.zip");

		InputStream cgiIS = myClassLoader
				.getResourceAsStream("trialforms/Cgi_2.zip");
		InputStream aufenthalteIS = myClassLoader
				.getResourceAsStream("trialforms/Aufenthalte_2.zip");
		InputStream spiegelIS = myClassLoader
				.getResourceAsStream("trialforms/Spiegel.zip");
		InputStream laborIS = myClassLoader
				.getResourceAsStream("trialforms/Labor.zip");

		Trial trial = new Trial();
		trial.setName("Soulstudie");
		trial.setFullName("Beispielstudie zum Thema Soul");
		trial.setStatus(TRIALSTATUS.EXECUTE);
		trial.setBeginDate(new GregorianCalendar(2009, 0, 1).getTime());
		trial.setEndDate(new GregorianCalendar(2009, 11, 30).getTime());
		trial.setDescription("Das hier ist ein Beispieleintrag.");
		trial.setSupervisor("Dr. Supervisor");

		TrialForm stammdatenForm = null;
		TrialForm medikationForm = null;
		TrialForm untersuchungForm = null;
		TrialForm cgiForm = null;
		TrialForm laborForm = null;
		TrialForm aufenthalteForm = null;
		TrialForm spiegelForm = null;

		String uri = null;
		try {
			stammdatenForm = trialReader.readTrialFormFromZip(stammdatenIS);
			medikationForm = trialReader.readTrialFormFromZip(medikationIS);
			untersuchungForm = trialReader.readTrialFormFromZip(untersuchungIS);

			cgiForm = trialReader.readTrialFormFromZip(cgiIS);
			laborForm = trialReader.readTrialFormFromZip(laborIS);
			aufenthalteForm = trialReader.readTrialFormFromZip(aufenthalteIS);
			spiegelForm = trialReader.readTrialFormFromZip(spiegelIS);

		} catch (Exception e) {
			log.error("failed to read trialform uri #0", uri);
			return;
		}

		stammdatenForm.setSort(0);
		medikationForm.setSort(1);
		untersuchungForm.setSort(2);
		cgiForm.setSort(3);
		laborForm.setSort(4);
		aufenthalteForm.setSort(5);
		spiegelForm.setSort(6);

		// rewire bidirectional assocations
		for (AttributeGroup ag : stammdatenForm.getAttributeGroups()) {
			ag.setTrialForm(stammdatenForm);
			for (Attribute a : ag.getAttributes()) {
				a.setAttributeGroup(ag);
			}
		}
		for (AttributeGroup ag : untersuchungForm.getAttributeGroups()) {
			ag.setTrialForm(untersuchungForm);
			for (Attribute a : ag.getAttributes()) {
				a.setAttributeGroup(ag);
			}
		}
		for (AttributeGroup ag : medikationForm.getAttributeGroups()) {
			ag.setTrialForm(medikationForm);
			for (Attribute a : ag.getAttributes()) {
				a.setAttributeGroup(ag);
			}
		}

		for (AttributeGroup ag : cgiForm.getAttributeGroups()) {
			ag.setTrialForm(cgiForm);
			for (Attribute a : ag.getAttributes()) {
				a.setAttributeGroup(ag);
			}
		}
		for (AttributeGroup ag : laborForm.getAttributeGroups()) {
			ag.setTrialForm(laborForm);
			for (Attribute a : ag.getAttributes()) {
				a.setAttributeGroup(ag);
			}
		}
		for (AttributeGroup ag : aufenthalteForm.getAttributeGroups()) {
			ag.setTrialForm(aufenthalteForm);
			for (Attribute a : ag.getAttributes()) {
				a.setAttributeGroup(ag);
			}
		}
		for (AttributeGroup ag : spiegelForm.getAttributeGroups()) {
			ag.setTrialForm(spiegelForm);
			for (Attribute a : ag.getAttributes()) {
				a.setAttributeGroup(ag);
			}
		}

		trial.getTrialForms().add(stammdatenForm);
		trial.getTrialForms().add(medikationForm);
		trial.getTrialForms().add(untersuchungForm);

		trial.getTrialForms().add(cgiForm);
		trial.getTrialForms().add(laborForm);
		trial.getTrialForms().add(aufenthalteForm);
		trial.getTrialForms().add(spiegelForm);

		trial.setUser(user);
		user.getTrials().add(trial);

		stammdatenForm.setTrial(trial);
		medikationForm.setTrial(trial);
		untersuchungForm.setTrial(trial);

		cgiForm.setTrial(trial);
		laborForm.setTrial(trial);
		aufenthalteForm.setTrial(trial);
		spiegelForm.setTrial(trial);

		trialFormDAO.persist(stammdatenForm);
		trialFormDAO.persist(medikationForm);
		trialFormDAO.persist(untersuchungForm);

		trialFormDAO.persist(cgiForm);
		trialFormDAO.persist(laborForm);
		trialFormDAO.persist(aufenthalteForm);
		trialFormDAO.persist(spiegelForm);

		trialDAO.persist(trial);

		Participation p2trial = new Participation();
		p2trial.setTrial(trial);
		p2trial.setUser(user);
		participationDAO.persist(p2trial);

		UserPermission ownerPermission = new UserPermission(user.getUsername(),
				"trial:" + trial.getId(), spicsPermissions
						.getPermissionString(spicsPermissions
								.getOwnerPermissions(null, null)), "user");
		userPermissionDAO.persist(ownerPermission);

		Patient patient = new Patient();
		patient.setKennnummer("soulpat1");
		patient.setSavedBy(user);
		patient.setRegistrationDate(new Date(System.currentTimeMillis()));
		patient.getGroups().add(group);

		p2trial.getPatients().add(patient);
		patient.setParticipation(p2trial);

		patientDAO.persist(patient);

		UserPermission patientPermission = new UserPermission(user
				.getUsername(), "Patient:" + patient.getId(), spicsPermissions
				.getPermissionString(spicsPermissions
						.getPatientOwnerPermission(null, null)), "user");
		userPermissionDAO.persist(patientPermission);

		// stammdaten form
		this.addTrialDataFromXLS(stammdatenForm, patient,
				"trialforms/soulpat1_Stammdaten1.xls");

		// medikation form
		this.addTrialDataFromXLS(medikationForm, patient,
				"trialforms/soulpat1_Medikation1.xls");
		this.addTrialDataFromXLS(medikationForm, patient,
				"trialforms/soulpat1_Medikation2.xls");
		this.addTrialDataFromXLS(medikationForm, patient,
				"trialforms/soulpat1_Medikation3.xls");
		this.addTrialDataFromXLS(medikationForm, patient,
				"trialforms/soulpat1_Medikation4.xls");

		// untersuchung form
		this.addTrialDataFromXLS(untersuchungForm, patient,
				"trialforms/soulpat1_Untersuchung1.xls");
		this.addTrialDataFromXLS(untersuchungForm, patient,
				"trialforms/soulpat1_Untersuchung2.xls");

		log.info("Ende Soulstudie-Erstellung");
	}

	private void addTrialDataFromXLS(TrialForm form, Patient patient,
			String path) {

		ClassLoader myClassLoader = this.getClass().getClassLoader();

		TrialData trialData = new TrialData();
		trialData.setPatient(patient);
		trialData.setTrialform(form);
		trialData.setLastModified(new Date());
		trialData.setLastModifiedBy(user);
		trialData.setSavedBy(user);
		trialData.setSavedOn(new Date());

		form.getTrialDatas().add(trialData);
		trialDataDAO.persist(trialData);

		InputStream is = myClassLoader.getResourceAsStream(path);

		try {
			trialDataImportAction.importTrialData(trialData.getId(), is);
		} catch (ExcelImportExportException e) {
			e.printStackTrace();
		}
	}

	private void generateWHATTestdata(OrgGroup group) {
		log.info("Beginne WHAT Studie-Erstellung");
		IXMLImportExport trialReader = new XMLImportExport();
		ClassLoader myClassLoader = this.getClass().getClassLoader();

		InputStream whatFormIS = myClassLoader
				.getResourceAsStream("trialforms/W.H.A.T._Daten.zip");

		Trial trial = new Trial();
		trial.setName("WHAT-Studie");
		trial.setFullName("Beispielstudie zum Thema WHAT");
		trial.setStatus(TRIALSTATUS.EXECUTE);
		trial.setBeginDate(new GregorianCalendar(2009, 0, 1).getTime());
		trial.setEndDate(new GregorianCalendar(2009, 11, 30).getTime());
		trial.setDescription("Das hier ist ein weiterer Beispieleintrag.");
		trial.setSupervisor("Dr. Supervisor");

		TrialForm whatForm = null;

		try {
			whatForm = trialReader.readTrialFormFromZip(whatFormIS);
		} catch (Exception e) {
			log.error("failed to read what trialform uri");
			return;
		}

		whatForm.setSort(0);

		// rewire bidirectional assocations
		for (AttributeGroup ag : whatForm.getAttributeGroups()) {
			ag.setTrialForm(whatForm);
			for (Attribute a : ag.getAttributes()) {
				a.setAttributeGroup(ag);
			}
		}

		trial.getTrialForms().add(whatForm);

		trial.setUser(user);
		user.getTrials().add(trial);

		whatForm.setTrial(trial);

		trialFormDAO.persist(whatForm);
		trialDAO.persist(trial);

		Participation p2trial = new Participation();
		p2trial.setTrial(trial);
		p2trial.setUser(user);
		participationDAO.persist(p2trial);

		UserPermission ownerPermission = new UserPermission(user.getUsername(),
				"trial:" + trial.getId(), spicsPermissions
						.getPermissionString(spicsPermissions
								.getOwnerPermissions(null, null)), "user");
		userPermissionDAO.persist(ownerPermission);

		Patient patient = new Patient();
		patient.setKennnummer("WHAT Pat1");
		patient.setSavedBy(user);
		patient.setRegistrationDate(new Date(System.currentTimeMillis()));
		patient.getGroups().add(group);

		p2trial.getPatients().add(patient);
		patient.setParticipation(p2trial);

		patientDAO.persist(patient);

		Patient patient2 = new Patient();
		patient2.setKennnummer("WHAT Pat2");
		patient2.setSavedBy(user);
		patient2.setRegistrationDate(new Date(System.currentTimeMillis()));
		patient2.getGroups().add(group);

		p2trial.getPatients().add(patient2);
		patient2.setParticipation(p2trial);

		patientDAO.persist(patient2);

		UserPermission patientPermission = new UserPermission(user
				.getUsername(), "Patient:" + patient.getId(), spicsPermissions
				.getPermissionString(spicsPermissions
						.getPatientOwnerPermission(null, null)), "user");
		userPermissionDAO.persist(patientPermission);

		UserPermission patient2Permission = new UserPermission(user
				.getUsername(), "Patient:" + patient2.getId(), spicsPermissions
				.getPermissionString(spicsPermissions
						.getPatientOwnerPermission(null, null)), "user");
		userPermissionDAO.persist(patient2Permission);

		// trialdata
		this.addTrialDataFromXLS(whatForm, patient,
				"trialforms/whatpat1_Daten1.xls");
		this.addTrialDataFromXLS(whatForm, patient,
				"trialforms/whatpat1_Daten2.xls");

		log.info("Ende WHAT Studie-Erstellung");
	}

	@Remove
	@Destroy
	public void destroy() {

	}
}
