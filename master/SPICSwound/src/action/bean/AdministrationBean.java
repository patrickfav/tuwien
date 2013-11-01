package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.management.IdentityManager;
import org.jboss.seam.security.management.JpaIdentityStore;

import util.JSFNavigationConstants;
import db.interfaces.IGroupDAO;
import db.interfaces.IUserDAO;
import entities.OrgGroup;
import entities.User;
import entities.UserRole;
import entities.event.EVENTTYPE;

@Stateful
@Scope(ScopeType.SESSION)
@Name("Administration")
@Restrict("#{s:hasRole('admin')}")
public class AdministrationBean implements Administration {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;

	@EJB
	private IUserDAO userDAO;

	@EJB
	private IGroupDAO groupDAO;

	@DataModel("usersDataModel")
	private List<User> users;

	@In
	private IdentityManager identityManager;

	@In(required = false)
	@Out(required = false, scope = ScopeType.CONVERSATION)
	private User selectedUser;

	@In(value = "user")
	@Out(value = "user", scope = ScopeType.SESSION)
	private User loggedInUser;

	private List<String> roles = new ArrayList<String>();

	private String newPassword;
	private String newPassword2;

	// search attributes
	private String searchString;
	private boolean searchEnabled;

	// data scroller current page
	private int page;

	// fixed values
	private static final int MAX_PAGES = 20;
	private static final int ROW_COUNT = 15;

	@Destroy
	@Remove
	public void destroy() {
	}

	@Begin(join = true)
	public String editUser(String id) {
		selectedUser = userDAO.findByID(id);
		
		roles = new ArrayList<String>();

		if (selectedUser != null) {

			for (UserRole role : selectedUser.getRoles()) {

				roles.add(role.getRolename());
			}
		}
		
		return JSFNavigationConstants.ADMINISTRATION_EDIT;
	}

	@Begin(join = true)
	public String createUser() {
		selectedUser = new User();
		return JSFNavigationConstants.ADMINISTRATION_CREATE;
	}

	public String saveUser() {

		if (StringUtils.equals(newPassword, newPassword2) == false) {

			log.info("the entered passwords don't match");
			FacesMessages.instance().addFromResourceBundle(Severity.ERROR,
					"error.pwsdontmatch");
			return JSFNavigationConstants.RELOADPAGE;
		}

		// new user
		if (identityManager.userExists(selectedUser.getUsername()) == false) {

			identityManager.createUser(selectedUser.getUsername(), newPassword,
					selectedUser.getFirstname(), selectedUser.getLastname());
			for (String role : roles) {
				identityManager.grantRole(selectedUser.getUsername(), role);
			}
			selectedUser.setNotifyOnEvents(false);
			selectedUser.setNotifyEvents(EVENTTYPE.getMailableNonAdminEventNames());
			
			userDAO.merge(selectedUser);
		} else {

			boolean changePW = false;

			if (newPassword != null || newPassword2 != null) {
				changePW = true;
			}

			userDAO.merge(selectedUser);

			if (changePW) {
				identityManager.changePassword(selectedUser.getUsername(),
						newPassword);
			}

			// update roles
			List<String> grantedRoles = identityManager
					.getGrantedRoles(selectedUser.getUsername());

			if (grantedRoles != null) {
				for (String role : grantedRoles) {
					if (!roles.contains(role))
						identityManager.revokeRole(selectedUser.getUsername(),
								role);
				}
			}

			for (String role : roles) {
				if (grantedRoles == null || !grantedRoles.contains(role)) {
					identityManager.grantRole(selectedUser.getUsername(), role);
				}
			}

			selectedUser = userDAO.findByID(selectedUser.getUsername());

			// check if updated user is equal to logged in user
			if (loggedInUser.getUsername().equals(selectedUser.getUsername())) {
				loggedInUser = selectedUser;
			}

			log.info("User successfully updated");
			FacesMessages.instance().addFromResourceBundle(Severity.INFO,
					"userupdated.info");
		}

		return JSFNavigationConstants.ADMINISTRATION;
	}

	@Observer(JpaIdentityStore.EVENT_PRE_PERSIST_USER)
	public void saveAdditionalFields(User user) {

		OrgGroup testGroup = groupDAO.findByName("testgroup");
		user.getGroups().add(testGroup);
		testGroup.getUsers().add(user);
		user.setEnabled(selectedUser.isEnabled());
		user.setEmail(selectedUser.getEmail());
		user.setOrganisation(selectedUser.getOrganisation());
		user.setPreferredLocale(selectedUser.getPreferredLocale());
		user.setScreenname(selectedUser.getScreenname());
		user.setTitle(selectedUser.getTitle());

		users = null;
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

	public List<String> getRoles() {

		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String cancelEditing() {
		return JSFNavigationConstants.ADMINISTRATION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bean.Administration#getSearchString()
	 */
	public String getSearchString() {
		return searchString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bean.Administration#setSearchString(java.lang.String)
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bean.Administration#resetSearchString()
	 */
	public void resetSearchString() {
		searchString = null;
		findUsers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bean.Administration#getSearchEnabled()
	 */
	public boolean getSearchEnabled() {
		return searchEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bean.Administration#setSearchEnabled(boolean)
	 */
	public void setSearchEnabled(boolean searchEnabled) {
		this.searchEnabled = searchEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bean.Administration#findUsers()
	 */
	@Factory("usersDataModel")
	public String findUsers() {
		log.info(String.format("find users, searchString=%s, searchEnabled=%s",
				searchString, searchEnabled));
		users = userDAO
				.find(searchString, searchEnabled, MAX_PAGES * ROW_COUNT);

		log.info(String.format("%s users found", users.size()));
		page = 1;

		return JSFNavigationConstants.RELOADPAGE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bean.Administration#getPage()
	 */
	public int getPage() {
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bean.Administration#setPage(int)
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bean.Administration#getMAX_PAGES()
	 */
	public int getMAX_PAGES() {
		return MAX_PAGES;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bean.Administration#getROW_COUNT()
	 */
	public int getROW_COUNT() {
		return ROW_COUNT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bean.Administration#getLastLoginDate(entities.User)
	 */
	public Date getLastLoginDate(User user) {
		return userDAO.getLastLoginDate(user);
	}
}
