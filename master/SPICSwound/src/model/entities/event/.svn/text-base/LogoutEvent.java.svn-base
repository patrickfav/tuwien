package entities.event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.User;

@Entity
@DiscriminatorValue(value="LOGOUT")
public class LogoutEvent extends AuthenticationEvent {

	private static final long serialVersionUID = 1L;
	
	public LogoutEvent() {
		super();
		setEventtype(EVENTTYPE.LOGOUT);
	}
	
	public LogoutEvent(User user) {
		super(user);
		setEventtype(EVENTTYPE.LOGOUT);
	}
	
	public String getEventString() {
		return super.getEventString() + EVENTTYPE.LOGOUT.toString();
	}


}
