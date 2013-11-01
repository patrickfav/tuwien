package bl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import ticketline.bl.GuiMemory;
import ticketline.bl.Login;
import ticketline.db.Kunde;

/**
 * 
 * @author PatrickM
 * @version 0.1
 *
 */
public class GuiMemoryTest {
	
	@After
	public void after() {
		GuiMemory.clear();
		GuiMemory.logout();
	}
	
	@Test
	public void clear_shouldSetEverythingToDefault() {
		Kunde k = new Kunde();
		k.setKartennr(55);
		
		assertEquals(null,GuiMemory.getAuffuehrung());
		assertEquals(null,GuiMemory.getKategorie());
		assertEquals(null,GuiMemory.getKunde());
		assertEquals(null,GuiMemory.getLogin());
		assertEquals(null,GuiMemory.getOrt());
		assertEquals(null,GuiMemory.getSaal());
		assertEquals(null,GuiMemory.getVeranstaltung());
		
		GuiMemory.attachKunde(k);
		int setKartenNr = GuiMemory.getKunde().getKartennr();
		assertEquals(55,setKartenNr);
		
		GuiMemory.clear();
		
		assertEquals(null,GuiMemory.getKunde());
	}
	
	@Test
	public void saveLogin_shouldSaveCorrectly() {
		Login l = new Login("pTPrjJhgZMs", "OEd Y");
		
		assertEquals(null,GuiMemory.getLogin());
		
		GuiMemory.saveLogin(l);
		assertEquals(l, GuiMemory.getLogin());
	}
	
	@Test
	public void logout_shouldWorkCorrectly() {
		Login l = new Login("pTPrjJhgZMs", "OEd Y");
		Kunde k = new Kunde();
		k.setKartennr(55);
		
		assertEquals(null,GuiMemory.getKunde());
		assertEquals(null,GuiMemory.getLogin());
		
		GuiMemory.saveLogin(l);
		GuiMemory.attachKunde(k);
		int setKartenNr = GuiMemory.getKunde().getKartennr();
		assertEquals(l, GuiMemory.getLogin());
		assertEquals(55,setKartenNr);
		
		
		GuiMemory.logout();
		assertEquals(null,GuiMemory.getLogin());
		assertEquals(null,GuiMemory.getKunde());
	}
}
