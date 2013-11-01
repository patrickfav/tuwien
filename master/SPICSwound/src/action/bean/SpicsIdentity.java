package bean;

import javax.ejb.EJB;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import entities.User;
import entities.event.LogoutEvent;

/**
 * Custom Identity to register a user initiated logout and to be able to distinguish
 * between a logout and a session timeout
 * 
 * @author inso
 *
 */

@Name("org.jboss.seam.security.identity")
@Scope(ScopeType.SESSION)
@Install(precedence = Install.APPLICATION)
@BypassInterceptors
@Startup
public class SpicsIdentity extends Identity {

	private static final long serialVersionUID = 1L;

	@EJB private EventManager eventManager;
	
	@Logger
	private Log log;
	
	private boolean loggedOut = false;	// needed for the preDestroyContext.SESSION observer
	
	@Override
	public void logout() {
		// check if we are logged in
		if (isLoggedIn()) {
			String username = this.getCredentials().getUsername();
			log.info("logout called for user #0", username);
			
			if(eventManager == null) {
				eventManager = (EventManager)Component.getInstance(EventManagerBean.class, true);
			}
			// throw spics event
			eventManager.registerEvent(new LogoutEvent(new User(username)));
			
			super.logout();
			
			loggedOut = true;
		} else {
			log.info("logout called for not logged in user");
		}
	}
	
	public boolean isLoggedOut() {
		return loggedOut;
	}
	
	public boolean equalsLoggedInUser(String username) {
		return this.getCredentials().getUsername().equals(username);
	}

}
