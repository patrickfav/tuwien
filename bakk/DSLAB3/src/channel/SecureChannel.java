package channel;

import java.security.SecureRandom;

import org.bouncycastle.util.encoders.Base64;

public class SecureChannel {
	
	protected byte[] client_challenge;
	protected byte[] server_challenge;
	
	public void generateServerChallenge() {
		server_challenge = createSecureRandomByte(32);
	}
	public void generateClientChallenge() {
		client_challenge = createSecureRandomByte(32);
	}
	
	/*
	 * PRIVAT METHODS
	 */
	
	protected String createSecureRandomNumAndBase64Encode(int length) {
		// generates a variable byte secure random number
		SecureRandom secureRandom = new SecureRandom();
		final byte[] number = new byte[length];
		secureRandom.nextBytes(number);
		return new String(Base64.encode(number));
	}
	
	protected byte[] createSecureRandomByte(int length) {
		// generates a variable byte secure random number
		SecureRandom secureRandom = new SecureRandom();
		final byte[] number = new byte[length];
		secureRandom.nextBytes(number);
		return number;
	}

	/*
	 * GETTER AND SETTER
	 */
	
	public byte[] getClient_challenge() {
		return client_challenge;
	}

	public byte[] getServer_challenge() {
		return server_challenge;
	}
	
	public String getBase64ClientChallenge() {
		return new String(Base64.encode(client_challenge));
	}
	
	public String getBase64ServerChallenge() {
		return new String(Base64.encode(server_challenge));
	}
	

	public void setBase64ClientChallenge(String challenge) {
		client_challenge = Base64.decode(challenge);
	}
	
	public void setBase64ServerChallenge(String challenge) {
		server_challenge = Base64.decode(challenge);
	}
}
