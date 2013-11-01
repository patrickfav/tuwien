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
import ticketline.dao.interfaces.KategorieDAO;
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
import ticketline.db.Kategorie;
import ticketline.db.Ort;
import ticketline.db.OrtKey;
import ticketline.db.Transaktion;
import ticketline.db.TransaktionKey;
import ticketline.db.Veranstaltung;
import ticketline.db.VeranstaltungKey;
/**
 * 
 * KategorieDAO_Test 
 * @author AndiS
 * @version 0.1
 *
 */
public class KategorieDAO_Test extends DAO_Test_Data
{

	private KategorieDAO kategorieDAO = DAOFactory.getKategorieDAO();
	
	
	@Before 
	public void before()
	{

		//save
		kategorieDAO.save(kategorie);
	}
	
	@After 
	public void after()
	{
		//delete
		kategorieDAO.remove(kategorie);
	}
	
	@Test 
	public void get() 
	{ 
		//search
		Kategorie result = kategorieDAO.get(kategorie.getComp_id());

		//check
		assertSame(kategorie.getPreismin(),result.getPreismin());
		assertSame(kategorie.getPreisstd(),result.getPreisstd());
		assertSame(kategorie.getPreismax(),result.getPreismax());
		assertSame(kategorie.getReihen(),result.getReihen());
	}
		
	@Test 
	public void edit() 
	{ 
		//edit
		BigDecimal dec = new BigDecimal(11);
		kategorie.setPreismin(dec);
		
		//save
		kategorieDAO.save(kategorie);
		
		//search
		Kategorie result = kategorieDAO.get(kategorie.getComp_id());

		//check
		assertSame(dec,result.getPreismin());
		
	}
}
