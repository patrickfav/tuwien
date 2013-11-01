package server;

import indexing.entities.lookup.Location;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MetaServiceStarter {
	public static void main(String[] arg0) throws Exception {
		
		MetaServiceServer s = new MetaServiceServer("127.0.0.1", 8181);
		MetaServiceServer.getLocations().add(new Location("http://localhost:9000","keys/location1.pub.pem"));
		MetaServiceServer.getLocations().add(new Location("http://localhost:9100","keys/location2.pub.pem"));
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
