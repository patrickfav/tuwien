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
import ticketline.dao.interfaces.ReiheDAO;
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
import ticketline.db.Transaktion;
import ticketline.db.TransaktionKey;
import ticketline.db.Veranstaltung;
import ticketline.db.VeranstaltungKey;
/**
 * 
 * ReiheDAO_Test
 * @author AndiS
 * @version 0.1
 *
 */
public class ReiheDAO_Test extends DAO_Test_Data
{

	private ReiheDAO reiheDAO = DAOFactory.getReiheDAO();
	
	
	@Before 
	public void before()
	{

		//save
		reiheDAO.save(reihe);
	}
	
	@After 
	public void after()
	{
		//delete
		reiheDAO.remove(reihe);
	}
	
	@Test 
	public void get() 
	{ 
		//search
		Reihe result = reiheDAO.get(reihe.getComp_id());

		//check
		assertSame(reihe.getStartplatz(),result.getStartplatz());
		assertSame(reihe.getAnzplaetze(),result.getAnzplaetze());
		assertSame(reihe.isSitzplatz(),result.isSitzplatz());
		assertSame(reihe.getBelegungen(),result.getBelegungen());

	}
		
	@Test 
	public void edit() 
	{ 
		//edit
		int startplatz = 99;
		reihe.setStartplatz(startplatz);
		
		//save
		reiheDAO.save(reihe);
		
		//search
		Reihe result = reiheDAO.get(reihe.getComp_id());

		//check
		assertSame(startplatz,result.getStartplatz());
		
	}
}
