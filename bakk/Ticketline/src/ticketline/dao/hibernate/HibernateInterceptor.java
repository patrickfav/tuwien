/*
 * PROJECT: TLJava
 * $Id: HibernateInterceptor.java,v 1.5 2006/05/22 11:59:40 0425918 Exp $
 */

package ticketline.dao.hibernate;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.CallbackException;
import org.hibernate.EntityMode;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import ticketline.db.Entity;

/**
 * Hibernate Interceptor.
 *
 * @author manuel
 */
public class HibernateInterceptor implements Interceptor, Serializable {

	private static final long serialVersionUID = -191599284735375777L;

	public void onCollectionRecreate(Object arg0, Serializable arg1)
			throws CallbackException {
		// do nothing
	}

	public void onCollectionRemove(Object arg0, Serializable arg1)
			throws CallbackException {
		// do nothing
	}

	public void onCollectionUpdate(Object arg0, Serializable arg1)
			throws CallbackException {
		// do nothing
	}

	public String onPrepareStatement(String arg0) {
		return arg0;
	}

	public void afterTransactionBegin(Transaction arg0) {
		// do nothing
	}

	public void afterTransactionCompletion(Transaction arg0) {
		// do nothing
	}

	public void beforeTransactionCompletion(Transaction arg0) {
		// do nothing
	}

	public Object getEntity(String arg0, Serializable arg1)
			throws CallbackException {
		return null;
	}

	public String getEntityName(Object arg0) throws CallbackException {
		return null;
	}

	public Object instantiate(String arg0, EntityMode arg1, Serializable arg2)
			throws CallbackException {
		return null;
	}

	public Boolean isTransient(Object arg0) {
		return null;
	}

	/**
	 * @see Interceptor#onLoad(Object, Serializable, Object[], String[], Type[])
	 */
	public boolean onLoad(Object arg0, Serializable arg1, Object[] arg2,
			String[] arg3, Type[] arg4) throws CallbackException {
		if (arg0 instanceof Entity)
			((Entity) arg0).onLoad();
		return false;
	}

	/**
	 * @see Interceptor#onFlushDirty(Object, Serializable, Object[], Object[],
	 *      String[], Type[])
	 */
	public boolean onFlushDirty(Object arg0, Serializable arg1, Object[] arg2,
			Object[] arg3, String[] arg4, Type[] arg5) throws CallbackException {
		return false;
	}

	/**
	 * @see Interceptor#onSave(Object, Serializable, Object[], String[], Type[])
	 */
	public boolean onSave(Object arg0, Serializable arg1, Object[] arg2,
			String[] arg3, Type[] arg4) throws CallbackException {
		if (arg0 instanceof Entity)
			((Entity) arg0).onSave();
		// System.out.println(((Entity) arg0).isSaved());
		return false;

	}

	/**
	 * @see Interceptor#onDelete(Object, Serializable, Object[], String[],
	 *      Type[])
	 */
	public void onDelete(Object arg0, Serializable arg1, Object[] arg2,
			String[] arg3, Type[] arg4) throws CallbackException {
		// do nothing
	}

	/**
	 * @see Interceptor#preFlush(Iterator)
	 */
	public void preFlush(Iterator arg0) throws CallbackException {
		// do nothing
	}

	/**
	 * @see Interceptor#postFlush(Iterator)
	 */
	public void postFlush(Iterator arg0) throws CallbackException {
		// do nothing
	}

	/**
	 * @param arg0
	 * @return Boolean
	 */
	public Boolean isUnsaved(Object arg0) {
		if (arg0 instanceof Entity) {
			return new Boolean(!((Entity) arg0).isSaved());
		}
		return null;
	}

	/**
	 * @see Interceptor#findDirty(Object, Serializable, Object[], Object[],
	 *      String[], Type[])
	 */
	public int[] findDirty(Object arg0, Serializable arg1, Object[] arg2,
			Object[] arg3, String[] arg4, Type[] arg5) {
		return null;
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return Object
	 * @throws CallbackException
	 */
	public Object instantiate(Class arg0, Serializable arg1)
			throws CallbackException {
		return null;
	}

}
