package entities.event;

import entities.User;

public abstract class AuthenticationEvent extends Event {

	@Override
	public String getEventString() {
		return EVENTSTRINGBASE + "auth.";
	}
	
	public AuthenticationEvent() { }
	
	public AuthenticationEvent(User user) {
		super(user);
	}

}
