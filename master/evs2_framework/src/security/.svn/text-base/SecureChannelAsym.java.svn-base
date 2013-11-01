package security;

import java.io.IOException;
import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;

public class SecureChannelAsym {
	protected static Logger log = Logger.getLogger(SecureChannelAsym.class);
	
	private Key privateKey;
	private Key serverPublicKey;
	private Map<InetAddress,PublicKey> clientKeyStore;
	
	/* server mode */
	public SecureChannelAsym(String privateKeyPath, Map<InetAddress,PublicKey> clientKeyStore) throws IOException {
		this.clientKeyStore = clientKeyStore;
		setPrivateKey(privateKeyPath);
		
		for(InetAddress addr: clientKeyStore.keySet()) {
			log.info("Added client to keystore: ip "+addr+", keylength:"+clientKeyStore.get(addr).getEncoded().length);
			;
		}
			
	}
	
	/* client mode */
	public SecureChannelAsym(String privateKeyPath, String serverPublicKey) throws IOException {
		setPrivateKey(privateKeyPath);
		setServerPublicKey(serverPublicKey);
	}
	
	/*
	 * METHODS
	 */
	
	public String encryptRSAWithPublicKey(InetAddress addr, String content) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		if(clientKeyStore == null)
			throw new UnsupportedOperationException("Client store must be set");
		
		return SecurityUtil.encryptRSAWithKeyAndReturnBase64(clientKeyStore.get(addr),content);
	}
	
	public String encryptRSAWithServerPublicKey(String content) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		if(serverPublicKey == null)
			throw new UnsupportedOperationException("Server Public key must be set");
		
		return SecurityUtil.encryptRSAWithKeyAndReturnBase64(serverPublicKey,content);
	}
	
	public String decryptRSAWithPrivateKey(String content) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return SecurityUtil.decryptRSAWithKeyAndBase64(privateKey,content);
	}
	
	
	/*
	 * GETTER/SETTER
	 */
	
	public void setPrivateKey(String path) throws IOException {
		privateKey = SecurityUtil.getPrivateKeyWithDefaultPassword(path);
	}
	
	public Key getPrivateKey () {
		return privateKey;
	}

	public void setServerPublicKey(String serverPublicKey) throws IOException {
		this.serverPublicKey = SecurityUtil.getPublicKey(serverPublicKey);
	}

	public Key getServerPublicKey() {
		if(serverPublicKey == null)
			throw new UnsupportedOperationException("Server Public key must be set");
		
		return serverPublicKey;
	}

}
