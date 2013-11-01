package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


/**
 * 
 * The Thread thats responsible for UDP
 * @author PatrickF
 *
 */

public class ServerUDPThread extends ServerThread {
	
	
	private DatagramPacket packet;
	private DatagramSocket udpSocket;
	private Integer udpPort;
	private Boolean active;
	
	/**
	 * CONSTRUCTOR 
	 * @param udpPort
	 */
	public ServerUDPThread(Integer udpPort) {
		try {
			this.udpPort = udpPort;
			udpSocket = new DatagramSocket(this.udpPort);
		} catch (SocketException e) {
			System.err.println("[Server/err]: Could not create Datagram Socket.");
		} 
		active = true;
	}
	
	/**
	 * Start accepting Datagram Packets (UDP)
	 */
	public void run() {
		while(active) {
			try {
				/* debug */
				//System.out.println("[Server]: UDP Thread loop started");
				
				/* receive a udp packet */
				packet = new DatagramPacket( new byte[1024], 1024 ); 
				udpSocket.receive( packet );
				
				Server.pool.execute(new ServerUDPUserThread(packet,udpSocket));
				
			} catch (IOException e) {
				//System.err.println("[Server/err]: Couldn't create Datagrampacket.");
				exitThread();
			}
		}
	}
	
	/**
	 * Closes the server socket
	 */
	public void exitThread() {
		active = false;
		udpSocket.close();
		
	}
}