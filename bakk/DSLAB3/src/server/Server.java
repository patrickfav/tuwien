package server;

import java.io.IOException;
import java.security.Key;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import channel.SecureChannelAsym;
import channel.SecureChannelSym;

import entities.User;


public class Server {
	
	/*
	 * Statics 
	 */
	public static ExecutorService pool; /* the shared used thread pool */
	public static ResourceBundle userData; /* the properties file is loaded in this bundle */
	public static ResourceBundle configData; /* the properties file is loaded in this bundle */
	public static HashMap<String,User> usersTable; /* a hashmap with all users */
    public static ArrayList<ServerTCPUserThread> threadList; /* the Arraylist containing all live TCPUserThreads */
    
	/*
	 * Config
	 */
	
	private static String userDataPath = "users";
	private static String configDataPath = "server";
	
	/*
	 * Internal Data and Settings
	 */
	private ServerThread TCPThread;
	private ServerThread UDPThread;
	private Scanner inScanner;
	
	protected static Integer serverTCPPort;
	protected static Integer serverUDPPort;
	protected static String key_public_dir;
	protected static String key_private_dir;
	protected static String server_key;
	protected static String ca_host;
	protected static Integer ca_port;
	protected static String ca_certificate;
	
	private SecureChannelAsym asym;
	
	/**
	 * CONSTRUCTOR
	 * 
	 * @param serverTCPPort
	 * @param serverUDPPort
	 */
	
	public Server() {
		
		/* managing user data */
		loadUsersData();
		createUserTable();
		loadConfigData();
		setUpConfig();
		
		/* initialize scanner and thread pool*/
		inScanner = new Scanner(System.in);
		pool = Executors.newFixedThreadPool(usersTable.size()*2);
		
		/* welcome msg */
		System.out.println("[Server]: Server started. Hit enter to exit.");
		
		
		
		/* start threads */
		try {
			TCPThread = new ServerTCPThread(serverTCPPort,asym);
			UDPThread = new ServerUDPThread(serverUDPPort);
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
	 * Loads the config into a ResourceBundle
	 */
	private void loadConfigData() {
		try {
			configData = ResourceBundle.getBundle(configDataPath);
		} catch (Exception e) {
			System.err.println("[Server/Err]: Could not load config properties: "+e);
			exitServer();
		}
		//System.out.println("[Server]: Users loaded successfully.");
	}
	
	/**
	 * Sets the config
	 */
	private void setUpConfig() {
		/* setting configs */
		try {
			serverTCPPort = Integer.valueOf(configData.getString("tcp.port"));
			serverUDPPort =  Integer.valueOf(configData.getString("udp.port"));
			key_public_dir = configData.getString("keys.public.dir");
			server_key  = configData.getString("key");
			ca_host  = configData.getString("ca.host");
			ca_port =  Integer.valueOf(configData.getString("ca.port"));
			ca_certificate = configData.getString("ca.certificate");
			
			/*
			for(Provider s: Security.getProviders()) {
				System.out.println(s.toString());
			}*/
			
			/* set up private key */
			asym = new SecureChannelAsym();
			asym.setServerPrivateKey(server_key);

			
		} catch(Exception e) {
			//e.printStackTrace();
			System.err.println("[Server/Err]:  error while retriving config data  - possible that passphrase is wrong.");
			exitServer();
		}
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
