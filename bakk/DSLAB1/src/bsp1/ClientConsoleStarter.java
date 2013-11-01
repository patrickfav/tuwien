package bsp1;

import client.Client;

public class ClientConsoleStarter {

	/**
	 * @param args
	 */
public static void main(String[] args) {
		
		try{
			Client c = new Client(Integer.valueOf(args[0]).intValue(),args[1],Integer.valueOf(args[2]).intValue(),Integer.valueOf(args[3]).intValue());
		} catch(Exception e) {
			System.out.println("Usage: ServerConsoleStarter ClientUDPPort ServerHost ServerTCPPort ServerUDPPort");
			System.err.println("[ClientConsoleStarter]: Error while retrieving the arguments:"+e);
			System.exit(0);
		}
	}

}
