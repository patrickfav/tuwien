package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import channel.SecureChannelAsym;
import channel.SecureChannelSym;



public class Client {
	
	private Scanner inScanner;
	
	/* secure channels */
	public static SecureChannelSym secSym;
	public static SecureChannelAsym secAsym;
	
	/*
	 * Network communication
	 */
	private Socket socket = null;
	private PrintWriter socketOut = null;
	private BufferedReader socketIn = null;
	private DatagramPacket packet;
	private DatagramSocket udpSocket;
	
	/*
	 * Threads
	 */
	private ClientThread listenerThread;
	private ClientThread udpListenerThread;
	
	/*
	 * Input placeholder Strings
	 */
	private String input;
	private String[] inputArray;
	private String cmd;
	
	/*
	 * Internal 
	 */
	private Boolean clientActive;
	private Boolean clientLoggedIn;
	private ResourceBundle configData;
	private String dataPath = "client";
	
	
	
	/*
	 * SETTINGS
	 */
	//private Integer clientUDPPort;
	private String serverHost;
	private Integer serverTCPPort;
	private Integer serverUDPPort;
	public static String key_public_dir;
	private String key_private_dir;
	private String server_key;
	private String ca_host;
	private Integer ca_port;
	private String ca_certificate;
	
	
	/**
	 * CONSTRUCTOR 
	 * 
	 */
	
	public Client() {
		
		initalizeClient();
		showWelcome();
		
		/* start threads */
		listenerThread = new ClientTCP(socket);
		udpListenerThread = new ClientUDP(udpSocket);
		
		udpListenerThread.start();
		listenerThread.start();
		
		handleInput();
		
		exit();
	}
	
	
	/**
	 * Initialising of the basic network stuff 
	 * 
	 */
	private void initalizeClient() {
		
		/*load config resourcebundle */
		loadConfigData();
		
		/* setting configs */
		try {
			serverHost = configData.getString("server.host");
			serverTCPPort = Integer.valueOf(configData.getString("server.tcp.port"));
			serverUDPPort =  Integer.valueOf(configData.getString("server.udp.port"));
			key_public_dir = configData.getString("keys.public.dir");
			key_private_dir  = configData.getString("keys.private.dir");
			server_key  = configData.getString("server.key");
			ca_host  = configData.getString("ca.host");
			ca_port =  Integer.valueOf(configData.getString("ca.port"));
			ca_certificate = configData.getString("ca.certificate");
		} catch(Exception e) {
			System.err.println("error while retriving config data  " + e);
			exit();
		}
		
		
		/* Initialising datamembers*/
		inScanner = new Scanner(System.in);
		inputArray = new String[1];
		clientActive = new Boolean(true);
		clientLoggedIn = new Boolean(false);
		
		/* security */
		secSym = new SecureChannelSym();
		secAsym = new SecureChannelAsym();
		
		/* initializing sockets */
		try {
			socket = new Socket(serverHost, serverTCPPort);
			socketOut = new PrintWriter(socket.getOutputStream(), true);
			
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + serverHost);
			exit();
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: " + serverHost);
			exit();
		}
		
		try {
			udpSocket = null;
			udpSocket = new DatagramSocket();
		} catch (SocketException e) {
			System.err.println("Couldn't get Datagram Socket: ");
			exit();
		}
		
	}
	
	/**
	 * Shows a simple welcome
	 */
	private void showWelcome() {
		System.out.println("Client started.");
	}
	
	/**
	 * Awaits the user input and handles it depending on 
	 * the type of command.
	 * 
	 * @param whole unformatted input
	 */
	private void handleInput() {
		
		/* FIRST STEP OF ATHENTIFICATION */
		while(clientActive) {
			/* Split input with regex and save in array */
			Pattern p = Pattern.compile(" ");
			inputArray = p.split(inScanner.nextLine());
			
			/* reading command */
			try{
				cmd = inputArray[0];
			} catch (Exception e) {
				System.out.println("Error while reading input");
			}
			
			/* if login */
			try {
				if(cmd.equals("!login") && inputArray[1] != null && !inputArray[1].equals("")) {
					try {
						secAsym.setServerPublicKey(server_key);
						secAsym.setClientPrivateKey(key_private_dir+"/"+inputArray[1]+".pem");
						//System.out.println(key_private_dir+"/"+inputArray[1]+".pem");
						secAsym.generateClientChallenge();
		
						socketOut.println(secAsym.encryptRSAWithServerPublicKey(cmd+" "+inputArray[1]+" "+secAsym.getBase64ClientChallenge()));
						break;
					}
					catch (Exception e) {
						System.err.println("[Client/Err]: Cannot login "+inputArray[1]+". User does not have a key or does not exist, or passphrase is wrong.");
					}
				} else if(cmd.equals("!end")){
					exit();
				} else {
					System.out.println("Please login first.");
				}
			} catch (Exception e) {
				System.err.println("[Client/Err]: no user selected");
			}
		} 
		
		/* iv and key filled, read to send */
		while(!secSym.ready()) {}
		
		while(clientActive) {
			
			/* Split input with regex and save in array */
			Pattern p = Pattern.compile(" ");
			String input = inScanner.nextLine();
			inputArray = p.split(input);
			
			/* reading command */
			try{
				cmd = inputArray[0];
			} catch (Exception e) {
				System.out.println("Error while reading input");
			}
			
			/* the command switch */
			if(cmd.equals("!end")) { /* ends and if needed logs out */
				System.out.println("Client closing.");
				exit();
			} else if(cmd.equals("!info") || cmd.equals("!list")) { /* sending this through udp */
				try {
					byte[] data = input.getBytes(); 
					packet = new DatagramPacket( data, data.length, InetAddress.getByName(serverHost), serverUDPPort );
					//System.out.println("Sending Client Port: "+serverUDPPort+" InetAdrr: "+InetAddress.getByName(serverHost).getHostAddress()+" Data: "+new String(data));
					udpSocket.send( packet );
				} catch (IOException e) {
					System.err.println("Could not send Datagram Data.");
				}
			} else if(cmd.equals("!msg")) {
				try {
					String text = Client.specialConcatStringArray(Arrays.copyOfRange(inputArray, 2, inputArray.length)).trim();
					String private_msg = cmd + " " +inputArray[1]+" ";
					private_msg += secAsym.signWithClientPrivateKeyAndBase64(text)+" "+text;
					
					//System.out.println(private_msg);
					
					socketOut.println(secSym.encryptAES(private_msg));
				} catch(Exception e) {
					System.out.println("Error while sending private message.");
				}
				
			} else { /* sends the message */
				try {
					socketOut.println(secSym.encryptAES(input));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
	}
	
	/**
	 * Loads the config into a ResourceBundle
	 */
	private void loadConfigData() {
		try {
			configData = ResourceBundle.getBundle(dataPath);
		} catch (Exception e) {
			System.err.println("[Client/Err]: Could not load config properties: "+e);
			exit();
		}
		//System.out.println("[Server]: Users loaded successfully.");
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
	 * Exits the program and releases all resources.
	 */
	private void exit() {
		clientActive = false;
		
		try {
			inScanner.close();
			socketOut.close();
			listenerThread.exitThread();
			udpListenerThread.exitThread();	
		} catch (Exception e) {
			System.err.println("Client: Error while closing client: "+e);
		}
		
		
	}
}

