package network;

import indexing.entities.IndexedService;

import java.net.InetAddress;
import java.util.Map;

import org.apache.log4j.Logger;

import network.entities.InetNetwork;
import network.entities.Network;

public class NetworkService {
	
	private static Logger log = Logger.getLogger(NetworkService.class);

	private NetworkStorage storage;
	
	public NetworkService() {
		storage = NetworkStorage.getInstance();
	}

	/**
	 * Service Methods
	 */
	
	public boolean hasToBeEncrypted(InetAddress address, IndexedService indexedService) {
		return hasToBeEncrypted(address, -1, indexedService);
	}
	
	public boolean hasToBeEncrypted(InetAddress address, int netPrefix, IndexedService indexedService) {
		
		log.info("Check if Network has to be an encrypted Connection ext: " + indexedService.isRequiresEncryptionExternal() +", int: " + indexedService.isRequiresEncryptionInternal());
		
		/*
		 * Return Custom Configuration
		 */
		
		InetAddress netMask = NetworkHelper.getNetMask(address);
		
		if(netPrefix > -1) {
			netMask = NetworkHelper.getNetMask(address, netPrefix);
			log.debug("Netmask: " + netMask.getHostAddress());
		}
	
		InetNetwork networkAddress = new InetNetwork(address, netMask);
		
		log.info("Network ip = " + networkAddress.toString());
		
		Network network = storage.getNetwork(networkAddress);
		
		if(network != null) {
			log.info("Return custom Network Configuration: Encryption = " + network.isEncrypted(indexedService));
			if(network.isAllEncrypted() != null) {
				return network.isAllEncrypted();
			} else {
				return network.isEncrypted(indexedService);
			}
		}
		
		/*
		 * Return default Configuration
		 */
		
		boolean local = NetworkHelper.isLocal(networkAddress.getNetwork());
		
		// If external Network
		if(!local && indexedService.isRequiresEncryptionExternal()) {
			log.info("Return External default: Encryption = true");
			return true;
		}
		
		// If internal Network
		if(local && indexedService.isRequiresEncryptionInternal()) {
			log.info("Return Internal default: Encryption = true");
			return true;
		}
		
		log.info("Return default: Encryption = false");
		return false;
	}
	
	/**
	 * Custom Configuration
	 */
	
	public void addNetworkConfiguration(InetAddress address, Map<IndexedService, Boolean> serviceMap, Boolean allEncrypted) {
		InetNetwork networkAddress = new InetNetwork(address, NetworkHelper.getNetMask(address));		
		Network network = new Network(networkAddress, serviceMap, allEncrypted);
		storage.addNetwork(network);
	}
	
	public void removeNetworkConfiguration(InetAddress address) {
		InetNetwork networkAddress = new InetNetwork(address, NetworkHelper.getNetMask(address));
		storage.removeNetwork(networkAddress);
	}
	
	public Network getNetworkConfiguration(InetAddress address) {
		InetNetwork networkAddress = new InetNetwork(address, NetworkHelper.getNetMask(address));
		return storage.getNetwork(networkAddress);
	}
	
}
