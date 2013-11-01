package bean;

import java.io.Serializable;

import javax.ejb.Local;

@Local
public interface TrialManager extends Serializable {
	
	public void queryTrials();
	
//	public void queryParticipations();
	
	public String selectTrial();
	
	public void setTrialByRequestParameter();
	
	public void destroy();
}
