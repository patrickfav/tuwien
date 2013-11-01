package swag.gui.convertes;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import swag.bl.IBaseManagement;
import swag.models.Base;

@ManagedBean
@ViewScoped
@FacesConverter(value="baseConverter",forClass=Base.class)
public class BaseConverter implements Converter, Serializable {
	
	private static final long serialVersionUID = 2617620420187985382L;
	
	@EJB
	private IBaseManagement baseManagement;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,String value) {
		try {
			System.out.println("baseManagement: " + baseManagement);
			return baseManagement.refreshBase(Long.valueOf(value));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,Object value) {
		return String.valueOf(((Base) value).getId());
	}

}
