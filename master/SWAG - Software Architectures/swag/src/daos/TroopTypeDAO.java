package daos;

import java.util.*;

import util.*;
import exceptions.*;
import models.TroopType;

public abstract class TroopTypeDAO {
	public static void insert(TroopType troopType)
			throws NoTransactionException {
		DAOManager.beforeInsert(TroopType.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(troopType);
			DAOManager.afterInsert(TroopType.class);
		} else {
			DAOManager.afterInsert(TroopType.class);
			throw new NoTransactionException();
		}

	}

	public static void update(TroopType troopType)
			throws NoTransactionException {
		DAOManager.beforeUpdate(TroopType.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(troopType);
			DAOManager.afterUpdate(TroopType.class);
		} else {
			DAOManager.afterUpdate(TroopType.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(TroopType troopType)
			throws NoTransactionException {
		DAOManager.beforeDelete(TroopType.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(troopType);
			DAOManager.afterDelete(TroopType.class);
		} else {
			DAOManager.afterDelete(TroopType.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<TroopType> findAll() {
		DAOManager.beforeFind(TroopType.class, "findAll");

		List<TroopType> list = (List<TroopType>) HibernateUtil
				.getEntityManager().createQuery("select x from TroopType x")
				.getResultList();

		DAOManager.afterFind(TroopType.class, "findAll");

		return list;
	}

}
