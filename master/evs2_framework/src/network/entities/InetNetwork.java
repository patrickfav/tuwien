package network.entities;

/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetNetwork {

	/*
	 * Implements network masking, and is compatible with RFC 1518 and RFC 1519,
	 * which describe CIDR: Classless Inter-Domain Routing.
	 */

	private InetAddress network;
	private InetAddress netmask;


	/**
	 * Constuctor
	 * 
	 * @param ip
	 *            the InetAddress to init the class
	 * @param netmask
	 *            the InetAddress represent the netmask to init the class
	 */
	public InetNetwork(InetAddress ip, InetAddress netmask) {
		network = maskIP(ip, netmask);
		this.netmask = netmask;
	}

	/**
	 * @see #contains(String)
	 */
	public boolean contains(final InetAddress ip) {
		return network.equals(maskIP(ip, netmask));
	}

	/**
	 * Return String represention of this class
	 * 
	 * @return string String representation of this class
	 */
	public String toString() {
		return network.getHostAddress() + "/" + netmask.getHostAddress();
	}

	/**
	 * Return hashCode representation of this class
	 * 
	 * @return hashCode the hashCode representation of this class
	 */
	public int hashCode() {
		return maskIP(network, netmask).hashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return (obj != null)
				&& (obj instanceof InetNetwork)
				&& ((((InetNetwork) obj).network.equals(network)) && (((InetNetwork) obj).netmask
						.equals(netmask)));
	}

	/**
	 * Return InetAddress generated of the passed argements. Return Null if any
	 * errors accour
	 * 
	 * @param ip
	 *            the byte[] represent the ip
	 * @param mask
	 *            the byte[] represent the netmask
	 * @return inetAddress the InetAddress generated of the passed arguments.
	 */
	public static InetAddress maskIP(final byte[] ip, final byte[] mask) {
		try {
			return getByAddress(new byte[] { (byte) (mask[0] & ip[0]),
					(byte) (mask[1] & ip[1]), (byte) (mask[2] & ip[2]),
					(byte) (mask[3] & ip[3]) });
		} catch (UnknownHostException e) {
			return null;
		}
	}

	/**
	 * @see #maskIP(byte[], byte[])
	 */
	public static InetAddress maskIP(final InetAddress ip,
			final InetAddress mask) {
		return maskIP(ip.getAddress(), mask.getAddress());
	}

	private static java.lang.reflect.Method getByAddress = null;

	static {
		try {
			Class<?> inetAddressClass = Class.forName("java.net.InetAddress");
			Class<?>[] parameterTypes = { byte[].class };
			getByAddress = inetAddressClass.getMethod("getByAddress",
					parameterTypes);
		} catch (Exception e) {
			getByAddress = null;
		}
	}

	/**
	 * Return InetAddress which represent the given byte[]
	 * 
	 * @param ip
	 *            the byte[] represent the ip
	 * @return ip the InetAddress generated of the given byte[]
	 * @throws java.net.UnknownHostException
	 */
	private static InetAddress getByAddress(byte[] ip)
			throws java.net.UnknownHostException {
		InetAddress addr = null;
		if (getByAddress != null)
			try {
				addr = (InetAddress) getByAddress.invoke(null,
						new Object[] { ip });
			} catch (IllegalAccessException e) {
			} catch (java.lang.reflect.InvocationTargetException e) {
			}

		if (addr == null) {
			addr = InetAddress.getByName(Integer.toString(ip[0] & 0xFF, 10)
					+ "." + Integer.toString(ip[1] & 0xFF, 10) + "."
					+ Integer.toString(ip[2] & 0xFF, 10) + "."
					+ Integer.toString(ip[3] & 0xFF, 10));
		}
		return addr;
	}

	public InetAddress getNetwork() {
		return network;
	}

	public void setNetwork(InetAddress network) {
		this.network = network;
	}

	public InetAddress getNetmask() {
		return netmask;
	}

	public void setNetmask(InetAddress netmask) {
		this.netmask = netmask;
	}
	
}
