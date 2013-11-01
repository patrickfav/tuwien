package bsp2;


import server.FileServer;

public class FileServerStarter {
	public static void main(String[] args) {
			try {
				FileServer fs = new FileServer(args[0],args[1]);
			} catch (ClassCastException ecc) {
				System.err.println("Cannot add a sub zone to a fileserver");
				System.exit(0);
			} catch(Exception e) {
				System.err.println("Error due wrong commandos: "+e);
				System.exit(0);
			}

	}
}
