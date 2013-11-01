package bean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import entities.User;

@Local
public interface UserEditor extends Serializable {

	public User getUser();
	
	public String saveUser();
	
	public String cancelUser();
	
	
	public String saveEvents();
	
	public List<String> getSelectedEvents();
	
	public void setSelectedEvents(List<String> selectedEvents);
	
	public List<String> getAllEvents();
	
	public void setAllEvents(List<String> allEvents);
	
	public String getEventLabel(String eventName);
	

	public String changePassword();

	public String getOldPassword();
	
	public void setOldPassword(String oldPassword);
	
	public String getNewPassword();
	
	public void setNewPassword(String newPassword);
	
	public String getNewPassword2();
	
	public void setNewPassword2(String newPassword2);
	
	
	public void destroy();
	
}
