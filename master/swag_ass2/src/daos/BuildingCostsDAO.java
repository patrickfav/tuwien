package daos;

import java.util.*;

import util.*;
import exceptions.*;
import models.BuildingCosts;

public abstract class BuildingCostsDAO {
	public static void insert(BuildingCosts buildingCosts)
			throws NoTransactionException {
		DAOManager.beforeInsert(BuildingCosts.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(buildingCosts);
			DAOManager.afterInsert(BuildingCosts.class);
		} else {
			DAOManager.afterInsert(BuildingCosts.class);
			throw new NoTransactionException();
		}

	}

	public static void update(BuildingCosts buildingCosts)
			throws NoTransactionException {
		DAOManager.beforeUpdate(BuildingCosts.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(buildingCosts);
			DAOManager.afterUpdate(BuildingCosts.class);
		} else {
			DAOManager.afterUpdate(BuildingCosts.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(BuildingCosts buildingCosts)
			throws NoTransactionException {
		DAOManager.beforeDelete(BuildingCosts.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(buildingCosts);
			DAOManager.afterDelete(BuildingCosts.class);
		} else {
			DAOManager.afterDelete(BuildingCosts.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<BuildingCosts> findAll() {
		DAOManager.beforeFind(BuildingCosts.class, "findAll");

		List<BuildingCosts> list = (List<BuildingCosts>) HibernateUtil
				.getEntityManager()
				.createQuery("select x from BuildingCosts x").getResultList();

		DAOManager.afterFind(BuildingCosts.class, "findAll");

		return list;
	}

}
