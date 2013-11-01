package dst2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

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
import javax.naming.Context;
import javax.naming.InitialContext;
import dst2.dto.IDCommandDTO;
import dst2.dto.TaskDTO;

public class Scheduler {
	
	public static String ASSIGN_COMMAND = "assign";
	public static String INFO_COMMAND = "info";
	public static String STOP_COMMAND = "stop";
	
	
	private static ConnectionFactory connectionFactory;
	private static Connection connection;
	
	private static Queue serverQueue;
	private static Queue callbackQueue;
	
	private static Context ctx;
	private static Session session;
	
	private static MessageConsumer consumer;
	private static MessageProducer producer;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/* jms variables */
		connection = null;
        session = null;
        consumer = null;
        producer = null;
        
        /* initiate resources */
        initializeJMSResources();
        
		/* input variables */
		String input;
		Long id;
		String[] inputArray;
		
		System.out.println("====================================================");
		System.out.println(new Date());
		System.out.println("Scheduler started.");
		System.out.println("\nAvailable commands:");
		System.out.println("\t assign <job-Id>");
		System.out.println("\t info <task-Id>");
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
				
				if(inputArray[0].equals(ASSIGN_COMMAND)) {
					if((id = checkInputLongValue(inputArray[1])) == null)
						continue;
					
					assign(id);
					
				} else if(inputArray[0].equals(INFO_COMMAND)) {
					if((id = checkInputLongValue(inputArray[1])) == null)
						continue;
					
					info(id);
					
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
		
		closeConnection();
		
		System.out.println("\n=============================================");
		System.out.println("Scheduler down.");
		
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
			serverQueue = (Queue)ctx.lookup("queue.dst.SchedulerQueue"); 
			callbackQueue = (Queue)ctx.lookup("queue.dst.SchedulerCallBackQueue"); 
			
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			consumer = session.createConsumer(callbackQueue);
            consumer.setMessageListener(new SchedulerListener());
            
            connection.start();
            
            producer = session.createProducer(serverQueue);
           
           
		} catch (Exception e) {
			printError("Could not create connection for JMS: "+e.toString());
			closeConnection();
			System.exit(1);
		}
	}
	
	/* ************************************************** Business Methods */
	
	private static void assign(long id) {
		//System.out.println("DEBUG: Assign "+id);
		ObjectMessage message = null;
        
		try {
			message = session.createObjectMessage();
			message.setObject(new IDCommandDTO(ASSIGN_COMMAND,id));
			producer.send(message);
		} catch (JMSException e) {
			printError("Could create assign message: "+e.toString());
		}
	}
	
	private static void info(long id) {
		//System.out.println("DEBUG: Info "+id);
		ObjectMessage message = null;
        
		try {
			message = session.createObjectMessage();
			message.setObject(new IDCommandDTO(INFO_COMMAND,id));
			producer.send(message);
		} catch (JMSException e) {
			printError("Could create info message: "+e.toString());
		}
	}
	
	static class SchedulerListener implements MessageListener {

		@Override
		public void onMessage(Message msg) {
			if(msg instanceof ObjectMessage) {
				try {
					TaskDTO dto = (TaskDTO) ((ObjectMessage) msg).getObject();
					System.out.println("Receiving: "+dto);
				} catch (JMSException e) {
					printError("Could not cast message: "+e.toString());
				}
			} else {
				System.out.println("A message has been reveived:\n"+msg);
			}
		}
		
	}
}
