package modeltest.testcases;

import models.Map;
import modeltest.testcases.abstr.AbstractModelTest;

import org.junit.Test;

import util.Transaction;
import daos.MapDAO;
import exceptions.NoTransactionException;

public class MapTest extends AbstractModelTest{
	
	
	
	@Test
	public void testInsert() throws NoTransactionException {
		Map m = new Map();
		m.setMapSquares(null);
		
		Transaction.begin();
		MapDAO.insert(m);
		Transaction.commit();
	}
	
	@Test
	public void testRemove() throws NoTransactionException {
		Map m = new Map();
		m.setMapSquares(null);
		
		Transaction.begin();
		MapDAO.insert(m);
		Transaction.commit();
		
		Transaction.begin();
		MapDAO.delete(m);
		Transaction.commit();
	}
	
	@Test
	public void testFind() {
		/* des test i ned */
	}
}
