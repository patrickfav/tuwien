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
import ticketline.dao.interfaces.EngagementDAO;
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
import ticketline.db.Ort;
import ticketline.db.OrtKey;
import ticketline.db.Saal;
import ticketline.db.Ticketcard;
import ticketline.db.Transaktion;
import ticketline.db.TransaktionKey;
import ticketline.db.Veranstaltung;
import ticketline.db.VeranstaltungKey;
/**
 * 
 * OrtDAO_Test 
 * @author AndiS
 * @version 0.1
 *
 */
public class OrtDAO_Test extends DAO_Test_Data
{

	private OrtDAO ortDAO = DAOFactory.getOrtDAO();
	
	@Before 
	public void before()
	{
		//save
		ortDAO.save(ort);
	}
	
	@After 
	public void after()
	{
		//delete
		ortDAO.remove(ort);
	}
	
	@Test 
	public void get()
	{ 
		//search
		Ort result = ortDAO.get(ort.getComp_id());
		OrtKey resultKey = result.getComp_id();
			
		//check
		assertSame(ort.getComp_id(),result.getComp_id());
		assertSame(ort.getKategorie(),result.getKategorie());
		assertSame(ort.getStrasse(),result.getStrasse());
		assertSame(ort.getPlz(),result.getPlz());
		assertSame(ort.getBundesland(),result.getBundesland());
		assertSame(ort.isKiosk(),result.isKiosk());
		assertSame(ort.isVerkaufsstelle(),result.isVerkaufsstelle());
		assertSame(ort.isAuffuehrungsort(),result.isAuffuehrungsort());
		assertSame(ort.getTicketcards(),result.getTicketcards());
		assertSame(ort.getTransaktionen(),result.getTransaktionen());
		assertSame(ort.getSaele(),result.getSaele());
	
	}
		
	@Test 
	public void search()
	{ 
		Ort crit = new Ort();
		crit.setComp_id(ort.getComp_id());
		
		crit.setBundesland(ort.getBundesland());
		
		//search
		List<Ort> resultList = ortDAO.search(crit);
		
		Ort result = resultList.get(0);
		
		OrtKey resultKey = result.getComp_id();
			
		//check
		assertSame(ort.getComp_id(),result.getComp_id());
		assertSame(ort.getKategorie(),result.getKategorie());
		assertSame(ort.getStrasse(),result.getStrasse());
		assertSame(ort.getPlz(),result.getPlz());
		assertSame(ort.getBundesland(),result.getBundesland());
		assertSame(ort.isKiosk(),result.isKiosk());
		assertSame(ort.isVerkaufsstelle(),result.isVerkaufsstelle());
		assertSame(ort.isAuffuehrungsort(),result.isAuffuehrungsort());
		assertSame(ort.getTicketcards(),result.getTicketcards());
		assertSame(ort.getTransaktionen(),result.getTransaktionen());
		assertSame(ort.getSaele(),result.getSaele());
	
	}
	@Test 
	public void edit() 
	{ 
		//edit
		ort.getComp_id().setBezeichnung("bezeichnungEDIT");
		
		//save
		ortDAO.save(ort);
		
		//search
		Ort result = ortDAO.get(ort.getComp_id());

		//check
		assertSame("bezeichnungEDIT",result.getComp_id().getBezeichnung());
		
	}

}
