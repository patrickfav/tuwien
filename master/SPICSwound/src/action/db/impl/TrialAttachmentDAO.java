package db.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.ITrialAttachmentDAO;
import entities.TrialAttachment;

@Stateless
@Name("trialAttachmentDAO")
public class TrialAttachmentDAO implements ITrialAttachmentDAO {

	@In
	private EntityManager entityManager;

	public TrialAttachment findByID(Long id) {
		return entityManager.find(TrialAttachment.class, id);
	}

	public TrialAttachment merge(TrialAttachment ta) {
		return entityManager.merge(ta);
	}

	public void persist(TrialAttachment ta) {
		entityManager.persist(ta);

	}

	public void remove(TrialAttachment ta) {
		entityManager.remove(ta);

	}

	public void deleteById(Long id) {
		entityManager.createQuery("delete from TrialAttachment t where t.id = :taId").setParameter("taId", id).executeUpdate();
	}

	public void refresh(TrialAttachment ta) {
		entityManager.refresh(ta);
		
	}

}
