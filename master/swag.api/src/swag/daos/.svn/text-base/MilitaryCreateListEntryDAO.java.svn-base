package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.MilitaryCreateListEntry;

public abstract class MilitaryCreateListEntryDAO {
	public static void insert(MilitaryCreateListEntry militaryCreateListEntry)
			throws NoTransactionException {
		DAOManager.beforeInsert(MilitaryCreateListEntry.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(militaryCreateListEntry);
			DAOManager.afterInsert(MilitaryCreateListEntry.class);
		} else {
			DAOManager.afterInsert(MilitaryCreateListEntry.class);
			throw new NoTransactionException();
		}

	}

	public static void update(MilitaryCreateListEntry militaryCreateListEntry)
			throws NoTransactionException {
		DAOManager.beforeUpdate(MilitaryCreateListEntry.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(militaryCreateListEntry);
			DAOManager.afterUpdate(MilitaryCreateListEntry.class);
		} else {
			DAOManager.afterUpdate(MilitaryCreateListEntry.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(MilitaryCreateListEntry militaryCreateListEntry)
			throws NoTransactionException {
		DAOManager.beforeDelete(MilitaryCreateListEntry.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(militaryCreateListEntry);
			DAOManager.afterDelete(MilitaryCreateListEntry.class);
		} else {
			DAOManager.afterDelete(MilitaryCreateListEntry.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<MilitaryCreateListEntry> findAll() {
		DAOManager.beforeFind(MilitaryCreateListEntry.class, "findAll");

		List<MilitaryCreateListEntry> list = (List<MilitaryCreateListEntry>) HibernateUtil
				.getEntityManager()
				.createQuery("select x from MilitaryCreateListEntry x")
				.getResultList();

		DAOManager.afterFind(MilitaryCreateListEntry.class, "findAll");

		return list;
	}

}
