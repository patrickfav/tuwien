package modeltest.testcases;

import models.Troop;
import modeltest.testcases.abstr.AbstractModelTest;

import org.junit.Test;

import util.Transaction;
import daos.TroopDAO;
import exceptions.NoTransactionException;

public class TroopTest extends AbstractModelTest {

	@Test
	public void testInsert() throws NoTransactionException {
		Troop r = new Troop();
		r.setCountSword(4);
		r.setCountAxe(4);
		
		Transaction.begin();
		TroopDAO.insert(r);
		Transaction.commit();
	}
	
	@Test
	public void testRemove() throws NoTransactionException {
		Troop r = new Troop();
		r.setCountSword(4);
		r.setCountAxe(4);
		
		Transaction.begin();
		TroopDAO.insert(r);
		Transaction.commit();
		
		Transaction.begin();
		TroopDAO.delete(r);
		Transaction.commit();
	}
	
	@Test
	public void testFind() {
		/* des test i ned */
	}
	
}
