package ticketline.dao.hibernate;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ticketline.dao.interfaces.OrtDAO;
import ticketline.db.Ort;
import ticketline.db.OrtKey;
import ticketline.db.Saal;

/**
 * Hibernate-Klasse fuer die Orte.
 *
 * @author geezmo
 */
public class OrtDAOHibernate implements OrtDAO {

	/**
	 *
	 * @see ticketline.dao.interfaces.OrtDAO#get(ticketline.db.OrtKey)
	 */
	public Ort get(OrtKey ortKey) {
		Ort newObject = null;

		try {
			Session session = HibernateSessionFactory.currentSession();
			newObject = (Ort) session.load(Ort.class, ortKey);
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return newObject;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.OrtDAO#getAll()
	 */
	public List getAll() {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			list = session.createQuery("from " + Ort.class.getName()).list();
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
		return list;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.OrtDAO#save(ticketline.db.Ort)
	 */
	public void save(Ort ort) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.saveOrUpdate(ort);
			HibernateSessionFactory.commitTransaction();
			session.flush();
			// } catch (HibernateException e) {
		} catch (Exception e) {
			HibernateSessionFactory.rollbackTransaction();
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.OrtDAO#remove(ticketline.db.Ort)
	 */
	public void remove(Ort ort) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.delete(ort);
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
	 * @see ticketline.dao.interfaces.KundeDAO#search(ticketline.db.Ort)
	 */
	public List search(Ort ort) {
		
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			
			Criteria crit = session.createCriteria(Ort.class);
			
			if(ort.getComp_id().getBezeichnung() != null) crit.add(Restrictions.ilike("comp_id.bezeichnung", "%"+ort.getComp_id().getBezeichnung()+"%"));
			if(ort.getComp_id().getOrt() != null) crit.add(Restrictions.ilike("comp_id.ort", "%"+ort.getComp_id().getOrt()+"%"));
			if(ort.getStrasse() != null) crit.add(Restrictions.ilike("strasse", "%"+ort.getStrasse()+"%"));
			if(ort.getPlz() != null) crit.add(Restrictions.ilike("plz", "%"+ort.getPlz()+"%"));
			if(ort.getBundesland() != null) crit.add(Restrictions.ilike("bundesland", ort.getBundesland())); //less or equal
			if(ort.isAuffuehrungsort() != null) crit.add(Restrictions.eq("auffuehrungsort", ort.isAuffuehrungsort()));
			if(ort.isKiosk() != null) crit.add(Restrictions.eq("kiosk", ort.isKiosk()));
			if(ort.isVerkaufsstelle() != null) crit.add(Restrictions.eq("verkaufsstelle", ort.isVerkaufsstelle()));
			
			//sort
			crit.addOrder(Order.asc("comp_id.bezeichnung"));
			
			list = crit.list();
			//session.flush();
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
					"from " + Ort.class.getName() + " where " + where).list();
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
