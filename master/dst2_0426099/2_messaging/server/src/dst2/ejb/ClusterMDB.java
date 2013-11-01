package dst2.ejb;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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

@MessageDriven(mappedName = "queue.dst.ClusterQueue" )
public class ClusterMDB implements MessageListener {
	
	public static String ACCEPT_COMMAND = "accept";
	public static String DENY_COMMAND = "deny";
	public static String NEW_PROCESS_COMMAND = "new_process";
	
	public static String SERVER_MODULE_NAME= "ClusterMDB";
	
	static final Logger log = Logger.getLogger(SERVER_MODULE_NAME);
	
	public static Map<Long,String> clusterMap = Collections.synchronizedMap(new HashMap<Long,String>());
	
	@Resource(mappedName = "dst.Factory")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "queue.dst.SchedulerCallBackQueue")
	private Queue callbackQueue;
	
	@Resource(mappedName = "topic.dst.ClusterTopic")
    private Topic clusterTopic;
	
	@Resource(mappedName = "queue.dst.ComputerQueue")
    private Queue computerQueue;
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
    public MessageDrivenContext mdc;
	
	
	
    public ClusterMDB() {
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void onMessage(Message msg) {
    	try {
    		Object objMsg = ((ObjectMessage) msg).getObject();
    		
    		log.info("Message received: "+objMsg);
    		
    		/* initialise resources for jms */
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			MessageProducer topicProducer = session.createProducer(clusterTopic);
			
			connection.start();
			
			/* a cluster trys to accept the task */
    		if(objMsg instanceof IDCommandDTO) {
    			IDCommandDTO msgDTO = (IDCommandDTO)objMsg;
    			
    			try {
	    			
	    			Task t = em.find(Task.class,msgDTO.getId());
	    			
	    			if(t == null) {
	    				log.warning("Task "+msgDTO.getId()+" could not be found - deleted?");
	    			} else {
	    				/* check if the task is free */
	    				if(t.getRatedBy() == null || t.getRatedBy().equals("")) {
	    					/* double check with local map */
	    					if(!clusterMap.containsKey(msgDTO.getId())) {
	    						clusterMap.put(msgDTO.getId(), msgDTO.getCommand());
		    					
	    						/* assign task to cluster */
		    					t.setRatedBy(msgDTO.getCommand());
		    					
		    					TaskDTO taskDTO = convertToDTO(t);
		    					taskDTO.setAdditionalCommand(msgDTO.getCommand());
		    					
		    					/* send to acknowledge to all clusters */
		    					topicProducer.send(session.createObjectMessage(taskDTO));
	    					}
	    				}
	    			}
	    			
	    			
    			}  catch(Exception e) {
    				log.warning("Could not commit find transaction");
    				try {
    					mdc.setRollbackOnly();
    				} catch (Exception e1) {
    					log.warning("Could not roll back.");
    				} 
    		    }
    			
    		} 
    		/* rating of a task by a cluster*/
    		else if(objMsg instanceof TaskDTO) {
    			TaskDTO taskDTO = (TaskDTO)objMsg;
    			
    			if(taskDTO.getAdditionalCommand().equals(ACCEPT_COMMAND)) {
    				/* do nothing special */
    			} else if(taskDTO.getAdditionalCommand().equals(DENY_COMMAND)) {
    				/* send info to scheduler */
    				MessageProducer schedulerQueueProducer = session.createProducer(callbackQueue);
    				schedulerQueueProducer.send(session.createObjectMessage(taskDTO));
    			}
    			
    			/* update entity */
    			try { 
	    			Task t = em.find(Task.class,taskDTO.getId());
	    			
	    			t.setComplexity(TaskComplexity.valueOf(taskDTO.getComplexity()));
	    			t.setJobId(taskDTO.getJobId());
	    			t.setStatus(TaskStatus.valueOf(taskDTO.getTaskStatus()));
	    			t.setRatedBy(taskDTO.getRatedBy());
	    			
    			}  catch(Exception e) {
    				log.warning("Could not commit find transaction");
    				try {
    					mdc.setRollbackOnly();
    				} catch (Exception e1) {
    					log.warning("Could not roll back.");
    				} 
    		    }
    			
    			/* inform computerMDB if accept*/
    			if(taskDTO.getAdditionalCommand().equals(ACCEPT_COMMAND)) {
    				MessageProducer computerQueueProducer = session.createProducer(computerQueue);
    				taskDTO.setAdditionalCommand(NEW_PROCESS_COMMAND);
    				computerQueueProducer.send(session.createObjectMessage(taskDTO));
    			}
    		}
    	
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
}