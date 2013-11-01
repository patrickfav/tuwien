package bean;

import javax.ejb.Local;

@Local
public interface TrialFormEditor extends AbstractTrialFormEditor {
	
	public String deleteAg(Integer sortId);

	public String createAg();
	
	public String getNewAGnach();

	public void setNewAGnach(String newAGnach);
	
	public String getNewAttributeGroupName();

	public void setNewAttributeGroupName(String newAttributeGroupName);
	
	public String back();
	
	public void resetOnLoad();

	public String saveNameAndDescription();
	
	public void destroy();
	
	public boolean isFormEditable();

	public void setFormEditable(boolean formEditable);
}