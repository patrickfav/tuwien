package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.LoadEventListener;
import org.hibernate.event.PersistEventListener;
import org.hibernate.event.def.DefaultPersistEventListener;

public abstract class HibernateUtil {
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
