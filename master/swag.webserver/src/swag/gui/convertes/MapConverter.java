package swag.gui.convertes;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import swag.bl.IMapManagement;
import swag.models.GameMap;

@ManagedBean
@ViewScoped
@FacesConverter(value="mapConverter",forClass=GameMap.class)
public class MapConverter implements Converter, Serializable {
	
	private static final long serialVersionUID = 2617620420187985382L;
	
	@EJB
	private IMapManagement mapManagement;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,String value) {
		try {
			System.out.println("mapManagement: "+mapManagement+ " - value: " + value + " " + mapManagement.getMap(Long.valueOf(value)).getName());
			return mapManagement.getMap(Long.valueOf(value));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,Object value) {
		return String.valueOf(((GameMap) value).getId());
	}

}
