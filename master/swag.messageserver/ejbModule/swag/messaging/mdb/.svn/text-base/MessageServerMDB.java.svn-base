package swag.messaging.mdb;

import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import swag.bl.IUserManagement;
import swag.dtos.MessageDTO;
import swag.exceptions.MessagingException;
import swag.messaging.MailServiceBean;
import swag.messaging.MessageStorageBean;
import swag.util.ReflectionUtil;

@MessageDriven(mappedName = "queue.swag.SendingQueue" )
public class MessageServerMDB implements MessageListener {
	
	private static final String ACKNOWLEDGE_KEYWORD = "ACK";
	private static final String FAIL_KEYWORD = "FAIL";
	
	private static Logger log = Logger.getLogger("MessageServerMDB");
	
	private static ConnectionFactory connectionFactory;
	private Topic topic;
	private Connection connection;
	private Context ctx;
	private Session session;
	private MessageProducer producer;
	
	@EJB
	private IUserManagement userManagement;
	
	@EJB
	private MessageStorageBean mmb;
	
	@EJB
	private MailServiceBean msb;
	
	@PostConstruct
	public void setup() {
		try {
			lookupResourcesFromBroker();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@PreDestroy
	public void closeBean() {
		try {
			closeConnections();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public void onMessage(Message message) {
		try {
			if(message instanceof ObjectMessage) {
				ObjectMessage objMsg = (ObjectMessage) message;
				
				if(objMsg.getObject() instanceof MessageDTO) {
				
					MessageDTO dto = (MessageDTO) objMsg.getObject();
					log.info("Received Message (MessageServer): "+ReflectionUtil.printObjectDetails(dto));
					
					swag.models.Message msg = dto.getAsMessage();
					mmb.persistToDB(msg);
					
					if(dto.getReciever() != null && userManagement.isLoggedIn(dto.getReciever().getId())) {
						mmb.addToCache(msg);
						producer.send(session.createObjectMessage(new MessageDTO(msg)));
					} else {
						msg.setTimestamp(new Date());
						msg.setSentByMail(true);
						msg.setAlreadySent(true);
						log.info("User is offline.");
						try {
							sendByMail(msg.getReceiver().getMail(),msg);
						}catch(MessagingException e){
							msg.setSentByMail(false);
							msg.setAlreadySent(false);
							msg.setError(true);
							mmb.addToCache(msg);
							e.printStackTrace();
						}
					}
					
				} else {
					log.warning("Wrong type of Message: "+objMsg.getObject().getClass());
				}
			} else if(message instanceof TextMessage) {
				
				TextMessage tMsg = (TextMessage) message;
				Long id = extractIdFromTextMessage(tMsg.getText());
				
				log.info("Received Response "+tMsg.getText()+" with probable id "+id);
				
				if(tMsg.getText().startsWith(ACKNOWLEDGE_KEYWORD)) {
					mmb.removeFromCache(id);
				} else if(tMsg.getText().startsWith(FAIL_KEYWORD)) {
					swag.models.Message msg = mmb.find(id);
					try {
						sendByMail(msg.getReceiver().getMail(),msg);
						msg.setTimestamp(new Date());
						msg.setSentByMail(true);
						msg.setAlreadySent(true);
						mmb.removeFromCache(id);
					}catch(MessagingException e){
						msg.setSentByMail(false);
						msg.setAlreadySent(false);
						msg.setError(true);
						e.printStackTrace();
					}
					
					userManagement.logout(mmb.getFromCache(id).getReceiver().getId());
					
				}
			}
			
		} catch (Exception e) {
			log.warning("ERROR: "+e.getMessage());
		}
	}
	
	
	@Lock(LockType.WRITE)
	private synchronized void lookupResourcesFromBroker() throws NamingException, JMSException {

		ctx = new InitialContext();

		connectionFactory = (ConnectionFactory) ctx.lookup("swag.FactoryPool");
		topic = (Topic) ctx.lookup("topic.swag.MessagesTopic");

		connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		producer = session.createProducer(topic);

	}
	
	private void closeConnections() throws JMSException {
		if (producer != null) {
			producer.close();
			producer = null;
		}
		
		if (session != null) {
			session.close();
			session = null;
		}
		if (connection != null) {
			 connection.close();
			 connection = null;
        }
	}
	
	private void sendByMail(String address, swag.models.Message msg) throws MessagingException {
		log.info("Senging Mail to "+address);
		msb.sendMail(address, msb.prepareSubject(msg.getSubject(), msg.getSender()), msb.prepareMailMsg(msg.getText(),msg.getSender()));
	
	}
	
	private Long extractIdFromTextMessage(String msg) {
		String[] arr = msg.split(" ");
		
		if(arr.length >= 2) {
			return Long.valueOf(arr[1]);
		}
		
		return null;
	}

}
