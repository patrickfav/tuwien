package bean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import org.richfaces.model.UploadItem;

import entities.TRIALSTATUS;
import entities.Trial;

@Local
public interface TrialEditor extends Serializable {
  
  public Trial getTrial();
  
  public void setTrial(Trial trial);
	
	public String saveTrial();
	
	public String cancelTrial();
	
	public void onLoad();
	
	public boolean isNewTrial();
	
	public TRIALSTATUS getStatus();

	public void setStatus(TRIALSTATUS status);
	
	public List<UploadItem> getUploads();

	public void setUploads(List<UploadItem> uploads);
	
	public void deleteTrialAttachment(Long trialAttachmentId);
	
	public void destroy();

}
