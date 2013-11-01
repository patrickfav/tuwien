package entities.event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.User;

@Entity
@DiscriminatorValue(value="LOGIN")
public class LoginEvent extends AuthenticationEvent {

	private static final long serialVersionUID = 1L;
	
	public LoginEvent() {
		super();
		setEventtype(EVENTTYPE.LOGIN);
	}
	
	public LoginEvent(User user) {
		super(user);
		setEventtype(EVENTTYPE.LOGIN);
	}
	
	public String getEventString() {
		return super.getEventString() + EVENTTYPE.LOGIN.toString();
	}

}
