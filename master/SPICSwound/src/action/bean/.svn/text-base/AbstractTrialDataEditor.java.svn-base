package bean;

import java.util.Map;

import javax.ejb.Local;

@Local
public interface AbstractTrialDataEditor extends AbstractTrialFormViewer {
	
	public static final String SAVEDONCE = "savedOnce";

	public void setUp();
	
	public Map<String, Object> getDataMap();
	
	public String save();
	
	public boolean isSavedOnce();
	
	public void setSavedOnce(boolean savedOnce);
}
