package db.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.IAttributeGroupDAO;
import entities.AttributeGroup;

@Stateless
@Name("attributeGroupDAO")
public class AttributeGroupDAO implements IAttributeGroupDAO {

	@In
	private EntityManager entityManager;

	public void persist(AttributeGroup ag) {
		entityManager.persist(ag);

	}

	public AttributeGroup findByID(Long id) {
		return entityManager.find(AttributeGroup.class, id);
	}

	public void remove(AttributeGroup toDelete) {
		entityManager.remove(toDelete);
	}

	public AttributeGroup merge(AttributeGroup ag) {
		return entityManager.merge(ag);
	}

	public AttributeGroup findByTrialFormIDandSort(Long tfId, Integer sort) {
		return (AttributeGroup) entityManager
				.createQuery(
						"FROM AttributeGroup ag WHERE ag.trialForm.id = :tfId AND ag.sort = :sort")
				.setParameter("tfId", tfId).setParameter("sort", sort)
				.getSingleResult();
	}

	public void refresh(AttributeGroup ag) {
		entityManager.refresh(ag);
	}

}
