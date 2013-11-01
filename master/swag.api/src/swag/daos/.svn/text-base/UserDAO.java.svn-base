package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.User;

public abstract class UserDAO {
	public static void insert(User user) throws NoTransactionException {
		DAOManager.beforeInsert(User.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(user);
			DAOManager.afterInsert(User.class);
		} else {
			DAOManager.afterInsert(User.class);
			throw new NoTransactionException();
		}

	}

	public static void update(User user) throws NoTransactionException {
		DAOManager.beforeUpdate(User.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(user);
			DAOManager.afterUpdate(User.class);
		} else {
			DAOManager.afterUpdate(User.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(User user) throws NoTransactionException {
		DAOManager.beforeDelete(User.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(user);
			DAOManager.afterDelete(User.class);
		} else {
			DAOManager.afterDelete(User.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<User> findAll() {
		DAOManager.beforeFind(User.class, "findAll");

		List<User> list = (List<User>) HibernateUtil.getEntityManager()
				.createQuery("select x from User x").getResultList();

		DAOManager.afterFind(User.class, "findAll");

		return list;
	}

	public static List<User> findByUsername(String username) {
		DAOManager.beforeFind(User.class, "findByUsername");

		List<User> list = (List<User>) HibernateUtil
				.getEntityManager()
				.createQuery(
						"select p from Player p where p.username='" + username
								+ "'").getResultList();

		DAOManager.afterFind(User.class, "findByUsername");

		return list;
	}

}
