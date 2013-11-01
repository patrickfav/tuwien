package swag.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import swag.bl.messaging.IMessageManagement;
import swag.ejb.local.ClientMessageManagementBean;
import swag.exceptions.MessagingException;
import swag.models.Message;
import swag.models.User;

@Stateless
public class MessageManagementBean implements IMessageManagement{
	
	private static Logger log = Logger.getLogger("MessageManagementBean");
	
	@EJB
	private ClientMessageManagementBean cmmb;
	
	@PersistenceContext
	public EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getAllMessagesByUser(long userId) throws MessagingException {
		log.info("Get all Messages for User "+userId);
		return (List<Message>) em.createQuery("select m from Message m where m.receiver.id = :id" +
				" order by m.sendDate desc").setParameter("id", userId).getResultList();
	}

	@Override
	public void persist(Message msg) {
		em.persist(msg);
	}

	@Override
	public void update(Message msg) {
		em.merge(msg);
	}

	@Override
	public Message find(long id) {
		return em.find(Message.class,id);
	}

	@Override
	public void sendSystemMessage(User receiver,String subject, String text) throws MessagingException {
		Message msg = new Message();
		msg.setReceiver(receiver);
		msg.setText(text);
		msg.setSubject(subject);
		msg.setSendDate(new Date());
		
		try {
			cmmb.send(msg);
		} catch (Exception e) {
			throw new MessagingException("Could not send System Message to "+receiver.getId(),e);
		}
		
	}

	@Override
	public List<Message> getAllMessagesForUser(long userId) {
		log.info("Get all Messages for User "+userId);
		return (List<Message>) em.createQuery("select m from Message m where m.receiver.id = :id or m.sender.id = :id" +
				" order by m.sendDate desc").setParameter("id", userId).getResultList();
	}

}
