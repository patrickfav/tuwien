package httptests.encrypted;

import static org.junit.Assert.assertEquals;

import httptests.AbstractRestTest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletResponse;

public class CreateTest extends AbstractRestTest{
	
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
	public void testCreatePerson() throws IOException, InterruptedException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		
		Set<Skill> skillSet = new HashSet<Skill>();
		skillSet.add(tdg.generateSkill());
		skillSet.add(tdg.generateSkill());
		
		Person p = new Person();
		p.setDescrption("A new Description");
		p.setPrename("Pername 1");
		p.setSurname("Surname 1");
		p.setSkills(skillSet);
		
    	exchange.setMethod("POST");
    	exchange.setURL(HOST+PERSON_SERVICE);
    	
    	/* serialize and save as buffer */
		b = new ByteArrayBuffer(scAsym.encryptRSAWithServerPublicKey(serialize(p,PERSON_CONTENTTYPE,exchange)));

    	
    	exchange.setRequestContent(b);
    	client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	/* check result state  */
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
    	assertEquals("A new Description",result.getDescrption());
    	assertEquals("Pername 1",result.getPrename());
    	assertEquals("Surname 1",result.getSurname());
	}
    	
    
	@Test
	public void testCreateCompany() throws IOException, InterruptedException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		
		Set<Skill> skillSet = new HashSet<Skill>();
		skillSet.add(tdg.generateSkill());
		skillSet.add(tdg.generateSkill());
		
		Company c = new Company();
		c.setFieldOfActivity("field of activity 1");
		c.setName("New Company Name 1");
		c.setFounded(new Date());
		c.setSkills(skillSet);
		
    	exchange.setMethod("POST");
    	exchange.setURL(HOST+COMPANY_SERVICE);
    	
    	/* serialize and save as buffer */
		b = new ByteArrayBuffer(scAsym.encryptRSAWithServerPublicKey(serialize(c,COMPANY_CONTENTTYPE,exchange)));

    	
    	exchange.setRequestContent(b);
    	client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	/* check result state  */
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
    	assertEquals("field of activity 1",result.getFieldOfActivity());
    	assertEquals("New Company Name 1",result.getName());
	}
	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
  
}
