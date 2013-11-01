package ticketline.dao.hibernate;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import ticketline.dao.interfaces.BestellungDAO;
import ticketline.db.Bestellung;
import ticketline.db.BestellungKey;

/**
 * Hibernate-Klasse fuer die Bestellung.
 *
 * @author geezmo
 */
public class BestellungDAOHibernate implements BestellungDAO {

	/**
	 *
	 * @see ticketline.dao.interfaces.BestellungDAO#get(ticketline.db.BestellungKey)
	 */
	public Bestellung get(BestellungKey bestellungKey) {
		Bestellung newObject = null;

		try {
			Session session = HibernateSessionFactory.currentSession();
			newObject = (Bestellung) session.load(Bestellung.class,
					bestellungKey);

		} catch (HibernateException e) {

			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return newObject;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.BestellungDAO#getAll()
	 */
	public List getAll() {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();

			list = session.createQuery("from " + Bestellung.class.getName())
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
	 * @see ticketline.dao.interfaces.BestellungDAO#save(ticketline.db.Bestellung)
	 */
	public void save(ticketline.db.Bestellung bestellung) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.saveOrUpdate(bestellung);
			HibernateSessionFactory.commitTransaction();
		} catch (HibernateException e) {
			HibernateSessionFactory.rollbackTransaction();
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.BestellungDAO#remove(ticketline.db.Bestellung)
	 */
	public void remove(ticketline.db.Bestellung bestellung) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.delete(bestellung);
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
					"from " + Bestellung.class.getName() + " where " + where)
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
