package network;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

public class NetworkHelper {

	private static Logger log = Logger.getLogger(NetworkHelper.class);
	
	/**
	 * Validates a given ip address
	 * @param ipAddress
	 * @return
	 */
	public final static boolean validateIPAddress(String ipAddress) {
		String[] parts = ipAddress.split("\\.");
		if (parts.length != 3) {
			return false;
		}
		for (String s : parts) {
			int i = Integer.parseInt(s);
			if ((i < 0) || (i > 255)) {
				return false;
			}
		}
		return true;
	}
	
	public final static InetAddress getAddressForIp(String ip) {
		try {
			return InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			log.info("Error, parsing InetAddress: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Check if Address is internal or external IP address
	 * @param address
	 * @return
	 */
	public final static boolean isLocal(InetAddress address) {
		if(address.isAnyLocalAddress() || address.isLinkLocalAddress() || address.isLoopbackAddress() || address.isMulticastAddress() || address.isSiteLocalAddress()) {
			return true;
		}
		return false;
	}
	
	/**
	 *  Returns the Subnet Address for given ip
	 * @param ip
	 * @return
	 */
	public static InetAddress getNetMask(InetAddress ip) {
		NetworkInterface networkInterface;
		try {
			networkInterface = NetworkInterface.getByInetAddress(ip);
			return getNetMask(ip, networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength());
		} catch (Exception e) {
			return getNetMask(ip, 24);
		}
	}
	
	/**
	 * Returns the Subnet Address for given ip and netPrefix
	 * @param ip
	 * @param netPrefix
	 * @return
	 */
	public static InetAddress getNetMask(InetAddress ip, int netPrefix) {
	    try {
	        int shiftby = (1<<31);
	       for (int i=netPrefix-1; i>0; i--) {
	           shiftby = (shiftby >> 1);
	        }
	        String maskString = Integer.toString((shiftby >> 24) & 255) + "." + Integer.toString((shiftby >> 16) & 255) + "." + Integer.toString((shiftby >> 8) & 255) + "." + Integer.toString(shiftby & 255);
	        return InetAddress.getByName(maskString);
	    }
	        catch(Exception e){e.printStackTrace();
	    }
	    return null;
	}
	
}
