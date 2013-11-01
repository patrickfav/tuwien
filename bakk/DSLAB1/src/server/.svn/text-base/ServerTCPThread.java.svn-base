package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;


public class ServerTCPThread extends ServerThread {
	
	private final ServerSocket serverSocket;
    private ServerTCPUserThread tempThread;
	
	
    /**
     * CONSTURCTOR
     * 
     * @param tcpPort
     * @param userBundle
     * @throws IOException
     */
	public ServerTCPThread(Integer tcpPort) throws IOException{
		serverSocket = new ServerSocket(tcpPort);
		Server.threadList = new ArrayList<ServerTCPUserThread>(Server.userData.keySet().size());
	}
	
	/**
	 * Start of the Thread: accepts Sockets and
	 * opens a new Thread for every accepted user
	 */
	@Override
	public void run() {
		try {
			while(true) {
				tempThread = new ServerTCPUserThread(serverSocket.accept());
				Server.threadList.add(tempThread);
				Server.pool.execute(tempThread);
				
	        }
		} catch (IOException ex) {
	       //System.err.println("[Server/err]["+Thread.currentThread().getId()+"]: Error while trying to accept new Connection");
			exitThread();
		}
	}
	
	/**
	 * Closes the server socket
	 */
	public void exitThread() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("[Server/err]["+Thread.currentThread().getId()+"]: Error while trying to close serversocket.");
		}
	}
}