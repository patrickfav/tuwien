package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import security.SecurityUtil;
import server.RestServer;
import testdata.TestData;


public class StorageStarter {
	
	public static void main(String[] arg0) throws Exception {
		
		Map<InetAddress, PublicKey> clientKeys = new HashMap<InetAddress, PublicKey>();
		clientKeys.put(InetAddress.getByName("127.0.0.1"), SecurityUtil.getPublicKey("keys/bill.pub.pem"));
		
		
		RestServer s1 = new RestServer("127.0.0.1", 9011, clientKeys);
		s1.startServer();
		
		RestServer s2 = new RestServer("127.0.0.1", 9012, clientKeys);
		s2.startServer();
		
		RestServer s3 = new RestServer("127.0.0.1", 9111, clientKeys);
		s3.startServer();
		
		RestServer s4 = new RestServer("127.0.0.1", 9112, clientKeys);
		s4.startServer();
		
		/* import some testdata */
		@SuppressWarnings("unused")
		TestData t = new TestData();
		
		System.out.println("\n=============================================");
		System.out.println("Services up. Hit enter to exit.");
		System.out.println("=============================================\n");
		
		// Wait for new line
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		stdIn.readLine();
		
		s1.stopServer();
		s2.stopServer();
		s3.stopServer();
		s4.stopServer();
		
		System.out.println("\n=============================================");
		System.out.println("Services down.");
	}
}
