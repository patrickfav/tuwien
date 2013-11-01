/*
 * PROJECT: TLJava
 * $RCSfile: HibernateSessionFactory.java,v $
 * $Revision: 1.8 $
 * AUTHOR: Mario Berhart
 * DATE: 07.12.2004
 * LANGUAGE: Java
 * DESCRIPTION: See class comment
 *
 * $Log: HibernateSessionFactory.java,v $
 * Revision 1.8  2006/05/23 14:25:46  0425918
 * code conventions lukas
 *
 * Revision 1.7  2006/05/22 12:16:49  0425918
 * code conventions lukas
 *
 * Revision 1.6  2006/05/22 11:59:40  0425918
 * code conventions lukas
 *
 * Revision 1.5  2006/05/12 20:25:23  0425906
 * code conventions update
 *
 * Revision 1.4  2006/05/05 23:13:48  0425906
 * neue javadoc ohne warnings
 *
 * Revision 1.3  2006/05/05 14:37:23  0425906
 * hibernate update auf 3.0
 *
 * Revision 1.2  2005/12/06 19:32:43  0425426
 * Update Hibernate 2.0 -> Hibernate 3.0, HSQLDB 1.7.3 -> HSQLDB 1.8.2
 *
 * Revision 1.1  2005/11/25 21:58:11  0425426
 * TLCore
 *
 * Revision 1.4  2004/12/10 15:43:49  tlcorej1
 * save added
 *
 * Revision 1.3  2004/12/09 16:44:27  tlcorej7
 * *** empty log message ***
 *
 * Revision 1.2  2004/12/07 17:48:38  tlcorej7
 * updates
 *
 * Revision 1.1  2004/12/07 16:51:41  tlcorej7
 * initial commit
 *
 */

package ticketline.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 *
 * @author mario
 */
public class HibernateSessionFactory {

	private static final SessionFactory sessionFactory;

	private static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();

	/** Holds a single instance of Session. */
	private static final ThreadLocal<Transaction> threadTransaction = new ThreadLocal<Transaction>();

	static {
		try {
			Configuration cfg = new Configuration();
			cfg.setInterceptor(new HibernateInterceptor());
			sessionFactory = cfg.configure().buildSessionFactory();
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the
	 * <code>SessionFactory</code> if needed.
	 *
	 * @return Session
	 * @throws NestedRuntimeException
	 */
	public static Session currentSession() throws RuntimeException {
		Session session = (Session) threadSession.get();
		try {
			if (session == null) {
				session = sessionFactory.openSession();
				threadSession.set(session);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"There was a problem retrieving the current session", e);
		}
		return session;
	}

	/**
	 * Close the single hibernate session instance.
	 *
	 * @throws NestedRuntimeException
	 */
	public static void closeSession() throws RuntimeException {
		try {
			Session session = (Session) threadSession.get();
			threadSession.set(null);
			if (session != null && session.isOpen()) {
				session.close();
			}
		} catch (HibernateException e) {
			throw new RuntimeException(
					"There was a problem closing the session", e);
		}
	}

	/**
	 * Begin a hiberante transaction.
	 *
	 * @throws NestedRuntimeException
	 */
	public static void beginTransaction() throws RuntimeException {
		try {
			Transaction tx = (Transaction) threadTransaction.get();
			if (tx == null) {
				tx = currentSession().beginTransaction();
				threadTransaction.set(tx);
			}
		} catch (HibernateException e) {
			throw new RuntimeException(
					"There was a problem beginning the transaction", e);
		}

	}

	/**
	 * Commit a hibernate transaction.
	 *
	 * @throws NestedRuntimeException
	 */
	public static void commitTransaction() throws RuntimeException {
		try {
			Transaction tx = (Transaction) threadTransaction.get();

			if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
				tx.commit();
			}
			threadTransaction.set(null);
		} catch (HibernateException e) {
			// do nothing
		}

	}

	/**
	 * Rollback a hibernate transaction.
	 *
	 * @throws NestedRuntimeException
	 */
	public static void rollbackTransaction() throws RuntimeException {
		try {
			Transaction tx = (Transaction) threadTransaction.get();
			threadTransaction.set(null);
			if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
				tx.rollback();
			}
			closeSession();
		} catch (HibernateException e) {
			throw new RuntimeException(
					"There was a problem rolling back the transaction", e);
		}

	}

}
