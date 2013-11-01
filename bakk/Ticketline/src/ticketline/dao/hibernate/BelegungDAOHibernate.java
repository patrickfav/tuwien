package ticketline.dao.hibernate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ticketline.dao.interfaces.BelegungDAO;
import ticketline.db.Belegung;
import ticketline.db.BelegungKey;
import ticketline.db.Saal;

/**
 * Hibernate-Klasse fuer die Belegung.
 *
 * @author geezmo
 */
public class BelegungDAOHibernate implements BelegungDAO {

	/**
	 *
	 * @see ticketline.dao.interfaces.BelegungDAO#get(ticketline.db.BelegungKey)
	 */
	public Belegung get(BelegungKey belegungKey) {
		Belegung newObject = null;

		try {
			Session session = HibernateSessionFactory.currentSession();
			newObject = (Belegung) session.load(Belegung.class, belegungKey);
		} catch (HibernateException e) {

			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return newObject;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.BelegungDAO#getAll()
	 */
	public List getAll() {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();

			list = session.createQuery("from " + Belegung.class.getName())
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
	 * @see ticketline.dao.interfaces.BelegungDAO#save(ticketline.db.Belegung)
	 */
	public void save(Belegung belegung) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.saveOrUpdate(belegung);
			HibernateSessionFactory.commitTransaction();
		} catch (HibernateException e) {
			HibernateSessionFactory.rollbackTransaction();
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
	}

	public void update(Belegung belegung) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.update(belegung);
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
	 * @see ticketline.dao.interfaces.BelegungDAO#remove(ticketline.db.Belegung)
	 */
	public void remove(Belegung belegung) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.delete(belegung);
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
					"from " + Belegung.class.getName() + " where " + where)
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

	public List<Belegung> getBelegungen(Saal saal, Date from, Date to)
		throws RuntimeException {
		
		try {
			Session session = HibernateSessionFactory.currentSession();
			Criteria crit = session.createCriteria(Belegung.class);
			
			if(saal.getComp_id().getBezeichnung() != null) crit.add(Restrictions.eq("comp_id.saalbez",saal.getComp_id().getBezeichnung()).ignoreCase());
			if(saal.getComp_id().getOrtbez() != null) crit.add(Restrictions.eq("comp_id.ortbez",saal.getComp_id().getOrtbez()));
			if(saal.getComp_id().getOrt() != null) crit.add(Restrictions.eq("comp_id.ort", saal.getComp_id().getOrt()));
			
			if(from!=null){
				if(to!= null){
					//Between
					crit.add(Restrictions.between("comp_id.datumuhrzeit",new Timestamp(from.getTime()),new Timestamp(to.getTime())));
				} else {
					//Greater than From
					crit.add(Restrictions.gt("comp_id.datumuhrzeit", new Timestamp(from.getTime())));
				}
			} else {
				if(to!= null){
					//Less than To
					crit.add(Restrictions.lt("comp_id.datumuhrzeit", new Timestamp(to.getTime())));
				}
			}
			
			return crit.list();
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		}	
	}
}
