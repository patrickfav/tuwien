package swag.messaging;

import java.util.Date;
import java.util.Properties;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import swag.exceptions.MessagingException;
import swag.models.User;

@Stateless
@Local
public class MailServiceBean {

	private static final String SMTP_HOST = "smtp.transvisa.at";
	private static final String SMTP_USER = "m02006c3";
	private static final String SMTP_PASS = "swa2011";
	private static final String SENDERS_ADRESS = "swag@transvisa.at";

	public void sendMail(String recipientsAddress, String subject, String text) throws MessagingException {
		MailAuthenticator auth = new MailAuthenticator(SMTP_USER, SMTP_PASS);

		Properties properties = new Properties();

		// Den Properties wird die ServerAdresse hinzugefügt
		properties.put("mail.smtp.host", SMTP_HOST);

		// !!Wichtig!! Falls der SMTP-Server eine Authentifizierung
		// verlangt
		// muss an dieser Stelle die Property auf "true" gesetzt
		// werden
		properties.put("mail.smtp.auth", "true");

		// Hier wird mit den Properties und dem implements Contructor
		// erzeugten
		// MailAuthenticator eine Session erzeugt
		Session session = Session.getDefaultInstance(properties, auth);

		try {
			// Eine neue Message erzeugen
			Message msg = new MimeMessage(session);

			// Hier werden die Absender- und Empfängeradressen gesetzt
			msg.setFrom(new InternetAddress(SENDERS_ADRESS));
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipientsAddress, false));

			// Der Betreff und Body der Message werden gesetzt
			msg.setSubject(subject);
			msg.setText(text);

			// Hier lassen sich HEADER-Informationen hinzufügen
			//msg.setHeader("Test", "Test");
			msg.setSentDate(new Date());

			// Zum Schluss wird die Mail natürlich noch verschickt
			Transport.send(msg);

		} catch (Exception e) {
			throw new MessagingException("Error while sending mail",e);
		}
	}
	
	public String prepareMailMsg(String text,User sender) {
		StringBuffer sb = new StringBuffer();
		
		if(sender != null) {
			sb.append("This is a private message from "+sender.getUsername());
			sb.append("\n---------------------------------------------------------------------\n");
			sb.append(text);
			sb.append("\n\nSend by Swag Mail Service at "+(new Date()));
		} else {
			sb.append("This is an automated message:");
			sb.append("\n---------------------------------------------------------------------\n");
			sb.append(text);
			sb.append("\n\nSend by Swag Mail Service at "+(new Date()));
		}
		
		return sb.toString();
	}
	
	public String prepareSubject(String subject,User sender) {
		StringBuffer sb = new StringBuffer();
		
		if(sender != null) {
			sb.append("[Swag] PM:"+subject);
		} else {
			sb.append("[Swag] "+subject);
		}
		return sb.toString();
	}
	
	class MailAuthenticator extends Authenticator {

		/**
		 * Ein String, der den Usernamen nach der Erzeugung eines Objektes<br>
		 * dieser Klasse enthalten wird.
		 */
		private final String user;

		/**
		 * Ein String, der das Passwort nach der Erzeugung eines Objektes<br>
		 * dieser Klasse enthalten wird.
		 */
		private final String password;

		/**
		 * Der Konstruktor erzeugt ein MailAuthenticator Objekt<br>
		 * aus den beiden Parametern user und passwort.
		 * 
		 * @param user
		 *            String, der Username fuer den Mailaccount.
		 * @param password
		 *            String, das Passwort fuer den Mailaccount.
		 */
		public MailAuthenticator(String user, String password) {
			this.user = user;
			this.password = password;
		}

		/**
		 * Diese Methode gibt ein neues PasswortAuthentication Objekt zurueck.
		 * 
		 * @see javax.mail.Authenticator#getPasswordAuthentication()
		 */
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(this.user, this.password);
		}
	}
}
