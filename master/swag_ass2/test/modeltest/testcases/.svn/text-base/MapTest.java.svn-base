package modeltest.testcases;

import models.Map;
import models.MapSquare;
import models.Troop;
import modeltest.testcases.abstr.AbstractModelTest;

import org.junit.Test;

import util.Transaction;
import daos.MapDAO;
import exceptions.NoTransactionException;
import java.util.List;
import java.util.ArrayList;

public class MapTest extends AbstractModelTest{
	
	
	
	@Test
	public void testInsert() throws NoTransactionException {
		Troop t = new Troop();
		t.setCountAxe(10);
		t.setCountSword(0);
		List<Troop> troops = new ArrayList<Troop>();
		troops.add(t);
		
		List<MapSquare> s = new ArrayList<MapSquare>();		
		MapSquare ms = new MapSquare();
		ms.setId((long)1);
		ms.setX(0);
		ms.setY(0);
		ms.setTroops(troops);
		
		Map m = new Map();
		m.setName("Map1");
		m.setMapSquares(s);
		
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
	public void testFind() throws NoTransactionException {
		Transaction.begin();
		List<Map> maps = MapDAO.findAll();
		Transaction.commit();

		Transaction.begin();
		for(Map m : maps)
		{
			System.out.println("Map("+m.getId()+") deleted");
			MapDAO.delete(m);
		}
		Transaction.commit();
	}
}
