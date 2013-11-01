package dao;

import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ticketline.dao.DAOFactory;
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
import ticketline.db.Ticketcard;
import ticketline.db.Transaktion;
import ticketline.db.TransaktionKey;
import ticketline.db.Veranstaltung;
import ticketline.db.VeranstaltungKey;
/**
 * 
 * VeranstaltungDAO_Test 
 * @author AndiS
 * @version 0.1
 *
 */
public class VeranstaltungDAO_Test extends DAO_Test_Data
{
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	private VeranstaltungDAO veranstaltungDAO = DAOFactory.getVeranstaltungDAO();
	
	@Before 
	public void before()
	{
		

		//save
		veranstaltungDAO.save(veranstaltung);
	}
	
	@After 
	public void after()
	{
		//delete
		veranstaltungDAO.remove(veranstaltung);
	}
	
	@Test 
	public void get()
	{ 
		//search
		Veranstaltung result = veranstaltungDAO.get(veranstaltung.getComp_id());

		//check
		assertSame(veranstaltung.getDauer(),result.getDauer());
		assertSame(veranstaltung.getInhalt(),result.getInhalt());
		assertSame(veranstaltung.getArtikel(),result.getArtikel());
		assertSame(veranstaltung.getAuffuehrungen(),result.getAuffuehrungen());
		assertSame(veranstaltung.getEngagements(),result.getEngagements());
	}
		
	@Test 
	public void search()
	{ 
		Veranstaltung crit = new Veranstaltung();
		crit.setComp_id(new VeranstaltungKey());
		
		crit.setInhalt(veranstaltung.getInhalt());
		
		//search
		List<Veranstaltung> resultList = veranstaltungDAO.search(crit);

		Veranstaltung result = resultList.get(0);

		//check
		assertSame(veranstaltung.getDauer(),result.getDauer());
		assertSame(veranstaltung.getInhalt(),result.getInhalt());
		assertSame(veranstaltung.getArtikel(),result.getArtikel());
		assertSame(veranstaltung.getAuffuehrungen(),result.getAuffuehrungen());
		assertSame(veranstaltung.getEngagements(),result.getEngagements());
	}

	@Test 
	public void edit() 
	{ 
		//edit
		veranstaltung.setInhalt("inhaltEDIT");
		
		//save
		veranstaltungDAO.save(veranstaltung);
		
		//search
		Veranstaltung result = veranstaltungDAO.get(veranstaltung.getComp_id());

		//check
		assertSame("inhaltEDIT",veranstaltung.getInhalt());
		
	}

}
