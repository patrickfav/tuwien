package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.regex.Pattern;

import entities.User;


public class ServerUDPUserThread extends Thread{
	
	private DatagramPacket packet;
	private DatagramSocket udpSocket;
	private String udpStringData;
	private String cmd;
	private String[] inputArray;
	private String list;
	private InetAddress address;
	private Integer clientPort;
	
	/**
	 * CONSTRUCTOR 
	 */
	public ServerUDPUserThread(DatagramPacket packet,DatagramSocket udpSocket) {
		this.packet = packet;
		this.udpSocket = udpSocket;
	}
	
	/**
	 * Start processing packets (UDP)
	 */
	public void run() {
		if(packet != null) {
			/* save the recipient */
			address = packet.getAddress();
			clientPort = packet.getPort();
			
			/* debug */
			//System.out.println("[Server]: UDP Packet received (Port: "+clientPort+"/Addr: "+address+"): "+new String(packet.getData(),0,packet.getLength()));
			
			udpStringData  = new String(packet.getData(),0,packet.getLength());
			
			/* Split input with regex and save in array */
			Pattern p = Pattern.compile(" ");
			inputArray = p.split(udpStringData);
			
			cmd = inputArray[0];
			
			/* command switch */
			if(cmd.equals("!list")) {
				list = new String();
				list += "Online Users: \n";
				
				/* cycle for through the users */
				for(User user :Server.usersTable.values()) {
					if(user.getOnline())
						list += "    * "+user.getName()+"\n";
				}
				/* debug */
				//System.out.println("sending list: "+list);
				
				if(list.equals("Online Users: \n"))
					list = "No users are online.";
				
				sendUDP(list);
				
			} else if(cmd.equals("!info")) {
				try {
					/* debug */
					//System.out.println("In info");
					if(Server.usersTable.containsKey(inputArray[1])) {
						if(Server.usersTable.get(inputArray[1]).getOnline()) {
							sendUDP(inputArray[1]+" is online since "+Server.usersTable.get(inputArray[1]).getUpdate().toString());
						} else {
							sendUDP(inputArray[1]+" is offline since "+Server.usersTable.get(inputArray[1]).getUpdate().toString());
						}
					} else {
						sendUDP("There is no such user as "+inputArray[1]+".");
					}
					
					
				} catch(Exception e) {
					sendUDP("Unknown requset");
				}	
			} else if(cmd.equals("Unknown request")) {
				/* do nothing */
			} else {
				System.out.println("unknown");
				sendUDP("Unknown request");
			}
		}
	}
	
	/**
	 * Sends a msg through udp. Needs the adress to be set.
	 * @param msg
	 */
	private void sendUDP(String msg) {

		byte[] data = msg.getBytes(); 
		packet = new DatagramPacket( data, data.length, address, clientPort );
		/* debug */
		//System.out.println("Sending Server Port: "+clientPort+" InetAdrr: "+address.getHostAddress()+" Data: "+new String(data));
		
		try {
			udpSocket.send( packet );
		} catch (IOException e) {
			System.err.println("[Server/err]: Could not send Datagram Data.");
		}
	}
}
