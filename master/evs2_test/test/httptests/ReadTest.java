package httptests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.Address;
import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpExchange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReadTest extends AbstractRestTest{
	
	private ContentExchange exchange;
	
	@Before
	public void before() {
		exchange = new ContentExchange();
	}
	
	@After
	public void after() {
		exchange = null;
	}
	
	@Test
	public void testReadSkill() throws IOException, InterruptedException {
		
		int id = 1;
		exchange.setRequestHeader("Host", "10.0.0.0");
		
    	exchange.setMethod("GET");
    	exchange.setURL(HOST+SKILL_SERVICE+"/"+id);
    	client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	assertEquals(HttpExchange.STATUS_COMPLETED,exchangeState);
    	assertEquals(HttpServletResponse.SC_OK,exchange.getResponseStatus());
    	
    	/* show output for vebose debbuging */
    	System.out.println("\nOutput:");
    	System.out.println(exchange.getResponseContent().toString()+"\n");
	}
	
	@Test
	public void testReadPerson() throws IOException, InterruptedException {
		
		int id = 1;
		
    	exchange.setMethod("GET");
    	exchange.setURL(HOST+PERSON_SERVICE+"/"+id);
    	client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	assertEquals(HttpExchange.STATUS_COMPLETED,exchangeState);
    	assertEquals(HttpServletResponse.SC_OK,exchange.getResponseStatus());
    	
    	/* show output for vebose debbuging */
    	System.out.println("\nOutput:");
    	System.out.println(exchange.getResponseContent().toString()+"\n");
	}	
    
	@Test
	public void testReadCompany() throws IOException, InterruptedException {
		
		int id = 1;
		
    	exchange.setMethod("GET");
    	exchange.setURL(HOST+COMPANY_SERVICE+"/"+id);
    	client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	assertEquals(HttpExchange.STATUS_COMPLETED,exchangeState);
    	assertEquals(HttpServletResponse.SC_OK,exchange.getResponseStatus());
    	
    	/* show output for vebose debbuging */
    	System.out.println("\nOutput:");
    	System.out.println(exchange.getResponseContent().toString()+"\n");
	}	
    	
	@Test
	public void testReadOccupation() throws IOException, InterruptedException {
		
		int id = 1;
		
    	exchange.setMethod("GET");
    	exchange.setURL(HOST+OCCUPATION_SERVICE+"/"+id);
    	client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	assertEquals(HttpExchange.STATUS_COMPLETED,exchangeState);
    	assertEquals(HttpServletResponse.SC_OK,exchange.getResponseStatus());
    	
    	/* show output for vebose debbuging */
    	System.out.println("\nOutput:");
    	System.out.println(exchange.getResponseContent().toString()+"\n");
	}	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
  
}
