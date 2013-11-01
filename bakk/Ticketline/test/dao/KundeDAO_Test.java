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
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.ArtikelDAO;
import ticketline.dao.interfaces.KundeDAO;
import ticketline.dao.interfaces.OrtDAO;
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
import ticketline.db.Kunde;
import ticketline.db.Ort;
import ticketline.db.OrtKey;
import ticketline.db.Transaktion;
import ticketline.db.TransaktionKey;
import ticketline.db.Veranstaltung;
import ticketline.db.VeranstaltungKey;
/**
 * 
 * KundeDAO_Test
 * @author AndiS
 * @version 0.1
 *
 */
public class KundeDAO_Test extends DAO_Test_Data
{

	private KundeDAO kundeDAO = DAOFactory.getKundeDAO();
	private OrtDAO ortDAO = DAOFactory.getOrtDAO();
	
	@Before 
	public void before()
	{

		//save
		//ortDAO.save(ort);
		//kundeDAO.save(kunde);
	}
	
	@After 
	public void after()
	{
		//delete
		//kundeDAO.remove(kunde);
		//ortDAO.remove(ort);
	}
	
	@Test 
	public void get()
	{ 	
		/*
		//search
		Kunde result = kundeDAO.get(kunde.getKartennr());

		//check
		assertSame(kunde.getTyp(),result.getTyp());
		assertSame(kunde.getOrtverk(),result.getOrtverk());
		assertSame(kunde.isErmaechtigung(),result.isErmaechtigung());
		assertSame(kunde.getErmaessigung(),result.getErmaessigung());
		assertSame(kunde.getTkgueltigbis(),result.getTkgueltigbis());
		assertSame(kunde.isGesperrt(),result.isGesperrt());
		assertSame(kunde.getGruppe(),result.getGruppe());
		assertSame(kunde.getTransaktionen(),result.getTransaktionen());
		assertSame(kunde.getBestellungen(),result.getBestellungen());
		*/
		//TODO
		assertTrue(true);
	}
	
	@Test 
	public void search()
	{ 
		/*
		Kunde kundeCrit = new Kunde();
		kundeCrit.setNname(kunde.getNname());
		
		//search
		List<Kunde> resultList = kundeDAO.search(kundeCrit);

		Kunde result = resultList.get(0);
		
		//check
		assertSame(kunde.getTyp(),result.getTyp());
		assertSame(kunde.getOrtverk(),result.getOrtverk());
		assertSame(kunde.isErmaechtigung(),result.isErmaechtigung());
		assertSame(kunde.getErmaessigung(),result.getErmaessigung());
		assertSame(kunde.getTkgueltigbis(),result.getTkgueltigbis());
		assertSame(kunde.isGesperrt(),result.isGesperrt());
		assertSame(kunde.getGruppe(),result.getGruppe());
		assertSame(kunde.getTransaktionen(),result.getTransaktionen());
		assertSame(kunde.getBestellungen(),result.getBestellungen());
		*/
		//TODO
		assertTrue(true);
	}
		
	@Test 
	public void edit() 
	{ 
		/*
		//edit
		kunde.setGruppe("gruppeEDIT");
		
		//save
		kundeDAO.update(kunde);
		
		//search
		Kunde result = kundeDAO.get(kunde.getKartennr());

		//check
		assertSame("gruppeEDIT",result.getGruppe());
		*/
		//TODO
		assertTrue(true);
	}
}
