package bean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.faces.component.UISelectItems;

import org.richfaces.model.UploadItem;

import util.plugin.IPageFragment;

@Local
public interface ViewImportExport extends Serializable {
	
	public static final String PAGE_ID = "viewImportExport.xhtml";

	public void destroy();
	
	public String importTrialForm();
	
	public List<UploadItem> getUploads();

	public void setUploads(List<UploadItem> uploads);
	
	public UISelectItems getTrialFormSelectItems();
	
	public void setTrialFormSelectItems(UISelectItems selectItems);
	
	public Long getTrialFormExportTfId();

	public void setTrialFormExportTfId(Long trialFormExportTfId);
	
	public void postActivate();
	
	public boolean canImportTrialForm();
	
	public void exportAllTrialData();
	
	public void exportOwnTrialData();
	
	public List<IPageFragment> getImportExportFragments();
}
