package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.Resource;

public abstract class ResourceDAO {
	public static void insert(Resource resource) throws NoTransactionException {
		DAOManager.beforeInsert(Resource.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(resource);
			DAOManager.afterInsert(Resource.class);
		} else {
			DAOManager.afterInsert(Resource.class);
			throw new NoTransactionException();
		}

	}

	public static void update(Resource resource) throws NoTransactionException {
		DAOManager.beforeUpdate(Resource.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(resource);
			DAOManager.afterUpdate(Resource.class);
		} else {
			DAOManager.afterUpdate(Resource.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(Resource resource) throws NoTransactionException {
		DAOManager.beforeDelete(Resource.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(resource);
			DAOManager.afterDelete(Resource.class);
		} else {
			DAOManager.afterDelete(Resource.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Resource> findAll() {
		DAOManager.beforeFind(Resource.class, "findAll");

		List<Resource> list = (List<Resource>) HibernateUtil.getEntityManager()
				.createQuery("select x from Resource x").getResultList();

		DAOManager.afterFind(Resource.class, "findAll");

		return list;
	}

}
