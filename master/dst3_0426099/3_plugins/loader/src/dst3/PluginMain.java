package dst3;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import dst3.dynload.IPluginExecutor;
import dst3.dynload.PluginExecutor;

public class PluginMain {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		
		IPluginExecutor pe = new PluginExecutor();
		
		
		
		pe.monitor(new File("C:\\Users\\PatrickF\\eclipse workspaces\\master workspace\\dst3_0426099\\3_plugins\\loader\\plugins"));
		pe.start();
		
		System.out.println("\nHit Enter to continue");
		
		stdIn.readLine();
		
		pe.stop();
	}

}
