package entities.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import entities.Participation;
import entities.TRIALSTATUS;
import entities.User;

@Entity
@DiscriminatorValue(value="STATUSCHANGE")
public class StatusChangeEvent extends TrialEditEvent implements Mailable {

	private static final long serialVersionUID = 1L;
	
	@Enumerated(EnumType.STRING)
    @Column(name="STATUSEVENT_FROM")
    private TRIALSTATUS from;
	
	@Enumerated(EnumType.STRING)
    @Column(name="STATUSEVENT_TO")
    private TRIALSTATUS to;
	
	public StatusChangeEvent() {
		super();
		setEventtype(EVENTTYPE.STATUSCHANGE);
	}
	
	public StatusChangeEvent(TRIALSTATUS from, TRIALSTATUS to) {
		super();
		setEventtype(EVENTTYPE.STATUSCHANGE);
		this.from = from;
		this.to = to;
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

	public TRIALSTATUS getFrom() {
		return from;
	}

	public void setFrom(TRIALSTATUS from) {
		this.from = from;
	}

	public TRIALSTATUS getTo() {
		return to;
	}

	public void setTo(TRIALSTATUS to) {
		this.to = to;
	}
	
	public String getEventString() {
		return super.getEventString() + "." + EVENTTYPE.STATUSCHANGE.toString();
	}
	
	public boolean isNotifyAdmin() {
		return false;
	}

	public boolean isNotifyAdminOnly() {
		return false;
	}
	
	@Override
	public String getEventText() {
		return "from: " + from + " to: " + to;
	}

}
