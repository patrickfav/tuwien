package findbugs;

import org.apache.log4j.Logger;
import at.ac.tuwien.ifs.bpse.basis.gui.MainFrame;


/**
 * Class containing just the main method, nothing more.
 * 
 * @author The SE-Team
 * @version 1.0
 */
public class Basis {

	/**
	 * Retrieves the logger for this root package
	 */
	private static Logger log = Logger.getLogger("Basis");

	/**
	 * Entry point for the program. Configures the the Log4j-Logger, 
	 * starts the MainFrame and then terminates.
	 * 
	 * @param args
	 *            the command-line arguments
	 * @see MainFrame
	 * @see Logger#configureLog4j()
	 */
	public static void main(String[] args) {
		// configure log4j based on log.properties
		log.info("Starting Application");
       
		MainFrame mf = new MainFrame();
		mf.setLocation(50, 50);
		mf.pack();
		mf.setVisible(true);
		log.info("Terminating Start Class");
	}

}
