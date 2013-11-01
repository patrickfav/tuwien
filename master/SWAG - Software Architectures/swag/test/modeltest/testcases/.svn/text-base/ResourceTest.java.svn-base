package modeltest.testcases;

import models.Resources;
import modeltest.testcases.abstr.AbstractModelTest;

import org.junit.Test;

import daos.ResourcesDAO;
import exceptions.NoTransactionException;

import util.Transaction;

public class ResourceTest extends AbstractModelTest{
	
	
	
	@Test
	public void testInsert() throws NoTransactionException {
		Resources r = new Resources();
		r.setCountIron(1);
		r.setCountWood(2);
		
		Transaction.begin();
		ResourcesDAO.insert(r);
		Transaction.commit();
	}
	
	@Test
	public void testRemove() throws NoTransactionException {
		Resources r = new Resources();
		r.setCountIron(1);
		r.setCountWood(2);
		
		Transaction.begin();
		ResourcesDAO.insert(r);
		Transaction.commit();
		
		Transaction.begin();
		ResourcesDAO.delete(r);
		Transaction.commit();
	}
	
	@Test
	public void testFind() {
	}
}
