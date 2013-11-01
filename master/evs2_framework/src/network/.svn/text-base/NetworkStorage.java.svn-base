package network;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import network.entities.InetNetwork;
import network.entities.Network;

import org.apache.log4j.Logger;

public class NetworkStorage {

	private static Logger log = Logger.getLogger(NetworkStorage.class);
	
	public Map<InetNetwork, Network> networkMap;
	
	public static NetworkStorage instance;
	
	/**
	 * Initialization
	 */
	
	private NetworkStorage() {
		log.info("Create singleton Instance of NetworkStorage");
		networkMap = Collections.synchronizedMap(new HashMap<InetNetwork,Network>());
	}
	
	public synchronized static NetworkStorage getInstance() {
        if (instance == null) {
            instance = new NetworkStorage();
        }
        return instance;
    }
	
	/**
	 * Methods
	 */

	public synchronized Map<InetNetwork, Network> getNetworkMap() {
		return networkMap;
	}	
	
	public Network getNetwork(InetNetwork address) {
		return networkMap.get(address);
	}
	
	public void addNetwork(Network network) {
		networkMap.put(network.getAddress(), network);
	}
	
	public void removeNetwork(InetNetwork address) {
		networkMap.remove(address);
	}
}
