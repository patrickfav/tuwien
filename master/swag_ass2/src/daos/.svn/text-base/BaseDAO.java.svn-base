package daos;

import java.util.*;

import util.*;
import exceptions.*;
import models.Base;

public abstract class BaseDAO {
	public static void insert(Base base) throws NoTransactionException {
		DAOManager.beforeInsert(Base.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(base);
			DAOManager.afterInsert(Base.class);
		} else {
			DAOManager.afterInsert(Base.class);
			throw new NoTransactionException();
		}

	}

	public static void update(Base base) throws NoTransactionException {
		DAOManager.beforeUpdate(Base.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(base);
			DAOManager.afterUpdate(Base.class);
		} else {
			DAOManager.afterUpdate(Base.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(Base base) throws NoTransactionException {
		DAOManager.beforeDelete(Base.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(base);
			DAOManager.afterDelete(Base.class);
		} else {
			DAOManager.afterDelete(Base.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Base> findAll() {
		DAOManager.beforeFind(Base.class, "findAll");

		List<Base> list = (List<Base>) HibernateUtil.getEntityManager()
				.createQuery("select x from Base x").getResultList();

		DAOManager.afterFind(Base.class, "findAll");

		return list;
	}

}
