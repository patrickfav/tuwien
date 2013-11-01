package db.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.IGroupDAO;
import entities.OrgGroup;

@Stateless
@Name("groupDAO")
@AutoCreate
public class GroupDAO implements IGroupDAO {

	@In
	private EntityManager entityManager;
	
	public OrgGroup findByID(Long id) {
		return entityManager.find(OrgGroup.class, id);
	}

	public OrgGroup merge(OrgGroup group) {
		return entityManager.merge(group);
	}

	public void persist(OrgGroup group) {
		entityManager.persist(group);

	}

	@SuppressWarnings("unchecked")
	public OrgGroup findByName(String name) {
		Query q = entityManager.createQuery("from OrgGroup o where o.name = :name");
		q.setParameter("name", name);
		List<OrgGroup> result = q.getResultList();
		
		if (result.size() == 0)
			return null;
			
		return result.get(0);
	}

	public void refresh(OrgGroup group) {
		entityManager.refresh(group);
	}

	public void remove(OrgGroup group) {
		entityManager.remove(group);
	}

}
