package dst2.ejb.interfaces;

import java.util.List;

import javax.ejb.Remote;

import dst1.model.hardware.Grid;

@Remote
public interface GeneralManagement {
	public List<Grid> getAllGrids();
	public int getFreeCPUs(Grid g);
	public boolean isUserNameFree(String usr);
	public void persist(Object o);
}
