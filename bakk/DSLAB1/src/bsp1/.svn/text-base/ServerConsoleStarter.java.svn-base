package bsp1;

import server.Server;

public class ServerConsoleStarter {
	public static void main(String[] args) {
		
		try{
			Server s = new Server(Integer.valueOf(args[0]).intValue(),Integer.valueOf(args[1]).intValue());
		} catch(Exception e) {
			System.out.println("Usage: ServerConsoleStarter TCPPort UDPPort");
			System.err.println("[ServerConsoleStarter]: Error while retrieving the arguments:"+e);
			System.exit(0);
		}
	}
}
