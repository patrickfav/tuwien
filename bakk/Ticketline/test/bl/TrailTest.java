package bl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ticketline.bl.Trail;
import ticketline.gui.AuffuehrungenSuchenGui;
import ticketline.gui.KategorieWaehlenGui;
import ticketline.gui.KundeSuchenGui;
import ticketline.gui.SitzplatzWaehlenGui;

/**
 * 
 * @author PatrickM
 * @version 0.1
 *
 */
public class TrailTest {
	
	@Test
	public void trail_IsInitializedWithCorrectTrailNumber() {
		Trail trail = new Trail(2);
		assertEquals(2,trail.getTrailNumber());
	}
	
	@Test
	public void getPrevious_returnsCorrectPanel() {
		Trail trail = new Trail(1);
		AuffuehrungenSuchenGui asg = new AuffuehrungenSuchenGui();
		
		@SuppressWarnings("unused")
		KundeSuchenGui ksg = (KundeSuchenGui) trail.getPrevious(asg);
	}
	
	@Test
	public void getNext_returnsCorrectPanel() {
		Trail trail = new Trail(1);
		AuffuehrungenSuchenGui asg = new AuffuehrungenSuchenGui();
		
		@SuppressWarnings("unused")
		KategorieWaehlenGui kwg = (KategorieWaehlenGui) trail.getNext(asg);
	}
	
	@Test
	public void getFirstComponent_returnsCorrectPanel() {
		Trail trail = new Trail(1);
		
		@SuppressWarnings("unused")
		KundeSuchenGui ksg = (KundeSuchenGui) trail.getFirstComponent();
	}
	
	@Test
	public void addNode_shouldAddPanelCorrectly() {
		Trail trail = new Trail(1);
		KundeSuchenGui ksg = new KundeSuchenGui();
		
		trail.addNode(ksg);
		@SuppressWarnings("unused")
		KundeSuchenGui testKSG1 = (KundeSuchenGui) trail.getFirstTrail();
		@SuppressWarnings("unused")
		KundeSuchenGui testKSG2 = (KundeSuchenGui) trail.getNext(new SitzplatzWaehlenGui());
	}
}
