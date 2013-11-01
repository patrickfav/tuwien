package security;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PasswordFinder;
import org.bouncycastle.util.encoders.Base64;

public class SecurityUtil {
	
	protected static Logger log = Logger.getLogger(SecurityUtil.class);
	
	private static final int BLOCK_SIZE = 120;
	private static final String DEFAULT_PK_PASSWORD = "12345"; 
	
	public static PublicKey getPublicKey(String path) throws IOException {
		PEMReader in = new PEMReader(new FileReader(path));
		return (PublicKey) in.readObject();
	}

	public static PrivateKey getPrivateKey(String path) throws IOException {
		System.out.println(path);
		PEMReader in = new PEMReader(new FileReader(path),
				new PasswordFinder() {

					@Override
					public char[] getPassword() {
						// reads the password from standard input for decrypting
						// the private key
						System.out.println("Private Key - Enter pass phrase: 12345 - correct");
						try {
							return new BufferedReader(new InputStreamReader(
									System.in)).readLine().toCharArray();
						} catch (IOException e) {
							System.err
									.println("Could not get Private Key " + e);
							return null;
						}
					}

				});

		KeyPair keyPair = (KeyPair) in.readObject();
		PrivateKey privatekey = keyPair.getPrivate();
		return privatekey;
	}

	public static PrivateKey getPrivateKeyWithDefaultPassword(String path) throws IOException {
		System.out.println(path);
		PEMReader in = new PEMReader(new FileReader(path),
				new PasswordFinder() {
					@Override
					public char[] getPassword() {
						/* hard coded password for developers */
						log.info("Private Key pass phrase: "+DEFAULT_PK_PASSWORD+" - correct");
						return DEFAULT_PK_PASSWORD.toCharArray();
					}

				});

		KeyPair keyPair = (KeyPair) in.readObject();
		PrivateKey privatekey = keyPair.getPrivate();
		return privatekey;
	}
	
	public static String encryptRSAWithKeyAndReturnBase64(Key key, String input)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher crypt = Cipher.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding");
		crypt.init(Cipher.ENCRYPT_MODE, key);
		
		StringBuffer sb = new StringBuffer();
		
		int count = (int) Math.ceil(new Double(input.length()) / new Double(BLOCK_SIZE));
		
		log.info("encrypting - count:"+count+" - length:"+input.length());
		
		/* partial encryption for messages that are to long */
		for(int i=0; i< count;i++) {
			if(i == (count -1)) {
				//log.info("from "+ (i*BLOCK_SIZE)+" to "+(input.length()-1));
				//log.info(input.substring(i*BLOCK_SIZE, (input.getBytes().length)));
				sb.append(new String(
						Base64.encode(
								crypt.doFinal(input.substring(i*BLOCK_SIZE, (input.getBytes().length)).getBytes()))));
			
				
			} else {
				//log.info("from "+ (i*BLOCK_SIZE)+" to "+(((i+1)*BLOCK_SIZE)));
				//log.info(input.substring(i*BLOCK_SIZE, ((i+1)*BLOCK_SIZE)));
				sb.append(new String(
						Base64.encode(
								crypt.doFinal(input.substring(i*BLOCK_SIZE, ((i+1)*BLOCK_SIZE)).getBytes()))));
				sb.append("|");
			}
		}
		
		return sb.toString();
	}

	public static String decryptRSAWithKeyAndBase64(Key key, String input)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher crypt = Cipher.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding");
		crypt.init(Cipher.DECRYPT_MODE, key);
		
		StringBuffer sb = new StringBuffer();
		log.info("decrypting: whole: "+input);
		for(String s: input.split("\\|")) {
			//log.info("(partial decrypt) msg: "+s);
			sb.append(new String(crypt.doFinal(Base64.decode(s))));
		}
		
		return sb.toString();
	}

	public static byte[] signMessage(Key key, String msg)
			throws NoSuchAlgorithmException, InvalidKeyException,
			SignatureException {
		PrivateKey signerKey = (PrivateKey) key;
		Signature signer = Signature.getInstance("SHA256withRSAandMGF1");
		signer.initSign(signerKey);
		// MESSAGE is the message to sign in bytes
		signer.update(msg.getBytes());
		return signer.sign();
	}
	
	
	public static boolean verifySignedMessage(String gotRSAMsg,byte[] referenceClearMsg, PublicKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		byte[] signature = Base64.decode(gotRSAMsg);
		Signature verifier = Signature.getInstance("SHA256withRSAandMGF1");
		verifier.initVerify(key);
		// MESSAGE is the message against which to verify in bytes
		verifier.update(referenceClearMsg);
		return verifier.verify(signature); 
	}
}
