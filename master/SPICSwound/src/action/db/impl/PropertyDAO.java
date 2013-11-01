package db.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.IPropertyDAO;
import entities.Property;
import entities.event.Event;

@Stateless
@Name("propertyDAO")
@AutoCreate
public class PropertyDAO implements IPropertyDAO {

	private static final long serialVersionUID = 1L;
	
	@In
	private EntityManager entityManager;

	public Property findByID(String id) {
		return entityManager.find(Property.class, id);
	}

	public Property merge(Property element) {
		return entityManager.merge(element);
	}

	public void persist(Property element) {
		entityManager.persist(element);

	}

	public void refresh(Property element) {
		entityManager.refresh(element);

	}

	public void remove(Property element) {
		entityManager.remove(element);
	}

	@SuppressWarnings("unchecked")
	public List<Property> findAll() {
		return (List<Property>)entityManager.createQuery("from Property p ORDER BY p.key desc").getResultList();
	}

}
