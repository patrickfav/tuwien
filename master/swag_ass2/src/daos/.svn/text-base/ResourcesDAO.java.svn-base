package daos;

import java.util.*;

import util.*;
import exceptions.*;
import models.Resources;

public abstract class ResourcesDAO {
	public static void insert(Resources resources)
			throws NoTransactionException {
		DAOManager.beforeInsert(Resources.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(resources);
			DAOManager.afterInsert(Resources.class);
		} else {
			DAOManager.afterInsert(Resources.class);
			throw new NoTransactionException();
		}

	}

	public static void update(Resources resources)
			throws NoTransactionException {
		DAOManager.beforeUpdate(Resources.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(resources);
			DAOManager.afterUpdate(Resources.class);
		} else {
			DAOManager.afterUpdate(Resources.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(Resources resources)
			throws NoTransactionException {
		DAOManager.beforeDelete(Resources.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(resources);
			DAOManager.afterDelete(Resources.class);
		} else {
			DAOManager.afterDelete(Resources.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Resources> findAll() {
		DAOManager.beforeFind(Resources.class, "findAll");

		List<Resources> list = (List<Resources>) HibernateUtil
				.getEntityManager().createQuery("select x from Resources x")
				.getResultList();

		DAOManager.afterFind(Resources.class, "findAll");

		return list;
	}

}
