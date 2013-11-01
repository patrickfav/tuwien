package daos;

import java.util.*;

import util.*;
import exceptions.*;
import models.BuildingType;

public abstract class BuildingTypeDAO {
	public static void insert(BuildingType buildingType)
			throws NoTransactionException {
		DAOManager.beforeInsert(BuildingType.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(buildingType);
			DAOManager.afterInsert(BuildingType.class);
		} else {
			DAOManager.afterInsert(BuildingType.class);
			throw new NoTransactionException();
		}

	}

	public static void update(BuildingType buildingType)
			throws NoTransactionException {
		DAOManager.beforeUpdate(BuildingType.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(buildingType);
			DAOManager.afterUpdate(BuildingType.class);
		} else {
			DAOManager.afterUpdate(BuildingType.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(BuildingType buildingType)
			throws NoTransactionException {
		DAOManager.beforeDelete(BuildingType.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(buildingType);
			DAOManager.afterDelete(BuildingType.class);
		} else {
			DAOManager.afterDelete(BuildingType.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<BuildingType> findAll() {
		DAOManager.beforeFind(BuildingType.class, "findAll");

		List<BuildingType> list = (List<BuildingType>) HibernateUtil
				.getEntityManager().createQuery("select x from BuildingType x")
				.getResultList();

		DAOManager.afterFind(BuildingType.class, "findAll");

		return list;
	}

}
