package bean;

import java.util.LinkedList;

import javax.ejb.Local;
import javax.faces.component.html.HtmlPanelGrid;

import util.ComponentMapKey;

import entities.TrialData;
import entities.TrialForm;

@Local
public interface TrialFormEditorUI {
	
	public void updateMainGrid(HtmlPanelGrid mainGrid, TrialForm tf, Boolean[] renderFlags);

	public void updateMainGrid(HtmlPanelGrid mainGrid, TrialForm tf, Boolean[] renderFlags, String beanName, boolean editExistingAgOnly);
	
	public LinkedList<ComponentMapKey> updateTrialDataEdit(HtmlPanelGrid tsGrid, TrialForm tf, TrialData td, Boolean[] renderFlags, String beanName);
	
	public LinkedList<ComponentMapKey> updateTrialDataEdit(HtmlPanelGrid tsGrid, TrialForm tf, TrialData td,
			Boolean[] renderFlags, String beanName, boolean canFill);
}
