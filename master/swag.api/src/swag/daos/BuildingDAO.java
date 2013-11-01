package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.Building;

public abstract class BuildingDAO {
	public static void insert(Building building) throws NoTransactionException {
		DAOManager.beforeInsert(Building.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(building);
			DAOManager.afterInsert(Building.class);
		} else {
			DAOManager.afterInsert(Building.class);
			throw new NoTransactionException();
		}

	}

	public static void update(Building building) throws NoTransactionException {
		DAOManager.beforeUpdate(Building.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(building);
			DAOManager.afterUpdate(Building.class);
		} else {
			DAOManager.afterUpdate(Building.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(Building building) throws NoTransactionException {
		DAOManager.beforeDelete(Building.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(building);
			DAOManager.afterDelete(Building.class);
		} else {
			DAOManager.afterDelete(Building.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Building> findAll() {
		DAOManager.beforeFind(Building.class, "findAll");

		List<Building> list = (List<Building>) HibernateUtil.getEntityManager()
				.createQuery("select x from Building x").getResultList();

		DAOManager.afterFind(Building.class, "findAll");

		return list;
	}

}
