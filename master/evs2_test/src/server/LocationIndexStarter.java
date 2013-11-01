package server;

import indexing.entities.lookup.Storage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LocationIndexStarter {
	public static void main(String[] arg0) throws Exception {
		
		LocationIndexServer s1 = new LocationIndexServer("127.0.0.1", 9010);
		List<Storage> storages1 = new ArrayList<Storage>();
		storages1.add(new Storage("http://localhost:9011"));
		storages1.add(new Storage("http://localhost:9012"));
		s1.setStorages(storages1);
		s1.startServer();
		
		LocationIndexServer s2 = new LocationIndexServer("127.0.0.1", 9110);
		List<Storage> storages2 = new ArrayList<Storage>();
		storages2.add(new Storage("http://localhost:9111"));
		storages2.add(new Storage("http://localhost:9112"));
		s2.setStorages(storages2);
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
