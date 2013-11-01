package db.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.IEventDAO;
import entities.event.EVENTTYPE;
import entities.event.Event;
import entities.event.ValueEvent;

@Stateless
@Name("EventDAO")
public class EventDAO implements IEventDAO {

	@In
	private EntityManager entityManager;
	
	public Event findByID(Long id) {
		return entityManager.find(Event.class, id);
	}

	public Event merge(Event event) {
		return entityManager.merge(event);
	}

	public void persist(Event event) {
		entityManager.persist(event);
	}

	public void remove(Event event) {
		entityManager.remove(event);
	}
	
	public void refresh(Event event) {
		entityManager.refresh(event);
	}

	/**
	 * returns all Events, newest ones first! 
	 */
	@SuppressWarnings("unchecked")
	public List<Event> findAll() {
		return (List<Event>)entityManager.createQuery("from Event e ORDER BY e.timestamp desc").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> findNewest(int maxResults) {
		Query q = entityManager.createQuery("from Event e ORDER BY e.timestamp desc");
		q.setMaxResults(maxResults);
		return (List<Event>)q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> findByType(EVENTTYPE type, int maxResults) {
		Query q = entityManager.createQuery("from Event e where e.eventtype = :type ORDER BY e.timestamp desc");
		q.setParameter("type", type);
		q.setMaxResults(maxResults);
		return (List<Event>)q.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Event> findByUsername(String username, int maxResults) {
		Query q = entityManager.createQuery("from Event e where e.user.username = :username ORDER BY e.timestamp desc");
		q.setParameter("username", username);
		q.setMaxResults(maxResults);
		return (List<Event>)q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ValueEvent> findByTrialData(Long trialDataId) {
		Query q = entityManager.createQuery("from Event where TRIALDATA_ID = :trialDataId");
		q.setParameter("trialDataId", trialDataId);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ValueEvent> getNewerEvents(ValueEvent ve) {
		Query q = entityManager.createQuery("from Event where TRIALDATA_ID = :trialDataId AND ATTRIBUTE_ID = :attributeId AND TIMESTAMP >= :timestamp");
		q.setParameter("trialDataId", ve.getTrialdataId());
		q.setParameter("attributeId", ve.getAttributeId());
		q.setParameter("timestamp", ve.getTimestamp());
		return q.getResultList();
	}

}
