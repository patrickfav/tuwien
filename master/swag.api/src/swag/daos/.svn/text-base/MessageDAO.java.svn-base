package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.Message;

public abstract class MessageDAO {
	public static void insert(Message message) throws NoTransactionException {
		DAOManager.beforeInsert(Message.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(message);
			DAOManager.afterInsert(Message.class);
		} else {
			DAOManager.afterInsert(Message.class);
			throw new NoTransactionException();
		}

	}

	public static void update(Message message) throws NoTransactionException {
		DAOManager.beforeUpdate(Message.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(message);
			DAOManager.afterUpdate(Message.class);
		} else {
			DAOManager.afterUpdate(Message.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(Message message) throws NoTransactionException {
		DAOManager.beforeDelete(Message.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(message);
			DAOManager.afterDelete(Message.class);
		} else {
			DAOManager.afterDelete(Message.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Message> findAll() {
		DAOManager.beforeFind(Message.class, "findAll");

		List<Message> list = (List<Message>) HibernateUtil.getEntityManager()
				.createQuery("select x from Message x").getResultList();

		DAOManager.afterFind(Message.class, "findAll");

		return list;
	}

}
