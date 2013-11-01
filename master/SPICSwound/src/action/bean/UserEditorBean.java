package bean;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.LocaleSelector;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.RunAsOperation;
import org.jboss.seam.security.management.IdentityManager;

import util.JSFNavigationConstants;
import util.MessageUtils;
import db.interfaces.IUserDAO;
import entities.User;
import entities.event.EVENTTYPE;

@Stateful
@Scope(ScopeType.CONVERSATION)
@Name("userEditor")
public class UserEditorBean implements UserEditor {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;

	@In
	private User user;

	@In("Menu")
	private Menu menu;

	@In
	private IdentityManager identityManager;
	
	@EJB
	private IUserDAO userDAO;

	private String oldPassword = "";
	private String newPassword = "";
	private String newPassword2 = "";
	
	@In(value = "MessageUtils", create = true)
	private MessageUtils messageUtils;

	private List<String> selectedEvents;
	private List<String> allEvents;

	public User getUser() {
		return user;
	}

	public String saveUser() {

		LocaleSelector ls = LocaleSelector.instance();
		if (user.getPreferredLocale() != null
				&& !ls.getLocale().getLanguage().equals(
						user.getPreferredLocale())) {
			ls.selectLanguage(user.getPreferredLocale());
			log.info("Changing locale to preferred locale #0", user
					.getPreferredLocale());
		}

		userDAO.merge(user);

		log.info("user #0 sucessfully updated!", user.getUsername());
		FacesMessages.instance().addFromResourceBundle(Severity.INFO, "userupdated.info");
		return JSFNavigationConstants.RELOADPAGE;
	}

	@End
	public String cancelUser() {
		log.info("Cancel editing user");

		return menu.setActive(JSFNavigationConstants.SHOWMYSTUDIES);
	}

	@End
	public String changePassword() {

		if (identityManager.authenticate(user.getUsername(), oldPassword) == false) {
			FacesMessages.instance().addFromResourceBundle(Severity.ERROR, "error.oldpwnotcorrect");

			return JSFNavigationConstants.RELOADPAGE;
		}

		if (newPassword.equals(newPassword2) == false) {
			FacesMessages.instance().addFromResourceBundle(Severity.ERROR, "error.pwsdontmatch");

			return JSFNavigationConstants.RELOADPAGE;
		}

		new RunAsOperation() {
			public void execute() {
				identityManager.changePassword(user.getUsername(), newPassword);
			}
		}.addRole("admin").run();
		
		this.user = userDAO.findByID(user.getUsername());

		FacesMessages.instance().addFromResourceBundle(Severity.INFO, "pwupdated.info");

		return JSFNavigationConstants.RELOADPAGE;
	}
	
	@End
	public String saveEvents() {
		user.setNotifyEvents(selectedEvents);
		
		userDAO.merge(user);

		log.info("events for user #0 sucessfully updated!", user.getUsername());
		FacesMessages.instance().addFromResourceBundle(Severity.INFO, "userupdated.info");
		return JSFNavigationConstants.RELOADPAGE;
	}
	
	public String getEventLabel(String eventName) {
		return messageUtils.get("label." + eventName);
	}
	
	@Remove
	@Destroy
	public void destroy() {
		log.info("destroyed...");
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPassword2() {
		return newPassword2;
	}

	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}

	public List<String> getSelectedEvents() {
		if (selectedEvents == null) {
			this.user = userDAO.findByID(user.getUsername());
			selectedEvents = user.getNotifyEvents();
		}
		return selectedEvents;
	}

	public void setSelectedEvents(List<String> selectedEvents) {
		this.selectedEvents = selectedEvents;
	}

	public List<String> getAllEvents() {
		return EVENTTYPE.getMailableNonAdminEventNames();
	}

	public void setAllEvents(List<String> allEvents) {
		this.allEvents = allEvents;
	}
}
