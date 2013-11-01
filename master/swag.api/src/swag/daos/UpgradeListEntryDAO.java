package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.UpgradeListEntry;

public abstract class UpgradeListEntryDAO {
	public static void insert(UpgradeListEntry upgradeListEntry)
			throws NoTransactionException {
		DAOManager.beforeInsert(UpgradeListEntry.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(upgradeListEntry);
			DAOManager.afterInsert(UpgradeListEntry.class);
		} else {
			DAOManager.afterInsert(UpgradeListEntry.class);
			throw new NoTransactionException();
		}

	}

	public static void update(UpgradeListEntry upgradeListEntry)
			throws NoTransactionException {
		DAOManager.beforeUpdate(UpgradeListEntry.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(upgradeListEntry);
			DAOManager.afterUpdate(UpgradeListEntry.class);
		} else {
			DAOManager.afterUpdate(UpgradeListEntry.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(UpgradeListEntry upgradeListEntry)
			throws NoTransactionException {
		DAOManager.beforeDelete(UpgradeListEntry.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(upgradeListEntry);
			DAOManager.afterDelete(UpgradeListEntry.class);
		} else {
			DAOManager.afterDelete(UpgradeListEntry.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<UpgradeListEntry> findAll() {
		DAOManager.beforeFind(UpgradeListEntry.class, "findAll");

		List<UpgradeListEntry> list = (List<UpgradeListEntry>) HibernateUtil
				.getEntityManager()
				.createQuery("select x from UpgradeListEntry x")
				.getResultList();

		DAOManager.afterFind(UpgradeListEntry.class, "findAll");

		return list;
	}

}
