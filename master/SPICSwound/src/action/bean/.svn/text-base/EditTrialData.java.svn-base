package bean;

import java.util.List;

import javax.ejb.Local;

import org.richfaces.model.UploadItem;

@Local
public interface EditTrialData extends AbstractTrialDataEditor {

	public String getPatientHeader();
	
	public String chancel();
	
	public void destroy();
	
	// fileupload
	
	public List<UploadItem> getUploads();

	public void setUploads(List<UploadItem> uploads);
	
	public void processFileUpload();
	
	public void cancelUpload();
	
	public void setUploadId(String key);
	
	public void deleteFile(String key, Integer index);
	
}
