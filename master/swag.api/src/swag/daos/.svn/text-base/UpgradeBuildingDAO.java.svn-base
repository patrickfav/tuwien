package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.UpgradeBuilding;

public abstract class UpgradeBuildingDAO {
	public static void insert(UpgradeBuilding upgradeBuilding)
			throws NoTransactionException {
		DAOManager.beforeInsert(UpgradeBuilding.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(upgradeBuilding);
			DAOManager.afterInsert(UpgradeBuilding.class);
		} else {
			DAOManager.afterInsert(UpgradeBuilding.class);
			throw new NoTransactionException();
		}

	}

	public static void update(UpgradeBuilding upgradeBuilding)
			throws NoTransactionException {
		DAOManager.beforeUpdate(UpgradeBuilding.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(upgradeBuilding);
			DAOManager.afterUpdate(UpgradeBuilding.class);
		} else {
			DAOManager.afterUpdate(UpgradeBuilding.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(UpgradeBuilding upgradeBuilding)
			throws NoTransactionException {
		DAOManager.beforeDelete(UpgradeBuilding.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(upgradeBuilding);
			DAOManager.afterDelete(UpgradeBuilding.class);
		} else {
			DAOManager.afterDelete(UpgradeBuilding.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<UpgradeBuilding> findAll() {
		DAOManager.beforeFind(UpgradeBuilding.class, "findAll");

		List<UpgradeBuilding> list = (List<UpgradeBuilding>) HibernateUtil
				.getEntityManager()
				.createQuery("select x from UpgradeBuilding x").getResultList();

		DAOManager.afterFind(UpgradeBuilding.class, "findAll");

		return list;
	}

}
