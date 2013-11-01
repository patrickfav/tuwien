package util.webservice;

import javax.ejb.Remote;

/**
 * @author inso
 * @author inso
 */
@Remote
public interface IAuthenticationService {

	/**
	 * Perform authentication with the given credentials.
	 * @param username
	 *            User name for the login credentials
	 * @param password
	 *            Password for the login credentials
	 * @return True if the login succeeds, false otherwise
	 * @throws SpicsWebserviceException
	 *             Exception with code
	 *             {@link IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION} if
	 *             the username is null. Exception with code
	 *             {@link IWebserviceConstants.ALREADY_LOGGED_IN_EXCEPTION} if
	 *             the user is already logged in.
	 */
	public boolean login(String username, String password)
			throws SpicsWebserviceException;

	/**
	 * Tries to invalidate an existing session
	 * @throws SpicsWebserviceException
	 *             Exception with Code
	 *             {@link IWebserviceConstants.NOT_LOGGED_IN_EXCEPTION} if the
	 *             user is not logged in.
	 */
	public void logout() throws SpicsWebserviceException;
}
