package httptests.encrypted;

import static org.junit.Assert.assertEquals;

import httptests.AbstractRestTest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
	public void testUpdatePerson1() throws IOException, InterruptedException, DatabaseException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		
		int id = 10;
		
		Person p = new Person();
		p.setId((long) id);
		p.setPrename("New Name");
		
    	exchange.setMethod("PUT");
    	exchange.setURL(HOST+PERSON_SERVICE+"/"+id);
   
    	/* serialize and save as buffer */
    	b = new ByteArrayBuffer(scAsym.encryptRSAWithServerPublicKey(serialize(p,PERSON_CONTENTTYPE,exchange)));
    	
    	exchange.setRequestContent(b);
    	client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	assertEquals(HttpExchange.STATUS_COMPLETED,exchangeState);
    	assertEquals(HttpServletResponse.SC_OK,exchange.getResponseStatus());
 
    	
    	/* show output for vebose debbuging */
    	System.out.println("\nOutput (encrypted):");
    	System.out.println(exchange.getResponseContent().toString()+"\n");
    	
    	String unenc = scAsym.decryptRSAWithPrivateKey(exchange.getResponseContent().toString());
    	
    	System.out.println("\nOutput (decrypted):");
    	System.out.println(unenc+"\n");
    	
    	/* check result entitiy */
    	Person result = deserialize(unenc,Person.class,PERSON_CONTENTTYPE);
    	assertEquals("New Name",result.getPrename());
	}
	
	@Test
	public void testUpdateCompany() throws IOException, InterruptedException, DatabaseException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		
		int id = 10;
		
		Company c = new Company();
		c.setFieldOfActivity("NEW FIELD");
		c.setName("NEW NAME");
		
    	exchange.setMethod("PUT");
    	exchange.setURL(HOST+COMPANY_SERVICE+"/"+id);
   
    	/* serialize and save as buffer */
    	b = new ByteArrayBuffer(scAsym.encryptRSAWithServerPublicKey(serialize(c,COMPANY_CONTENTTYPE,exchange)));
    	
    	exchange.setRequestContent(b);
    	client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	assertEquals(HttpExchange.STATUS_COMPLETED,exchangeState);
    	assertEquals(HttpServletResponse.SC_OK,exchange.getResponseStatus());
    	
    	/* show output for vebose debbuging */
    	System.out.println("\nOutput (encrypted):");
    	System.out.println(exchange.getResponseContent().toString()+"\n");
    	
    	String unenc = scAsym.decryptRSAWithPrivateKey(exchange.getResponseContent().toString());
    	
    	System.out.println("\nOutput (decrypted):");
    	System.out.println(unenc+"\n");
    	
    	/* check result entitiy */
    	Company result = deserialize(unenc,Company.class,COMPANY_CONTENTTYPE);
    	assertEquals("NEW FIELD",result.getFieldOfActivity());
    	assertEquals("NEW NAME",result.getName());
	}
}
