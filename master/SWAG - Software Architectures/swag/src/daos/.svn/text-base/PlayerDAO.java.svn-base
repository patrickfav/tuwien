package daos;

import java.util.*;

import util.*;
import exceptions.*;
import models.Player;

public abstract class PlayerDAO {
	public static void insert(Player player) throws NoTransactionException {
		DAOManager.beforeInsert(Player.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(player);
			DAOManager.afterInsert(Player.class);
		} else {
			DAOManager.afterInsert(Player.class);
			throw new NoTransactionException();
		}

	}

	public static void update(Player player) throws NoTransactionException {
		DAOManager.beforeUpdate(Player.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(player);
			DAOManager.afterUpdate(Player.class);
		} else {
			DAOManager.afterUpdate(Player.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(Player player) throws NoTransactionException {
		DAOManager.beforeDelete(Player.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(player);
			DAOManager.afterDelete(Player.class);
		} else {
			DAOManager.afterDelete(Player.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Player> findAll() {
		DAOManager.beforeFind(Player.class, "findAll");

		List<Player> list = (List<Player>) HibernateUtil.getEntityManager()
				.createQuery("select x from Player x").getResultList();

		DAOManager.afterFind(Player.class, "findAll");

		return list;
	}

	public static List<Player> findByUsername(String username) {
		DAOManager.beforeFind(Player.class, "findByUsername");

		List<Player> list = (List<Player>) HibernateUtil
				.getEntityManager()
				.createQuery(
						"select p from Player p where p.username='" + username
								+ "'").getResultList();

		DAOManager.afterFind(Player.class, "findByUsername");

		return list;
	}

}
