package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import entities.User;


public class Server {
	
	/*
	 * Statics 
	 */
	public static ExecutorService pool; /* the shared used thread pool */
	public static ResourceBundle userData; /* the properties file is loaded in this bundle */
	public static HashMap<String,User> usersTable; /* a hashmap with all users */
    public static ArrayList<ServerTCPUserThread> threadList; /* the Arraylist containing all live TCPUserThreads */
    
	/*
	 * Config
	 */
	
	private static String userDataPath = "users";
	
	/*
	 * Internal Data and Settings
	 */
	private ServerThread TCPThread;
	private ServerThread UDPThread;
	private Integer tcpPort;
	private Integer udpPort;
	private Scanner inScanner;
	
	/**
	 * CONSTRUCTOR
	 * 
	 * @param tcpPort
	 * @param udpPort
	 */
	
	public Server(Integer tcpPort, Integer udpPort) {
		
		this.tcpPort = tcpPort;
		this.udpPort = udpPort;
		
		/* managing user data */
		loadUsersData();
		createUserTable();
		
		/* initialize scanner and thread pool*/
		inScanner = new Scanner(System.in);
		pool = Executors.newFixedThreadPool(usersTable.size()*2);
		
		/* welcome msg */
		System.out.println("[Server]: Server started. Hit enter to exit.");
		
		
		
		/* start threads */
		try {
			TCPThread = new ServerTCPThread(tcpPort);
			UDPThread = new ServerUDPThread(udpPort);
		} catch (IOException e) {
			System.err.println("[Server/Err]: Could not start server thread (socket error): "+e);
			exitServer();
		}
		
		UDPThread.start();
		TCPThread.start();
		
		/* recognizes the enter */
		inScanner.nextLine();
		
		System.out.println("[Server]: Server ending.");
		exitServer();
	}
	/**
	 * Loads the users into a ResourceBundle
	 */
	private void loadUsersData() {
		try {
			userData = ResourceBundle.getBundle(userDataPath);
		} catch (Exception e) {
			System.err.println("[Server/Err]: Could not load Users properties: "+e);
			exitServer();
		}
		//System.out.println("[Server]: Users loaded successfully.");
	}
	
	/**
	 * Generates a Hashtable according to the user.properties
	 * data and defines every user as offline.
	 */
	public static void createUserTable() {
		User tempUser;
		Server.usersTable = new HashMap<String,User>();
		
		for(String s: Server.userData.keySet()) {
			tempUser = new User(s,Server.userData.getString(s),false);
			Server.usersTable.put(s, tempUser);
		}
	}
	
	/**
	 * Easy way to change the onlines status of a user in 
	 * the hashtable.
	 * 
	 * @param user to change
	 * @param onlineStatus
	 */
	public synchronized static void setUsersTableOnlineStatus(String user, Boolean onlineStatus) {
		User temp = usersTable.get(user);
		temp.setOnline(onlineStatus);
		usersTable.put(user, temp);
	}
	
	/**
	 * Returns the online status of the given user
	 * @param user
	 * @return
	 */
	public synchronized static Boolean ceckUserTableOnlineStatus(String user) {
		User temp = usersTable.get(user);
		return temp.getOnline();
	}
	
	/**
	 * Just a little helper method for 
	 * concating String Arrays to 1 String and
	 * adds a whitespace in fornt of every part
	 * 
	 * @param arr - the array containing the String parts
	 * @return the whole String
	 */
	public static String specialConcatStringArray(String[] arr) {
		String returnString = new String();
		
		for(String s:arr) {
			returnString += " "+s;
		}
		
		return returnString;
	}
	
	/**
	 * Shuts down the thread pool and exits the program
	 * 
	 */
	public void exitServer() {
		for(ServerThread st:threadList) {
			st.exitThread();
		}
		
		TCPThread.exitThread();
		UDPThread.exitThread();
	
		pool.shutdown();
	}
}
