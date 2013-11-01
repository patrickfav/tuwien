package swag.daos;

import java.util.*;

import swag.util.*;
import swag.exceptions.*;
import swag.models.BuildListEntry;

public abstract class BuildListEntryDAO {
	public static void insert(BuildListEntry buildListEntry)
			throws NoTransactionException {
		DAOManager.beforeInsert(BuildListEntry.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(buildListEntry);
			DAOManager.afterInsert(BuildListEntry.class);
		} else {
			DAOManager.afterInsert(BuildListEntry.class);
			throw new NoTransactionException();
		}

	}

	public static void update(BuildListEntry buildListEntry)
			throws NoTransactionException {
		DAOManager.beforeUpdate(BuildListEntry.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(buildListEntry);
			DAOManager.afterUpdate(BuildListEntry.class);
		} else {
			DAOManager.afterUpdate(BuildListEntry.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(BuildListEntry buildListEntry)
			throws NoTransactionException {
		DAOManager.beforeDelete(BuildListEntry.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(buildListEntry);
			DAOManager.afterDelete(BuildListEntry.class);
		} else {
			DAOManager.afterDelete(BuildListEntry.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<BuildListEntry> findAll() {
		DAOManager.beforeFind(BuildListEntry.class, "findAll");

		List<BuildListEntry> list = (List<BuildListEntry>) HibernateUtil
				.getEntityManager()
				.createQuery("select x from BuildListEntry x").getResultList();

		DAOManager.afterFind(BuildListEntry.class, "findAll");

		return list;
	}

}
