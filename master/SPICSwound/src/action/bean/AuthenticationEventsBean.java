package bean;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.LocaleSelector;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.management.JpaIdentityStore;

import util.BeanResetter;
import entities.OrgGroup;
import entities.User;
import entities.event.LoginEvent;

@Stateful
@Scope(ScopeType.SESSION)
@Name("authenticationEvents")
public class AuthenticationEventsBean implements AuthenticationEvents {
	@Logger
	private Log log;

	@SuppressWarnings("unused")
	@Out(scope = ScopeType.SESSION, required = false)
	private User user;

	@SuppressWarnings("unused")
	@Out(scope = ScopeType.SESSION, required = false)
	private OrgGroup group;
	
	@SuppressWarnings("unused")
	@Out(scope = ScopeType.SESSION, required = false)
	@EJB
	private BeanResetter beanResetter;
	
	@SuppressWarnings("unused")
	@Out(required = false, scope = ScopeType.SESSION)
	@EJB
	private SessionInfo sessionInfo;

	@EJB
	private EventManager eventManager;

	@Observer(JpaIdentityStore.EVENT_USER_AUTHENTICATED)
	public void loginSuccessful(User user) {
		
		this.log.info("Authenticated #0", user.getUsername());
		
		this.user = user;
		
		// TODO: get from login
		this.group = user.getGroups().size() != 0 ? user.getGroups().get(0)
				: null;

		LocaleSelector ls = LocaleSelector.instance();

		// check if the user has a locale set and we have a web request
		// should not be called for soap requests
		if (user.getPreferredLocale() != null
				&& !ls.getLocale().getLanguage().equals(
						user.getPreferredLocale()) &&
						FacesContext.getCurrentInstance() != null) {
			ls.selectLanguage(user.getPreferredLocale());
			log.info("Changing locale to preferred locale #0", user
					.getPreferredLocale());
		}

		eventManager.registerEvent(new LoginEvent(user));
	}

	@Remove
	@Destroy
	public void remove() {
	}
}
