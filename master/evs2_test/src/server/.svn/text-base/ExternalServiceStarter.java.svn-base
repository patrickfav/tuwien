package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExternalServiceStarter {
	public static void main(String[] arg0) throws Exception {
		
		ExternalServiceServer s = new ExternalServiceServer("127.0.0.1", 8080);
		s.startServer();
		
		System.out.println("\n=============================================");
		System.out.println("Services up. Hit enter to exit.");
		System.out.println("=============================================\n");
		
		// Wait for new line
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		stdIn.readLine();
		
		s.stopServer();
		
		System.out.println("\n=============================================");
		System.out.println("Services down.");
	}
}
