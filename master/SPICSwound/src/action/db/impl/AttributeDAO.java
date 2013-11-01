package db.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import bean.action.AttributeIdentifier;
import db.interfaces.IAttributeDAO;
import entities.Attribute;

@Stateless
@AutoCreate
@Name("attributeDAO")
public class AttributeDAO implements IAttributeDAO{

	@In
	private EntityManager entityManager;
	
	public void persist(Attribute a){
		entityManager.persist(a);
	}
	
	public void removeById(long id){
		entityManager.createQuery("delete from Attribute a where a.id=:id").setParameter("id", id).executeUpdate();
	}

	public void remove(Attribute a) {
		entityManager.remove(a);
		
	}

	public Attribute findByID(Long id) {
		return entityManager.find(Attribute.class, id);
	}

	public Attribute merge(Attribute att) {
		return entityManager.merge(att);
	}

	public void refresh(Attribute att) {
		entityManager.refresh(att);
	}

	@SuppressWarnings("unchecked")
	public List<AttributeIdentifier> getAttributeIdentifiersForTrialForm(
			Long trialFormID) {
		Query q = entityManager.createQuery("SELECT new bean.action.AttributeIdentifier(att.name, att.attributeGroup.name, att.id) " +
				"from Attribute att where att.attributeGroup.trialForm.id = :trialFormId");
		q.setParameter("trialFormId", trialFormID);
		
		List<AttributeIdentifier> idents = q.getResultList();
		
		return idents;
	}
	
}
