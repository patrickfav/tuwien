package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.ResourceBuilding;

public abstract class ResourceBuildingDAO {
	public static void insert(ResourceBuilding resourceBuilding)
			throws NoTransactionException {
		DAOManager.beforeInsert(ResourceBuilding.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(resourceBuilding);
			DAOManager.afterInsert(ResourceBuilding.class);
		} else {
			DAOManager.afterInsert(ResourceBuilding.class);
			throw new NoTransactionException();
		}

	}

	public static void update(ResourceBuilding resourceBuilding)
			throws NoTransactionException {
		DAOManager.beforeUpdate(ResourceBuilding.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(resourceBuilding);
			DAOManager.afterUpdate(ResourceBuilding.class);
		} else {
			DAOManager.afterUpdate(ResourceBuilding.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(ResourceBuilding resourceBuilding)
			throws NoTransactionException {
		DAOManager.beforeDelete(ResourceBuilding.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(resourceBuilding);
			DAOManager.afterDelete(ResourceBuilding.class);
		} else {
			DAOManager.afterDelete(ResourceBuilding.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<ResourceBuilding> findAll() {
		DAOManager.beforeFind(ResourceBuilding.class, "findAll");

		List<ResourceBuilding> list = (List<ResourceBuilding>) HibernateUtil
				.getEntityManager()
				.createQuery("select x from ResourceBuilding x")
				.getResultList();

		DAOManager.afterFind(ResourceBuilding.class, "findAll");

		return list;
	}

}
