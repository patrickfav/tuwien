package db.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.IUserPermissionDAO;
import entities.UserPermission;

@Stateless
@Name("userPermissionDAO")
public class UserPermissionDAO implements IUserPermissionDAO {
	
	@In
	private EntityManager entityManager;

	public UserPermission findByID(Integer id) {
		return entityManager.find(UserPermission.class, id);
	}

	public UserPermission merge(UserPermission element) {
		return entityManager.merge(element);
	}

	public void persist(UserPermission element) {
		entityManager.persist(element);
	}

	public void refresh(UserPermission element) {
		entityManager.refresh(element);
	}

	public void remove(UserPermission element) {
		entityManager.remove(element);
	}

}
