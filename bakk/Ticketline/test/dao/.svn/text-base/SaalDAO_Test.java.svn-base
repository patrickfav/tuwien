package dao;

import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ticketline.dao.DAOFactory;

import ticketline.dao.interfaces.AuffuehrungDAO;
import ticketline.dao.interfaces.KategorieDAO;
import ticketline.dao.interfaces.OrtDAO;
import ticketline.dao.interfaces.SaalDAO;

import ticketline.db.Auffuehrung;
import ticketline.db.Kategorie;
import ticketline.db.Ort;
import ticketline.db.OrtKey;
import ticketline.db.Saal;
import ticketline.db.SaalKey;
import ticketline.db.Ticketcard;
import ticketline.db.Transaktion;

/**
 * 
 * SaalDAO_Test 
 * @author AndiS
 * @version 0.1
 *
 */
public class SaalDAO_Test extends DAO_Test_Data
{
	private SaalDAO saalDAO = DAOFactory.getSaalDAO();
	private OrtDAO ortDAO = DAOFactory.getOrtDAO();
	private KategorieDAO kategorieDAO = DAOFactory.getKategorieDAO();
	private AuffuehrungDAO auffuehrungDAO = DAOFactory.getAuffuehrungDAO();
	
	@Before 
	public void before()
	{
		//save
		kategorieDAO.save(kategorie);
		auffuehrungDAO.save(auffuehrung);
		ortDAO.save(ort);
		saalDAO.save(saal);
	}
	
	@After 
	public void after()
	{
		//delete
		saalDAO.remove(saal);
		ortDAO.remove(ort);
		auffuehrungDAO.remove(auffuehrung);
		kategorieDAO.remove(kategorie);
	}
	
	@Test 
	public void get()
	{ 
		//search
		Saal result = saalDAO.get(saal.getComp_id());
		
		SaalKey saalKey = saal.getComp_id();
		SaalKey resultKey = result.getComp_id();
			
		//check
		assertSame(saalKey.getBezeichnung(),resultKey.getBezeichnung());
		assertSame(saalKey.getOrtbez(),resultKey.getOrtbez());
		assertSame(saalKey.getOrt(),resultKey.getOrt());
		assertSame(saal.getTyp(),result.getTyp());
		assertSame(saal.getAnzplaetze(),result.getAnzplaetze());
		assertSame(saal.getKostenprotag(),result.getKostenprotag());
		assertSame(saal.getOrt(),result.getOrt());
		assertSame(saal.getAuffuehrungen(),result.getAuffuehrungen());
		assertSame(saal.getKategorien(),result.getKategorien());

	}
		
	@Test 
	public void search()
	{ 
		Saal searchFor = new Saal();
		SaalKey sk = new SaalKey();
		searchFor.setComp_id(sk);
		searchFor.setKostenprotag(new BigDecimal(8675));
		
		//search
		List<Saal> resultList = saalDAO.search(searchFor);
		
		Saal result = resultList.get(0);
		
		SaalKey saalKey = saal.getComp_id();
		SaalKey resultKey = result.getComp_id();
			
		//check
		assertSame(saalKey.getBezeichnung(),resultKey.getBezeichnung());
		assertSame(saalKey.getOrtbez(),resultKey.getOrtbez());
		assertSame(saalKey.getOrt(),resultKey.getOrt());
		assertSame(saal.getTyp(),result.getTyp());
		assertSame(saal.getAnzplaetze(),result.getAnzplaetze());
		assertSame(saal.getKostenprotag(),result.getKostenprotag());
		assertSame(saal.getOrt(),result.getOrt());
		assertSame(saal.getAuffuehrungen(),result.getAuffuehrungen());
		assertSame(saal.getKategorien(),result.getKategorien());

	}
	@Test 
	public void edit() 
	{ 
		//edit
		saal.getComp_id().setBezeichnung("bezeichnungEDIT");
		
		//save
		saalDAO.save(saal);
		
		//search
		Saal result = saalDAO.get(saal.getComp_id());

		//check
		assertSame("bezeichnungEDIT",result.getComp_id().getBezeichnung());
		
	}

}
