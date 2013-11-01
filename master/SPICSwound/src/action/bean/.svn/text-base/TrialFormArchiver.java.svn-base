package bean;

import java.io.Serializable;

import javax.ejb.Local;

@Local
public interface TrialFormArchiver extends Serializable {

	public void destroy();
	
	public String doArchive();
	
	public String cancelArchive();
	
	public boolean isStillFillable();
	
	public void setStillFillable(boolean stillFillable);
}
