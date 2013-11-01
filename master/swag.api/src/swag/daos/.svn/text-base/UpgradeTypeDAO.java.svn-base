package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.UpgradeType;

public abstract class UpgradeTypeDAO {
	public static void insert(UpgradeType upgradeType)
			throws NoTransactionException {
		DAOManager.beforeInsert(UpgradeType.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(upgradeType);
			DAOManager.afterInsert(UpgradeType.class);
		} else {
			DAOManager.afterInsert(UpgradeType.class);
			throw new NoTransactionException();
		}

	}

	public static void update(UpgradeType upgradeType)
			throws NoTransactionException {
		DAOManager.beforeUpdate(UpgradeType.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(upgradeType);
			DAOManager.afterUpdate(UpgradeType.class);
		} else {
			DAOManager.afterUpdate(UpgradeType.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(UpgradeType upgradeType)
			throws NoTransactionException {
		DAOManager.beforeDelete(UpgradeType.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(upgradeType);
			DAOManager.afterDelete(UpgradeType.class);
		} else {
			DAOManager.afterDelete(UpgradeType.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<UpgradeType> findAll() {
		DAOManager.beforeFind(UpgradeType.class, "findAll");

		List<UpgradeType> list = (List<UpgradeType>) HibernateUtil
				.getEntityManager().createQuery("select x from UpgradeType x")
				.getResultList();

		DAOManager.afterFind(UpgradeType.class, "findAll");

		return list;
	}

}
