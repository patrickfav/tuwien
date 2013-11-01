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
import ticketline.dao.interfaces.BelegungDAO;
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
import ticketline.db.Transaktion;
import ticketline.db.TransaktionKey;
import ticketline.db.Veranstaltung;
import ticketline.db.VeranstaltungKey;
/**
 * 
 * ArtikelDAO_Test 
 * @author AndiS
 * @version 0.1
 *
 */
public class BelegungDAO_Test extends DAO_Test_Data
{

	private BelegungDAO belegungDAO = DAOFactory.getBelegungDAO();
	
	
	@Before 
	public void before()
	{

		//save
		belegungDAO.save(belegung);
	}
	
	@After 
	public void after()
	{
		//delete
		belegungDAO.remove(belegung);
	}
	
	@Test 
	public void get()
	{ 
		//search
		Belegung result = belegungDAO.get(belegung.getComp_id());

		//check
		assertSame(belegung.getAnzfrei(),result.getAnzfrei());
		assertSame(belegung.getAnzres(),result.getAnzres());
		assertSame(belegung.getAnzverk(),result.getAnzverk());
		assertSame(belegung.getTransaktionen(),result.getTransaktionen());

		belegung.setAnzfrei(10);
		belegung.setAnzres(12);
		belegung.setAnzverk(20);
		belegung.setTransaktionen(transaktionen);
	}
		
	@Test 
	public void edit() 
	{ 
		//edit
		belegung.setAnzfrei(11);
		//save
		belegungDAO.save(belegung);
		
		//search
		Belegung result = belegungDAO.get(belegung.getComp_id());

		//check
		assertSame(11,result.getAnzfrei());
	}
}
