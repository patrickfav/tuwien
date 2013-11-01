package db.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.IConstraintDAO;
import entities.constraint.Constraint;

@Stateless
@Name("constraintDAO")
public class ConstraintDAO implements IConstraintDAO {

	@In
	private EntityManager entityManager;
	
	public Constraint findByID(Long id) {
		return entityManager.find(Constraint.class, id);
	}

	public Constraint merge(Constraint c) {
		return entityManager.merge(c);
	}

	public void persist(Constraint c) {
		entityManager.persist(c);

	}

	public void remove(Constraint toDelete) {
		entityManager.remove(toDelete);
	}

	public void refresh(Constraint element) {
		entityManager.refresh(element);
		
	}


}
