package client;

import interfaces.IFileServer;
import interfaces.INameServer;
import interfaces.IServer;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import entities.CustomURL;
import error.FileServerFileNotFound;
import error.NoSuchServerFound;



public class Client {
	
	private static Registry rmiReg;
	private Scanner inScanner;
	private String input;
	private Boolean clientActive;
	private CustomURL url;
	private Properties props;
	private INameServer root;
	private String rootServerName;
	private HashMap<String,IServer> cache;
	private IFileServer currentFS;
	private Boolean nsFoundInCache;
	private Boolean fsFoundInCache;
	private Integer iterator;
	private Boolean err;
	
	public Client() {
		
		initialize();
		
		System.out.println("Type in the url. Typ !EXIT to quit.");
		
		while(clientActive) {

			try{
				input = inScanner.nextLine();
				
			} catch(Exception e) {
				System.err.println("Error occured during reading the input.");
			}
			
			/* exit status */
			if(input.equals("!EXIT")) {
				clientActive = false;
				System.out.println("Client shutting down.");
				break;
			}
			
			currentFS = null;
			url =  new CustomURL(input.toLowerCase());
			nsFoundInCache = false;
			fsFoundInCache = false;
			iterator = 0;
			err = false;
			
			List<String> cacheCheckUrl = url.cloneDomainList();
			INameServer next = null;
			IServer cacheServer = null;
			String domain = new String();
			CustomURL tempURL= url;
			Integer found_max_url = url.getDomain().size();
			String cachedURL = new String();
			
			/* check cache */
			for(int j=0; j<(url.getDomain().size());j++) {
				
				tempURL = new CustomURL(cacheCheckUrl,url.getDomainToString());
				
				//System.out.println(tempURL.getReverseDomainToString()+" "+j+"/"+url.getDomain().size());
				
				if(cache.containsKey(tempURL.getReverseDomainToString())) {
					cacheServer = cache.get(tempURL.getReverseDomainToString());
					cachedURL = tempURL.getReverseDomainToString();
					
					if(j==0) {
						fsFoundInCache = true;
					} else {
						nsFoundInCache = true;
					}
					
					//System.out.println("found");
					break;
				} else {
					cacheCheckUrl.remove(0);
					found_max_url--;
					//System.out.println("not found");
				}
			}
			
			//System.out.println("found_max_url: "+found_max_url);
			
			/* if fs found, use it */
			if(fsFoundInCache) {
				try {
					currentFS = (IFileServer) cacheServer;
				} catch (ClassCastException ecc) {
						System.out.println(input+" is not a Fileserver.");	
						err=true;
				} 
			} else if(!err){
				
				if (nsFoundInCache) { /* else if "only" a nameserver use additional info */
					next = (INameServer) cacheServer;
					//System.out.println("take from cache:"+tempURL.getDomainToString());
					domain = cachedURL;
				} else {
					next = root;
				}

				/* find node */
				try {
					for(int i=(url.getDomain().size()-1-found_max_url); i>-1;i--) {
						
						/* get first node from root, skip if first child has been found */
						if(i==0) {
							//System.out.println("Fileserver found: - domain so far "+domain+" - "+url.getDomain().get(i)+" - "+i);

							currentFS = (IFileServer) next.getChild(url.getDomain().get(i));
							
							if(0==(url.getDomain().size()-1-found_max_url) && !nsFoundInCache) {
								domain += url.getDomain().get(i);
							} else {
								domain += "."+url.getDomain().get(i);
							}
							cache.put(domain, currentFS);
							
						} else {
							next = (INameServer) next.getChild(url.getDomain().get(i));
							
							if(i==(url.getDomain().size()-1-found_max_url)&& !nsFoundInCache) {
								domain += url.getDomain().get(i);
							} else {
								domain += "."+url.getDomain().get(i);
							}
							cache.put(domain, next);
							//System.out.println("NameServer found: - domain so far "+domain);
						}
						iterator++;
	
					}
				} catch (ClassCastException ecc) {
					System.out.println(input+" is not a Fileserver.");	
					err=true;
				} catch (NoSuchServerFound ne) {
					System.out.println(ne);	
					err=true;
				} /*catch (NullPointerException ne) {
					System.out.println(input+" does not exist.");	
					err=true;
				}*/ catch (Exception e) {
					 System.err.println("RemoteError while retriving node.");
			         e.printStackTrace();
			         err=true;
				}
			}
			if(!err) {
				System.out.println(input.toLowerCase()+" found in "+iterator+" steps.");
				try {
					System.out.println(currentFS.download(url.getPath()));
				} catch (FileServerFileNotFound e) {
					System.out.println(e);
				} catch (Exception e) {
					System.out.println(url.getDomainToString()+" Fileserver is not reachable anymore deleting from cache.");
					
					if(cache.containsKey(url.getReverseDomainToString())) {
						cache.remove(url.getReverseDomainToString());
					}
				}
			}
			
			//System.out.println(cache);
		}
	}
	
	private void initialize() {
		loadRegistryData();
		
		/* Initialising datamembers*/
		inScanner = new Scanner(System.in);
		clientActive = new Boolean(true);
		
		rootServerName = props.getProperty("ro_id");
		
		/* reload root reg */
		try {
			rmiReg = LocateRegistry.getRegistry(props.getProperty("registry.host"), Integer.valueOf(props.getProperty("registry.port")));
		} catch (Exception e) {
			System.err.println("[Client/Err]: Could not load registry rmi: "+e);
		}
		
		/* reload root object */
		try {
			root = (INameServer) rmiReg.lookup(rootServerName);
		
		} catch (Exception e) {
            System.err.println("Rootserver could not be looked up:");
            e.printStackTrace();
        }
		
		cache = new HashMap<String,IServer>();
		
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
}
