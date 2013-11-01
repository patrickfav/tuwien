package util.webservice;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.wsf.spi.annotation.WebContext;

import util.webservice.common.IWebserviceConstants;

/**
 * @author inso
 * @author inso
 */
@Stateless
@WebContext(contextRoot = "/SPICSWebservice")
@WebService(name = "AuthenticationService", serviceName = "AuthenticationService")
@Name("AuthenticationService")
public class AuthenticationServiceImpl extends AbstractService implements
		IAuthenticationService {

	@Logger
	Log log;

	@In
	private Credentials credentials;

	@WebMethod
	public boolean login(@WebParam(name = "username") String username,
			@WebParam(name = "password") String password)
			throws SpicsWebserviceException {
		// do not allow blank username
		if (username == null) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.ILLEGAL_ARGUMENTS_EXCEPTION,
					"Invalid login credentials, username may not be empty");
		}
		
		// prevent npe
		if (password == null) {
			password = "";
		}

		// already logged in
		if (Identity.instance().isLoggedIn()) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.ALREADY_LOGGED_IN_EXCEPTION,
					"Already logged in with user {0}", new String[] { Identity
							.instance().getCredentials().getUsername() });
		}

		credentials.setUsername(username);
		credentials.setPassword(password);

		log.info("Authentication with username #0", username);

		return Identity.instance().login() != null;
	}

	@WebMethod
	public void logout() throws SpicsWebserviceException {
		if (Identity.instance().isLoggedIn()) {
			Identity.instance().logout();
		} else {
			throw new SpicsWebserviceException(
					IWebserviceConstants.NOT_LOGGED_IN_EXCEPTION,
					"Logout request send but not logged in");
		}
	}
}
