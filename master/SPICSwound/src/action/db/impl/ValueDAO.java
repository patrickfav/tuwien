package db.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.IValueDAO;
import entities.value.Value;
import entities.value.Value.ValueId;

@Stateless
@AutoCreate
@Name("valueDAO")
public class ValueDAO implements IValueDAO {

	@In
	private EntityManager entityManager;

	public Value findByID(ValueId valueId) {
		return entityManager.find(Value.class, valueId);
	}

	public Value merge(Value value) {
		return entityManager.merge(value);

	}

	public void persist(Value value) {
		entityManager.persist(value);

	}

	public void remove(Value value) {
		entityManager.remove(value);
	}

	public void refresh(Value value) {
		entityManager.refresh(value);
	}

	public Value findByAttributeAndTrialData(Long attributeId, Long trialDataId) {
		if (attributeId == null || trialDataId == null) {
			return null;
		}

		Query q = entityManager.createQuery("FROM Value v"
				+ " WHERE v.id.attributeId = :attributeId "
				+ " AND v.id.trialDataId = :trialDataId");

		q.setParameter("attributeId", attributeId);
		q.setParameter("trialDataId", trialDataId);

		try {
			return (Value) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
