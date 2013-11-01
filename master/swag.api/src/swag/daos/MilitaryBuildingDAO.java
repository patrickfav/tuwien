package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.MilitaryBuilding;

public abstract class MilitaryBuildingDAO {
	public static void insert(MilitaryBuilding militaryBuilding)
			throws NoTransactionException {
		DAOManager.beforeInsert(MilitaryBuilding.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(militaryBuilding);
			DAOManager.afterInsert(MilitaryBuilding.class);
		} else {
			DAOManager.afterInsert(MilitaryBuilding.class);
			throw new NoTransactionException();
		}

	}

	public static void update(MilitaryBuilding militaryBuilding)
			throws NoTransactionException {
		DAOManager.beforeUpdate(MilitaryBuilding.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(militaryBuilding);
			DAOManager.afterUpdate(MilitaryBuilding.class);
		} else {
			DAOManager.afterUpdate(MilitaryBuilding.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(MilitaryBuilding militaryBuilding)
			throws NoTransactionException {
		DAOManager.beforeDelete(MilitaryBuilding.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(militaryBuilding);
			DAOManager.afterDelete(MilitaryBuilding.class);
		} else {
			DAOManager.afterDelete(MilitaryBuilding.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<MilitaryBuilding> findAll() {
		DAOManager.beforeFind(MilitaryBuilding.class, "findAll");

		List<MilitaryBuilding> list = (List<MilitaryBuilding>) HibernateUtil
				.getEntityManager()
				.createQuery("select x from MilitaryBuilding x")
				.getResultList();

		DAOManager.afterFind(MilitaryBuilding.class, "findAll");

		return list;
	}

}
