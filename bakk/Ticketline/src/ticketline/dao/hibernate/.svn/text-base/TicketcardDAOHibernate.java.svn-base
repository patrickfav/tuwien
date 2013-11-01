package ticketline.dao.hibernate;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import ticketline.dao.interfaces.TicketcardDAO;
import ticketline.db.Ticketcard;

/**
 * Hibernate-Klasse fuer die Ticketcards.
 *
 * @author geezmo
 */
public class TicketcardDAOHibernate implements TicketcardDAO {

	/**
	 *
	 * @see ticketline.dao.interfaces.TicketcardDAO#get(java.lang.Integer)
	 */
	public Ticketcard get(Integer kartennr) {
		Ticketcard newObject = null;

		try {
			Session session = HibernateSessionFactory.currentSession();
			newObject = (Ticketcard) session.load(Ticketcard.class, kartennr);
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return newObject;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.TicketcardDAO#getAll()
	 */
	public List getAll() {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			list = session.createQuery("from " + Ticketcard.class.getName())
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
	 * @see ticketline.dao.interfaces.TicketcardDAO#save(ticketline.db.Ticketcard)
	 */
	public void save(Ticketcard ticketcard) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.saveOrUpdate(ticketcard);
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
	 * @see (ticketline.db.Ticketcard)
	 */
	public void update(Ticketcard ticketcard) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.merge(ticketcard);
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
	 * @see ticketline.dao.interfaces.TicketcardDAO#remove(ticketline.db.Ticketcard)
	 */
	public void remove(Ticketcard ticketcard) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			session.flush();
			HibernateSessionFactory.beginTransaction();
			session.delete(ticketcard);
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
	 * @see ticketline.dao.interfaces.DAO#find(java.lang.String)
	 */
	public List find(String where) {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			list = session.createQuery(
					"from " + Ticketcard.class.getName() + " where " + where)
					.list();
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return list;
	}

	public Set initSet(Set set) {
		try {
			Hibernate.initialize(set);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
		return set;
	}

}
