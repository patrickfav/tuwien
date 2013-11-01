package modeltest.testcases;

import org.junit.Test;

import util.Transaction;
import daos.BuildingCostsDAO;
import exceptions.NoTransactionException;
import models.BuildingCosts;

public class BuildingCostsTest {

	@Test
	public void testInsert() throws NoTransactionException {
		BuildingCosts m = new BuildingCosts();
		m.setNeededWood(10);
		m.setNeedIron(5);
		
		Transaction.begin();
		BuildingCostsDAO.insert(m);
		Transaction.commit();
	}
	
	@Test
	public void testRemove() throws NoTransactionException {
		BuildingCosts m = new BuildingCosts();
		
		Transaction.begin();
		BuildingCostsDAO.insert(m);
		Transaction.commit();
		
		Transaction.begin();
		BuildingCostsDAO.delete(m);
		Transaction.commit();
	}
	
	@Test
	public void testFind() {
		/* des test i ned */
	}
}
