package server;

import interfaces.IFileServer;
import interfaces.INameServer;
import interfaces.IServer;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import entities.CustomURL;
import error.CouldNotCreateNewServer;
import error.NoSuchServerFound;
import error.ServerNameAlreadyInUse;

public class NameServerRMI implements INameServer{
	

	//Nameserver vars
	private ConcurrentHashMap<String,IServer> children;
	private static final long serialVersionUID = -4550894504937635447L;
	
	/**
	 * CONSTRUCTOR
	 * Nameserver
	 * @throws ServerNameAlreadyInUse 
	 * @throws NoSuchServerFound 
	 */
	public NameServerRMI() {
		children = new ConcurrentHashMap<String,IServer>();
	}
	
	/**
	 * Registeres recursivly a Nameserver in the zone. Its similar to a tree
	 */
	@Override
	public INameServer register(IServer ns, String domain) throws RemoteException,NoSuchServerFound,ServerNameAlreadyInUse {
		
		CustomURL url = new CustomURL(domain);
		List<String> urlList = url.getDomain();
		
		if(urlList.size() <= 0) { /* error - no domain given */
			throw new NoSuchServerFound("While registering: not enough nodenames");
		} else if(urlList.size() == 1) { /* last node, create in registry */
			try {
	            this.setChild(urlList.get(0), ns);
	            System.out.println(now()+": Registering "+urlList.get(0)+".");
	            return this;
			} catch (ServerNameAlreadyInUse e) {
				throw new ServerNameAlreadyInUse(e.toString());
	        } catch (Exception e) {
	            System.err.println("Server exception:");
	            e.printStackTrace();
	            exitServer();
	        }
		} else if(urlList.size() > 1) { /* go recursivly on */
			
			try {
				INameServer child = (INameServer) this.getChild(urlList.get(urlList.size()-1));
				urlList.remove(urlList.size()-1);
				CustomURL newDomain = new CustomURL(urlList,"");		
				//System.out.println(this+" -> "+child);
				child.register(ns, newDomain.getDomainToString());
			} catch(NoSuchServerFound ne) {
				throw new NoSuchServerFound("While registering, "+urlList.get(urlList.size()-1)+" could not be found");
			} catch (ClassCastException ecc) {
				throw new NoSuchServerFound("FileServer cannot have sub zones.");
			}
		}
		
		return null;
	}
	
	/**
	 * Similiar algo as register, but unregisters the given domain
	 */
	@Override
	public void unregister(String name) throws RemoteException,NoSuchServerFound{
		CustomURL url = new CustomURL(name);
		List<String> urlList = url.getDomain();
		
		if(urlList.size() <= 0) { /* error - no domain given */
			throw new NoSuchServerFound("While unregistering: not enough nodenames");
		} else if(urlList.size() == 1) { /* last node, create in registry */
			try {
				
	            this.removeChild(urlList.get(0));
	            
	            System.out.println(now()+": Unregister "+urlList.get(0));
			} catch(NoSuchServerFound ne) {
				throw new NoSuchServerFound("Unregister: could not remove");
	        } catch (Exception e) {
	            System.err.println("Server remove exception:");
	            e.printStackTrace();
	            exitServer();
	        }
		} else if(urlList.size() > 1) { /* go recursivly on */
			try {
				INameServer child = (INameServer) this.getChild(urlList.get(urlList.size()-1));
				urlList.remove(urlList.size()-1);
				CustomURL newDomain = new CustomURL(urlList,"");		
				child.unregister(newDomain.getDomainToString());
			} catch(NoSuchServerFound ne) {
				throw new NoSuchServerFound("While unregistering, "+urlList.get(urlList.size()-1)+" could not be found");
			} 
		}
		
	}
	
	/**
	 * Sets in the specific server a new child-node / child-server
	 */
	@Override
	public void setChild(String name, IServer ns) throws RemoteException,CouldNotCreateNewServer,ServerNameAlreadyInUse {
		
		if(children.containsKey(name))
			throw new ServerNameAlreadyInUse(name+" already in server list.");
		
		try {
			children.put(name, ns);
			//System.out.println("In setchil - ok "+name);
		} catch(Exception e) {
			throw new CouldNotCreateNewServer("Server "+name+" could not be created: "+e);
		}
	}
	
	/**
	 * Gets the node with the given name. Throws exception if not found
	 */
	@Override
	public IServer getChild(String name) throws RemoteException,NoSuchServerFound {
		//System.out.println(name+" in getChild");
		if(children.containsKey(name)) {
			//System.out.println(name+" looked up in NS and found");
			if(children.get(name) instanceof IFileServer)
				System.out.println(now()+": Fileserver "+name+" requested from client.");
			
			return children.get(name);
		} else {
			//System.out.println(name+" not found for any reason");
			throw new NoSuchServerFound(name+" could not be found.");
		}
	}
	
	/**
	 * Removes the child.
	 */
	@Override
	public void removeChild(String name)  throws RemoteException,NoSuchServerFound {
		try {
			children.remove(name);
		} catch(Exception e) {
			throw new NoSuchServerFound("Server "+name+" could not be removed "+e);
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
		System.exit(0);
	}
}
