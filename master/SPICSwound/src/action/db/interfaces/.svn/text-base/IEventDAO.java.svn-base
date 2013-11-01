package db.interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.event.EVENTTYPE;
import entities.event.Event;
import entities.event.ValueEvent;

@Local
public interface IEventDAO extends IGenericDAO<Event, Long>{
	
	public List<Event> findAll();
	
	public List<Event> findNewest(int maxResults);
	
	public List<Event> findByType(EVENTTYPE type, int maxResults);
	
	public List<Event> findByUsername(String username, int maxResults);
	
	public List<ValueEvent> findByTrialData(Long trialDataId);

	public List<ValueEvent> getNewerEvents(ValueEvent ve);
	
}
