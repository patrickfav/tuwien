package util.webservice.common;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

/**
 * Util class to enable session support to a set of webservice references.
 * Should be called from a client which would like to have one session for
 * several webservice references.
 * @author inso
 */
public class WebserviceUtils {

	/**
	 * Enables the binding providers to use one shared message handler instance
	 * which stores the session info.
	 * @param bindingProviders
	 *            The binding providers which should share one session.
	 */
	@SuppressWarnings("unchecked")
	public static void enableSessionSupport(BindingProvider... bindingProviders) {
		// create global handler
		MessageHandler handler = new MessageHandler();
		List<Handler> handlers = new ArrayList<Handler>();
		handlers.add(handler);

		// enable session support
		for (BindingProvider p : bindingProviders) {
			p.getRequestContext().put(
					BindingProvider.SESSION_MAINTAIN_PROPERTY, true);
			p.getBinding().setHandlerChain(handlers);
		}
	}
}
