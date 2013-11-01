package util;

import java.io.Serializable;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import org.jboss.seam.security.permission.Permission;
import org.jboss.seam.security.permission.PermissionManager;

import entities.Patient;
import entities.Trial;

public class SpicsPermissions implements Serializable{

	// Permissions defined on Trials aka Documentations:

	private static final long serialVersionUID = 1L;

	/**
	 * This Permission is required to change the trial meta data (title,
	 * description, attached files, ...)
	 */
	public final static String EDIT_TRIAL_META_DATA = "editTrialMetaData";
	
	/** This Permission is required to add a partner to a trial */
	public final static String PARTNER_MANAGEMENT = "partnerManagement";
	
	/** This Permission is required to export all trial data as CSV */
	public final static String TRIAL_DATA_FULL_EXPORT = "trialDataFullExport";  

	/** This Permission is required to create/edit/import trial forms */
	public final static String EDIT_TRIAL_FORMS = "editTrialForms";
	
	/** This Permission is required to export trial forms */
	public final static String EXPORT_TRIAL_FORMS = "exportTrialForms";
	
	/** This Permission is required to create patients for a trial*/
	public final static String CREATE_PATIENTS = "createPatients";

	/** This Permission is required to view trial data */
	public final static String VIEW_TRIAL_DATA = "viewTrialData";

	/** This Permission is required to edit trial data */
	public final static String EDIT_TRIAL_DATA = "editTrialData";

	// Permissions defined on Patients:
	
	/** This Permission is required to view a patient*/
	public final static String VIEW_PATIENT = "viewPatient";

	/** This Permission is required to edit a patient*/
	public final static String EDIT_PATIENT = "editPatient";

	/** singleton * */
	private static SpicsPermissions instance_ = null;

	static {
		instance_ = new SpicsPermissions();
	}

	public static SpicsPermissions instance() {
		return instance_;
	}

	/** singleton end* */

	private SpicsPermissions() {
	}

	public void grantPartner(Trial trial, Principal principal) {

		PermissionManager.instance().grantPermissions(
				getPartnerPermissions(trial, principal));
	}

	public void revokePartner(Trial trial, Principal principal) {

		PermissionManager.instance().revokePermissions(
				getPartnerPermissions(trial, principal));
	}

	public void grantOwner(Trial trial, Principal principal) {

		PermissionManager.instance().grantPermissions(
				getOwnerPermissions(trial, principal));
	}
	
	public void grantPatientOwner(Patient patient, Principal principal) {

		PermissionManager.instance().grantPermissions(
				getPatientOwnerPermission(patient, principal));
	}

	public void grantPermission(Object entity, String permission,
			Principal principal) {

		Permission p = new Permission(entity, permission, principal);
		PermissionManager.instance().grantPermission(p);
	}

	public List<Permission> getPartnerPermissions(Trial trial,
			Principal principal) {

		List<Permission> permissions = new LinkedList<Permission>();
	
		permissions.add(new Permission(trial, SpicsPermissions.CREATE_PATIENTS,
				principal));		
		permissions.add(new Permission(trial, SpicsPermissions.VIEW_TRIAL_DATA,
				principal));
		permissions.add(new Permission(trial, SpicsPermissions.EDIT_TRIAL_DATA,
				principal));

		return permissions;
	}

	public List<Permission> getOwnerPermissions(Trial trial, Principal principal) {

		List<Permission> permissions = new LinkedList<Permission>();

		permissions.add(new Permission(trial, SpicsPermissions.EDIT_TRIAL_META_DATA,
				principal));
		permissions.add(new Permission(trial,
				SpicsPermissions.PARTNER_MANAGEMENT, principal));	
		permissions.add(new Permission(trial,
				SpicsPermissions.TRIAL_DATA_FULL_EXPORT, principal));	
		permissions.add(new Permission(trial,
				SpicsPermissions.EDIT_TRIAL_FORMS, principal));		
		permissions.add(new Permission(trial,
				SpicsPermissions.EXPORT_TRIAL_FORMS, principal));
		permissions.add(new Permission(trial, SpicsPermissions.CREATE_PATIENTS,
				principal));		
		permissions.add(new Permission(trial, SpicsPermissions.VIEW_TRIAL_DATA,
				principal));
		permissions.add(new Permission(trial, SpicsPermissions.EDIT_TRIAL_DATA,
				principal));

		return permissions;
	}
	
	public List<Permission> getPatientOwnerPermission(Patient patient, Principal principal) {

		List<Permission> permissions = new LinkedList<Permission>();

		permissions.add(new Permission(patient, SpicsPermissions.EDIT_PATIENT,
				principal));
		permissions.add(new Permission(patient,
				SpicsPermissions.VIEW_PATIENT, principal));

		return permissions;
	}

	public String getPermissionString(List<Permission> list) {

		StringBuffer sb = new StringBuffer();

		int i = 0;
		for (; i < list.size() - 1; i++) {
			sb.append(list.get(i).getAction());
			sb.append(',');
		}

		if (list.size() - 1 == i)
			sb.append(list.get(i).getAction());

		return sb.toString();
	}

}
