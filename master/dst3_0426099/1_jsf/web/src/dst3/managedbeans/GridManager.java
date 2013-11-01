package dst3.managedbeans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import dst1.model.hardware.Grid;
import dst2.ejb.interfaces.GeneralManagement;

@ManagedBean
public class GridManager {
	
	@EJB
	GeneralManagement gm;

	
	public List<Grid> getAllGrids() {
		return gm.getAllGrids();
	}
	public int getFreeCPUs(Grid g) {
		return gm.getFreeCPUs(g);
	}

	
	/* 
	 * 
	 * GETTER AND SETTER 
	 * 
	 */

	
	
}
