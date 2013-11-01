package bsp3;

import client.Client;

public class ClientConsoleStarter {

	/**
	 * @param args
	 */
public static void main(String[] args) {
		
		try{
			Client c = new Client();
		} catch(Exception e) {
			//System.out.println("Usage: ServerConsoleStarter");
			System.exit(0);
		}
	}

}
