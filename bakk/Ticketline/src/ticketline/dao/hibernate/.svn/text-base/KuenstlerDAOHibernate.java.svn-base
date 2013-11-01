package ticketline.dao.hibernate;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ticketline.dao.interfaces.KuenstlerDAO;
import ticketline.db.Kuenstler;
import ticketline.db.Kunde;

/**
 * Hibernate-Klasse fuer die Kuenstler.
 *
 * @author geezmo
 */
public class KuenstlerDAOHibernate implements KuenstlerDAO {

	/**
	 *
	 * @see ticketline.dao.interfaces.KuenstlerDAO#get(java.lang.Integer)
	 */
	public Kuenstler get(Integer kuenstlernr) {
		Kuenstler newObject = null;

		try {
			Session session = HibernateSessionFactory.currentSession();
			newObject = (Kuenstler) session.load(Kuenstler.class, kuenstlernr);
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return newObject;
	}
	
	public List<Kuenstler> search(Kuenstler kuenstler) {
		
		List<Kuenstler> list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();			
			Criteria crit = session.createCriteria(Kuenstler.class);
			
			if(kuenstler.getVname() != null) crit.add(Restrictions.ilike("vname", "%"+kuenstler.getVname()+"%"));
			if(kuenstler.getNname() != null) crit.add(Restrictions.ilike("nname", "%"+kuenstler.getNname()+"%"));			
			if(kuenstler.getGeschlecht() != null) crit.add(Restrictions.eq("geschlecht", kuenstler.getGeschlecht()));			
			
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
	 * @see ticketline.dao.interfaces.KuenstlerDAO#getAll()
	 */
	public List getAll() {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			list = session.createQuery("from " + Kuenstler.class.getName())
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
	 * @see ticketline.dao.interfaces.KuenstlerDAO#save(ticketline.db.Kuenstler)
	 */
	public void save(Kuenstler kuenstler) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.saveOrUpdate(kuenstler);
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
	 * @see ticketline.dao.interfaces.KuenstlerDAO#remove(ticketline.db.Kuenstler)
	 */
	public void remove(Kuenstler kuenstler) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.delete(kuenstler);
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
					"from " + Kuenstler.class.getName() + " where " + where)
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
