package dao;

import static org.junit.Assert.assertSame;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.BestellungDAO;
import ticketline.db.Bestellung;
import ticketline.db.BestellungKey;
/**
 * 
 * BestellungDAO_Test 
 * @author AndiS
 * @version 0.1
 *
 */
public class BestellungDAO_Test extends DAO_Test_Data
{

	private BestellungDAO bestellungDAO = DAOFactory.getBestellungDAO();
	
	@Before 
	public void before()
	{
		
		//save
		bestellungDAO.save(bestellung);
	}
	
	@After 
	public void after()
	{
		//delete
		bestellungDAO.remove(bestellung);
	}
	
	@Test 
	public void get() 
	{ 
		//search
		Bestellung result = bestellungDAO.get(bestellung.getComp_id());
		
		//check
		assertSame(bestellung.getComp_id(),result.getComp_id());
		assertSame(bestellung.getMenge(),result.getMenge());
		assertSame(bestellung.getZahlart(),result.getZahlart());
	}
		
	@Test 
	public void edit() 
	{ 
		//edit
		bestellung.setZahlart("Kredit");
		
		//save
		bestellungDAO.save(bestellung);
		
		//search
		Bestellung result = bestellungDAO.get(bestellung.getComp_id());
		
		//check
		assertSame(bestellung.getComp_id(),result.getComp_id());
		assertSame(bestellung.getMenge(),result.getMenge());
		assertSame("Kredit",result.getZahlart());
	}

}
