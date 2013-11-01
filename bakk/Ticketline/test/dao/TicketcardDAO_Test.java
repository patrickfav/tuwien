package dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.ArtikelDAO;
import ticketline.dao.interfaces.OrtDAO;
import ticketline.dao.interfaces.TicketcardDAO;
import ticketline.dao.interfaces.VeranstaltungDAO;
import ticketline.db.Artikel;
import ticketline.db.Auffuehrung;
import ticketline.db.AuffuehrungKey;
import ticketline.db.Belegung;
import ticketline.db.BelegungKey;
import ticketline.db.Bestellung;
import ticketline.db.BestellungKey;
import ticketline.db.Engagement;
import ticketline.db.EngagementKey;
import ticketline.db.Ort;
import ticketline.db.OrtKey;
import ticketline.db.Reihe;
import ticketline.db.Ticketcard;
import ticketline.db.Transaktion;
import ticketline.db.TransaktionKey;
import ticketline.db.Veranstaltung;
import ticketline.db.VeranstaltungKey;
/**
 * 
 * TicketcardDAO_Test 
 * @author AndiS
 * @version 0.1
 *
 */
public class TicketcardDAO_Test extends DAO_Test_Data
{

	private TicketcardDAO tickedcardDAO = DAOFactory.getTicketcardDAO();
	private OrtDAO ortDAO = DAOFactory.getOrtDAO();
	
	
	@Before 
	public void before()
	{
		//save
		ortDAO.save(ort);
		tickedcardDAO.save(ticketcard);
	}
	
	@After 
	public void after()
	{
		//delete
		tickedcardDAO.remove(ticketcard);
		ortDAO.remove(ort);
	}
	
	@Test 
	public void get()
	{ 
		//search
		Ticketcard result = tickedcardDAO.get(ticketcard.getKartennr());

		//check
		assertSame(ticketcard.getKartennr(),result.getKartennr());
		assertSame(ticketcard.getTyp(),result.getTyp());
		assertSame(ticketcard.getOrtverk(),result.getOrtverk());
		assertSame(ticketcard.getNname(),result.getNname());
		assertSame(ticketcard.getVname(),result.getVname());
		assertSame(ticketcard.getTitel(),result.getTitel());
		assertSame(ticketcard.getGeschlecht(),result.getGeschlecht());
		assertSame(ticketcard.getGeburtsdatum(),result.getGeburtsdatum());
		assertSame(ticketcard.getStrasse(),result.getStrasse());
		assertSame(ticketcard.getPlz(),result.getPlz());
		assertSame(ticketcard.getOrt(),result.getOrt());
		assertSame(ticketcard.getOrtverk(),result.getOrtverk());
		assertSame(ticketcard.getTelnr(),result.getTelnr());
		assertSame(ticketcard.getEmail(),result.getEmail());

	}
		
	@Test 
	public void edit() 
	{ 
		//edit
		ticketcard.setEmail("emailEDIT");
		
		//save
		tickedcardDAO.save(ticketcard);
		
		//search
		Ticketcard result = tickedcardDAO.get(ticketcard.getKartennr());

		//check
		assertSame("emailEDIT",result.getEmail());
		
		

		
	}
}
