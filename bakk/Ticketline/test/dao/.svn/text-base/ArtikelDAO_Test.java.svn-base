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
public class ArtikelDAO_Test extends DAO_Test_Data
{

	private ArtikelDAO artikelDAO = DAOFactory.getArtikelDAO();
	
	
	@Before 
	public void before()
	{

		//save
		artikelDAO.save(artikel);
	}
	
	@After 
	public void after()
	{
		//delete
		artikelDAO.remove(artikel);
	}
	
	@Test 
	public void get() 
	{ 
		//search
		Artikel result = artikelDAO.get(artikel.getArtikelnr());

		//check
		assertSame(artikel.getKurzbezeichnung(),result.getKurzbezeichnung());
		assertSame(artikel.getPreis(),result.getPreis());
		assertSame(artikel.getKategorie(),result.getKategorie());
		assertSame(artikel.getVeranstaltung(),result.getVeranstaltung());
		assertSame(artikel.getBestellungen(),result.getBestellungen());
	}
		
	@Test 
	public void edit() 
	{ 
		//edit
		artikel.setKurzbezeichnung("kurzbezeichnungEDIT");
		
		//save
		artikelDAO.save(artikel);
		
		//search
		Artikel result = artikelDAO.get(artikel.getArtikelnr());

		//check
		assertSame("kurzbezeichnungEDIT",result.getKurzbezeichnung());
		
	}
}
