package bean;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.ScrollableResults;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;
import org.richfaces.model.UploadItem;

import util.JSFNavigationConstants;
import util.SpicsPermissions;
import util.excel.FullTrialDataExport;
import util.plugin.IPageFragment;
import util.plugin.IPluginRegistry;
import util.xml.IXMLImportExport;
import util.xml.XMLImportExport;
import util.xml.XmlImportExportException;
import db.interfaces.ITrialDataDAO;
import db.interfaces.ITrialFormDAO;
import entities.Attribute;
import entities.AttributeGroup;
import entities.Trial;
import entities.TrialData;
import entities.TrialForm;
import entities.User;

@Stateful
@Scope(ScopeType.SESSION)
@Name("ViewImportExport")
public class ViewImportExportBean implements ViewImportExport {

	private static final long serialVersionUID = 1L;
	//private static final int MAX_FETCHSIZE = 30;
	
	@Logger
	private Log log;

	@EJB
	private ITrialFormDAO trialFormDAO;
	
	@EJB
	private ITrialDataDAO trialDataDAO;

	@In
	private SessionInfo sessionInfo;

	@In
	User user;
	
	@In("PluginRegistry")
	private IPluginRegistry pluginRegistry;

	private Long trialFormExportTfId;


	private transient List<UploadItem> uploads = new LinkedList<UploadItem>();

	/* ui specific */
	public UISelectItems getTrialFormSelectItems() {
		UISelectItems items = new UISelectItems();
		items.setId(getUid());
		LinkedList<SelectItem> selectList = new LinkedList<SelectItem>();
		List<TrialForm> trialForms = sessionInfo.getTrial().getTrialForms();
		
		if(trialForms.size() == 0) {
			SelectItem item = new SelectItem("");
			selectList.add(item);
		} else {
		
			trialFormExportTfId = trialForms.get(0).getId();
			
			for (TrialForm tf : trialForms) {
				SelectItem item = new SelectItem();
				item.setLabel(tf.getName() == null ? "" : tf.getName());
				item.setValue(tf.getId());
				selectList.add(item);
			}
		}
		items.setValue(selectList);
		return items;
	}
	
	public void setTrialFormSelectItems(UISelectItems selectItems) {	}	// required by JSF 1.2

	/* logic */
	public String importTrialForm() {
		try {
			
			if(uploads.size() > 0) {
			
				UploadItem ui = uploads.get(0);
				
				IXMLImportExport xmlExport = new XMLImportExport();
				
				TrialForm uploaded = xmlExport.readTrialFormFromZip(ui.getFile());
				
				log.info("sucessfully unmarshalled trialform #0", uploaded.getName());
				
				Trial t = sessionInfo.getTrial();
				
				uploaded.setTrial(t);
				uploaded.setLastModified(new Date(System.currentTimeMillis()));
				uploaded.setSort(t.getTrialForms().size());
				
				// rewire bidirectional assocations
				for(AttributeGroup ag : uploaded.getAttributeGroups()) {
					ag.setTrialForm(uploaded);
					for(Attribute a : ag.getAttributes()) {
						a.setAttributeGroup(ag);
					}
				}
				
				t.getTrialForms().add(uploaded);
				
				trialFormDAO.persist(uploaded);
				
				log.info("Trialform #0 sucessfully imported!", uploaded.getName());
				FacesMessages.instance().addFromResourceBundle(Severity.INFO, "trialformuploaded.info");
				
			} else {
				log.warn("importTrialForm: no file uploaded");
				FacesMessages.instance().addFromResourceBundle(Severity.ERROR, "error.uploadbeforeimport");
			}
		} catch (XmlImportExportException e) {
			log.error("Import of trialform failed, reason: #0", e.getMessage());
			log.error("Printing stack trace");
			e.printStackTrace();
			FacesMessages.instance().addFromResourceBundle(Severity.ERROR, "error.importfailed");
		} 
		
		uploads = new LinkedList<UploadItem>();
		return JSFNavigationConstants.RELOADPAGE;
	}
	
	/* getters and setters */
	public List<UploadItem> getUploads() {
		return uploads;
	}

	public void setUploads(List<UploadItem> uploads) {
		this.uploads = uploads;
	}	

	public Long getTrialFormExportTfId() {
		return trialFormExportTfId;
	}

	public void setTrialFormExportTfId(Long trialFormExportTfId) {
		this.trialFormExportTfId = trialFormExportTfId;
	}
	
	public boolean canImportTrialForm() {
		return sessionInfo.getTrial().isEditable() && 
			Identity.instance().hasPermission(sessionInfo.getTrial(), SpicsPermissions.EDIT_TRIAL_FORMS);
	}
	
	@PostActivate
	public void postActivate() {
		this.uploads = new LinkedList<UploadItem>();
	}
	
	public void exportAllTrialData() {
		performExport(null);
	}
	
	public void exportOwnTrialData() {
		performExport(user);
	}
	
	private void performExport(User user) {
		Trial trial = sessionInfo.getTrial();

		FullTrialDataExport ftde = new FullTrialDataExport(trial, user == null);
		String filename = "";
		ScrollableResults scrollResult;
		
		if(user == null) {
			Identity.instance().checkPermission(trial, SpicsPermissions.TRIAL_DATA_FULL_EXPORT);
			filename = "fullexport.csv";
			scrollResult = trialDataDAO.getScrollableResultTrailDatasForFullExport(trial.getId());
			
		} else {
			scrollResult = trialDataDAO.getScrollableResultsTrialDatasForPersonalExport(trial.getId(), user.getUsername());
			filename = user.getUsername() + "_export.csv";
		}
		
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		// file should be downloaded without display
		response.setContentType("application/x-download");
		response.setHeader("Content-disposition","attachement; filename=\""+ filename + "\"");
		//response.setContentLength((new Long(srcdoc.length())).intValue());
		
		PrintWriter writer;
		try {
			
			writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "ISO-8859-1"));

			String[][] result= null;
			StringBuffer line;
		
			
			scrollResult.first();
			
			/* get next results */
			while(scrollResult.scroll(1)) {
				/*convert object resultset to trialform list */
				List<TrialData> trialDataList = new ArrayList<TrialData>();
				
				//log.info("The ResultSet is "+ scrollResult.get(),"");
				
				if(scrollResult.get() == null)
					break;
				
				for(Object o: scrollResult.get()) {
					if(o != null)
						trialDataList.add((TrialData) o);
				}
				
				/* write to outputfile */
				result = ftde.export(trialDataList);
				for(int i = 0; i < result.length; i++) {
					line = new StringBuffer();
					for(int j = 0; j < result[i].length; j++) {
						line.append(result[i][j] == null ? "" : result[i][j]);
						line.append(';');
					}
					writer.append(line.toString()).append('\r').append('\n');
				}

				
			}
			
			
			writer.flush();
			writer.close();
			scrollResult.close();
			
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Remove
	@Destroy
	public void destroy() {
		System.out.println("ViewImportExportBean destroyed...");
	}
	
	private String getUid() {
		return FacesContext.getCurrentInstance().getViewRoot().createUniqueId();
	}
	
	public List<IPageFragment> getImportExportFragments() {
		return pluginRegistry.getPageFragments(PAGE_ID);
	}

}
