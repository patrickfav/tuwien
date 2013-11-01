package entities.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.Participation;
import entities.User;

@Entity
@DiscriminatorValue("TRIALEDIT")
public class TrialEditEvent extends TrialEvent implements Mailable {

	private static final long serialVersionUID = 1L;
	
	public TrialEditEvent() {
		super();
		setEventtype(EVENTTYPE.TRIALEDIT);
	}
	
	public Map<String, List<User>> getNotificationTargets() {
		Map<String, List<User>> targets = new HashMap<String, List<User>>();
		List<User> users = new ArrayList<User>();
		for (Participation p: (getTrial().getParticipators())) {
			users.add(p.getUser());
		}
		targets.put("participants", users);
		return targets;
	}
	
	public boolean isNotifyAdmin() {
		return false;
	}
	
	public boolean isNotifyAdminOnly() {
		return false;
	}
	
	public String getEventString() {
		return super.getEventString() + "." + EVENTTYPE.TRIALEDIT.toString();
	}
}
