package modeltest.testcases;

import org.junit.Test;

import util.Transaction;
import daos.BaseDAO;
import exceptions.NoTransactionException;
import models.Base;

public class BaseTest {

	@Test
	public void testInsert() throws NoTransactionException {
		Base m = new Base();
		
		Transaction.begin();
		BaseDAO.insert(m);
		Transaction.commit();
	}
	
	@Test
	public void testRemove() throws NoTransactionException {
		Base m = new Base();
		
		Transaction.begin();
		BaseDAO.insert(m);
		Transaction.commit();
		
		Transaction.begin();
		BaseDAO.delete(m);
		Transaction.commit();
	}
	
	@Test
	public void testFind() {
		/* des test i ned */
	}
}
