package ticketline.bl;

import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.MitarbeiterDAO;
import ticketline.db.Mitarbeiter;

/**
 * This class logs a user in and saves the login data
 * The login class is not persistent and MUST BE passed
 * over to every class that makes use of login data.
 * 
 * @author PatrickF
 * @version 0.2
 */
public class Login {
	
	private Mitarbeiter mitarbeiter = null;
	private Boolean isLoggedIn = new Boolean(false);
	
	/**
	 * CONSTRUCTOR
	 * Tries to login with the given parameters
	 * 
	 * @param username
	 * @param pass
	 */
	public Login(String username, String pass) {
		MitarbeiterDAO mdao = DAOFactory.getMitarbeiterDAO();
		
		mitarbeiter = mdao.login(username, pass);
		
		if(mitarbeiter != null && mitarbeiter.getPasswort().trim().equals(pass)) {
			//saves the login for the gui container
			isLoggedIn = true;
			GuiMemory.saveLogin(this);
		} else {
			mitarbeiter = null;
			isLoggedIn = false;
		}
	}
	
	/**
	 * Returns true if the instance notifies a valid login
	 * @return if logged in
	 */
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	
	/**
	 * Returns the whole (if logged in) user object (with all attributes).
	 * If not logged in, it returns null.
	 * 
	 * @return user object
	 */
	public Mitarbeiter getUserObject() {
		return mitarbeiter;
	}
}
