package modeltest.testcases;
import models.Player;
import modeltest.testcases.abstr.AbstractModelTest;

import org.junit.Test;

import util.Transaction;
import daos.PlayerDAO;
import exceptions.NoTransactionException;

public class PlayerTest extends AbstractModelTest{
	
	@Test
	public void testInsert() throws NoTransactionException {
		Player p = new Player();
		p.setFullname("Max Mustermann");
		p.setOnline(false);
		p.setPassword("pass");
		p.setUsername("mmustermann");
		
		Transaction.begin();
		PlayerDAO.insert(p);
		Transaction.commit();
	}
	
	@Test
	public void testRemove() throws NoTransactionException {
		Player p = new Player();
		p.setFullname("Max Mustermann");
		p.setOnline(false);
		p.setPassword("pass");
		p.setUsername("mmustermann");
		
		Transaction.begin();
		PlayerDAO.insert(p);
		Transaction.commit();
		
		Transaction.begin();
		PlayerDAO.delete(p);
		Transaction.commit();
	}
	
	@Test
	public void testFind() {
		/* des test i ned */
	}
}
