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
import ticketline.dao.interfaces.AuffuehrungDAO;
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
 * AuffuehrungDAO_Test
 * @author AndiS
 * @version 0.1
 *
 */
public class AuffuehrungDAO_Test extends DAO_Test_Data
{

	private AuffuehrungDAO auffuehrungDAO = DAOFactory.getAuffuehrungDAO();
	
	
	@Before 
	public void before()
	{

		//save
		auffuehrungDAO.save(auffuehrung);
	}
	
	@After 
	public void after()
	{
		//delete
		auffuehrungDAO.remove(auffuehrung);
	}
	
	@Test 
	public void get()
	{ 
		//search
		Auffuehrung result = auffuehrungDAO.get(auffuehrung.getComp_id());

		//check
		assertSame(auffuehrung.isStorniert(),result.isStorniert());
		assertSame(auffuehrung.getPreis(),result.getPreis());
		assertSame(auffuehrung.getVeranstaltung(),result.getVeranstaltung());
		assertSame(auffuehrung.getBelegungen(),result.getBelegungen());

	}
		
	@Test 
	public void edit() 
	{ 
		//edit
		auffuehrung.setPreis("preisEDIT");
		
		//save
		auffuehrungDAO.save(auffuehrung);
		
		//search
		Auffuehrung result = auffuehrungDAO.get(auffuehrung.getComp_id());

		//check
		assertSame("preisEDIT",result.getPreis());
		
	}
}
