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
import java.util.Scanner;
import java.util.regex.Pattern;

public class Client {
	
	private Scanner inScanner;
	
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
	
	/*
	 * SETTINGS
	 */
	private Integer clientUDPPort;
	private String serverHost;
	private Integer serverTCPPort;
	private Integer serverUDPPort;
	
	/**
	 * CONSTRUCTOR 
	 * 
	 * @param clientUDPPort
	 * @param serverHost
	 * @param serverTCPPort
	 * @param serverUDPPort
	 */
	
	public Client(Integer clientUDPPort, String serverHost, Integer serverTCPPort, Integer serverUDPPort) {
		/* setting up */
		this.clientUDPPort = clientUDPPort;
		this.serverHost = serverHost;
		this.serverTCPPort = serverTCPPort;
		this.serverUDPPort = serverUDPPort;
		
		initalizeClient();
		showWelcome();
		
		/* start threads */
		listenerThread = new ClientTCP(socket);
		udpListenerThread = new ClientUDP(udpSocket);
		
		udpListenerThread.start();
		listenerThread.start();
		
		while(clientActive) {
			try{
				input = inScanner.nextLine();
				
			} catch(Exception e) {
				System.err.println("Error occured during reading the input.");
			}
			
			handleInput(input);
		}
		
		exit();
	}
	
	
	/**
	 * Initialising of the basic network stuff 
	 * 
	 */
	private void initalizeClient() {
		
		/* Initialising datamembers*/
		inScanner = new Scanner(System.in);
		inputArray = new String[1];
		clientActive = new Boolean(true);
		
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
			udpSocket = new DatagramSocket(clientUDPPort);
		} catch (SocketException e) {
			System.err.println("Couldn't get Datagram Socket: " + clientUDPPort);
			exit();
		}
		
	}
	
	/**
	 * Shows a simple welcome
	 */
	private void showWelcome() {
		System.out.println("Client started. Login or type info/list");
	}
	
	/**
	 * Awaits the user input and handles it depending on 
	 * the type of command.
	 * 
	 * @param whole unformatted input
	 */
	private void handleInput(String input) {
		/* Split input with regex and save in array */
		Pattern p = Pattern.compile(" ");
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
		} else { /* sends the message */
			
			socketOut.println(input);
		}
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

