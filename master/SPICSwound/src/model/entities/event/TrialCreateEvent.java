package entities.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.User;

@Entity
@DiscriminatorValue("TRIALCREATE")
public class TrialCreateEvent extends TrialEvent implements Mailable {

	private static final long serialVersionUID = 1L;
	private static final Map<String, List<User>> notificationTargets = new HashMap<String, List<User>>(); 
	
	public TrialCreateEvent() {
		super();
		setEventtype(EVENTTYPE.TRIALCREATE);
	}
	
	public Map<String, List<User>> getNotificationTargets() {
		return notificationTargets;
	}
	
	public boolean isNotifyAdmin() {
		return true;
	}
	
	public boolean isNotifyAdminOnly() {
		return true;
	}
	
	public String getEventString() {
		return super.getEventString() + "." + EVENTTYPE.TRIALCREATE.toString();
	}

}
