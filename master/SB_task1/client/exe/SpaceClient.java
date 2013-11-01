package exe;

import org.apache.log4j.Logger;

import gui.LoginDialog;


public class SpaceClient {

	public static final String SPACE_URI = "jini://*/sboodle_container1/sboodle";
	private static Logger log = Logger.getLogger(SpaceClient.class);
	
	public static void main(String[] args) {
		new LoginDialog(SPACE_URI);
	}
	
	public static void exitClient(int code) {
		log.info("Exit Client ("+code+")");
		System.exit(code);
	}
}
