package modeltest.testcases;

import models.Building;
import modeltest.testcases.abstr.AbstractModelTest;

import org.junit.Test;

import util.Transaction;
import daos.BuildingDAO;
import exceptions.NoTransactionException;

public class BuildingTest extends AbstractModelTest{
	
	@Test
	public void testInsert() throws NoTransactionException {
		Building b = new Building();
		b.setLevel(4);
		
		Transaction.begin();
		BuildingDAO.insert(b);
		Transaction.commit();
	}
	
	@Test
	public void testRemove() throws NoTransactionException {
		Building b = new Building();
		b.setLevel(4);
		
		Transaction.begin();
		BuildingDAO.insert(b);
		Transaction.commit();
		
		Transaction.begin();
		BuildingDAO.delete(b);
		Transaction.commit();
	}
	
	@Test
	public void testFind() {
		/* des test i ned */
	}
}
