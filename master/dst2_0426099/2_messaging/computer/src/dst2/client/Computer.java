package dst2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.jms.TemporaryQueue;
import javax.naming.Context;
import javax.naming.InitialContext;

import dst2.dto.ComputerCommandDTO;
import dst2.dto.TaskDTO;

public class Computer {

	public static String PROCESSED_COMMAND = "processed";
	public static String REGISTER_COMMAND = "register";
	public static String STOP_COMMAND = "stop";
	
	public static String EASY_COMPLEXITY = "EASY";
	public static String CHALLENGING_COMPLEXITY = "CHALLENGING";
	
	private static String clusterName;
	private static String computerName;
	private static String computerComplexity;
	
	private static Set<Long> taskIdSet;
	
	private static ConnectionFactory connectionFactory;
	private static Connection connection;
	
	private static Queue serverQueue;
	private static TemporaryQueue tempQueue;
	
	private static Context ctx;
	private static Session session;
	
	private static MessageConsumer consumer;
	private static MessageProducer producer;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String input;
		Long id;
		String[] inputArray;
		
		/* checks arguments */
		if(args.length < 3 || args.length > 3) {
			printError("Invalid arguments length. (Too few or too many)");
			usage();
			System.exit(0);
		} else if(!args[2].equals(EASY_COMPLEXITY) && !args[2].equals(CHALLENGING_COMPLEXITY)) {
			printError("Please use only EASY or CHALLENGING as complexity.");
			usage();
			System.exit(0);
		}
		
		computerName = args[0];
		clusterName = args[1];
		computerComplexity = args[2];
		
		initializeJMSResources();
		
		System.out.println("====================================================");
		System.out.println(new Date());
		System.out.println("Computer "+computerName+"["+computerComplexity+"] for Cluster "+clusterName+" started.");
		System.out.println("\nAvailable commands:");
		System.out.println("\t processed <task-Id>");
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
				
				if(inputArray[0].equals(PROCESSED_COMMAND)) {
					if((id = checkInputLongValue(inputArray[1])) == null)
						continue;
					
					processed(id);
					
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
		System.out.println("Computer "+computerName+" down.");
		
		
		closeConnection();
		System.exit(0);
	}
	
	private static void printWrongInput(String msg) {
		System.out.println("The input is not vaild. Try again. "+msg);
	}
	
	private static void printError(String msg) {
		System.err.println("Error: "+msg);
	}
	
	private static Long checkInputLongValue(String longValue) {
		Long l = null;
		try {
			l =  new Long(longValue);
		} catch (Exception e) {
			printWrongInput("Could not read id.");
		} 
		
		return l;
	}
	private static void usage() {
		System.out.println("Usage: dst2.client.Computer <computerName> <clusterName> <EASY|CHALLENGING>");
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
			taskIdSet = new HashSet<Long>();
			
			ctx = new InitialContext();
			
			connectionFactory = (ConnectionFactory)ctx.lookup("dst.Factory"); 
			serverQueue = (Queue)ctx.lookup("queue.dst.ComputerQueue"); 
			
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			tempQueue = session.createTemporaryQueue();
			consumer = session.createConsumer(tempQueue);
            consumer.setMessageListener(new ComputerListener());
            
            connection.start();
            
            producer = session.createProducer(serverQueue);
            
            ObjectMessage objMsg = session.createObjectMessage(new ComputerCommandDTO(REGISTER_COMMAND, clusterName, computerComplexity));
            objMsg.setJMSReplyTo(tempQueue);
            
            producer.send(objMsg);
            
		} catch (Exception e) {
			printError("Could not create connection for JMS: "+e.toString());
			closeConnection();
			System.exit(1);
		}
	}
	
	/* ************************************************** Business Methods */
	
	private static void processed(long id) {
		/* check if the task can be processed by this computer */
		if(taskIdSet.contains(id)) {
			try {
				producer.send(session.createObjectMessage(new ComputerCommandDTO(PROCESSED_COMMAND, clusterName, computerComplexity, id)));
			} catch (JMSException e) {
				printError("Could not send:  "+e.toString());
			}
			taskIdSet.remove(id);
			System.out.println("Process command sent.");
		} else {
			System.out.println("This process was not assigned to the computer");
		}
	}
	
	static class ComputerListener implements MessageListener {

		@Override
		public void onMessage(Message msg) {
			if(msg instanceof ObjectMessage) {
				try {
					TaskDTO dto = (TaskDTO) ((ObjectMessage) msg).getObject();
					System.out.println("New Task for processing: "+dto);
					
					/* save possible processable tasks */
					taskIdSet.add(dto.getId());
				} catch (JMSException e) {
					printError("Could not cast message: "+e.toString());
				}
			} else {
				System.out.println("A message has been reveived:\n"+msg);
			}
		}
		
	}
}
