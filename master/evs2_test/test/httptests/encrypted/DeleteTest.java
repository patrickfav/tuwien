package httptests.encrypted;

import static org.junit.Assert.assertEquals;

import httptests.AbstractRestTest;

import java.io.IOException;

import org.eclipse.jetty.client.HttpExchange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exceptions.DatabaseException;


public class DeleteTest extends AbstractRestTest{
	
	private HttpExchange exchange;
	
	
	@Before
	public void before() {
		exchange = new HttpExchange();
	}
	
	@After
	public void after() {
		exchange = null;
	}
	
	
	@Test
	public void testDeletePerson1() throws IOException, InterruptedException, DatabaseException{
		
		int id = 10;
		
    	exchange.setMethod("DELETE");
    	exchange.setURL(HOST+PERSON_SERVICE+"/"+id);
    	/*client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	assertEquals(HttpExchange.STATUS_COMPLETED,exchangeState);*/
	}
	
	@Test 
	public void testDeleteCompany1() throws IOException, InterruptedException, DatabaseException{
		
		int id = 5;
		
    	exchange.setMethod("DELETE");
    	exchange.setURL(HOST+COMPANY_SERVICE+"/"+id);
    	/*client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	assertEquals(HttpExchange.STATUS_COMPLETED,exchangeState);*/
	}
}
