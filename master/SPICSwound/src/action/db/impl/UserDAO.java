package db.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.IUserDAO;
import entities.User;
import entities.event.EVENTTYPE;

@Stateless
@Name("userDAO")
@AutoCreate
public class UserDAO implements IUserDAO {

	@In
	private EntityManager entityManager;
	
	public void persist(User u) {
		entityManager.persist(u);

	}

	public User findByID(String id) {
		return entityManager.find(User.class, id);
		
	}

	public User merge(User user) {
		return entityManager.merge(user);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> usersNotParticipatingInTrial(Long trialId) {
		Query q = entityManager.createQuery("from User u where u.enabled = true AND u NOT IN (SELECT p.user from Participation p where p.trial.id = :trialId)");
		q.setParameter("trialId", trialId);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return (List<User>)entityManager.createQuery("from User").getResultList();
	}

	public void refresh(User user) {
		entityManager.refresh(user);
	}

	public void remove(User user) {
		entityManager.remove(user);
	}

	/* (non-Javadoc)
	 * @see db.interfaces.IUserDAO#find(java.lang.String, boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<User> find(String searchString, boolean searchEnabled, int maxResults) {
		searchString = searchString == null || searchString.length() == 0 ?
				"%" : "%" + searchString.toLowerCase() + "%";
		
		Query q = entityManager.createQuery("FROM User u" +
				" WHERE ( " +
				"   lower(u.username) LIKE :searchString" +
				"   OR lower(u.firstname) LIKE :searchString" +
				"   OR lower(u.lastname) LIKE :searchString " +
				" )" +
				( searchEnabled ? " AND u.enabled = :enabled" : "" )
		);
		
		q.setParameter("searchString", searchString);
		if (searchEnabled) {
			q.setParameter("enabled", searchEnabled);
		}
		q.setMaxResults(maxResults);
		
		return q.getResultList();
	}
	
	/* (non-Javadoc)
	 * @see db.interfaces.IUserDAO#getLastLoginDate(entities.User)
	 */
	public Date getLastLoginDate(User user) {
		if (user == null) {
			return null;
		}
		
		Query q = entityManager.createQuery(
				"SELECT max(e.timestamp) FROM Event AS e " +
				" WHERE e.user = :user " +
				" AND e.eventtype = :eventtype"
		);
		
		q.setParameter("user", user);
		q.setParameter("eventtype", EVENTTYPE.LOGIN);
		
		Object result = q.getSingleResult();
		return result != null ? (Date) result : null;
	}
}
