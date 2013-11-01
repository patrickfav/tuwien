package modeltest.testcases;

import models.MapSquare;
import modeltest.testcases.abstr.AbstractModelTest;

import org.junit.Test;

import util.Transaction;
import daos.MapSquareDAO;
import exceptions.NoTransactionException;

public class MapSquareTest extends AbstractModelTest{
	
	@Test
	public void testInsert() throws NoTransactionException {
		MapSquare m = new MapSquare();
		m.setX(1);
		m.setY(2);
		
		Transaction.begin();
		MapSquareDAO.insert(m);
		Transaction.commit();
	}
	
	@Test
	public void testRemove() throws NoTransactionException {
		MapSquare m = new MapSquare();
		m.setX(1);
		m.setY(2);
		
		Transaction.begin();
		MapSquareDAO.insert(m);
		Transaction.commit();
		
		Transaction.begin();
		MapSquareDAO.delete(m);
		Transaction.commit();
	}
	
	@Test
	public void testFind() {
		/* des test i ned */
	}
}
