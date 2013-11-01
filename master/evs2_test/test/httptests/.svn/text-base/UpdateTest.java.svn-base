package httptests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import model.Company;
import model.Person;
import model.Skill;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exceptions.DatabaseException;


public class UpdateTest extends AbstractRestTest{
	
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
	public void testUpdateSkill1() throws IOException, InterruptedException, DatabaseException{
		
		int id = 2;
		
		Skill s = new Skill();
		s.setId((long) id);
		s.setName("new Name");
		
    	exchange.setMethod("PUT");
    	exchange.setURL(HOST+SKILL_SERVICE+"/"+id);
    	
    	/* serialize and save as buffer */
    	b = new ByteArrayBuffer(serialize(s,SKILL_CONTENTTYPE,exchange));
    	
    	exchange.setRequestContent(b);
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
	public void testUpdatePerson1() throws IOException, InterruptedException, DatabaseException{
		
		int id = 10;
		
		Person p = new Person();
		p.setId((long) id);
		p.setPrename("New Name");
		
    	exchange.setMethod("PUT");
    	exchange.setURL(HOST+PERSON_SERVICE+"/"+id);
   
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
    	System.out.println(exchange.getResponseContent().toString()+"\n");
	}
	
	@Test
	public void testUpdateCompany() throws IOException, InterruptedException, DatabaseException{
		
		int id = 10;
		
		Company c = new Company();
		c.setFieldOfActivity("NEW FIELD");
		c.setName("NEW NAME");
		
    	exchange.setMethod("PUT");
    	exchange.setURL(HOST+COMPANY_SERVICE+"/"+id);
   
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
    	System.out.println(exchange.getResponseContent().toString()+"\n");
	}
}
