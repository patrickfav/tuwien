package dst3.managedbeans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dst1.model.user.User;
import dst2.ejb.interfaces.GeneralManagement;
import dst3.util.I18nUtil;

@ManagedBean
public class UserManager {

	@EJB
	GeneralManagement gm;

	private User user= new User();
	private String checkPassword="";
	
	public UserManager() {
		
	}
	
	public String save() {
		
		if(!user.getPassword().equals(checkPassword)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							I18nUtil.getInstance(FacesContext.getCurrentInstance()).getString("password_does_not_match"), null));
			
			return "register.xhtml";
		}
		
		if(!gm.isUserNameFree(user.getUsername())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							I18nUtil.getInstance(FacesContext.getCurrentInstance()).getString("username_already_taken"), null));
			
			return "register.xhtml";
		}
		
		System.out.println("USER:   "+user);
		
		gm.persist(user);
		
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						I18nUtil.getInstance(FacesContext.getCurrentInstance()).getString("info.successfully_inserted"), null));

		return "home.xhtml";
	}

	/*
	 * 
	 * GETTER AND SETTER
	 */

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword;
	}

	public String getCheckPassword() {
		return checkPassword;
	}
}
