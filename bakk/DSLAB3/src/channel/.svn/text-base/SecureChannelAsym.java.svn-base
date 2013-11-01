package channel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.PublicKey;

import org.bouncycastle.openssl.PEMReader; 
import org.bouncycastle.openssl.PasswordFinder;
import org.bouncycastle.util.encoders.Base64;

public class SecureChannelAsym extends SecureChannel {
	
	private Key server_public_key;
	private Key client_public_key;
	private Key server_private_key;
	private Key client_private_key;
	
	public String encryptRSAWithServerPublicKey(String input) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return encryptRSAWithKeyAndReturnBase64(server_public_key,input);
	}
	public String encryptRSAWithClientPublicKey(String input) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return encryptRSAWithKeyAndReturnBase64(client_public_key,input);
	}
	
	public String decryptRSAWithServerPrivateKey(String input) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return decryptRSAWithKeyAndBase64(server_private_key,input);
	}
	public String decryptRSAWithClientPrivateKey(String input) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return decryptRSAWithKeyAndBase64(client_private_key,input);
	}
	
	public void setServerPublicKey(String path) throws IOException {
		server_public_key = getPublicKey(path);
	}
	public void setClientPublicKey(String path) throws IOException {
		client_public_key = getPublicKey(path);
	}
	
	public void setServerPrivateKey(String path) throws IOException {
		server_private_key = getPrivateKey(path);
	}
	public void setClientPrivateKey(String path) throws IOException {
		client_private_key = getPrivateKey(path);
	}
	public String signWithClientPrivateKeyAndBase64(String msg) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		return new String(Base64.encode(signMessage(client_private_key,msg)));
	}
	
	public PublicKey getPubKey(String path) throws IOException {
		return getPublicKey(path);
	}
	public boolean verifySignedMessage(String gotRSAMsg,byte[] referenceClearMsg, PublicKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		byte[] signature = Base64.decode(gotRSAMsg);
		Signature verifier = Signature.getInstance("SHA256withRSAandMGF1");
		verifier.initVerify(key);
		// MESSAGE is the message against which to verify in bytes
		verifier.update(referenceClearMsg);
		return verifier.verify(signature); 
	}
	
	
	/*
	 * PRIVATE METHODS
	 */
	
	private String encryptRSAWithKeyAndReturnBase64(Key key, String input) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
			Cipher crypt = Cipher.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding");
			crypt.init(Cipher.ENCRYPT_MODE, key );
			return new String(Base64.encode(crypt.doFinal(input.getBytes())));
	}
	
	private String decryptRSAWithKeyAndBase64(Key key, String input) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher crypt = Cipher.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding");
		crypt.init(Cipher.DECRYPT_MODE, key );
		return new String(crypt.doFinal(Base64.decode(input)));
	}
	
	private PublicKey getPublicKey(String path) throws IOException {
		PEMReader in = new PEMReader(new FileReader(path));
		return (PublicKey) in.readObject(); 
	}
	private PrivateKey getPrivateKey(String path) throws IOException {
		PEMReader in = new PEMReader(new FileReader(path), new PasswordFinder() {

		    @Override
		    public char[] getPassword() {
			    // reads the password from standard input for decrypting the private key
			    System.out.println("Enter pass phrase:");
			    try {
					return new BufferedReader(new InputStreamReader(System.in)).readLine().toCharArray();
				} catch (IOException e) {
					System.err.println("Could not get Private Key "+e);
					return null;
				}
		    }

		});

		KeyPair keyPair = (KeyPair) in.readObject();
		PrivateKey privatekey = keyPair.getPrivate(); 
		return privatekey;
	}
	
	private byte[] signMessage(Key key, String msg) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		PrivateKey signerKey = (PrivateKey) key;
		Signature signer = Signature.getInstance("SHA256withRSAandMGF1");
		signer.initSign(signerKey);
		// MESSAGE is the message to sign in bytes
		signer.update(msg.getBytes());
		return signer.sign(); 
	}
	
	
	
	public Key getServerPrivateKey () {
		return server_private_key;
	}
	public Key getClientPrivateKey() {
		return client_private_key;
	}

}
