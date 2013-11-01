package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.Troop;

public abstract class TroopDAO {
	public static void insert(Troop troop) throws NoTransactionException {
		DAOManager.beforeInsert(Troop.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(troop);
			DAOManager.afterInsert(Troop.class);
		} else {
			DAOManager.afterInsert(Troop.class);
			throw new NoTransactionException();
		}

	}

	public static void update(Troop troop) throws NoTransactionException {
		DAOManager.beforeUpdate(Troop.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(troop);
			DAOManager.afterUpdate(Troop.class);
		} else {
			DAOManager.afterUpdate(Troop.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(Troop troop) throws NoTransactionException {
		DAOManager.beforeDelete(Troop.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(troop);
			DAOManager.afterDelete(Troop.class);
		} else {
			DAOManager.afterDelete(Troop.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Troop> findAll() {
		DAOManager.beforeFind(Troop.class, "findAll");

		List<Troop> list = (List<Troop>) HibernateUtil.getEntityManager()
				.createQuery("select x from Troop x").getResultList();

		DAOManager.afterFind(Troop.class, "findAll");

		return list;
	}

}
