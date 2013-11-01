package server;

import interfaces.INameServer;
import interfaces.IServer;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Scanner;

import error.NoSuchServerFound;
import error.ServerNameAlreadyInUse;



public class NameServer {
	
	//serial id
	private static final long serialVersionUID = -4758218675420771271L;
	
	//Config
	private final Boolean setSecurityPolicy = false;
	
	//Nameserver vars
	private INameServer root;
	private String rootServerName;
	private Properties props;
	private Registry rmiReg;
	private INameServer ns;
	private IServer stub_ns;
	
	/**
	 * CONSTRUCTOR
	 * Nameserver
	 * @throws ServerNameAlreadyInUse 
	 * @throws NoSuchServerFound 
	 */
	public NameServer(String domain) {
		initialize();
		
		/* get registry */
		try {
			rmiReg = LocateRegistry.getRegistry(props.getProperty("registry.host"), Integer.valueOf(props.getProperty("registry.port")));
		} catch (Exception e) {
			System.err.println("[Server/Err/Nameserver]: Could not load registry rmi: "+e);
			exitServer();
		}

		
		/* get root server */
		try {
			root = (INameServer) rmiReg.lookup(rootServerName);
		} catch (Exception e) {
            System.err.println("Rootserver could not be looked up");
            e.printStackTrace();
        }
		
		/* try to register nameserver */
		try {
			ns = new NameServerRMI();
			stub_ns = (IServer) UnicastRemoteObject.exportObject(ns, 0);
			root.register(stub_ns,domain.toLowerCase());
		} catch (RemoteException e) {
			e.printStackTrace();
			exitServer();
		} catch (NoSuchServerFound e) {
			System.out.println(e);
			exitServer();
		} catch (ServerNameAlreadyInUse e) {
			System.out.println(e);
			exitServer();
		}
		
		System.out.println("Nameserver["+domain+"]: Hit enter to exit.");
		
		/* recognizes the enter */
		Scanner inScanner = new Scanner(System.in);
		inScanner.nextLine();
		
		System.out.println("Nameserver["+domain+"]: Server ending.");
		
		exitServer();
	}
	
	/**
	 * CONSTRUTOR
	 * Rootserver
	 */
	public NameServer() {
		
		initialize();
		
		/* create registry */
		try {
			rmiReg = LocateRegistry.createRegistry(Integer.valueOf(props.getProperty("registry.port")));
		} catch (Exception e) {
			System.err.println("[Server/Err/Rootserver]: Could not load registry rmi: "+e);
			exitRootServer();
		}
		
		/* create an bind root server */
        try {
        	ns = new NameServerRMI();
            stub_ns = (IServer) UnicastRemoteObject.exportObject(ns, 0);
            rmiReg.rebind(rootServerName, stub_ns);
            System.out.println(now()+": RootServer bound");
        } catch (Exception e) {
            System.err.println("RootServer could not be found.");
            exitRootServer();
        }
        
        System.out.println(now()+": Hit enter to exit.");
        
        /* recognizes the enter */
		Scanner inScanner = new Scanner(System.in);
		inScanner.nextLine();
		
		System.out.println(now()+" Rootserver: Server ending.");
		
		exitRootServer();
	}
	
	
	/**
	 * Initializes the server (root and nameserver)
	 */
	private void initialize() {
		loadRegistryData();
		rootServerName = props.getProperty("ro_id");
		
		if(setSecurityPolicy) {
			System.setProperty("java.security.policy", "./policy.policy");
		
			if (System.getSecurityManager() == null) {
	            System.setSecurityManager(new RMISecurityManager());
	        }
		} else {
			System.setSecurityManager(null);
		}
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
	 * Helper method: returns the actual time for the log
	 * @return
	 */
	private static String now() {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    return sdf.format(cal.getTime());

	 }
	
	
	/**
	 * Shuts down and unexports the server object
	 */
	private void exitServer() {
	
		try {
			UnicastRemoteObject.unexportObject(ns, true);
		} catch (NoSuchObjectException e) {
			System.out.println("Could not unexport");
		}
		
		System.exit(0);
	}
	
	private void exitRootServer() {
		
		if(rmiReg != null)
			try {
				UnicastRemoteObject.unexportObject(ns, true);
				rmiReg.unbind(rootServerName);
				UnicastRemoteObject.unexportObject(rmiReg, true);
			} catch (NoSuchObjectException e) {
				System.out.println("Could not unexport");
			} catch (Exception e) {
				System.out.println("Could not unbind or unexport root");
			}
		
		System.exit(0);
	}
}
