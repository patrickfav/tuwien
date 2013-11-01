package util.webservice.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * Message handler which saves the session cookie from a HTTP response and adds
 * the session info to the outgoing HTTP header. This message handler should be
 * added in the handler chain of the binding provider of the service. Webservice
 * clients which use JAXB should use this handler to extend a session over
 * several services.
 * @author inso
 */
public class MessageHandler implements Handler<SOAPMessageContext> {

	/**
	 * The QName of the conversation ID element in the SOAP request header.
	 * Copied from {@link org.jboss.seam.webservice.SOAPRequestHandler}.
	 */
	private static final QName CIDQN = new QName(
			"http://www.jboss.org/seam/webservice", "conversationId", "seam");

	private static final String SESSION_COOKIES = "org.jboss.ws.maintain.session.cookies";

	// session coockie
	private String cookieName = null;
	private String cookieValue = null;

	// conversation id
	private String conversationId = null;

	public boolean handleMessage(SOAPMessageContext smc) {
		Boolean outbound = (Boolean) smc
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outbound == null)
			throw new IllegalStateException("Cannot obtain required property: "
					+ MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		return outbound ? handleOutbound(smc) : handleInbound(smc);
	}

	private boolean handleOutbound(SOAPMessageContext smc) {
		try {
			// set session cookie
			smc.getMessage().getMimeHeaders().addHeader("Cookie",
					cookieName + "=" + cookieValue);

			// set conversation id
			SOAPHeader header = smc.getMessage().getSOAPHeader();
			if (conversationId != null && header != null) {
				SOAPElement element = header.addChildElement(CIDQN);
				element.addTextNode(conversationId);
				smc.getMessage().saveChanges();
			}

			return true;
		} catch (SOAPException e) {
			System.out.println("Exception processing outbound message: "
					+ e.getMessage());
			return false;
		}
	}

	private boolean handleInbound(SOAPMessageContext smc) {
		try {
			// extract session cookie
			Map<String, String> cookies = extractSessionCookies(smc);
			if (cookies != null && cookies.size() > 0) {
				Entry<String, String> cookie = cookies.entrySet().iterator()
						.next();
				cookieName = cookie.getKey();
				cookieValue = cookie.getValue();
			}

			// extract conversation id
			conversationId = extractConversationId(smc);

			return true;
		} catch (SOAPException e) {
			System.out.println("Error handling inbound SOAP request: "
					+ e.getMessage());
			return false;
		}
	}

	/**
	 * Extracts the conversation ID from an incoming SOAP message. Copied from
	 * {@link org.jboss.seam.webservice.SOAPRequestHandler}.
	 * @param smc
	 *            The SOAP message context
	 * @return The conversation ID, or null if there is no conversation ID set
	 * @throws SOAPException
	 */
	@SuppressWarnings("unchecked")
	private String extractConversationId(SOAPMessageContext smc)
			throws SOAPException {
		SOAPHeader header = smc.getMessage().getSOAPHeader();
		if (header != null) {
			Iterator iter = header.getChildElements(CIDQN);
			if (iter.hasNext()) {
				SOAPElement element = (SOAPElement) iter.next();
				return element.getFirstChild().getNodeValue();
			}
		}
		return null;
	}

	/**
	 * Extracts the session cookies from the incoming SOAP message.
	 * @param smc
	 *            The SOAP message context
	 * @return A map with the cookie name and value.
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> extractSessionCookies(SOAPMessageContext smc) {
		return (Map<String, String>) smc.get(SESSION_COOKIES);
	}

	public void close(MessageContext arg0) {
		// nothing to do
	}

	public boolean handleFault(SOAPMessageContext smc) {
		// nothing to do
		return true;
	}
}
