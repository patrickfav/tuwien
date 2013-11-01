package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import server.listener.CommandListener;
import server.listener.PacmanListener;
import space.SpaceManager;
import space.dao.ServerDao;

public class ServerStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpaceManager.setMode(SpaceManager.ACCESS_TYPE_SERVER);
		SpaceManager man = SpaceManager.getInstance();
		
		
		ServerDao sDao = new ServerDao();
		sDao.registerCommandListener(new CommandListener(sDao));
		sDao.registerMovementListener(new PacmanListener(sDao));
		
		System.out.println("\n=============================================");
		System.out.println("SERVER UP AND RUNNING\n");
		
		// Wait for new line
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			stdIn.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sDao.deleteAllNotificationListener();
		
		man.shutdown();
		
		System.out.println("=============================================");
		System.out.println("SpaceServer down.");
		
		
		System.exit(0);

	}

}
