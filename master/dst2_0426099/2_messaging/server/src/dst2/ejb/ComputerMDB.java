package dst2.ejb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst2.dto.ComputerCommandDTO;
import dst2.dto.TaskDTO;
import dst2.model.Task;
import dst2.model.Task.TaskComplexity;
import dst2.model.Task.TaskStatus;

@MessageDriven(mappedName = "queue.dst.ComputerQueue" )
public class ComputerMDB implements MessageListener {
	
	public static String NEW_PROCESS_COMMAND = "new_process";
	public static String REGISTER_COMMAND = "register";
	public static String PROCESSED_COMMAND = "processed";
	
	public static String SERVER_MODULE_NAME= "ComputerMDB";
	
	static final Logger log = Logger.getLogger(SERVER_MODULE_NAME);
	
	public static Map<String,Map<TaskComplexity,List<Destination>>> computerMap = Collections.synchronizedMap(new HashMap<String,Map<TaskComplexity,List<Destination>>>());
	
	@Resource(mappedName = "dst.Factory")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "queue.dst.SchedulerCallBackQueue")
	private Queue schedulerQueue;
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
    public MessageDrivenContext mdc;
	
	
    public ComputerMDB() {
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void onMessage(Message msg) {
    	try {
    		Object objMsg = ((ObjectMessage) msg).getObject();
    		
    		log.info("Message received: "+objMsg);
    		
    		/* initialise resources for jms */
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			
			connection.start();
			
			
    		if(objMsg instanceof ComputerCommandDTO) {
    			ComputerCommandDTO msgDTO = (ComputerCommandDTO)objMsg;
    			
    			/* register a new client */
    			if(msgDTO.getCommand().equals(REGISTER_COMMAND)) {
    				
    				/* instanciante if does not exist already */
    				if(!computerMap.containsKey(msgDTO.getCluster())) {
    					computerMap.put(msgDTO.getCluster(), new HashMap<TaskComplexity,List<Destination>>());
    				} 
    				
    				if(!computerMap.get(msgDTO.getCluster()).containsKey(TaskComplexity.valueOf(msgDTO.getComplexity()))) {
    					computerMap.get(msgDTO.getCluster()).put(TaskComplexity.valueOf(msgDTO.getComplexity()), new ArrayList<Destination>());
    				}
    				
    				/* adding destination */
    				computerMap.get(msgDTO.getCluster()).get(TaskComplexity.valueOf(msgDTO.getComplexity())).add(msg.getJMSReplyTo());
    				log.info("Destination successfully added:"+msg.getJMSReplyTo());
    				
    				/* send available tasks to newly registered computer */
    				MessageProducer producer = session.createProducer(msg.getJMSReplyTo());
    				for(TaskDTO dto: getSuitableTasks(msgDTO.getCluster(),TaskComplexity.valueOf(msgDTO.getComplexity()))) {
    					sendMsgToComputer(session.createObjectMessage(dto),producer);
    				}
    				
    			} else if(msgDTO.getCommand().equals(PROCESSED_COMMAND)) {
    				/* update task to processed */
    				try {
    	    			
    	    			Task t = em.find(Task.class,msgDTO.getId());
    	    			
    	    			if(t == null) {
    	    				log.warning("Task "+msgDTO.getId()+" could not be found - deleted?");
    	    			} else {
    	    				if(t.getStatus() != TaskStatus.PROCESSED && t.getRatedBy().equals(msgDTO.getCluster()) && t.getComplexity().equals(TaskComplexity.valueOf(msgDTO.getComplexity()))) {
    	    					t.setStatus(TaskStatus.PROCESSED);
    	    				
	    	    				/* send info to scheduler */
	    	    				MessageProducer schedulerQueueProducer = session.createProducer(schedulerQueue);
	    	    				schedulerQueueProducer.send(session.createObjectMessage(convertToDTO(t)));
	    	    				log.info("Successfully processed:"+t);
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
        			log.info("Destination successfully added:"+msg.getJMSReplyTo());
    			}
    			
    		} 
    		/* propagate new process to computers */ 
    		else if(objMsg instanceof TaskDTO) {
    			TaskDTO taskDTO = (TaskDTO)objMsg;
    			
    			/* a new task was sent from clusterMDB */
    			if(taskDTO.getAdditionalCommand().equals(NEW_PROCESS_COMMAND)) {
    				
    				/* check if computer with the certain cluster and 
    				 * complexity exist and iterate over its destinations */
    				if(computerMap.containsKey(taskDTO.getRatedBy()))
    					if(computerMap.get(taskDTO.getRatedBy()).containsKey(TaskComplexity.valueOf(taskDTO.getComplexity())))
    						for(Destination d: computerMap.get(taskDTO.getRatedBy()).get(TaskComplexity.valueOf(taskDTO.getComplexity()))) {
    							/* create temporary queues and send msg */
	    						sendMsgToComputer(session.createObjectMessage(taskDTO),session.createProducer(d));
    						}
    				
    				log.info("Successfully sent to all relevant computers");
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
    
    @SuppressWarnings("unchecked")
	private List<TaskDTO> getSuitableTasks(String cluster, TaskComplexity complexity) {
    	TaskDTO tempDTO;
    	List<TaskDTO> dtoList = new ArrayList<TaskDTO>();
    	List<Task> list = em.createQuery("select t from Task t where t.complexity = :complexity " +
    			"AND t.ratedBy = :cluster AND t.status = :status")
    			.setParameter("cluster", cluster).setParameter("complexity", complexity).setParameter("status", TaskStatus.READY_FOR_PROCESSING).getResultList();
    	
    	for(Task t: list) {
    		tempDTO = convertToDTO(t);
    		tempDTO.setOrigintator(SERVER_MODULE_NAME);
    		tempDTO.setAdditionalCommand(PROCESSED_COMMAND);
    		
    		dtoList.add(tempDTO);
    	}
    	
    	return dtoList;
    }
    
    private void sendMsgToComputer(ObjectMessage objMsg,MessageProducer producer) throws JMSException {
    	/* create temporary queues and send msg */
		producer.send(objMsg);
    }
}