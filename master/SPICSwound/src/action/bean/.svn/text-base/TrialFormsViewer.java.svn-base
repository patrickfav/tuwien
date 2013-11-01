package bean;

import javax.ejb.Local;

import org.jboss.seam.annotations.Factory;

import util.Resettable;

import entities.TrialForm;

@Local
public interface TrialFormsViewer extends Resettable {

	@Factory("trialForms")
	public void setUp();

	public String createTrialForm();

	public String viewTrialForm();
	
	public TrialForm getNewTrialForm();

	public void setNewTrialForm(TrialForm newTrialForm);
	
	public void remove();
	
	public String delete();
	
	public String moveTfUp();
	
	public String moveTfDown();
	
	public boolean isEditable();

	public void setEditable(boolean editable);

}