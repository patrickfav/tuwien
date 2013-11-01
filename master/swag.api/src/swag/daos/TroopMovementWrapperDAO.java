package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.TroopMovementWrapper;

public abstract class TroopMovementWrapperDAO {
	public static void insert(TroopMovementWrapper troopMovementWrapper)
			throws NoTransactionException {
		DAOManager.beforeInsert(TroopMovementWrapper.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(troopMovementWrapper);
			DAOManager.afterInsert(TroopMovementWrapper.class);
		} else {
			DAOManager.afterInsert(TroopMovementWrapper.class);
			throw new NoTransactionException();
		}

	}

	public static void update(TroopMovementWrapper troopMovementWrapper)
			throws NoTransactionException {
		DAOManager.beforeUpdate(TroopMovementWrapper.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(troopMovementWrapper);
			DAOManager.afterUpdate(TroopMovementWrapper.class);
		} else {
			DAOManager.afterUpdate(TroopMovementWrapper.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(TroopMovementWrapper troopMovementWrapper)
			throws NoTransactionException {
		DAOManager.beforeDelete(TroopMovementWrapper.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(troopMovementWrapper);
			DAOManager.afterDelete(TroopMovementWrapper.class);
		} else {
			DAOManager.afterDelete(TroopMovementWrapper.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<TroopMovementWrapper> findAll() {
		DAOManager.beforeFind(TroopMovementWrapper.class, "findAll");

		List<TroopMovementWrapper> list = (List<TroopMovementWrapper>) HibernateUtil
				.getEntityManager()
				.createQuery("select x from TroopMovementWrapper x")
				.getResultList();

		DAOManager.afterFind(TroopMovementWrapper.class, "findAll");

		return list;
	}

}
