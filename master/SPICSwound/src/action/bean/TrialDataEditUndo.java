package bean;

import javax.ejb.Local;

import entities.event.EVENTTYPE;
import entities.event.ValueEvent;

@Local
public interface TrialDataEditUndo {
	
	public void getChanges();
	
	public void remove();
	
	public String getEventIcon(EVENTTYPE type);
	
	public String getAttributeName(Long id);
	
	public String undo(ValueEvent ve);
	
	public String back();

}
