package swag.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;


public abstract class HibernateUtil{
	private static EntityManager em;
	private static CriteriaBuilder cb;

	private HibernateUtil() {
	}

	static {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("swag");
		em = emf.createEntityManager();
		cb = emf.getCriteriaBuilder();

	}

	public static EntityManager getEntityManager() {
		return em;
	}

	public static CriteriaBuilder getCriteriaBuilder() {
		return cb;
	}

}
