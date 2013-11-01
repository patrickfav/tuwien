package swag.daos;

import java.util.*;

import swag.models.enums.MilitaryType;
import swag.util.*;
import swag.exceptions.*;

public abstract class MilitaryTypeDAO {
	public static void insert(MilitaryType militaryType)
			throws NoTransactionException {
		DAOManager.beforeInsert(MilitaryType.class);

		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().persist(militaryType);
			DAOManager.afterInsert(MilitaryType.class);
		} else {
			DAOManager.afterInsert(MilitaryType.class);
			throw new NoTransactionException();
		}

	}

	public static void update(MilitaryType militaryType)
			throws NoTransactionException {
		DAOManager.beforeUpdate(MilitaryType.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().merge(militaryType);
			DAOManager.afterUpdate(MilitaryType.class);
		} else {
			DAOManager.afterUpdate(MilitaryType.class);
			throw new NoTransactionException();
		}
	}

	public static void delete(MilitaryType militaryType)
			throws NoTransactionException {
		DAOManager.beforeDelete(MilitaryType.class);
		if (HibernateUtil.getEntityManager().getTransaction().isActive()) {
			HibernateUtil.getEntityManager().remove(militaryType);
			DAOManager.afterDelete(MilitaryType.class);
		} else {
			DAOManager.afterDelete(MilitaryType.class);
			throw new NoTransactionException();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<MilitaryType> findAll() {
		DAOManager.beforeFind(MilitaryType.class, "findAll");

		List<MilitaryType> list = (List<MilitaryType>) HibernateUtil
				.getEntityManager().createQuery("select x from MilitaryType x")
				.getResultList();

		DAOManager.afterFind(MilitaryType.class, "findAll");

		return list;
	}

}
