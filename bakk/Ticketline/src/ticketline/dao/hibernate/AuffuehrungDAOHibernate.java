package ticketline.dao.hibernate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ticketline.bl.GuiMemory;
import ticketline.dao.interfaces.AuffuehrungDAO;
import ticketline.db.Auffuehrung;
import ticketline.db.AuffuehrungKey;
import ticketline.gui.AuffuehrungenSuchenGui;

/**
 * Hibernate-Klasse fuer die Auffuehrung.
 *
 * @author geezmo, AndiS
 */
public class AuffuehrungDAOHibernate implements AuffuehrungDAO {

	/**
	 *
	 * @see ticketline.dao.interfaces.AuffuehrungDAO#get(ticketline.db.AuffuehrungKey)
	 */
	public Auffuehrung get(AuffuehrungKey auffuehrungKey) {
		Auffuehrung newObject = null;

		try {
			Session session = HibernateSessionFactory.currentSession();
			newObject = (Auffuehrung) session.load(Auffuehrung.class,
					auffuehrungKey);
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return newObject;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.AuffuehrungDAO#getAll()
	 */
	public List getAll() {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			list = session.createQuery("from " + Auffuehrung.class.getName())
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
	 * @see ticketline.dao.interfaces.AuffuehrungDAO#save(ticketline.db.Auffuehrung)
	 */
	public void save(Auffuehrung auffuehrung) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.saveOrUpdate(auffuehrung);
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
	 * @see ticketline.dao.interfaces.AuffuehrungDAO#remove(ticketline.db.Auffuehrung)
	 */
	public void remove(Auffuehrung auffuehrung) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.delete(auffuehrung);
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
			list = session.createQuery(
					"from " + Auffuehrung.class.getName() + " where " + where)
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

	@Override
	public List search(Auffuehrung auffuehrung) throws RuntimeException {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			Criteria crit = session.createCriteria(Auffuehrung.class);
			
			if(auffuehrung.getComp_id() != null) {
				if(auffuehrung.getComp_id().getOrt()!=null)crit.add(Restrictions.eq("comp_id.ort",auffuehrung.getComp_id().getOrt()));
				if(auffuehrung.getComp_id().getOrtbez()!=null)crit.add(Restrictions.eq("comp_id.ortbez",auffuehrung.getComp_id().getOrtbez()));
				if(auffuehrung.getComp_id().getSaalbez()!=null)crit.add(Restrictions.eq("comp_id.saalbez",auffuehrung.getComp_id().getSaalbez()));
				if(auffuehrung.getComp_id().getDatumuhrzeit()!=null)crit.add(Restrictions.like("comp_id.datumuhrzeit",auffuehrung.getComp_id().getDatumuhrzeit()));
			}
			
			if(auffuehrung.getVeranstaltung() != null)crit.add(Restrictions.eq("veranstaltung",auffuehrung.getVeranstaltung()));
			if(auffuehrung.getPreis() != null)crit.add(Restrictions.ilike("preis","%"+auffuehrung.getPreis()+"%"));
			if(auffuehrung.isStorniert() != null)crit.add(Restrictions.eq("storniert",auffuehrung.isStorniert()));
			if(auffuehrung.getHinweis()!=null)crit.add(Restrictions.ilike("hinweis","%"+auffuehrung.getHinweis()+"%"));
			
			//sort
			//crit.addOrder(Order.asc("veranstaltung"));
			list = crit.list();
			//session.flush();
		} catch (HibernateException e) {
			System.out.println(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
		return list;
	}
	
	public List searchWithDate(Auffuehrung auffuehrung, Date from, Date to) throws RuntimeException {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			Criteria crit = session.createCriteria(Auffuehrung.class);
			
			if(auffuehrung.getComp_id().getOrt()!=null)crit.add(Restrictions.eq("comp_id.ort",auffuehrung.getComp_id().getOrt()));
			if(auffuehrung.getComp_id().getOrtbez()!=null)crit.add(Restrictions.eq("comp_id.ortbez",auffuehrung.getComp_id().getOrtbez()));
			if(auffuehrung.getComp_id().getSaalbez()!=null)crit.add(Restrictions.eq("comp_id.saalbez",auffuehrung.getComp_id().getSaalbez()));
			//if(auffuehrung.getComp_id().getDatumuhrzeit()!=null)crit.add(Restrictions.like("comp_id.datumuhrzeit",auffuehrung.getComp_id().getDatumuhrzeit()));	
			
			if(auffuehrung.getVeranstaltung() != null)crit.add(Restrictions.eq("veranstaltung",auffuehrung.getVeranstaltung()));
			if(auffuehrung.getPreis() != null)crit.add(Restrictions.ilike("preis","%"+auffuehrung.getPreis()+"%"));
			if(auffuehrung.isStorniert() != null)crit.add(Restrictions.eq("storniert",auffuehrung.isStorniert()));
			if(auffuehrung.getHinweis()!=null)crit.add(Restrictions.ilike("hinweis","%"+auffuehrung.getHinweis()+"%"));
			 
			if(from!=null){
				if(to!= null){
					//Between
					crit.add(Restrictions.between("comp_id.datumuhrzeit",new Timestamp(from.getTime()),new Timestamp(to.getTime())));
		}
				else{
					//Greater than From
					crit.add(Restrictions.gt("comp_id.datumuhrzeit", new Timestamp(from.getTime())));
				}
			}else
			{
				if(to!= null){
					//Less than To
					crit.add(Restrictions.lt("comp_id.datumuhrzeit", new Timestamp(to.getTime())));
				}
			}
			
			
			list = crit.list();
			//session.flush();
		} catch (HibernateException e) {
			System.out.println(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
		return list;
	}
}
