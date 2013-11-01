package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.GameMap;

public abstract class GameMapDAO {
	public static void insert(GameMap gameMap) throws NoTransactionException {
		DAOManager.beforeInsert(GameMap.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(gameMap);
			DAOManager.afterInsert(GameMap.class);
		} else {
			DAOManager.afterInsert(GameMap.class);
			throw new NoTransactionException();
		}

	}

	public static void update(GameMap gameMap) throws NoTransactionException {
		DAOManager.beforeUpdate(GameMap.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(gameMap);
			DAOManager.afterUpdate(GameMap.class);
		} else {
			DAOManager.afterUpdate(GameMap.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(GameMap gameMap) throws NoTransactionException {
		DAOManager.beforeDelete(GameMap.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(gameMap);
			DAOManager.afterDelete(GameMap.class);
		} else {
			DAOManager.afterDelete(GameMap.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<GameMap> findAll() {
		DAOManager.beforeFind(GameMap.class, "findAll");

		List<GameMap> list = (List<GameMap>) HibernateUtil.getEntityManager()
				.createQuery("select x from GameMap x").getResultList();

		DAOManager.afterFind(GameMap.class, "findAll");

		return list;
	}

}
