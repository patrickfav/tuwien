package util.webservice;

import org.jboss.seam.security.Identity;

import util.webservice.common.IWebserviceConstants;

/**
 * Abstract service for basic user validation.
 * @author inso
 */
public class AbstractService {

	protected void preValidateUser() throws SpicsWebserviceException {
		if (!Identity.instance().isLoggedIn()) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.NOT_LOGGED_IN_EXCEPTION,
					"Not allowed to perform this action without login");
		}

		if (!Identity.instance().hasRole("admin")) {
			throw new SpicsWebserviceException(
					IWebserviceConstants.INSUFFICIENT_PRIVILEGES_EXCEPTION,
					"Insufficient privileges to perform this action");
		}
	}
}
