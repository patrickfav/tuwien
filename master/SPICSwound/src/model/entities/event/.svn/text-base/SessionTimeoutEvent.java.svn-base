package entities.event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.User;

@Entity
@DiscriminatorValue("TIMEOUT")
public class SessionTimeoutEvent extends AuthenticationEvent {

	private static final long serialVersionUID = 1L;
	
	public SessionTimeoutEvent() {
		super();
		setEventtype(EVENTTYPE.TIMEOUT);
	}
	
	public SessionTimeoutEvent(User user) {
		super(user);
		setEventtype(EVENTTYPE.TIMEOUT);
	}
	
	public String getEventString() {
		return super.getEventString() + EVENTTYPE.TIMEOUT.toString();
	}

}
