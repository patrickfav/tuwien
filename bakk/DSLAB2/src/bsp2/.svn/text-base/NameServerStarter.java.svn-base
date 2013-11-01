package bsp2;


import server.NameServer;

public class NameServerStarter {
	public static void main(String[] args) {
			try {
				NameServer ns = new NameServer(args[0]);
			} catch (ClassCastException ecc) {
				System.err.println("Cannot add a sub zone to a fileserver");
				System.exit(0);
			} catch(Exception e) {
				System.err.println("Error due wrong commandos: "+e);
				System.exit(0);
			}

	}
}
