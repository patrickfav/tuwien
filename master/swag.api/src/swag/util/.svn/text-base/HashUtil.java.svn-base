package swag.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
	
	 public static String sha256(String password, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
	       MessageDigest digest = MessageDigest.getInstance("SHA-256");
	       digest.reset();
	       digest.update(salt.getBytes());
	       
	       byte[] mdbytes = digest.digest(password.getBytes("UTF-8"));
	       
	       StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<mdbytes.length;i++) {
	    	  hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
	    	}
	       
	       return hexString.toString();
	 }
}
