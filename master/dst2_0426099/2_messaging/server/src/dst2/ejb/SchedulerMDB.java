package dst2.ejb;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst2.dto.IDCommandDTO;
import dst2.dto.TaskDTO;
import dst2.model.Task;
import dst2.model.Task.TaskComplexity;
import dst2.model.Task.TaskStatus;

@MessageDriven(mappedName = "queue.dst.SchedulerQueue" )
public class SchedulerMDB implements MessageListener {
	
	public static String ASSIGN_COMMAND = "assign";
	public static String INFO_COMMAND = "info";
	
	public static String SERVER_MODULE_NAME= "SchedulerMDB";
	
	static final Logger log = Logger.getLogger(SERVER_MODULE_NAME);
	
	@Resource(mappedName = "dst.Factory")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "queue.dst.SchedulerCallBackQueue")
	private Queue callbackQueue;
	
	@Resource(mappedName = "topic.dst.ClusterTopic")
    private Topic clusterTopic;
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
    public MessageDrivenContext mdc;
	
    public SchedulerMDB() {
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void onMessage(Message msg) {
    	try {
    		ObjectMessage objMsg = (ObjectMessage) msg;
    		IDCommandDTO msgDTO = (IDCommandDTO)objMsg.getObject();
			log.info("Message received: "+msgDTO);
			
			TaskDTO dto = null;
			Task t = null;
			
			/* initialise resources for jms */
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			/* decide what to do */
			if(msgDTO.getCommand().equals(ASSIGN_COMMAND)) {
				/* persist entity */
				t = persist(new Task(msgDTO.getId(),"",TaskStatus.ASSIGNED,TaskComplexity.UNRATED));
				dto = convertToDTO(t);
				
				/* publish to cluster topic */
				if(t != null) {
					MessageProducer topicProducer = session.createProducer(clusterTopic);
					topicProducer.send(session.createObjectMessage(dto));
				}
			} else if(msgDTO.getCommand().equals(INFO_COMMAND)) {
				t = find(msgDTO.getId(),Task.class);
				dto = convertToDTO(t);
			} else {
				dto = convertToDTO(null);
				log.warning("Wrong argument: "+msgDTO.getCommand());
			}
	    	
			/* create reply to scheduler*/
			MessageProducer queueProducer = session.createProducer(callbackQueue);	
			queueProducer.send(session.createObjectMessage(dto));
			
			/* close connection */
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
					log.warning("Could not close Session: "+ e.toString());
				}
			}
			if (connection != null) {
	            try {
	                connection.close();
	            } catch (Exception e) {
	            	log.warning("Could not close connection: "+ e.toString());
	            }
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    private TaskDTO convertToDTO(Task t) {
    	if(t == null)
    		return new TaskDTO(SERVER_MODULE_NAME);
    	
    	TaskDTO dto = new TaskDTO(t.getJobId(),SERVER_MODULE_NAME);
    	dto.setId(t.getId());
    	dto.setComplexity(t.getComplexity().toString());
    	dto.setRatedBy(t.getRatedBy());
    	dto.setTaskStatus(t.getStatus().toString());
    	
    	return dto;
    }
    
/* *************************************************** CRUD METODS */
	
	public synchronized <T> T find(long id,Class<T> clazz) {
		T o = null;
		try {
				
			o = em.find(clazz, id);
			
		}  catch(Exception e) {
			log.warning("Could not commit find transaction. Rolling back.");
			try {
				mdc.setRollbackOnly();
			} catch (Exception e1) {
				log.warning("Could not roll back.");
			} 
	    }
		
		if(o != null) {
			log.info("Task "+o+" found.");
		} else {
			log.info("Task "+o+" could not be found.");
		}
		
		return o;
	}
	
	public synchronized Task persist(Task o) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		try {
			
			em.persist(o);
		}  catch(Exception e) {
			log.warning("Could not commit persist transaction. Rolling back.");
			try {
				mdc.setRollbackOnly();
			} catch (Exception e1) {
				log.warning("Could not roll back.");
			} 
	    }
		if(o != null)
			log.info("Task "+o+" persistet with id "+o.getId());
		
		return o;
	}
}