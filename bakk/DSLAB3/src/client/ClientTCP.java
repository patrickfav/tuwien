package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.regex.Pattern;


/**
 *  Thread for listening to
 *  the server and printing it out in 
 *  the console.
 */

public class ClientTCP extends ClientThread {
	
	private BufferedReader socketIn = null;
	private PrintWriter socketOut = null;
	private Socket socket = null;
	private Boolean active;
	private String[] inputArray;
	
	
	public ClientTCP(Socket socket) {
		this.socket = socket;
		this.active = true;
	}
	
	@Override
	public void run() {
		String buffer;
		
		try {
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e1) {
			System.err.println("Couldn't get I/O for the connection to: " + e1);
		}
		
		try {
			/* ATHENTIFICATION */
			while(active && (buffer = socketIn.readLine()) != null) {
				
				try {
					//System.out.println("first: "+buffer);
					//System.out.println(Client.secAsym.getClientPrivateKey().getEncoded());
					String okMsg = Client.secAsym.decryptRSAWithClientPrivateKey(buffer);
					//System.out.println(okMsg);
					
					Pattern p = Pattern.compile(" ");
					inputArray = p.split(okMsg);
					
					Client.secAsym.setBase64ServerChallenge(inputArray[2]);
					Client.secSym.setBase64StringtoKey(inputArray[3]);
					Client.secSym.setBase64Iv(inputArray[4]);
					
					//System.out.println(Client.secAsym.getBase64ClientChallenge());
					//System.out.println(socketOut+" - "+ inputArray[0]+" "+inputArray[2]);
					
					if(inputArray[0].equals("!ok") && inputArray[1].equals(Client.secAsym.getBase64ClientChallenge())) {
						socketOut.println(Client.secSym.encryptAES(inputArray[2]));
					} else {
						System.out.println("Error after first Server response. Cannot read: "+inputArray[1]+" "+Client.secAsym.getBase64ClientChallenge());
					}
					break;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			/* NORMAL CHAT */
			while(active && (buffer = socketIn.readLine()) != null) {
				
				try {
					String msg = Client.secSym.decryptAES(buffer);
					
					Pattern p = Pattern.compile(" ");
					inputArray = p.split(msg);
					
					/* if private message */
					if(inputArray[0].equals("!msg")) {
						
						PublicKey pk = Client.secAsym.getPubKey(Client.key_public_dir+"/"+inputArray[1]+".pub.pem");
						String text = Client.specialConcatStringArray(Arrays.copyOfRange(inputArray, 3, inputArray.length)).trim();
						//System.out.println(inputArray[2]);
						//System.out.println("-"+text+"-");
						//System.out.println(Client.key_public_dir+"/"+inputArray[1]+".pub.pem");
						
						try {
							if(Client.secAsym.verifySignedMessage(inputArray[2].trim(),text.getBytes(), pk)) {
								System.out.println("[VALID/PRIVATE] "+inputArray[1]+": "+text);
							} else {
								System.out.println("[INVALID] "+inputArray[1]+": "+text);
							}
						} catch(Exception e) {
							System.out.println("[UNVERIFIED] "+inputArray[1]+": "+text);
						}
						
					} else {
						System.out.println(msg);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
			exitThread();
			
		} catch (IOException e) {
			/* do nothing */
			//System.err.println("Error while sending to socket: " + e);
			//e.printStackTrace();
		}
	}
	
	/**
	 * Closes the TCP Socket
	 */
	public void exitThread() {
		active = false;
		try {
			socketIn.close();
			socket.close();
		} catch (IOException e) {
			System.err.println("[Client] Connection could not be closed: " + e);
		}
	}
}
