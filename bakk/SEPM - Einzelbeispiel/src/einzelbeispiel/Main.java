package einzelbeispiel;

import org.apache.log4j.*;

import gui.Gui;


public class Main {

	private static Logger logger = Logger.getRootLogger();
	private static ConsoleAppender ca = new ConsoleAppender();	
	
	public static void main(String[] args) {
		
		logger.addAppender(ca);
		logger.setLevel( Level.ALL );
		logger.info("Application started");
		
		new Gui();
		

	}

}
