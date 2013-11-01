package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.Config;

public abstract class ConfigDAO {
	public static void insert(Config config) throws NoTransactionException {
		DAOManager.beforeInsert(Config.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(config);
			DAOManager.afterInsert(Config.class);
		} else {
			DAOManager.afterInsert(Config.class);
			throw new NoTransactionException();
		}

	}

	public static void update(Config config) throws NoTransactionException {
		DAOManager.beforeUpdate(Config.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(config);
			DAOManager.afterUpdate(Config.class);
		} else {
			DAOManager.afterUpdate(Config.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(Config config) throws NoTransactionException {
		DAOManager.beforeDelete(Config.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(config);
			DAOManager.afterDelete(Config.class);
		} else {
			DAOManager.afterDelete(Config.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Config> findAll() {
		DAOManager.beforeFind(Config.class, "findAll");

		List<Config> list = (List<Config>) HibernateUtil.getEntityManager()
				.createQuery("select x from Config x").getResultList();

		DAOManager.afterFind(Config.class, "findAll");

		return list;
	}

}
