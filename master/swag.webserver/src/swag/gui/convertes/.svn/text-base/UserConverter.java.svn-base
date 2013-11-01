package swag.gui.convertes;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import swag.bl.IUserManagement;
import swag.models.User;

@ManagedBean
@RequestScoped
@FacesConverter(value="userConverter",forClass=User.class)
public class UserConverter implements Converter{
	
	@EJB
	private IUserManagement userManagement;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,String value) {
		try {
			System.out.println("userManagement: "+userManagement);
			return userManagement.refreshUser(Long.valueOf(value));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,Object value) {
		return String.valueOf(((User) value).getId());
	}

}
