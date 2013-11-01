package server;

import interfaces.IFileServer;
import interfaces.INameServer;
import interfaces.IServer;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.Scanner;

import error.NoSuchServerFound;
import error.ServerNameAlreadyInUse;


public class FileServer{
	
	private static final long serialVersionUID = -7162822258501023279L;
	
	private String domain;
	private INameServer root;
	private Registry rmiReg;
	private Properties props;
	private String rootServerName;
	private IFileServer fs;
	private IServer stub_fs;
	
	/**
	 * CONSTRUCTOR
	 * @param domain
	 * @param path
	 */
	public FileServer(String domain, String path) {
		this.domain = domain;
		
		loadRegistryData();
		rootServerName = props.getProperty("ro_id");
		
		/* get registry */
		try {
			rmiReg = LocateRegistry.getRegistry(props.getProperty("registry.host"), Integer.valueOf(props.getProperty("registry.port")));
		} catch (Exception e) {
			System.err.println("[Server/Err/Fileserver]: Could not load registry rmi: "+e);
		}
		
		/* get root */
		try {
			root = (INameServer) rmiReg.lookup(rootServerName);
		
		} catch (Exception e) {
            System.err.println("Rootserver could not be looked up:");
            e.printStackTrace();
        }
		/* register */
		try {
			fs = new FileServerRMI(path);
			stub_fs = (IServer) UnicastRemoteObject.exportObject(fs, 0);
			root.register(stub_fs,domain);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (NoSuchServerFound e) {
			System.out.println(e);
			System.exit(0);
		} catch (ServerNameAlreadyInUse e) {
			System.out.println(e);
			System.exit(0);
		}
		
		System.out.println("Fileserver["+domain+"]: Hit enter to exit.");
		
		/* recognizes the enter */
		Scanner inScanner = new Scanner(System.in);
		inScanner.nextLine();
	
		exitServer();
	}
	
	
	
	/**
	 * Loads the users into a ResourceBundle
	 */
	private void loadRegistryData() {
		java.io.InputStream is = ClassLoader.getSystemResourceAsStream("registry.properties");
		if (is != null) {
			props = new java.util.Properties();
		try {
			props.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		} else {
			System.err.println("Properties file not found!");
		}
	}
	
	
	/**
	 * Shuts down the server
	 */
	public void exitServer() {
		try {
			UnicastRemoteObject.unexportObject(fs, true);
		} catch (NoSuchObjectException e) {
			System.out.println("Could not unexport");
		}
		try {
			root.unregister(domain);
		} catch (RemoteException e) {
			System.out.println(e);
		} catch (NoSuchServerFound e) {
			System.out.println(e);
		}
		
		System.out.println("Fileserver["+domain+"]: Server ending.");
		
		
		
		System.exit(0);
	}
}
