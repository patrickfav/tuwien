package util;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import bean.EventManager;
import bean.SpicsIdentity;
import entities.User;
import entities.event.SessionTimeoutEvent;

@SuppressWarnings("serial")
@Stateless
@Name("SessionTimeoutHandler")
public class SessionTimeoutHandlerBean implements SessionTimeoutHandler {

	@Logger
	private Log log;
	
	@EJB private EventManager eventManager;
	
	@Observer("org.jboss.seam.preDestroyContext.SESSION")
	public void handleSessionTimeout() {
		
		Identity i = Identity.instance();
		
		if (i instanceof SpicsIdentity) {
			SpicsIdentity spicsIdentity = (SpicsIdentity) i;
			String username = spicsIdentity.getCredentials().getUsername();
			
			boolean isLoggedIn = spicsIdentity.isLoggedIn();
			
			log.info("checking if user is logged in #0", isLoggedIn);
			
			if(!isLoggedIn)
				return;
			
			if(username == null)	// falscher alarm... 
				return;
			
			if(spicsIdentity.isLoggedOut())
				return;	// already got logout event
			
			log.info("SessionTimeoutHandler called... for #0", username);
			eventManager.registerEvent(new SessionTimeoutEvent(new User(username)));
		} else {
			log.info("Could not handle session timeout, expected identity of type #0 but got #1", SpicsIdentity.class, i.getClass());
			return;
		}
	}

}
