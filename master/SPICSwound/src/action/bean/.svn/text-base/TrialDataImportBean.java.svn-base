package bean;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;
import org.richfaces.model.UploadItem;

import util.JSFNavigationConstants;
import util.excel.ExcelImportExportException;
import bean.action.TrialDataImportAction;
import bean.action.TrialDataImportResult;
import db.interfaces.ITrialDataDAO;
import entities.TrialData;

@Stateful
@Scope(ScopeType.SESSION)
@Name("TrialDataImport")
public class TrialDataImportBean implements TrialDataImport {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;

	@EJB
	private ITrialDataDAO trialDataDAO;

	@In
	private Long editTrialDataID;

	@In
	private TrialDataImportAction trialDataImportAction;

	@SuppressWarnings("unused")
	@Out(required = false)
	private Boolean resetTDViewer;

	@SuppressWarnings("unused")
	@Out(required = false)
	private Boolean resetTDEditor;

	private transient List<UploadItem> uploads = new LinkedList<UploadItem>();

	private TrialData trialData;

	@Remove
	@Destroy
	public void destroy() {
		System.out.println("TrialDataImportBean destroyed...");
	}

	public String importTrialData() {

		if (uploads.size() == 0) {
			log.warn("importTrialData: no file uploaded");
			FacesMessages.instance().addFromResourceBundle(Severity.ERROR,
					"error.uploadbeforeimport");

			return JSFNavigationConstants.RELOADPAGE;
		}
		
		UploadItem ui = uploads.get(0);

		TrialDataImportResult result = null;
		
		try {
			result = trialDataImportAction.importTrialData(this.editTrialDataID, ui
					.getFile());

		} catch (ExcelImportExportException e) {
			log.error("Import of trialdata failed, reason: #0", e.getMessage());
			log.error("Printing stack trace");
			e.printStackTrace();
			FacesMessages.instance().addFromResourceBundle(Severity.ERROR,
					"error.importfailed", e.getMessage());
			return JSFNavigationConstants.RELOADPAGE;
		}

		log.info("Trialdata #0 sucessfully imported!", ui.getFileName());
		FacesMessages.instance().addFromResourceBundle(Severity.INFO,
				"trialdatauploaded.info", result.getImportCnt(),
				result.getSuccCnt());

		uploads = new LinkedList<UploadItem>();
		resetTDEditor = true;
		return JSFNavigationConstants.EDITTRIALDATA;
	}

	/* getters and setters */
	public List<UploadItem> getUploads() {
		return uploads;
	}

	public void setUploads(List<UploadItem> uploads) {
		this.uploads = uploads;
	}

	@End
	public String cancel() {
		log.info("cancelling - no changes will be stored");

		trialData = trialDataDAO.findByID(this.editTrialDataID);

		if (trialData.getValues().size() == 0) { // remove empty TDs
			trialData.getTrialform().getTrialDatas().remove(trialData);
			trialDataDAO.remove(trialData);
		}

		resetTDViewer = true;
		uploads = new LinkedList<UploadItem>();

		return JSFNavigationConstants.FINISHEDEDITINGTRIALDATA;
	}

	@PostActivate
	public void postActivate() {
		this.uploads = new LinkedList<UploadItem>();
	}
}
