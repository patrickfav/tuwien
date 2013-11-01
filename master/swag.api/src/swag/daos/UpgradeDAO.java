package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.Upgrade;

public abstract class UpgradeDAO {
	public static void insert(Upgrade upgrade) throws NoTransactionException {
		DAOManager.beforeInsert(Upgrade.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(upgrade);
			DAOManager.afterInsert(Upgrade.class);
		} else {
			DAOManager.afterInsert(Upgrade.class);
			throw new NoTransactionException();
		}

	}

	public static void update(Upgrade upgrade) throws NoTransactionException {
		DAOManager.beforeUpdate(Upgrade.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(upgrade);
			DAOManager.afterUpdate(Upgrade.class);
		} else {
			DAOManager.afterUpdate(Upgrade.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(Upgrade upgrade) throws NoTransactionException {
		DAOManager.beforeDelete(Upgrade.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(upgrade);
			DAOManager.afterDelete(Upgrade.class);
		} else {
			DAOManager.afterDelete(Upgrade.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Upgrade> findAll() {
		DAOManager.beforeFind(Upgrade.class, "findAll");

		List<Upgrade> list = (List<Upgrade>) HibernateUtil.getEntityManager()
				.createQuery("select x from Upgrade x").getResultList();

		DAOManager.afterFind(Upgrade.class, "findAll");

		return list;
	}

}
