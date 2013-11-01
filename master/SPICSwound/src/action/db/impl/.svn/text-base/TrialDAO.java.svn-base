package db.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.ITrialDAO;
import entities.Trial;
import entities.User;

@Stateless
@AutoCreate
@Name("trialDAO")
public class TrialDAO implements ITrialDAO {

	@In
	private EntityManager entityManager;
	
	public void persist(Trial t) {
		entityManager.persist(t);	
	}

	@SuppressWarnings("unchecked")
	public List<Trial> findActiveByUser(User u) {
		return (List<Trial>)entityManager
		  .createQuery("SELECT t FROM Trial AS t JOIN t.participators AS p WHERE p.user.username = :username AND p.active = true")
		  .setParameter("username",u.getUsername())
		  .getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Trial> findAll() {
		return (List<Trial>)entityManager
		  .createQuery("FROM Trial t")
		  .getResultList();
	}

	public Trial merge(Trial trial) {
		return entityManager.merge(trial);
	}

	public Trial findByID(Long id) {
		return entityManager.find(Trial.class, id);
	}

	public void refresh(Trial trial) {
		entityManager.refresh(trial);		
	}

	public void remove(Trial trial) {
		entityManager.remove(trial);		
	}

}
