package dao;

import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.EngagementDAO;
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
 * EngagementDAO_Test 
 * @author AndiS
 * @version 0.1
 *
 */
public class EngagementDAO_Test extends DAO_Test_Data
{
	private EngagementDAO engagementDAO = DAOFactory.getEngagementDAO();

	@Before 
	public void before()
	{
		//save
		engagementDAO.save(engagement);
	}
	
	@After 
	public void after()
	{
		//delete
		engagementDAO.remove(engagement);
	}
	
	@Test 
	public void get()
	{ 
		//search
		Engagement result = engagementDAO.get(engagement.getComp_id());
		EngagementKey resultKey = result.getComp_id();
			
		//check
		assertSame(engagement.getComp_id().getKuenstlernr(),resultKey.getKuenstlernr());
		assertSame(engagement.getComp_id().getBezeichnung(),resultKey.getBezeichnung());
		assertSame(engagement.getComp_id().getKategorie(),resultKey.getKategorie());
		assertSame(engagement.getComp_id(),result.getComp_id());
	
	}
		
	@Test 
	public void edit() 
	{ 
		//edit
		engagement.getComp_id().setBezeichnung("bezeichnungEDIT");
		
		//save
		engagementDAO.save(engagement);
		
		//search
		Engagement result = engagementDAO.get(engagement.getComp_id());

		//check
		assertSame("bezeichnungEDIT",result.getComp_id().getBezeichnung());
		
	}

}
