package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *  Thread for listening to
 *  the server and printing it out in 
 *  the console.
 */

public class ClientTCP extends ClientThread {
	
	private BufferedReader socketIn = null;
	private Socket socket = null;
	private Boolean active;
	
	public ClientTCP(Socket socket) {
		this.socket = socket;
		this.active = true;
	}
	
	@Override
	public void run() {
		String buffer;
		
		try {
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e1) {
			System.err.println("Couldn't get I/O for the connection to: " + e1);
		}
		
		try {
			while(active && (buffer = socketIn.readLine()) != null) {
				System.out.println(buffer);
			}
			
			exitThread();
			
		} catch (IOException e) {
			/* do nothing */
			//System.err.println("Error while sending to socket: " + e);
			//e.printStackTrace();
		}
	}
	
	/**
	 * Closes the TCP Socket
	 */
	public void exitThread() {
		active = false;
		try {
			socketIn.close();
			socket.close();
		} catch (IOException e) {
			System.err.println("[Client] Connection could not be closed: " + e);
		}
	}
}
