package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.MapSquare;

public abstract class MapSquareDAO {
	public static void insert(MapSquare mapSquare)
			throws NoTransactionException {
		DAOManager.beforeInsert(MapSquare.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(mapSquare);
			DAOManager.afterInsert(MapSquare.class);
		} else {
			DAOManager.afterInsert(MapSquare.class);
			throw new NoTransactionException();
		}

	}

	public static void update(MapSquare mapSquare)
			throws NoTransactionException {
		DAOManager.beforeUpdate(MapSquare.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(mapSquare);
			DAOManager.afterUpdate(MapSquare.class);
		} else {
			DAOManager.afterUpdate(MapSquare.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(MapSquare mapSquare)
			throws NoTransactionException {
		DAOManager.beforeDelete(MapSquare.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(mapSquare);
			DAOManager.afterDelete(MapSquare.class);
		} else {
			DAOManager.afterDelete(MapSquare.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<MapSquare> findAll() {
		DAOManager.beforeFind(MapSquare.class, "findAll");

		List<MapSquare> list = (List<MapSquare>) HibernateUtil
				.getEntityManager().createQuery("select x from MapSquare x")
				.getResultList();

		DAOManager.afterFind(MapSquare.class, "findAll");

		return list;
	}

}
