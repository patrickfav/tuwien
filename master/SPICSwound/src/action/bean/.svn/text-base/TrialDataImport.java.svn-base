package bean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import org.richfaces.model.UploadItem;

@Local
public interface TrialDataImport extends Serializable{

	public void destroy();
	
	public String importTrialData();
	
	public List<UploadItem> getUploads();

	public void setUploads(List<UploadItem> uploads);
	
	public String cancel();
	
	public void postActivate();
}
