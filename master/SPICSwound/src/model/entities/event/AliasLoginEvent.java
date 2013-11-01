package entities.event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import entities.User;

@Entity
@DiscriminatorValue("ALIASLOGIN")
public class AliasLoginEvent extends AuthenticationEvent {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private User asUser;
	
	public AliasLoginEvent() {
		super();
		setEventtype(EVENTTYPE.ALIASLOGIN);
	}
	
	public AliasLoginEvent(User user, User asUser) {
		super(user);
		this.asUser = asUser;
		setEventtype(EVENTTYPE.ALIASLOGIN);
	}

	public User getAsUser() {
		return asUser;
	}

	public void setAsUser(User asUser) {
		this.asUser = asUser;
	}

}
