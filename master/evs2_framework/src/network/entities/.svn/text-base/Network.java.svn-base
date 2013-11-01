package network.entities;

import indexing.entities.IndexedService;

import java.util.Map;


public class Network {

	private InetNetwork address;	
	private Map<IndexedService, Boolean> serviceMap;
	private Boolean allEncrypted = null;

	public Network(InetNetwork address, Map<IndexedService, Boolean> serviceMap, Boolean allEncrypted) {
		super();
		this.address = address;
		this.serviceMap = serviceMap;
		this.allEncrypted = allEncrypted;
	}

	/**
	 * Getter / Setter
	 */
	
	public InetNetwork getAddress() {
		return address;
	}

	public void setAddress(InetNetwork address) {
		this.address = address;
	}

	public Map<IndexedService, Boolean> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<IndexedService, Boolean> serviceMap) {
		this.serviceMap = serviceMap;
	}
	
	public boolean isEncrypted(IndexedService is) {
		return serviceMap.get(is);
	}
	
	public Boolean isAllEncrypted() {
		return allEncrypted;
	}

	public void setAllEncrypted(Boolean allEncrypted) {
		this.allEncrypted = allEncrypted;
	}
	
}
