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
import ticketline.dao.interfaces.KuenstlerDAO;
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
import ticketline.db.Kuenstler;
import ticketline.db.Ort;
import ticketline.db.OrtKey;
import ticketline.db.Transaktion;
import ticketline.db.TransaktionKey;
import ticketline.db.Veranstaltung;
import ticketline.db.VeranstaltungKey;
/**
 * 
 * KuenstlerDAO_Test 
 * @author AndiS
 * @version 0.1
 *
 */
public class KuenstlerDAO_Test extends DAO_Test_Data
{

	private KuenstlerDAO kuenstlerDAO = DAOFactory.getKuenstlerDAO();
	
	
	@Before 
	public void before()
	{

		//save
		kuenstlerDAO.save(kuenstler);
	}
	
	@After 
	public void after()
	{
		//delete
		kuenstlerDAO.remove(kuenstler);
	}
	
	@Test 
	public void get() 
	{ 
		//search
		Kuenstler result = kuenstlerDAO.get(kuenstler.getKuenstlernr());

		//check
		assertSame(kuenstler.getNname(),result.getNname());
		assertSame(kuenstler.getVname(),result.getVname());
		assertSame(kuenstler.getGeschlecht(),result.getGeschlecht());
		assertSame(kuenstler.getEngagements(),result.getEngagements());
}
		
	@Test 
	public void edit() 
	{ 
		//edit
		kuenstler.setNname("nnameEDIT");
		
		//save
		kuenstlerDAO.save(kuenstler);
		
		//search
		Kuenstler result = kuenstlerDAO.get(kuenstler.getKuenstlernr());

		//check
		assertSame("nnameEDIT",result.getNname());
		
	}
}
