package util;

public abstract class Transaction 
{
	public static void begin()
	{
		HibernateUtil.getEntityManager().getTransaction().begin();
	}
	
	public static void rollback()
	{
		HibernateUtil.getEntityManager().getTransaction().rollback();
	}
	
	public static void commit()
	{
		HibernateUtil.getEntityManager().getTransaction().commit();
	}
}
