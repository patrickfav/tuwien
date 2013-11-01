package httptests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
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
	public void testCreateSkill() throws IOException, InterruptedException{
		Skill s = new Skill();
		s.setName("New Name for a Skill 1");
		
    	exchange.setMethod("POST");
    	exchange.setURL(HOST+SKILL_SERVICE);
    	
    	/* serialize and save as buffer */
    	b = new ByteArrayBuffer(serialize(s,SKILL_CONTENTTYPE,exchange));
    	
    	exchange.setRequestContent(b);
    	client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	/* check result state  */
    	assertEquals(HttpExchange.STATUS_COMPLETED,exchangeState);
    	assertEquals(HttpServletResponse.SC_OK,exchange.getResponseStatus());
    	
    	/* show output for vebose debbuging */
    	System.out.println("\nOutput:");
    	System.out.println(exchange.getResponseContent().toString()+"\n");
    	
    	/* check result entitiy */
    	Skill resultSkill = deserialize(exchange.getResponseContent().toString(),Skill.class,SKILL_CONTENTTYPE);
    	assertEquals("New Name for a Skill 1",resultSkill.getName());
	}
	
	@Test
	public void testCreatePerson() throws IOException, InterruptedException{
		
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
    	b = new ByteArrayBuffer(serialize(p,PERSON_CONTENTTYPE,exchange));
    	
    	exchange.setRequestContent(b);
    	client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	/* check result state  */
    	assertEquals(HttpExchange.STATUS_COMPLETED,exchangeState);
    	assertEquals(HttpServletResponse.SC_OK,exchange.getResponseStatus());
    	
    	/* show output for vebose debbuging */
    	System.out.println("\nOutput:");
    	System.out.println(exchange.getResponseContent().toString()+"\n");
    	
    	/* check result entitiy */
    	Person result = deserialize(exchange.getResponseContent().toString(),Person.class,PERSON_CONTENTTYPE);
    	assertEquals("A new Description",result.getDescrption());
    	assertEquals("Pername 1",result.getPrename());
    	assertEquals("Surname 1",result.getSurname());
	}
	
	@Test
	public void testCreateCompany() throws IOException, InterruptedException{
		
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
    	b = new ByteArrayBuffer(serialize(c,COMPANY_CONTENTTYPE,exchange));
    	
    	exchange.setRequestContent(b);
    	client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	/* check result state  */
    	assertEquals(HttpExchange.STATUS_COMPLETED,exchangeState);
    	assertEquals(HttpServletResponse.SC_OK,exchange.getResponseStatus());
    	
    	/* show output for vebose debbuging */
    	System.out.println("\nOutput:");
    	System.out.println(exchange.getResponseContent().toString()+"\n");
    	
    	/* check result entitiy */
    	Company result = deserialize(exchange.getResponseContent().toString(),Company.class,COMPANY_CONTENTTYPE);
    	assertEquals("field of activity 1",result.getFieldOfActivity());
    	assertEquals("New Company Name 1",result.getName());
	}
	
	@Test
	public void testCreateCompanyShouldFailValidation() throws IOException, InterruptedException{
		
		Set<Skill> skillSet = new HashSet<Skill>();
		skillSet.add(tdg.generateSkill());
		skillSet.add(tdg.generateSkill());
		
		Company c = new Company();
		c.setFieldOfActivity("field of activity 1");
		c.setName("New Company Name 1");
		
		c.setSkills(skillSet);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, 30);
		
		c.setFounded(cal.getTime());
		
    	exchange.setMethod("POST");
    	exchange.setURL(HOST+COMPANY_SERVICE);
    	
    	/* serialize and save as buffer */
    	b = new ByteArrayBuffer(serialize(c,COMPANY_CONTENTTYPE,exchange));
    	
    	exchange.setRequestContent(b);
    	client.send(exchange);
    	
    	assertEquals("STARTED",client.getState());
    	
    	int exchangeState = exchange.waitForDone();
    	
    	/* check result state  */
    	assertEquals(HttpExchange.STATUS_COMPLETED,exchangeState);
    	assertEquals(HttpServletResponse.SC_BAD_REQUEST,exchange.getResponseStatus());
    	
    	/* show output for vebose debbuging */
    	System.out.println("\nOutput:");
    	System.out.println(exchange.getResponseContent().toString()+"\n");
	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
  
}
