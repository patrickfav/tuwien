package ticketline.dao.hibernate;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ticketline.dao.interfaces.SaalDAO;
import ticketline.db.Kunde;
import ticketline.db.Saal;
import ticketline.db.SaalKey;

/**
 * Hibernate-Klasse fuer die Saele.
 *
 * @author geezmo
 */
public class SaalDAOHibernate implements SaalDAO {

	/**
	 *
	 * @see ticketline.dao.interfaces.SaalDAO#get(ticketline.db.SaalKey)
	 */
	public Saal get(SaalKey key) {
		Saal newObject = null;

		try {
			Session session = HibernateSessionFactory.currentSession();
			newObject = (Saal) session.load(Saal.class, key);
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return newObject;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.SaalDAO#getAll()
	 */
	public List getAll() {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			list = session.createQuery("from " + Saal.class.getName()).list();
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
		return list;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.SaalDAO#save(ticketline.db.Saal)
	 */
	public void save(Saal saal) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.saveOrUpdate(saal);
			HibernateSessionFactory.commitTransaction();
			//session.flush();
		} catch (HibernateException e) {
			HibernateSessionFactory.rollbackTransaction();
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.SaalDAO#remove(ticketline.db.Saal)
	 */
	public void remove(Saal saal) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.delete(saal);
			HibernateSessionFactory.commitTransaction();
			//session.flush();
		} catch (Exception e) {
			HibernateSessionFactory.rollbackTransaction();
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
	}
	
	/**
	 * @see ticketline.dao.interfaces.KundeDAO#search(ticketline.db.Saal)
	 */
	public List search(Saal saal) {
		
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			
			Criteria crit = session.createCriteria(Saal.class);
			if(saal.getComp_id() != null) {
				if(saal.getComp_id().getBezeichnung() != null) crit.add(Restrictions.ilike("comp_id.bezeichnung", "%"+saal.getComp_id().getBezeichnung()+"%"));
				if(saal.getComp_id().getOrt() != null) crit.add(Restrictions.ilike("comp_id.ort", "%"+saal.getComp_id().getOrt()+"%"));
				if(saal.getComp_id().getOrtbez() != null) crit.add(Restrictions.ilike("comp_id.ortbez", "%"+saal.getComp_id().getOrtbez()+"%"));
			}
			if(saal.getTyp() != null) crit.add(Restrictions.ilike("typ", "%"+saal.getTyp()+"%"));
			if(saal.getAnzplaetze() != null) crit.add(Restrictions.le("anzplaetze", saal.getAnzplaetze())); //less or equal
			if(saal.getKostenprotag() != null) crit.add(Restrictions.eq("kostenprotag", saal.getKostenprotag()));
			
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
					"from " + Saal.class.getName() + " where " + where).list();
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
