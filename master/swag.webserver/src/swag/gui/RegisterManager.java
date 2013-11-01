package swag.gui;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import swag.bl.IUserManagement;
import swag.exceptions.UserManagementException;
import swag.models.User;
import swag.utils.MessageUtil;

@ManagedBean
@RequestScoped
public class RegisterManager implements Serializable {

	private static final long serialVersionUID = 8659622388210140920L;

	@EJB
	private IUserManagement userManagement;
	
	private FacesContext context = FacesContext.getCurrentInstance();
	
	public User user = new User();
	
    public String register() {   	
    	try {
			userManagement.registerUser(user);
		} catch (UserManagementException e) {	
			FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", MessageUtil.loadMessage(context, "register_error"));
			context.addMessage(null, error);
			return "/public/register.xhtml";
		}
		FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", MessageUtil.loadMessage(context, "register_success"));
		context.addMessage(null, success);
    	return "/public/home.xhtml";
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
