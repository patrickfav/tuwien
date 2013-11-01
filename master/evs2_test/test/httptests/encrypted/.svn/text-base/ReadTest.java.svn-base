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
	public void testReadPerson() throws IOException, InterruptedException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		int id = 1;
		
    	exchange.setMethod("GET");
    	exchange.setURL(HOST+PERSON_SERVICE+"/"+id);
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
	}	
    
	@Test
	public void testReadCompany() throws IOException, InterruptedException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		int id = 1;
		
    	exchange.setMethod("GET");
    	exchange.setURL(HOST+COMPANY_SERVICE+"/"+id);
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
	}		
    	
  
}
