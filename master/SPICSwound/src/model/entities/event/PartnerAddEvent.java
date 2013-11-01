package entities.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.Participation;
import entities.User;

@Entity
@DiscriminatorValue("PARTNERADD")
public class PartnerAddEvent extends TrialEvent implements Mailable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="PARTNER_ID")
	private String partnerId;
	
	public PartnerAddEvent() {
		super();
		setEventtype(EVENTTYPE.PARTNERADD);
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
	
	public String getEventString() {
		return super.getEventString() + "." + EVENTTYPE.PARTNERADD.toString();
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	public boolean isNotifyAdmin() {
		return false;
	}
	
	public boolean isNotifyAdminOnly() {
		return false;
	}
	
}
