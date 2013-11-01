package bean;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import util.DeploymentAction;
import util.MessageUtils;
import entities.User;
import entities.event.Event;
import entities.event.Mailable;

@Stateless
@Name("MailNotification")
@Scope(ScopeType.APPLICATION)
public class MailNotificationBean implements MailNotification {
	
	private static Properties mailProps;
	private static Authenticator mailAuth;
	
	@Logger
	private Log log;
	
	@In(create = false, required = true, value = "DeploymentAction")
	private DeploymentAction deployment;
	
	@In(value = "MessageUtils", create = true)
	private MessageUtils messageUtils;
	
//	@PostConstruct
	public void init() {
		mailProps = new Properties();
		mailProps.put("mail.smtp.host", deployment.getMailHost());
		mailProps.put("mail.smtp.port", deployment.getMailPort());
		mailProps.put("mail.from", deployment.getMailFrom());
		mailProps.put("mail.smtp.auth", deployment.getMailAuth());
		mailAuth = new MailAuth();
	}
	
	public void notifyUsers(Mailable event) {
		Map<String, List<User>> targets = event.getNotificationTargets();
		List<String> actualAddresses = new ArrayList<String>();
		String eventType = ((Event)event).getEventtype().name();
		String subject = getSubject(eventType);
		
		if (!event.isNotifyAdminOnly()) {
			for (Map.Entry<String, List<User>> e: targets.entrySet()) {
				List<User> allUsers = e.getValue();
				for (User user: allUsers) {
					if (user.isNotifyOnEvents() && user.getNotifyEvents().contains(eventType)) {
						actualAddresses.add(user.getEmail());
					}
				}
				if (actualAddresses.size() > 0) {
					send(actualAddresses, subject, getText(eventType, e.getKey()));
				}
				actualAddresses.clear();
			}
			
		}
		if (event.isNotifyAdmin() && (!deployment.getAdminEmail().trim().equals(""))) {
			actualAddresses.add(deployment.getAdminEmail());
			send(actualAddresses, subject, getAdminText(eventType));
			actualAddresses.clear();
		}
	}
	
	private void send(List<String> addresses, String subject, String text) {
		if (mailProps == null || mailAuth == null) {
			init();
		}
		Session session = Session.getInstance(mailProps, mailAuth);
//		session.setDebug(true);
		Message msg = new MimeMessage(session);
		try {
			log.info("setting subject " + subject);
			msg.setSubject(subject);
			log.info("setting text: " + text);
			msg.setContent(text, "text/plain");
			for (String address: addresses) {
				log.info("adding recipient: " + address);
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
			}
			Transport.send(msg);
		} catch (AddressException e) {
			log.warn("Invalid mail address: #0", e.getMessage());
		} catch (MessagingException e) {
			log.warn("Messaging error: #0", e.getMessage());
		}
	}
	
	private String getSubject(String eventType) {
		return messageUtils.get("events." + eventType + ".subject");
	}
	
	private String getText(String eventType, String group) {
		return messageUtils.get("events." + eventType + "." + group);
	}
	
	private String getAdminText(String eventType) {
		return messageUtils.get("events." + eventType + ".admin");
	}
	
	private class MailAuth extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication()
	    {
			return new PasswordAuthentication(deployment.getMailAuthUser(), deployment.getMailAuthPw());
	    }
	}
}
