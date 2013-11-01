package bean;

import java.io.Serializable;

import javax.ejb.Local;

import entities.Trial;

@Local
public interface SessionInfo extends Serializable {
	
	public void setTrialID(Long id);
	
	public Long getTrialID();
	
	public Trial getTrial();
	
	public void destroy();

	public void newTrial();

}
