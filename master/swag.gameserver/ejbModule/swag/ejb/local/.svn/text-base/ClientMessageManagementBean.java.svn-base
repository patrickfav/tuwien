package swag.ejb.local;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import swag.dtos.MessageDTO;
import swag.exceptions.MessagingException;
import swag.models.Message;

@Singleton
@Local
public class ClientMessageManagementBean {
	
	private ConnectionFactory connectionFactory;
	private Queue sendersQueue;
	private Connection connection;
	private Context ctx;
	private Session session;

	private MessageProducer producer;
	
	@PostConstruct
	public void setup() {

	}
	
	@PreDestroy
	public void closeBean() {

	}
	
	public void send(Message msg) throws MessagingException {
		try {
			lookupQueueFromBroker();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		ObjectMessage message = null;
        try {
			message = session.createObjectMessage();
			message.setObject(new MessageDTO(msg));
			
			producer.send(message);
        } catch(Exception e) {
        	throw new MessagingException("Could not sent message (client ejb)",e);
        }
        
        try {
        	 closeConnections();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	@Lock(LockType.WRITE)
	private synchronized void lookupQueueFromBroker() throws NamingException, JMSException {

		ctx = new InitialContext();

		connectionFactory = (ConnectionFactory) ctx.lookup("swag.FactoryPool");
		sendersQueue = (Queue) ctx.lookup("queue.swag.SendingQueue");

		connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		producer = session.createProducer(sendersQueue);

	}
}
