package ticketline.dao.hibernate;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ticketline.dao.interfaces.KundeDAO;
import ticketline.db.Kunde;
import ticketline.db.Ticketcard;

/**
 * Hibernate-Klasse fuer die Kunden.
 *
 * @author geezmo
 */
public class KundeDAOHibernate implements KundeDAO {
	
	private static Logger logger = Logger.getLogger(KundeDAOHibernate.class);
	
	/**
	 *
	 * @see ticketline.dao.interfaces.KundeDAO#get(java.lang.Integer)
	 */
	public Kunde get(Integer kartennr) {
		Kunde newObject = null;

		try {
			Session session = HibernateSessionFactory.currentSession();
			newObject = (Kunde) session.load(Kunde.class, kartennr);
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return newObject;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.KundeDAO#getAll()
	 */
	public List getAll() {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			list = session.createQuery("from " + Kunde.class.getName()).list();
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
		return list;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.KundeDAO#save(ticketline.db.Kunde)
	 */
	public void save(Kunde kunde) {
		Session session = HibernateSessionFactory.currentSession();
		try {			
			HibernateSessionFactory.beginTransaction();			
			session.saveOrUpdate(kunde);					
			HibernateSessionFactory.commitTransaction();			
		} catch (HibernateException e) {
			HibernateSessionFactory.rollbackTransaction();
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
		session.flush();
	}
	
	public void update(Kunde kunde) {
		Session session = HibernateSessionFactory.currentSession();
		try {			
			HibernateSessionFactory.beginTransaction();			
			session.merge(kunde);					
			HibernateSessionFactory.commitTransaction();			
		} catch (HibernateException e) {
			HibernateSessionFactory.rollbackTransaction();
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
		session.flush();
	}
	/**
	 *
	 * @see ticketline.dao.interfaces.KundeDAO#remove(ticketline.db.Kunde)
	 */
	public void remove(Kunde kunde) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.delete(kunde);
			HibernateSessionFactory.commitTransaction();
		} catch (HibernateException e) {
			HibernateSessionFactory.rollbackTransaction();
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
	}
	
	/**
	 * @see ticketline.dao.interfaces.KundeDAO#search(ticketline.db.Kunde)
	 */
	public List search(Kunde kunde) {
		
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			
			Criteria crit = session.createCriteria(Kunde.class);
			
			if(kunde.getNname() != null) crit.add(Restrictions.ilike("nname", "%"+kunde.getNname()+"%"));
			if(kunde.getVname() != null) crit.add(Restrictions.ilike("vname", "%"+kunde.getVname()+"%"));
			if(kunde.getKartennr() != null) crit.add(Restrictions.eq("kartennr", kunde.getKartennr()));
			if(kunde.getStrasse() != null) crit.add(Restrictions.ilike("strasse", "%"+kunde.getStrasse()+"%"));
			if(kunde.getOrt() != null) crit.add(Restrictions.ilike("ort", "%"+kunde.getOrt()+"%"));
			if(kunde.getEmail() != null) crit.add(Restrictions.ilike("email", "%"+kunde.getEmail()+"%"));
			if(kunde.getGeschlecht() != null) crit.add(Restrictions.eq("geschlecht", kunde.getGeschlecht()));
			if(kunde.getGeburtsdatum() != null) crit.add(Restrictions.eq("geburtsdatum",kunde.getGeburtsdatum()));
			if(kunde.getPlz() != null) crit.add(Restrictions.ilike("plz", kunde.getPlz()));
			if(kunde.getErmaessigung() != null) crit.add(Restrictions.eq("ermaessigung", kunde.getErmaessigung()));
			
			//sort
			crit.addOrder(Order.asc("nname"));
			list = crit.list();

			//list = session.createQuery(query).list();
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return list;
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
					"from " + Kunde.class.getName() + " where " + where).list();
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
