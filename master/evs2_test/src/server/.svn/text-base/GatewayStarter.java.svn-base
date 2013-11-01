package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GatewayStarter {
	
	public static void main(String[] arg0) throws Exception {
		
		GatewayServer s1 = new GatewayServer("127.0.0.1", 9000, "http://127.0.0.1:9010/");
		s1.startServer();
		
		GatewayServer s2 = new GatewayServer("127.0.0.1", 9100, "http://127.0.0.1:9110/");
		s2.startServer();
		
		System.out.println("\n=============================================");
		System.out.println("Services up. Hit enter to exit.");
		System.out.println("=============================================\n");
		
		// Wait for new line
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		stdIn.readLine();
		
		s1.stopServer();
		s2.stopServer();
		
		System.out.println("\n=============================================");
		System.out.println("Services down.");
	}
}
