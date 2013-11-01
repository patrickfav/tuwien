package bean;

import java.io.Serializable;

import javax.ejb.Local;
import javax.faces.event.ValueChangeEvent;

@Local
public interface TrialStatusManager extends Serializable {

	public void statusChanged(ValueChangeEvent vce);
	
	public String changeStatus();
	
	public void destroy();
}
