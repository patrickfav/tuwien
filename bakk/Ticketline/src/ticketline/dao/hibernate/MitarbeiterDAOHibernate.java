package ticketline.dao.hibernate;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ticketline.dao.interfaces.MitarbeiterDAO;
import ticketline.db.Kunde;
import ticketline.db.Mitarbeiter;

/**
 * Hibernate-Klasse fuer die Mitarbeiter.
 *
 * @author geezmo
 */
public class MitarbeiterDAOHibernate implements MitarbeiterDAO {
	
	private static Logger logger = Logger.getLogger(MitarbeiterDAOHibernate.class);
	
	/**
	 *
	 * @see ticketline.dao.interfaces.MitarbeiterDAO#get(java.lang.Integer)
	 */
	public Mitarbeiter get(Integer kartennr) {
		Mitarbeiter newObject = null;

		try {
			Session session = HibernateSessionFactory.currentSession();
			newObject = (Mitarbeiter) session.load(Mitarbeiter.class, kartennr);
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return newObject;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.MitarbeiterDAO#getAll()
	 */
	public List getAll() {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			list = session.createQuery("from " + Mitarbeiter.class.getName())
					.list();
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
		return list;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.MitarbeiterDAO#save(ticketline.db.Mitarbeiter).
	 */
	public void save(Mitarbeiter mitarbeiter) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.saveOrUpdate(mitarbeiter);
			HibernateSessionFactory.commitTransaction();
			session.flush();
		} catch (HibernateException e) {
			HibernateSessionFactory.rollbackTransaction();
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.MitarbeiterDAO#remove(ticketline.db.Mitarbeiter).
	 */
	public void remove(Mitarbeiter mitarbeiter) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.delete(mitarbeiter);
			HibernateSessionFactory.commitTransaction();
			session.flush();
		} catch (HibernateException e) {
			HibernateSessionFactory.rollbackTransaction();
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
	}
	
	/**
	 * 
	 * @see ticketline.dao.interfaces.MitarbeiterDAO#login(ticketline.db.Mitarbeiter).
	 */
	public Mitarbeiter login(String user, String password) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			
			Criteria crit = session.createCriteria(Mitarbeiter.class);
			crit.add(Restrictions.eq("username", user));
			//crit.add(Restrictions.ilike("passwort", password));
			
			return (Mitarbeiter) crit.uniqueResult();
			
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
	}
	
	/**
	 *
	 * @see ticketline.dao.interfaces.DAO#find(java.lang.String).
	 */
	public List find(String where) {
		List list = null;
		String query = "from " + Mitarbeiter.class.getName() + " where " + where;
		try {
			logger.debug("Query: "+query);
			Session session = HibernateSessionFactory.currentSession();
			list = session.createQuery(query).list();
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return list;
	}

	public Set initSet(Set set) {
		try {
			//skjdgf
			Hibernate.initialize(set);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
		return set;
	}

}
