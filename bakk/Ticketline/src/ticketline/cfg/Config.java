package ticketline.cfg;

import java.awt.Dimension;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.zip.DataFormatException;

import javax.swing.UIManager;

import org.apache.log4j.Logger;

/**
 * The Config class - defines global values
 * 
 * @author PatrickF
 * @version 0.2
 */
public class Config {
	
	private static Logger logger = Logger.getLogger(Config.class);
	
	/*
	 * CONFIG DATA - CHANGE HERE:
	 */
	
	private static String languageFile = "ticketline.lang.lang_de";
	private static Locale currentLocale = new Locale("de", "AT");
	
	private static String saleLocation = "TU Wien Verkaufsstand";
	
	private static final String version = "Ticketline  v0.99 beta build 56 - 2009/06/02";
	
	//GUI
	private static int defaultPanelWidth;	
	private static int defaultPanelHeight;
	private static final Dimension mainFrameMinDimension = new Dimension(850,750);	
	private static final Dimension mainFramePrefDimension = new Dimension(1000,750);
	private static final Dimension defaultSubSelectButtonDimension = new Dimension(120,22);
	private static final Dimension deSelectButtonDimension = new Dimension(22,22);
	private static int pnl_mainAreaWidth;
	private static int pnl_mainAreaHeight;
	
	private static String lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
	
	//NEWS
	private static String newsUri = "http://www.oeticket.com/portal/de/rss/latest.xml";
	private static int newsCount = 8;
	
	//Localisation
	private static String currency = "Euro";
	private static String currencyAccr = "eur";
	private static String currencySym = "€";
	
	/*
	 *  Program intern config
	 */
	

	private static final Integer dummyUser = new Integer(1);
	
	/*
	 * CONFIG CLASS INTERN DATA
	 */
	
	private Boolean hasChanged=false;
	private ResourceBundle rB = null;
	
	/**
	 * Empty Constructor
	 */
	public Config() {
	}
	
	/**
	 * Retrieves the Language ResourceBundle defined in class members
	 * @pre Language Ressourcebundle is available and language_file var is set
	 * @post Lang RB has been found
	 * @return Language RessourceBundle
	 */
	public ResourceBundle getLanguageBundle() {
		if(rB == null || hasChanged)
			try {
				rB = ResourceBundle.getBundle(languageFile,currentLocale);
				logger.debug("Language ResourceBundle loading: "+languageFile+" with Locale "+currentLocale.toString());
			} catch (Exception e) {
				logger.error("Error while gathering RessourceBundle "+languageFile+" - Msg:"+e.getMessage());
				System.exit(0);
			}
		
		return rB;
	}
	
	/**
	 * Will change the language and locale attributes.
	 * 
	 * @post new parameters are set
	 * @param lang_filename
	 * @param local
	 */
	public void setLanguage(String lang_filename, Locale local){
		languageFile = lang_filename;
		currentLocale = local;
		hasChanged=true;
	}
	
	/**
	 * Simple Config Getter
	 * @return the default panel width
	 */
	public int getDefaultPanelWidth() {
		return defaultPanelWidth;
	}
	
	/**
	 * Simple Config Getter
	 * @return the default panel height
	 */
	public int getDefaultPanelHeight() {
		return defaultPanelHeight;
	}
	/**
	 * Simple Config Setter
	 * sets the default panel width
	 */
	public void setDefaultPanelWidth(int w){
		if(w <= 0)
			throw new NumberFormatException();
		defaultPanelWidth = w;
	}
	/**
	 * Simple Config Setter
	 * sets the default panel height
	 */
	public void setDefaultPanelHeight(int w){
		if(w <= 0)
			throw new NumberFormatException();
		defaultPanelHeight = w;
	}
	/**
	 * Simple Config Getter
	 * @return the current local
	 */
	public Locale getLocale() {
		return currentLocale;
	}
	
	/**
	 * Simple Config Setter
	 * @return the current Sale Location
	 */
	public void setSaleLocation(String sl) {
		Config.saleLocation = sl;
	}
	
	/**
	 * Simple Config Getter
	 * @return the current Sale Location
	 */
	public String getSaleLocation() {
		return saleLocation;
	}
	
	/**
	 * Simple Config Getter
	 * @return the current defaultSubSelectButtonDimension
	 */
	public Dimension getDefaultSubSelectButtonDimension() {
		return defaultSubSelectButtonDimension;
	}
	
	/**
	 * Simple Config Getter
	 * @return the current deSelectButtonDimension
	 */
	public Dimension getDeSelectButtonDimension() {
		return deSelectButtonDimension;
	}
	
	/**
	 * Simple Config Getter
	 * @return the current version
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * Simple Config Getter
	 * @return the current lookAndFeel
	 */
	public String getLookAndFeel() {
		return lookAndFeel;
	}
	
	/**
	 * Simple Config Getter
	 * @return the current dummyUser
	 */
	public Integer getDummyUser() {
		return dummyUser;
	}
	
	/**
	 * Simple Config Getter
	 * @return the current getMainFrameMinDimension
	 */
	public Dimension getMainFrameMinDimension() {
		return mainFrameMinDimension;
	}
	
	/**
	 * Simple Config Getter
	 * @return the current getMainFramePrefDimension
	 */
	public Dimension getMainFramePrefDimension() {
		return mainFramePrefDimension;
	}
	/**
	 * Simple Config Getter
	 * @return 
	 */
	public int getPnl_mainAreaWidth() {
		return pnl_mainAreaWidth;
	}
	/**
	 * Simple Config Getter
	 * @return 
	 */
	public void setPnl_mainAreaWidth(int pnl_mainAreaWidth) {
		Config.pnl_mainAreaWidth = pnl_mainAreaWidth;
	}
	/**
	 * Simple Config Getter
	 * @return 
	 */
	public int getPnl_mainAreaHeight() {
		return pnl_mainAreaHeight;
	}
	/**
	 * Simple Config Getter
	 * @return
	 */
	public void setPnl_mainAreaHeight(int pnl_mainAreaHeight) {
		Config.pnl_mainAreaHeight = pnl_mainAreaHeight;
	}
	/**
	 * Simple Config Getter
	 * @return URI/URL of the rss feed for news
	 */
	public String getNewsUri() {
		return newsUri;
	}
	/**
	 * Simple Config Setter
	 * @parm URI/URL of the rss feed for news
	 */
	public void setNewsUri(String news_uri) {
		if(news_uri == null || news_uri.length() <= 0)
			throw new NumberFormatException();
		
		Config.newsUri = news_uri;
	}
	/**
	 * Simple Config Getter
	 * @return count of news to show
	 */
	public int getNewsCount() {
		return newsCount;
	}
	/**
	 * Simple Config Setter
	 * @param count of news to show
	 */
	public void setNewsCount(int news_count) {
		if(news_count <= 0)
			throw new NumberFormatException();
		Config.newsCount = news_count;
	}
	/**
	 * Simple Config Getter
	 * @return currency string
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * Simple Config Getter
	 * @return currency accronym string
	 */
	public String getCurrencyAccr() {
		return currencyAccr;
	}
	/**
	 * Simple Config Getter
	 * @return currency symbol string
	 */
	public String getCurrencySym() {
		return currencySym;
	}
}
