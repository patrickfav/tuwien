package entities.event;

import java.util.List;
import java.util.Map;

import entities.User;

public interface Mailable {
	
	public Map<String, List<User>> getNotificationTargets();
	
	public boolean isNotifyAdmin();
	
	public boolean isNotifyAdminOnly();

}
