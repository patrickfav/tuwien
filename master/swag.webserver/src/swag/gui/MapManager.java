package swag.gui;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import swag.bl.IMapManagement;
import swag.models.GameMap;

@ManagedBean
@RequestScoped
public class MapManager implements Serializable {

	private static final long serialVersionUID = -4299812300015030909L;
	
	@EJB
	private IMapManagement mapManagement;
	
	public List<GameMap> getAllMaps() {
		return mapManagement.getAllMaps();
	}
	
	public GameMap getMapById(long id) {
		return mapManagement.getMap(id);
	}
	
}
