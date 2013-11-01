package httptests.network;

import static org.junit.Assert.*;

import indexing.entities.IndexedService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import network.NetworkHelper;
import network.NetworkService;

import org.junit.Before;
import org.junit.Test;

public class NetworkTest {

	NetworkService networkService;
	IndexedService is;
	Map<IndexedService, Boolean> config1;
	Map<IndexedService, Boolean> config2;
	
	@Before 
	public void setUp() {
		networkService = new NetworkService();
		
		is = new IndexedService();
		is.setName("Service 1");
		
		config1 = new HashMap<IndexedService, Boolean>();
		config1.put(is, true);
		
		config2 = new HashMap<IndexedService, Boolean>();
		config2.put(is, false);
		
	}
	
	/**
	 * Test internal Network default Service Configuration
	 * Service encryption = false
	 * @throws UnknownHostException
	 */
	@Test
	public void testLocalNetworkNotEncrypted() throws UnknownHostException {
		InetAddress address = InetAddress.getByName("127.0.0.1");
		is.setRequiresEncryptionExternal(true);
		is.setRequiresEncryptionInternal(false);
		boolean encrypted = networkService.hasToBeEncrypted(address, is);
		assertFalse(encrypted);
	}
	
	/**
	 * Test internal Network default Service Configuration
	 * Service encryption = true
	 * @throws UnknownHostException
	 */
	@Test
	public void testLocalNetworkEncrypted() throws UnknownHostException {
		InetAddress address = InetAddress.getByName("127.0.0.1");
		is.setRequiresEncryptionExternal(false);
		is.setRequiresEncryptionInternal(true);
		boolean encrypted = networkService.hasToBeEncrypted(address, is);
		assertTrue(encrypted);
	}
	
	/**
	 * Test external Network default Service Configuration
	 * Service encryption = false
	 * @throws UnknownHostException
	 */
	@Test
	public void testExternNetworkNotEncrypted() throws UnknownHostException {
		InetAddress address = InetAddress.getByName("128.131.199.0");
		is.setRequiresEncryptionExternal(false);
		is.setRequiresEncryptionInternal(false);
		boolean encrypted = networkService.hasToBeEncrypted(address, is);
		assertFalse(encrypted);
	}
	
	/**
	 * Test external Network default Service Configuration
	 * Service encryption = true
	 * @throws UnknownHostException
	 */
	@Test
	public void testExternNetworkEncrypted() throws UnknownHostException {
		InetAddress address = InetAddress.getByName("128.131.199.0");
		is.setRequiresEncryptionExternal(true);
		is.setRequiresEncryptionInternal(false);
		boolean encrypted = networkService.hasToBeEncrypted(address, is);
		assertTrue(encrypted);
	}
	
	/**
	 * Check external IP address
	 * @throws UnknownHostException
	 */
	@Test
	public void testLocalNetworkFalse() throws UnknownHostException {
		InetAddress address = InetAddress.getByName("128.131.199.0");
		assertFalse(NetworkHelper.isLocal(address));
	}
	
	/**
	 * Check internal IP address
	 * @throws UnknownHostException
	 */
	@Test
	public void testLocalNetworkTrue() throws UnknownHostException {
		InetAddress address = InetAddress.getByName("81.82.83.0");
		assertFalse(NetworkHelper.isLocal(address));
	}
	
	/**
	 * Test a custom internal network configuration
	 * Service encryption = true
	 * Network configuration = false
	 * Encryption should be false
	 * @throws UnknownHostException
	 */
	@Test
	public void testAddCustomLocalConfig() throws UnknownHostException {
		InetAddress address = InetAddress.getByName("127.0.0.1");
		networkService.addNetworkConfiguration(address, config1, null);
		is.setRequiresEncryptionExternal(false);
		is.setRequiresEncryptionInternal(false);
		boolean encrypted = networkService.hasToBeEncrypted(address, is);
		assertTrue(encrypted);
	}
	
	/**
	 * Test a custom external network configuration
	 * Service encryption = true
	 * Network configuration = false
	 * Encryption should be false
	 * @throws UnknownHostException
	 */
	@Test
	public void testAddCustomExternConfig() throws UnknownHostException {
		InetAddress address = InetAddress.getByName("128.131.199.0");
		networkService.addNetworkConfiguration(address, config1, null);
		is.setRequiresEncryptionExternal(false);
		is.setRequiresEncryptionInternal(false);
		boolean encrypted = networkService.hasToBeEncrypted(address, is);
		assertTrue(encrypted);
	}
	
	/**
	 * Test local net address with prefix
	 * @throws UnknownHostException
	 */
	@Test
	public void testInternalSubnetAddr() throws UnknownHostException {
		InetAddress address = InetAddress.getByName("192.168.1.0");
		is.setRequiresEncryptionExternal(true);
		is.setRequiresEncryptionInternal(false);
		boolean encrypted = networkService.hasToBeEncrypted(address, 24, is);
		assertFalse(encrypted);
	}
	
	/**
	 * Test external net address with prefix
	 * @throws UnknownHostException
	 */
	@Test
	public void testExternalSubnetAddr() throws UnknownHostException {
		InetAddress address = InetAddress.getByName("81.82.83.0");
		is.setRequiresEncryptionExternal(true);
		is.setRequiresEncryptionInternal(false);
		boolean encrypted = networkService.hasToBeEncrypted(address, 24, is);
		assertTrue(encrypted);
	}
	
	/**
	 * Test external net address with prefix
	 * @throws UnknownHostException
	 */
	@Test
	public void testExternalConfigSubnetAddr() throws UnknownHostException {
		InetAddress address = InetAddress.getByName("81.82.83.0");
		networkService.addNetworkConfiguration(address, config1, null);
		is.setRequiresEncryptionExternal(true);
		is.setRequiresEncryptionInternal(false);
		boolean encrypted = networkService.hasToBeEncrypted(address, 24, is);
		assertTrue(encrypted);
	}
	
	/**
	 * Test all services requires encryption
	 * @throws UnknownHostException
	 */
	@Test
	public void testAllServices() throws UnknownHostException {
		InetAddress address = InetAddress.getByName("81.82.83.0");
		networkService.addNetworkConfiguration(address, config2, true);
		is.setRequiresEncryptionExternal(true);
		is.setRequiresEncryptionInternal(true);
		boolean encrypted = networkService.hasToBeEncrypted(address, 24, is);
		assertTrue(encrypted);
	}
}
