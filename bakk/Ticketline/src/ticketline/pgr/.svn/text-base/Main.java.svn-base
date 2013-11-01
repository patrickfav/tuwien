package ticketline.pgr;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import ticketline.cfg.ConfigFactory;
import ticketline.gui.Gui;

public class Main {
	
	private static Logger logger = Logger.getRootLogger();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		    // Set cross-platform Java L&F (also called "Metal")
	        UIManager.setLookAndFeel(
	        		ConfigFactory.getConfig().getLookAndFeel());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
	    
		logger.info("Application started");
		new Gui();
	}

}
