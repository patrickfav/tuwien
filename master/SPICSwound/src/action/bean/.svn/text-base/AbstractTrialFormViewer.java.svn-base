package bean;

import javax.ejb.Local;
import javax.faces.component.html.HtmlPanelGrid;

import util.Resettable;
import entities.TrialForm;

@Local
public interface AbstractTrialFormViewer extends AgRenderer, Resettable {
	
	public void setUp();

	public String moveAgDown(Integer sortId);

	public String moveAgUp(Integer sortId);
	
	public HtmlPanelGrid getMainGrid();

	public void setMainGrid(HtmlPanelGrid mainGrid);
	
	public TrialForm getTrialForm();

}
