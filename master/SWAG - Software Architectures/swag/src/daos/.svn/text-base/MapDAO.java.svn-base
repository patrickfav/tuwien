package daos;

import java.util.*;

import util.*;
import exceptions.*;
import models.Map;

public abstract class MapDAO {
	public static void insert(Map map) throws NoTransactionException {
		DAOManager.beforeInsert(Map.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(map);
			DAOManager.afterInsert(Map.class);
		} else {
			DAOManager.afterInsert(Map.class);
			throw new NoTransactionException();
		}

	}

	public static void update(Map map) throws NoTransactionException {
		DAOManager.beforeUpdate(Map.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(map);
			DAOManager.afterUpdate(Map.class);
		} else {
			DAOManager.afterUpdate(Map.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(Map map) throws NoTransactionException {
		DAOManager.beforeDelete(Map.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(map);
			DAOManager.afterDelete(Map.class);
		} else {
			DAOManager.afterDelete(Map.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Map> findAll() {
		DAOManager.beforeFind(Map.class, "findAll");

		List<Map> list = (List<Map>) HibernateUtil.getEntityManager()
				.createQuery("select x from Map x").getResultList();

		DAOManager.afterFind(Map.class, "findAll");

		return list;
	}

}
