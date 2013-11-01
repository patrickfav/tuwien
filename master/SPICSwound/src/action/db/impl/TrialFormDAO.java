package db.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.ITrialFormDAO;
import entities.TrialForm;

@Stateless
@Name("trialFormDAO")
@AutoCreate
public class TrialFormDAO implements ITrialFormDAO {

	@In
	private EntityManager entityManager;

	public void persist(TrialForm tf) {
		entityManager.persist(tf);

	}

	public TrialForm getByID(Long id) {
		return entityManager.find(TrialForm.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<TrialForm> getAll() {
		return entityManager.createQuery("from TrialForm").getResultList();
	}

	public TrialForm merge(TrialForm tf) {
		return entityManager.merge(tf);
	}

	public void remove(TrialForm trialForm) {
		entityManager.remove(trialForm);

	}

	public boolean canFillTrialForm(Long tfId, String kennnummer) {
		Query q = entityManager.createQuery("select count(*) from TrialData td, TrialForm tf WHERE td.trialform.id = tf.id AND tf.fillOnce = true AND tf.id = :tfId AND td.patient.kennnummer = :kennr");
		q.setParameter("tfId", tfId);
		q.setParameter("kennr", kennnummer);
		long result = (Long)q.getSingleResult();
		return result == 0;
	}

	public TrialForm findByID(Long id) {
		return entityManager.find(TrialForm.class, id);
	}

	public void refresh(TrialForm element) {
		entityManager.refresh(element);		
	}
	
	public Long lookupTrialForm(String formName, Long trialId) {
		Query q = entityManager.createQuery("select tf.id from TrialForm tf where tf.name = :formName AND tf.trial.id = :trialId");
		q.setParameter("formName", formName);
		q.setParameter("trialId", trialId);
		
		try {
			return (Long)q.getSingleResult();
		} catch (NoResultException e) {
		}
		
		return null;
	}
	
}
