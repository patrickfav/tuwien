package httptests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import model.Company;
import model.Person;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exceptions.DatabaseException;


public class SearchTest extends AbstractRestTest{
	
	private ContentExchange exchange;
	private Buffer b;
	
	@Before
	public void before() {
		exchange = new ContentExchange();
	}
	
	@After
	public void after() {
		exchange = null;
	}
	
	@Test
	public void testSearchPerson1() throws IOException, InterruptedException, DatabaseException{
		
		Person p = new Person();
		p.setPrename("ry");
		
    	exchange.setMethod("PUT");
    	exchange.setURL(HOST+PERSON_SERVICE+"/"+SEARCH_URL_KEYWORD);
    	
    	/* serialize and save as buffer */
    	b = new ByteArrayBuffer(serialize(p,PERSON_CONTENTTYPE,exchange));
    	
    	exchange.setRequestContent(b);
    	client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	assertEquals(HttpExchange.STATUS_COMPLETED,exchangeState);
    	assertEquals(HttpServletResponse.SC_OK,exchange.getResponseStatus());
    	
    	/* show output for vebose debbuging */
    	System.out.println("\nOutput:");
    	System.out.println(exchange.getResponseContent());
	}
	
	@Test
	public void testSearchCompany1() throws IOException, InterruptedException, DatabaseException{
		
		Company c = new Company();
		c.setFieldOfActivity("e");
		
    	exchange.setMethod("PUT");
    	exchange.setURL(HOST+COMPANY_SERVICE+"/"+SEARCH_URL_KEYWORD);
    	
    	/* serialize and save as buffer */
    	b = new ByteArrayBuffer(serialize(c,COMPANY_CONTENTTYPE,exchange));
    	
    	exchange.setRequestContent(b);
    	client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	assertEquals(HttpExchange.STATUS_COMPLETED,exchangeState);
    	assertEquals(HttpServletResponse.SC_OK,exchange.getResponseStatus());
    	
    	/* show output for vebose debbuging */
    	System.out.println("\nOutput:");
    	System.out.println(exchange.getResponseContent());
	}
}
