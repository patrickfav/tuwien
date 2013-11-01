package cfg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ticketline.cfg.Config;
import ticketline.cfg.ConfigFactory;

/**
 * 
 * @author PatrickM,PatrickF
 * @version 0.3
 *
 */
public class ConfigTest {
	Config config = null;
	
	@Before
	public void before() {
		config = ConfigFactory.getConfig();
	}
	
	@After
	public void after() {
		config = null;
	}
	
	@Test
	public void setLanguage_shouldSetCorrectly() {
		String lang_filename = "ticketline.lang.lang_de";
		Locale localEN = new Locale("en","UK");
		Locale localDE = new Locale("de","AT");
		
		// Old Value
		assertEquals("de", config.getLocale().getLanguage());
		assertEquals(localDE, config.getLocale());
		
		config.setLanguage(lang_filename, localEN);
		assertEquals("en", config.getLocale().getLanguage());
		assertEquals(localEN, config.getLocale());
	}
	
	@Test
	public void setDefaultPanelWidth_shouldSetCorrectly() {
		int width = 1200;
		
		assertEquals(0, config.getDefaultPanelWidth());
		
		config.setDefaultPanelWidth(width);
		assertEquals(width, config.getDefaultPanelWidth());
	}
	
	@Test
	public void setDefaultPanelHeight_shouldSetCorrectly() {
		int height = 1200;
		
		assertEquals(0, config.getDefaultPanelHeight());
		
		config.setDefaultPanelHeight(height);
		assertEquals(height, config.getDefaultPanelHeight());
	}
	
	@Test
	public void setSaleLocatoin_shouldSetCorrectly() {
		String saleLocation = "Testweg";
		
		assertEquals("TU Wien Verkaufsstand", config.getSaleLocation());
		
		config.setSaleLocation(saleLocation);
		assertEquals(saleLocation, config.getSaleLocation());
	}
	
	@Test
	public void getSubSelectButtonDimension_shouldReturnCorrectly() {
		Dimension defaultDim = new Dimension(120,22);
		
		assertEquals(defaultDim, config.getDefaultSubSelectButtonDimension());
	}
	
	@Test
	public void setDefaultPanelWidth_shouldReactCorrectWithNegativeWidth() {
		int width = -1000;
		boolean fails = true;
		
		try {
			config.setDefaultPanelWidth(width);
			assertEquals(fails, false);
		} catch (Exception e) {
			assertEquals(fails,true);
		}
	}
	
	@Test
	public void setDefaultPanelHeight_shouldReactCorrectWithNegativeHeight() {
		int height = -1000;
		boolean fails = true;
		
		try {
			config.setDefaultPanelHeight(height);
			assertEquals(fails, false);
		} catch (Exception e) {
			assertEquals(fails,true);
		}
	}
	
	@Test
	public void setDefaultPanelWidth_shouldReactCorrectToZeroWidth() {
		int width = 0;
		boolean fails = true;
		
		try {
			config.setDefaultPanelWidth(width);
			assertEquals(fails, false);
		} catch (Exception e) {
			assertEquals(fails,true);
		}
	}
	
	@Test
	public void setDefaultPanelHeight_shouldReactCorrectToZeroHeight() {
		int height = 0;
		boolean fails = true;
		
		try {
			config.setDefaultPanelHeight(height);
			assertEquals(fails, false);
		} catch (Exception e) {
			assertEquals(fails,true);
		}
	}
	
	@Test
	public void setPnl_mainAreaHeight_shouldSetCorrectly() {
		config.setPnl_mainAreaHeight(55);
		assertEquals(55,config.getPnl_mainAreaHeight());
	}
	
	@Test
	public void setPnl_mainAreaWidth_shouldSetCorrectly() {
		config.setPnl_mainAreaWidth(33);
		assertEquals(33,config.getPnl_mainAreaWidth());
	}
	
	@Test
	public void setNewsCount_shouldSetCorrectly() {
		config.setNewsCount(9);
		assertEquals(9,config.getNewsCount());
	}
	
	@Test
	public void setNewsUri_shouldSetCorrectly() {
		config.setNewsUri("http://www.testrssfeed.com/rss/thisisanrss.xml");
		assertEquals("http://www.testrssfeed.com/rss/thisisanrss.xml",config.getNewsUri());
	}
	
	@Test
	public void setNewsUri_shouldThrowException() {
		try {
			config.setNewsUri("");
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void setNewsCount_shouldThrowException() {
		try {
			config.setNewsCount(0);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}
}
