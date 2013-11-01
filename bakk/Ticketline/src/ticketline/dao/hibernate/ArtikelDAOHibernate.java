/*
 * PROJECT: TLJava
 * $Id: ArtikelDAOHibernate.java,v 1.6 2006/05/22 11:59:40 0425918 Exp $
 */
package ticketline.dao.hibernate;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import ticketline.dao.interfaces.ArtikelDAO;
import ticketline.db.Artikel;

/**
 * Hibernate-Klasse fuer die Artikel.
 *
 * @author geezmo
 */
public class ArtikelDAOHibernate implements ArtikelDAO {
	/**
	 *
	 * @see ticketline.dao.interfaces.ArtikelDAO#get(java.lang.Integer)
	 */
	public Artikel get(Integer artikelnr) {
		Artikel artikel = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			artikel = (Artikel) session.load(Artikel.class, artikelnr);
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return artikel;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.ArtikelDAO#getAll()
	 */
	public List getAll() {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			list = session.createQuery("from " + Artikel.class.getName())
					.list();
		} catch (HibernateException e) {
			// throw new RuntimeException(e.getMessage());
			throw e;
		} finally {
			// HibernateSessionFactory.closeSession();
		}
		return list;
	}

	/**
	 * @see ticketline.dao.interfaces.ArtikelDAO#save(ticketline.db.Artikel)
	 */
	public void save(Artikel artikel) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.saveOrUpdate(artikel);
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
	 * @see ticketline.dao.interfaces.ArtikelDAO#remove(ticketline.db.Artikel)
	 */
	public void remove(Artikel artikel) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.delete(artikel);
			HibernateSessionFactory.commitTransaction();
			session.flush();
		} catch (Exception e) {
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
					"from " + Artikel.class.getName() + " where " + where)
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
