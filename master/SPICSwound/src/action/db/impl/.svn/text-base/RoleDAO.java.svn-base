package db.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.IRoleDAO;
import entities.UserRole;

@Stateless
@Name("roleDAO")
public class RoleDAO implements IRoleDAO {

	@In
	private EntityManager entityManager;

	
	public UserRole findByID(String id) {
		return entityManager.find(UserRole.class, id);
	}

	public UserRole merge(UserRole element) {
		return entityManager.merge(element);
	}

	public void persist(UserRole element) {
		entityManager.persist(element);

	}

	public void refresh(UserRole element) {
		entityManager.refresh(element);

	}

	public void remove(UserRole element) {
		entityManager.remove(element);

	}

}
