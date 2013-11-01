package modeltest.testcases;

import models.BuildingType;
import modeltest.testcases.abstr.AbstractModelTest;

import org.junit.Test;

import util.Transaction;
import daos.BuildingTypeDAO;
import exceptions.NoTransactionException;


public class BuildingTypeTest  extends AbstractModelTest{
	
	@Test
	public void testInsert() throws NoTransactionException {
		BuildingType b = new BuildingType();
		b.setOutput(1);
		
		Transaction.begin();
		BuildingTypeDAO.insert(b);
		Transaction.commit();
	}
	
	@Test
	public void testRemove() throws NoTransactionException {
		BuildingType b = new BuildingType();
		b.setOutput(1);
		
		Transaction.begin();
		BuildingTypeDAO.insert(b);
		Transaction.commit();
		
		Transaction.begin();
		BuildingTypeDAO.delete(b);
		Transaction.commit();
	}
	
	@Test
	public void testFind() {
		/* des test i ned */
	}

}
