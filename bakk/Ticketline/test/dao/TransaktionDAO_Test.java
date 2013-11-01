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
import ticketline.dao.interfaces.TransaktionDAO;
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
 * TransaktionDAO_Test 
 * @author AndiS
 * @version 0.1
 *
 */
public class TransaktionDAO_Test extends DAO_Test_Data
{
 
	private TransaktionDAO transaktionDAO = DAOFactory.getTransaktionDAO();
	
	@Before 
	public void before()
	{

		//save
		transaktionDAO.save(transaktion);
	}
	
	@After 
	public void after()
	{
		//delete
		transaktionDAO.remove(transaktion);
	}
	
	@Test 
	public void get()
	{ 
		//search
		Transaktion result = transaktionDAO.get(transaktion.getComp_id());

		//check
		assertSame(transaktion.getComp_id(),result.getComp_id());
		assertSame(transaktion.isVerkauft(),result.isVerkauft());
		assertSame(transaktion.isStorniert(),result.isStorniert());
		assertSame(transaktion.getPreis(),result.getPreis());
		assertSame(transaktion.getStartplatz(),result.getStartplatz());
		assertSame(transaktion.getAnzplaetze(),result.getAnzplaetze());
		assertSame(transaktion.getBelegung(),result.getBelegung());
		assertSame(transaktion.getOrt(),result.getOrt());

	}
		
	@Test 
	public void edit() 
	{ 
		//edit
		transaktion.setStartplatz(2);
		
		//save
		transaktionDAO.save(transaktion);
		
		//search
		Transaktion result = transaktionDAO.get(transaktion.getComp_id());

		//check
		assertSame(2,result.getStartplatz());
		
	}
}
