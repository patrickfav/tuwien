package ticketline.dao.hibernate;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ticketline.dao.interfaces.KategorieDAO;
import ticketline.db.Kategorie;
import ticketline.db.KategorieKey;

/**
 * Hibernate-Klasse fuer die Kategorie.
 *
 * @author geezmo
 */
public class KategorieDAOHibernate implements KategorieDAO {

	/**
	 *
	 * @see ticketline.dao.interfaces.KategorieDAO#get(ticketline.db.KategorieKey)
	 */
	public Kategorie get(KategorieKey kategorieKey) {
		Kategorie newObject = null;

		try {
			Session session = HibernateSessionFactory.currentSession();

			newObject = (Kategorie) session.load(Kategorie.class, kategorieKey);
		} catch (HibernateException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}

		return newObject;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.KategorieDAO#getAll()
	 */
	public List getAll() {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			list = session.createQuery("from " + Kategorie.class.getName())
					.list();
		} catch (HibernateException e) {
			HibernateSessionFactory.rollbackTransaction();
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
		return list;
	}

	/**
	 *
	 * @see ticketline.dao.interfaces.KategorieDAO#save(ticketline.db.Kategorie)
	 */
	public void save(Kategorie kategorie) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.saveOrUpdate(kategorie);
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
	 * @see ticketline.dao.interfaces.KategorieDAO#remove(ticketline.db.Kategorie)
	 */
	public void remove(Kategorie kategorie) {
		try {
			Session session = HibernateSessionFactory.currentSession();
			HibernateSessionFactory.beginTransaction();
			session.delete(kategorie);
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
					"from " + Kategorie.class.getName() + " where " + where)
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
	public List search(Kategorie kategorie) throws RuntimeException {
		List list = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			Criteria crit = session.createCriteria(Kategorie.class);
			if(kategorie.getSaal()!=null){
				crit.add(Restrictions.eq("saal.comp_id.bezeichnung",kategorie.getSaal().getComp_id().getBezeichnung()));
				crit.add(Restrictions.eq("saal.comp_id.ortbez",kategorie.getSaal().getComp_id().getOrtbez()));
				crit.add(Restrictions.eq("saal.comp_id.ort",kategorie.getSaal().getComp_id().getOrt()));
			}
			list = crit.list();
		} catch (HibernateException e) {
			System.out.println(e.getStackTrace());
			throw new RuntimeException(e.getMessage());
		} finally {
			// HibernateSessionFactory.closeSession();
		}
		return list;
	}

}
