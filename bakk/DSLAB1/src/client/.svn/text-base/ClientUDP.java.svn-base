package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


/**
 * Like ClientTCP but listens to UDP
 * 
 */
public class ClientUDP extends ClientThread {
	
	private DatagramSocket udpSocket;
	private DatagramPacket packet;
	
	public ClientUDP(DatagramSocket udpSocket) {
		this.udpSocket = udpSocket;
	}
	
	@Override
	public void run() {
		while(true) {
		
			try {	
				//DatagramSocket tempsocket = new DatagramSocket(clientUDPPort);
				/* receive a udp packet */
				//packet = null;
				packet = new DatagramPacket( new byte[1024], 1024 ); 
				udpSocket.receive( packet );
				
				/* printing out udp packet */
				System.out.println(new String(packet.getData(),0,packet.getLength()));
				//System.out.println("[Client]: UDP Packet received (Port: "+packet.getPort()+"/Addr: "+packet.getAddress()+"): "+new String(packet.getData(),0,packet.getLength()));
				
				
				
			} catch (IOException e) {
				//System.err.println("Couldn't create Datagrampacket while listening.");
				exitThread();
				break;
			}
		}
	}
	
	public void exitThread() {
		udpSocket.close();
	}
}
