package swag.util;


import java.util.LinkedList;
import java.util.List;

import swag.interfaces.DAOListener;

public abstract class DAOManager 
{
	private static List<DAOListener> listeners = new LinkedList<DAOListener>();
	
	public static void addListener(DAOListener listener)
	{
		listeners.add(listener);
	}
	
	public static void removeListener(DAOListener listener)
	{
		listeners.remove(listener);
	}
	
	public static void beforeInsert(Class c)
	{
		for(DAOListener l : listeners)
		{
			l.beforeInsert(c);
		}
	}

	public static void afterInsert(Class c)
	{
		for(DAOListener l : listeners)
		{
			l.afterInsert(c);
		}
	}

	public static void beforeUpdate(Class c)
	{
		for(DAOListener l : listeners)
		{
			l.beforeUpdate(c);
		}
	}

	public static void afterUpdate(Class c)
	{
		for(DAOListener l : listeners)
		{
			l.afterUpdate(c);
		}
	}

	
	public static void beforeDelete(Class c)
	{
		for(DAOListener l : listeners)
		{
			l.beforeDelete(c);
		}
	}
	
	public static void afterDelete(Class c)
	{
		for(DAOListener l : listeners)
		{
			l.afterDelete(c);
		}
	}
	
	public static void beforeFind(Class c, String search)
	{
		for(DAOListener l : listeners)
		{
			l.beforeFind(c, search);
		}
	}
	
	public static void afterFind(Class c, String search)
	{
		for(DAOListener l : listeners)
		{
			l.afterFind(c, search);
		}
	}
}
