package modeltest.testcases;

import models.BaseSquare;
import modeltest.testcases.abstr.AbstractModelTest;

import org.junit.Test;

import util.Transaction;
import daos.BaseSquareDAO;
import exceptions.NoTransactionException;

public class BaseSquareTest extends AbstractModelTest{
	
	@Test
	public void testInsert() throws NoTransactionException {
		BaseSquare s = new BaseSquare();
		s.setX(1);
		s.setY(2);
		
		Transaction.begin();
		BaseSquareDAO.insert(s);
		Transaction.commit();
	}
	
	@Test
	public void testRemove() throws NoTransactionException {
		BaseSquare s = new BaseSquare();
		s.setX(1);
		s.setY(2);
		
		Transaction.begin();
		BaseSquareDAO.insert(s);
		Transaction.commit();
		
		Transaction.begin();
		BaseSquareDAO.delete(s);
		Transaction.commit();
	}
	
	@Test
	public void testFind() {
		/* des test i ned */
	}
}
