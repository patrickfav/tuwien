package channel;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;

public class SecureChannelSym extends SecureChannel {
	
	private SecretKey aes_key = null;
	private byte[] iv = null;
	
	
	public String encryptAES(String input) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return encryptAESWithIV(aes_key,input,iv);
	}
	public String decryptAES(String input) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return decryptAESWithIV(aes_key,input,iv);
	}
	public void generateKey() throws NoSuchAlgorithmException {
		aes_key = generateAESKey();
	}
	public void generateIV() {
		iv = createSecureRandomByte(16);
	}
	
	/**
	 * Returns true if key and iv are set (and read to encrypt)
	 * @return
	 */
	public Boolean ready() {
		if(aes_key != null && iv != null) {
			return true;
		}
		return false;
	}
	
	/*
	 * PRIVATES
	 */
	
	private SecretKey generateAESKey() throws NoSuchAlgorithmException {
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		// KEYSIZE is in bits
		generator.init(256);
		return generator.generateKey(); 
	}
	
	private String encryptAESWithIV(Key key,String input,byte[] iv) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher crypt = Cipher.getInstance("AES/CTR/NoPadding");
		crypt.init(Cipher.ENCRYPT_MODE, key , new IvParameterSpec(iv));
		
		return new String(Base64.encode(crypt.doFinal(input.getBytes())));
	}
	
	private String decryptAESWithIV(Key key,String input,byte[] iv) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher crypt = Cipher.getInstance("AES/CTR/NoPadding");
		crypt.init(Cipher.DECRYPT_MODE, key , new IvParameterSpec(iv));
		
		return new String(crypt.doFinal(Base64.decode(input)));
	}
	
	/*
	 * GETTER AND SETTER
	 */
	
	public void setAes_key(SecretKey aes_key) {
		this.aes_key = aes_key;
	}

	public SecretKey getAes_key() {
		return aes_key;
	}

	public void setIv(byte[] iv) {
		this.iv = iv;
	}

	public byte[] getIv() {
		return iv;
	}
	
	
	
	public String getBase64Iv() {
		return new String(Base64.encode(iv));
	}
	
	public String getBase64Key() {
		return new String(Base64.encode(aes_key.getEncoded()));
	}
	
	public void setBase64Iv(String iv) {
		this.iv = Base64.decode(iv);
	}
	
	public void setBase64StringtoKey(String skey) {
		aes_key = new SecretKeySpec(Base64.decode(skey),"AES");
	}
}
