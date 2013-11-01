package ticketline.dao.hibernate;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ticketline.dao.interfaces.TransaktionDAO;
import ticketline.db.Transaktion;
import ticketline.db.TransaktionKey;

/**
 * Hibernate-Klasse fuer die Transaktion.
 *
 * @author geezmo
 */
public class TransaktionDAOHibernate implements TransaktionDAO {

	/**
	 *
	 * @see ticketline.dao.interfaces.TransaktionDAO#get(ticketline.db.TransaktionKey)
	 */
	public Transaktion get(TransaktionKey transaktionKey) {
		Transaktion newObject = null;
		
		try {
			Session session = HibernateSessionFactory.currentSession();
			newObject = (Transaktion) session.load(Transaktion.class,
					transaktionKey);
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return newObject;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.TransaktionDAO#getAll()
	 */
	@SuppressWarnings("unchecked")
	public List getAll() {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			list = session.createQuery("from " + Transaktion.class.getName())
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
	 * @see ticketline.dao.interfaces.TransaktionDAO#save(ticketline.db.Transaktion)
	 */
	public void save(Transaktion transaktion) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			session.evict(transaktion);
			HibernateSessionFactory.beginTransaction();
			session.save(transaktion);
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
	 * @see ticketline.dao.interfaces.TransaktionDAO#remove(ticketline.db.Transaktion)
	 */
	public void remove(Transaktion transaktion) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.delete(transaktion);
			HibernateSessionFactory.commitTransaction();
			session.evict(transaktion);
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
	@SuppressWarnings("unchecked")
	public List find(String where) {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			list = session.createQuery(
					"from " + Transaktion.class.getName() + " where " + where)
					.list();
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Transaktion> searchTransaktion(TransaktionKey transKey,int resNr,
			boolean isTicket, boolean isReservation)
			throws RuntimeException {
		
		try {
			Session session = HibernateSessionFactory.currentSession();
			Criteria crit = session.createCriteria(Transaktion.class);
			
			if(transKey.getKundennr() != null) crit.add(Restrictions.eq("comp_id.kundennr", transKey.getKundennr()));
			if(resNr != -1) crit.add(Restrictions.eq("resnr", resNr));
			if(isTicket) {
				crit.add(Restrictions.eq("verkauft", true));
				crit.add(Restrictions.eq("storniert", false));
			}
			if(isReservation) {
				crit.add(Restrictions.eq("verkauft", false));
				crit.add(Restrictions.eq("storniert", false));
			}
			
			return crit.list();
			
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public Integer getNextResNr(){
		Session session = HibernateSessionFactory.currentSession();
		Query query = session.createQuery("Select max(resnr) from Transaktion");
		List list = query.list();
		return (Integer) list.get(0) + 1 ;
		
	}
	
	public void update(Transaktion transaktion) {
		Session session = HibernateSessionFactory.currentSession();
		try {			
			HibernateSessionFactory.beginTransaction();			
			session.update(transaktion);	
			HibernateSessionFactory.commitTransaction();			
		} catch (HibernateException e) {
			HibernateSessionFactory.rollbackTransaction();
			throw new RuntimeException(e.getMessage());
		} 
	}
}
