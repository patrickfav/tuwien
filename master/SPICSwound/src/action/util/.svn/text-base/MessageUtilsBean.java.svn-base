package util;

import java.text.MessageFormat;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.international.Messages;

@Stateless
@Name("MessageUtils")
public class MessageUtilsBean implements MessageUtils {

	private static MessageUtilsBean instance;

	private ResourceBundle rb;

	@In
	private Map<String, String> messages;

	// singleton only makes sense in POJOs, otherwise use the @EJB injection
	public static MessageUtils getInstance() {
		if (instance == null) {
			instance = new MessageUtilsBean();
		}

		return instance;
	}

	/**
	 * formats a message with the standard java MessageFormat class
	 * 
	 * @param messageKey -
	 *            the message
	 * @param args -
	 *            the arguments that are supposed to be formated into the
	 *            message
	 * @return - the formated message String
	 */
	public static String formatMessage(String messageKey, Object... args) {
		return MessageFormat.format(getInstance().get(messageKey), args);
	}

	/**
	 * formats an error message (in internationalized form) that looks like
	 * this: "Error: <component name>: <message string>
	 * 
	 * @param messageKey -
	 *            the message that should be used as an error message
	 * @param args:
	 *            arg[0] if not null is used as the component name, all other
	 *            arguments are passed to MessageFormat
	 * @return the formated message as a string
	 */
	public static String formatErrorMessage(String messageKey, Object... args) {
		String compName = null;
		Object[] remainingArgs = null;
		if (args != null && args.length != 0) {
			compName = (String) args[0];

			if (args.length != 1) {
				remainingArgs = new Object[args.length - 1];
				for (int i = 1; i < args.length; i++) {
					remainingArgs[i - 1] = args[i];
				}
			}
		}
		if (compName != null)
			return MessageFormat.format("{0}: {1}: {2}", getInstance().get(
					"error"), compName, formatMessage((messageKey),
					remainingArgs));

		return MessageFormat.format("{0}: {1}", getInstance().get("error"),
				formatMessage((messageKey), remainingArgs));
	}
	
	public static FacesMessage formatErrorFacesMessage(String messageKey, Object... args) {
		return new FacesMessage(FacesMessage.SEVERITY_ERROR, "", formatErrorMessage(messageKey, args));
	}

	/**
	 * first tries to use the injected messages map to get a specific message,
	 * if this fails, it tries to load the RessourceBundle containing the
	 * messages using the threads classloader
	 * 
	 * @throws NullPointerException
	 *             if both the injected messages and the ressourcebundle are
	 *             null
	 */
	public String get(String name) {
		if (getMessagesMap() == null) {
			
			// TODO: this is most likely legacy code! and should be removed
			// seam.Messages.instance() should be sufficient in _all_ cases!
			if (getBeanFromSeamContext()) {
				return instance.get(name);
			} else {
				loadMessageBundle();
				try {
					return rb.getString(name);
				} catch (MissingResourceException e) {
					return "Error: "
							+ name
							+ "not found - check Message_"
							+ FacesContext.getCurrentInstance().getViewRoot()
									.getLocale() + ".properties";
				}
			}
		}

		return messages.get(name);
	}

	private boolean getBeanFromSeamContext() {
		try {
			Object messageUtils = Component.getInstance(Component
					.getComponentName(MessageUtilsBean.class));
			if (messageUtils instanceof MessageUtilsBean) {
				MessageUtilsBean tmp = (MessageUtilsBean) messageUtils;
				if (tmp.messages == null)
					return false;

				return true;
			}

		} catch (Exception e) {
			System.out.println("MessageUtils: Exception:" + e.getMessage());
		}
		return false;
	}

	private void loadMessageBundle() {

		if (rb != null) // no need to load again
			return;

		rb = ResourceBundle.getBundle("messages", FacesContext
				.getCurrentInstance().getViewRoot().getLocale(), Thread
				.currentThread().getContextClassLoader());

		if (rb == null)
			throw new NullPointerException("ResourceBundle messages");
	}
	
	private Map<String, String> getMessagesMap() {
		if(this.messages == null)
			this.messages = Messages.instance();
		return this.messages;
	}

}
