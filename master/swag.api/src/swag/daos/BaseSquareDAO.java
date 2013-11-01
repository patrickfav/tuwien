package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.BaseSquare;

public abstract class BaseSquareDAO {
	public static void insert(BaseSquare baseSquare)
			throws NoTransactionException {
		DAOManager.beforeInsert(BaseSquare.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(baseSquare);
			DAOManager.afterInsert(BaseSquare.class);
		} else {
			DAOManager.afterInsert(BaseSquare.class);
			throw new NoTransactionException();
		}

	}

	public static void update(BaseSquare baseSquare)
			throws NoTransactionException {
		DAOManager.beforeUpdate(BaseSquare.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(baseSquare);
			DAOManager.afterUpdate(BaseSquare.class);
		} else {
			DAOManager.afterUpdate(BaseSquare.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(BaseSquare baseSquare)
			throws NoTransactionException {
		DAOManager.beforeDelete(BaseSquare.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(baseSquare);
			DAOManager.afterDelete(BaseSquare.class);
		} else {
			DAOManager.afterDelete(BaseSquare.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<BaseSquare> findAll() {
		DAOManager.beforeFind(BaseSquare.class, "findAll");

		List<BaseSquare> list = (List<BaseSquare>) HibernateUtil
				.getEntityManager().createQuery("select x from BaseSquare x")
				.getResultList();

		DAOManager.afterFind(BaseSquare.class, "findAll");

		return list;
	}

}
