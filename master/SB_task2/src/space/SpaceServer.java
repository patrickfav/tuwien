package space;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SpaceServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpaceManager.setMode(SpaceManager.ACCESS_TYPE_SERVER);
		SpaceManager man = SpaceManager.getInstance();
		
		// Wait for new line
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			stdIn.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		man.shutdown();
		
		System.out.println("=============================================");
		System.out.println("SpaceServer down.");

	}

}
