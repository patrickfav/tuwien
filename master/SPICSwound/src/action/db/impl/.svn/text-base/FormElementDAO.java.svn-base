package db.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.IFormElementDAO;
import entities.formelement.FormElement;

@Stateless
@Name("formElementDAO")
public class FormElementDAO implements IFormElementDAO{

	@In
	private EntityManager entityManager;

	public void persist(FormElement formElement){
		entityManager.persist(formElement);
	}
	
	public FormElement findByID(Long id){
		return entityManager.find(FormElement.class, id);
	}

	public FormElement merge(FormElement fe) {
		return entityManager.merge(fe);
	}

	public void remove(FormElement fe) {
		entityManager.remove(fe);
	}

	public void refresh(FormElement element) {
		entityManager.refresh(element);
	}
	
}
