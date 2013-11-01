package dst2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;

import dst2.dto.IDCommandDTO;
import dst2.dto.TaskDTO;

public class Cluster {

	public static String ACCEPT_COMMAND = "accept";
	public static String DENY_COMMAND = "deny";
	public static String STOP_COMMAND = "stop";
	
	public static String EASY_COMPLEXITY = "EASY";
	public static String CHALLENGING_COMPLEXITY = "CHALLENGING";
	
	public static String SCHEDULER_SERVER_MODULE_NAME= "SchedulerMDB";
	public static String CLUSTER_SERVER_MODULE_NAME= "ClusterMDB";
	
	private static String clusterName;
	private static TaskDTO task;
	
	private static ConnectionFactory connectionFactory;
	private static Connection connection;
	
	private static Queue serverQueue;
	 private static Topic clusterTopic;
	 
	private static Context ctx;
	private static Session session;
	
	private static MessageConsumer consumer;
	private static MessageProducer producer;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String input;
		String[] inputArray;
		
		if(args.length < 1 || args.length > 1) {
			printError("Invalid arguments length. (Too few or too many)");
			usage();
			closeConnection();
			System.exit(0);
		}
		
		
		
		clusterName = args[0];
		
		initializeJMSResources();
		
		System.out.println("====================================================");
		System.out.println(new Date());
		System.out.println("Cluster "+clusterName+" started.");
		System.out.println("\nAvailable commands:");
		System.out.println("\t accept <EASY|CHALLENGING>");
		System.out.println("\t deny");
		System.out.println("\t stop");
		System.out.println("====================================================");
		
		// Wait for new line
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		try {
			while((input = stdIn.readLine()) != null) {
				
				inputArray = input.trim().split(" ");
				
				/* check if there is any input */
				if(input.length() <= 0 || inputArray == null || inputArray.length <= 0) {
					printWrongInput("Command too short.");
					continue;
				}
				
				if(inputArray[0].equals(ACCEPT_COMMAND)) {
					if(inputArray[1].equals(EASY_COMPLEXITY)) {
						accept(EASY_COMPLEXITY);
					} else if(inputArray[1].equals(CHALLENGING_COMPLEXITY)) {
						accept(CHALLENGING_COMPLEXITY);
					} else {
						printWrongInput("Unkown complexity.");
						continue;
					}
					
				} else if(inputArray[0].equals(DENY_COMMAND)) {			
					deny();
					
				} else if(inputArray[0].equals(STOP_COMMAND)) {
					break;
				} else {
					printWrongInput("Unkown command.");
					continue;
				}
			}
		} catch (IOException e) {
			printError("Could not read line.");
			e.printStackTrace();
		}
		
		
		
		System.out.println("====================================================");
		System.out.println("Cluster "+clusterName+" down.");
		
		closeConnection();
		System.exit(0);
	}
	
	private static void printWrongInput(String msg) {
		System.out.println("The input is not vaild. Try again. "+msg);
	}
	
	private static void printError(String msg) {
		System.err.println("Error: "+msg);
	}
	
	private static void usage() {
		System.out.println("Usage: dst2.client.Cluster <clusterName>");
	}
	
/* ************************************************** JMS Methods */
	
	private static void closeConnection() {
		if (session != null) {
			try {
				session.close();
			} catch (JMSException e) {
				printError("Could not close Session: "+ e.toString());
			}
		}
		if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
            	printError("Could not close connection: "+ e.toString());
            }
        }
	}
	
	private static void initializeJMSResources() {
		try {
			ctx = new InitialContext();
			
			connectionFactory = (ConnectionFactory)ctx.lookup("dst.Factory"); 
			serverQueue = (Queue)ctx.lookup("queue.dst.ClusterQueue"); 
			clusterTopic = (Topic) ctx.lookup("topic.dst.ClusterTopic"); 
			
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			consumer = session.createConsumer(clusterTopic);
            consumer.setMessageListener(new ClusterListener());
            
            connection.start();
            
            producer = session.createProducer(serverQueue);
           
           
		} catch (Exception e) {
			printError("Could not create connection for JMS: "+e.toString());
			closeConnection();
			System.exit(1);
		}
	}
	
	/* ************************************************** Business Methods */
	
	private static void accept(String complexity) {
		if(task == null) {
			System.out.println("No Task assigned to this cluster yet.");
			return;
		}
		
		/* set fields */
		task.setAdditionalCommand(ACCEPT_COMMAND);
		task.setComplexity(complexity);
		task.setRatedBy(clusterName);
		task.setTaskStatus("READY_FOR_PROCESSING");
		
		try {
			producer.send(session.createObjectMessage(task));
			
			consumer = session.createConsumer(clusterTopic);
	        consumer.setMessageListener(new ClusterListener());
		} catch (JMSException e) {
			printError("Could not send accept or resubscribe: "+e.toString());
		}
		
		System.out.println("Task accepted. May accept another task now.");
		
		task = null;
	}
	
	private static void deny() {
		if(task == null) {
			System.out.println("No Task assigned to this cluster yet.");
			return;
		}
		
		/* set fields */
		task.setAdditionalCommand(DENY_COMMAND);
		task.setRatedBy(clusterName);
		task.setTaskStatus("PROCESSING_NOT_POSSIBLE");
		
		try {
			producer.send(session.createObjectMessage(task));
			
			consumer = session.createConsumer(clusterTopic);
	        consumer.setMessageListener(new ClusterListener());
		} catch (JMSException e) {
			printError("Could not send accept or resubscribe: "+e.toString());
		}
		
		System.out.println("Task denied. May accept another task now.");
		
		task = null;
	}
	
	
	/* ************************************************** Listener */
	
	static class ClusterListener implements MessageListener {

		@Override
		public void onMessage(Message msg) {
			if(msg instanceof ObjectMessage) {
				try {
					Object dto = ((ObjectMessage) msg).getObject();
					
					
					if(dto instanceof TaskDTO) {
						TaskDTO taskDTO = (TaskDTO) dto;
						/* task announcement */
						if(taskDTO.getOrigintator().equals(SCHEDULER_SERVER_MODULE_NAME)) {
							
		    				/* Randomize task acceptance - will work without these lines
		    				 * but the acceptance will be more "random" with it.         */
		    				Random r = new Random();
			    			Thread.sleep(r.nextInt(300));
			    			
							/* send ok msg */
							producer.send(session.createObjectMessage(new IDCommandDTO(clusterName, taskDTO.getId())));
						} 
						/* task was assigned to a certain cluster */
						else if(taskDTO.getOrigintator().equals(CLUSTER_SERVER_MODULE_NAME)) {
							
							/* if task was assigned to this cluster */
							if(taskDTO.getAdditionalCommand().equals(clusterName)) {
								/* close subscription */
								consumer.close();
								
								task = taskDTO;
								/* print out receive msg */
								System.out.println("A new Task was assigned: "+task);
							}
						}
					}  else {
						System.out.println("Receiving: "+dto);
					}
				} catch (Exception e) {
					printError("Excpetion while MS Listener: "+e.toString());
				}
			} else {
				System.out.println("A message has been reveived:\n"+msg);
			}
		}
		
	}
}
