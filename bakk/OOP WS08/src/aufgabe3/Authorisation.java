package aufgabe3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;

public class Authorisation implements Serializable{
	private static final long serialVersionUID = 8460911874782499643L;
	private static HashMap<Date, String> tickets = new HashMap<Date, String>();
	private HashMap<String, String> admins = new HashMap<String, String>();
	private final String userName="admin";
	private final String password="secret";
	
	/**
	 * CONSTRUCTOR
	 * 
	 * adds the default admin user if he doesn't exist already in the user-hashtable
	 */
	public Authorisation() {
		if (!admins.containsKey(userName)) admins.put(userName, password);
	}
	
	/**
	 * checkLogin
	 * 
	 * checks if the handed over authorisation is valid
	 * 
	 * @param ticket - hash is the hashkey that the user gets from the server when he loggs in
	 * @return if the hash is valid
	 */
	public boolean checkLogin(String ticket) {
		if(tickets.containsValue(ticket))
			return true;
		
		return false;
	}
	
	/**
	 * doLogin
	 * 
	 * Expects username and password as Strings and returns a validation-hash if the transfered credentials are valid
	 * @param user
	 * @param password
	 * @return validation-hash
	 */
	public String doLogin(String user, String password) {
		Date timestamp =null;
		String s ="";
		if (admins.containsKey(user) && admins.get(user).equals(password)){
			timestamp = new Date(System.currentTimeMillis());
			s= getHash(timestamp.toString());
			tickets.put(timestamp, s);
		}
				
		return s;
	}
	
	/**
	 * doLogout
	 * 
	 * loggs an user out; requires the validation-hash which the user got during the log-in
	 * @param ticket - validation-hash
	 * @return if the check out was successful
	 */
	public boolean doLogout(String ticket) {
		if (tickets.containsValue(ticket)){
			for(Date aKey : tickets.keySet()){
				String s = tickets.get(aKey);
				if (s.equals(ticket)) tickets.remove(aKey);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * getHash
	 * 
	 * generates a MD5 hash out of the transfered object
	 * @param o Object on which the MD5 hash is based
	 * @return MD5 hash-string
	 */
	private String getHash(Object o) {
    	try
    	{
    		MessageDigest mdAlgorithm = MessageDigest.getInstance("MD5");
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		ObjectOutputStream oos = new ObjectOutputStream(baos);
    		oos.writeObject(o);
    		mdAlgorithm.update(baos.toByteArray());

    		byte[] digest = mdAlgorithm.digest();
    		StringBuffer hexString = new StringBuffer();

    		for (int i = 0; i < digest.length; i++)
    		{
    			String x = Integer.toHexString(0xFF & digest[i]);
    			if (x.length() < 2) x = "0" + x;
    			hexString.append(x);
    		}
    		return(hexString.toString());
    	}
    	catch(NoSuchAlgorithmException e) { return(null); }
    	catch(IOException e) { return(null); }
    }
}
