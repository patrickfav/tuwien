package ticketline.dao.hibernate;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ticketline.dao.interfaces.VeranstaltungDAO;
import ticketline.db.Saal;
import ticketline.db.Veranstaltung;
import ticketline.db.VeranstaltungKey;

/**
 * Hibernate-Klasse fuer die Veranstaltung.
 *
 * @author geezmo
 */
public class VeranstaltungDAOHibernate implements VeranstaltungDAO {

	/**
	 *
	 * @see ticketline.dao.interfaces.VeranstaltungDAO#get(ticketline.db.VeranstaltungKey)
	 */
	public Veranstaltung get(VeranstaltungKey veranstaltungKey) {
		Veranstaltung newObject = null;

		try {
			Session session = HibernateSessionFactory.currentSession();
			newObject = (Veranstaltung) session.load(Veranstaltung.class,
					veranstaltungKey);
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return newObject;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.VeranstaltungDAO#getAll()
	 */
	public List getAll() {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			list = session.createQuery("from " + Veranstaltung.class.getName())
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
	 * @see ticketline.dao.interfaces.VeranstaltungDAO#save(ticketline.db.Veranstaltung)
	 */
	public void save(Veranstaltung veranstaltung) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.saveOrUpdate(veranstaltung);
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
	 * @see ticketline.dao.interfaces.VeranstaltungDAO#remove(ticketline.db.Veranstaltung)
	 */
	public void remove(Veranstaltung veranstaltung) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.delete(veranstaltung);
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
	 * @see ticketline.dao.interfaces.DAO#find(java.lang.String)
	 */
	public List find(String where) {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			list = session
					.createQuery(
							"from " + Veranstaltung.class.getName() + " where "
									+ where).list();
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return list;
	}

	/**
	 * @see ticketline.dao.interfaces.KundeDAO#search(ticketline.db.Veranstaltung)
	 */
	public List search(Veranstaltung veranstaltung) {
		
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			
			Criteria crit = session.createCriteria(Veranstaltung.class);
			
			if(veranstaltung.getComp_id().getBezeichnung() != null) crit.add(Restrictions.ilike("comp_id.bezeichnung", "%"+veranstaltung.getComp_id().getBezeichnung()+"%"));
			if(veranstaltung.getComp_id().getKategorie() != null) crit.add(Restrictions.ilike("comp_id.kategorie", "%"+veranstaltung.getComp_id().getKategorie()+"%"));
			if(veranstaltung.getDauer() != null) crit.add(Restrictions.le("dauer", veranstaltung.getDauer())); //less or equal
			if(veranstaltung.getJahrerstellung() != null) crit.add(Restrictions.eq("jahrerstellung", veranstaltung.getJahrerstellung()));
			if(veranstaltung.getInhalt() != null) crit.add(Restrictions.ilike("inhalt", "%"+veranstaltung.getInhalt()+"%")); //less or equal
			if(veranstaltung.getSubkategorie() != null) crit.add(Restrictions.ilike("subkategorie", "%"+veranstaltung.getSubkategorie()+"%"));
			if(veranstaltung.getSpracheton() != null) crit.add(Restrictions.ilike("spracheton", "%"+veranstaltung.getSpracheton()+"%"));
			if(veranstaltung.getSpracheut() != null) crit.add(Restrictions.ilike("spracheut", "%"+veranstaltung.getSpracheut()+"%"));
			
			//sort
			//crit.addOrder(Order.asc("comp_id.bezeichnung"));
			list = crit.list();
			
			//session.flush();
			//list = session.createQuery(query).list();
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
