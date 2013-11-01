package modeltest.testcases;

import models.Message;
import org.junit.Test;

import util.Transaction;
import daos.MessageDAO;
import exceptions.NoTransactionException;

public class MessageTest {

	@Test
	public void testInsert() throws NoTransactionException {
		Message r = new Message();
		r.setContent("Hallo");
		
		Transaction.begin();
		MessageDAO.insert(r);
		Transaction.commit();
	}
	
	@Test
	public void testRemove() throws NoTransactionException {
		Message r = new Message();
		r.setContent("Hallo 2");
		Transaction.begin();
		MessageDAO.insert(r);
		Transaction.commit();
		
		Transaction.begin();
		MessageDAO.delete(r);
		Transaction.commit();
	}
	
	@Test
	public void testFind() {
		
	}
}
