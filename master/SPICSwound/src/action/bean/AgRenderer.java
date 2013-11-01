package bean;

import javax.ejb.Local;

@Local
public interface AgRenderer {

	public Integer getCurrentSortId();

	public void setCurrentSortId(Integer currentSortId);
	
	public String toggleRendering(Integer sortId);

}
