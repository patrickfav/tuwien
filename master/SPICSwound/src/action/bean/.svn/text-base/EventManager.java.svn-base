package bean;

import java.io.Serializable;

import javax.ejb.Local;

import entities.event.Event;

@Local
public interface EventManager extends Serializable {
	
	public void registerEvent(Event event);
	
	public void registerEvent(Event event, boolean noLog);

}
