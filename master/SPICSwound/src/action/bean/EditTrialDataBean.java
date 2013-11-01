package bean;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;
import org.richfaces.event.FileUploadListener;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import util.BeanResetter;
import util.JSFNavigationConstants;
import util.SpicsPermissions;
import db.interfaces.ITrialDataDAO;
import entities.FileSet;
import entities.TrialAttachment;
import entities.User;

@Stateful
@Scope(ScopeType.SESSION)
@Name("EditTrialData")
public class EditTrialDataBean extends AbstractTrialDataEditorBean implements
		EditTrialData, FileUploadListener {

	private static final long serialVersionUID = 1L;

	public static final String BEANNAME = "EditTrialData";

	@In(required = false)
	private Long editTrialDataID;

	@In
	private User user;

	@EJB
	private ITrialDataDAO trialDataDAO;

	@Logger
	private Log log;

	@In
	private transient Map<String, String> messages;

	@In(required = false)
	private BeanResetter beanResetter;

	@In(required = false)
	@Out
	private Boolean resetTDEditor;

	@SuppressWarnings("unused")
	@Out(required = false)
	private Boolean resetTDViewer;

	private transient List<UploadItem> uploads = new LinkedList<UploadItem>();

	private String currentUploadKey;

	public String getPatientHeader() {

		if (super.trialData == null)
			super.trialData = trialDataDAO.findByID(editTrialDataID);

		return messages.get("label.patient") + ": "
				+ super.trialData.getPatient().getKennnummer();
	}

	public String chancel() {
		log.info("cancelling - no changes will be stored");
		if (this.trialData.getValues().size() == 0) { // remove empty TDs
			log.info("removing empty TrialData entity");
			super.trialData = trialDataDAO.findByID(trialData.getId());
			trialData.getTrialform().getTrialDatas().remove(trialData);
			trialDataDAO.remove(trialData);
		}

		filesToDelete = new LinkedList<TrialAttachment>();
		setSavedOnce(false);
		resetTDViewer = true;
		return JSFNavigationConstants.FINISHEDEDITINGTRIALDATA;
	}

	@End
	public String save() {
		resetTDViewer = true;
		String result =  super.save();
		
		trialDataDAO.flush();
		
		return result;
	}

	@Remove
	@Destroy
	public void destroy() {
		mainGrid = null;
	}

	@Begin(join=true)
	public void setUp() {
		log.info("setup called, reloading trialdata, conversationid #0", Conversation.instance().getId());
		beanResetter.addGridBean(this);
		setSavedOnce(false);

		super.trialData = trialDataDAO.findByID(editTrialDataID);

		super.tf = super.trialData.getTrialform();
		super.beanName = BEANNAME;

		super.user = user;

		log.info("data loaded, conversationid #0", Conversation.instance().getId());
		super.setUp();

	}

	public void reset() {
		log.info("EditTrialData: resetting grids ");
		super.reset();
	}

	@Override
	protected boolean wantToReset() {
		boolean doreset = resetTDEditor == null ? false : resetTDEditor
				.booleanValue();
		log.info("testing if we need to reset: #0", doreset);
		resetTDEditor = false;
		return doreset;
	}

	/**
	 * for the special case when the logged in user can view but not edit a
	 * trialform for a specific patient because he is neither the owner of the
	 * patient, nor an administrator!
	 * 
	 * for all other cases the update method of the super class is called!
	 */
	@Override
	protected void updateGrid() {

		if (super.trialData.getTrialform().isFillable()
				&& !(Identity.instance().hasPermission(super.trialData
						.getPatient(), SpicsPermissions.EDIT_PATIENT))) {
			super.keyList = ui.updateTrialDataEdit(super.mainGrid, trialData
					.getTrialform(), trialData, renderFlags, beanName, false);
			return;
		}

		super.updateGrid();

	}

	public void processUpload(UploadEvent event) {
		log.info("File uploaded...");
	}

	public List<UploadItem> getUploads() {
		return uploads;
	}

	public void setUploads(List<UploadItem> uploads) {
		this.uploads = uploads;
	}

	public void processFileUpload() {
		if (uploads.size() == 0) {
			log.warn("cannot do anything, no files uploaded!!");
			return;
		}
		log.info("ProcessFileUpload called, uploads size: #0 for key #1",
				uploads.size(), currentUploadKey);

		FileSet fs = (FileSet) getDataMap().get(currentUploadKey);

		for (UploadItem ui : uploads) {
			TrialAttachment ta = new TrialAttachment();
			try {
				ta.loadUploadItem(ui);
			} catch (IOException e) {
				log.warn("fileupload failed! due to #0", e.getMessage());
				// TODO: only for debugging - remove
				e.printStackTrace();
			}
			// wire
			ta.setFileSet(fs);
			fs.getFiles().add(ta);
		}

		uploads = new LinkedList<UploadItem>();
		currentUploadKey = null;
	}

	public void cancelUpload() {
		log.info("file upload canceled");
		uploads = new LinkedList<UploadItem>();
		currentUploadKey = null;
	}

	@PostActivate
	public void postActivate() {
		this.uploads = new LinkedList<UploadItem>();
	}

	public void setUploadId(String key) {
		log.info("upload window opened for component with key: #0", key);
		currentUploadKey = key;
	}

	public void deleteFile(String key, Integer index) {
		log.info("deleteFile called with params #0 #1", key, index);

		FileSet fs = (FileSet) getDataMap().get(key);

		TrialAttachment ta = fs.getFiles().get(index.intValue());
		ta.setFileSet(null);

		filesToDelete.add(ta);

		fs.getFiles().remove(index.intValue());
	}

}
