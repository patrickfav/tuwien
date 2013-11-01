package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import channel.SecureChannelAsym;
import channel.SecureChannelSym;

import entities.User;


/**
 * 
 * The Thread thats opened for every user.
 * @author PatrickF
 *
 */
public class ServerTCPUserThread extends ServerThread {
	
	private Socket socket;
	private PrintWriter socketOut = null;
	private BufferedReader socketIn = null;
	
	private String user;
	private String cmd;
	private String[] inputArray;
	private Boolean active;
	private Boolean loggedIn;
	
	private String buffer;
	
	private SecureChannelAsym secAsym;
	private SecureChannelSym secSym;
	
	/**
	 * CONSTRUCTOR 
	 * @param socket
	 */
	
	public ServerTCPUserThread(Socket socket, SecureChannelAsym asym) {
		this.socket = socket;
		
		active = new Boolean(true);
		loggedIn = new Boolean(false);
		
		/* security */
		secAsym = asym;
		secSym = new SecureChannelSym();
		
		try {
			socketOut = new PrintWriter(socket.getOutputStream(), true);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("[Server/err]["+Thread.currentThread().getId()+"]: Don't know about client:"+e);
		} catch (IOException e) {
			System.err.println("[Server/err]["+Thread.currentThread().getId()+"]: Couldn't get I/O :"+e);
		}
	}
	
	/**
	 * Start of the Thread: trys to read form Socket
	 * and reacts according to the given request
	 */
	@Override
	public void run() {
		try {
			
			/* first step authentification */
			while((buffer = socketIn.readLine()) != null) {
				
				try {
					//debug
					//System.out.println(buffer);
					
					secAsym.generateServerChallenge();
	
					Pattern p = Pattern.compile(" ");
					inputArray = p.split(secAsym.decryptRSAWithServerPrivateKey(buffer));
					
					cmd = inputArray[0];
					
					//System.out.println(cmd+" "+inputArray[1]+" "+inputArray[2]);
					
					/* the command switch */
					if (cmd.equals("!login") && inputArray[1] != null && !inputArray[1].equals("")) {
						try {
							
							this.user = inputArray[1];
										
							secAsym.setClientPublicKey(Server.key_public_dir+"/"+user+".pub.pem");
							
							secSym.generateIV();
							secSym.generateKey();
							
							String okMsg = "!ok "+inputArray[2]+" "+secAsym.getBase64ServerChallenge()+ " "+ secSym.getBase64Key() +" " +secSym.getBase64Iv();
							sendRSA(okMsg);
							break;
						} catch(Exception e) {
							//sendSelf("Unknown requset / error while reading login");
							e.printStackTrace();
						}
					} else {
						//sendSelf("Please login first!");
					}
				} catch (Exception e) {
					System.out.println("[Server/err]["+Thread.currentThread().getId()+"] Error while trying to log in.");
					exitThread();
				}
			}
			
			/* third step authentification */
			while((buffer = socketIn.readLine()) != null) {
				try {
					if(secSym.decryptAES(buffer).equals(secAsym.getBase64ServerChallenge())) {
						//System.out.println("logging in");
						login(user);
						break;
					} else {
						System.out.println("[Server/err]["+Thread.currentThread().getId()+"] Wrong Server challange");
					}
				} catch (Exception e) {
					System.out.println("[Server/err]["+Thread.currentThread().getId()+"] Error while reading second response from client.");
					exitThread();
				}
			}
			
			
			while(active && (buffer = socketIn.readLine()) != null) {
				
				/* Split input with regex and save in array */
				Pattern p = Pattern.compile(" ");
				inputArray = p.split(secSym.decryptAES(buffer));
				
				cmd = inputArray[0];
			
				/* the command switch */
				if(cmd.equals("!logout")) {
					logout();
				} else if(cmd.equals("!send")) {
					try {
						send(Server.specialConcatStringArray(Arrays.copyOfRange(inputArray, 1, inputArray.length)));
						
					} catch(Exception e) {
						sendAES("Unknown requset / error while sending");
					}	
				} else if(cmd.equals("!msg")) {
					try {
						sendPrivate(inputArray[1],inputArray[2],Server.specialConcatStringArray(Arrays.copyOfRange(inputArray, 3, inputArray.length)));
					} catch(Exception e) {
						sendAES("Unknown requset / error while sending");
					}	
				} else {
					sendAES("Unknown requset");
				}
			}
			
		} catch (IOException e) {
			System.err.println("[Server/err]["+Thread.currentThread().getId()+"]:Error while reading from Socket (IO):"+e);
			e.printStackTrace();
			exitThread();
		} catch (NullPointerException e) {
			System.err.println("[Server/err]["+Thread.currentThread().getId()+"]:Error while reading from Socket (Nullpointer):"+e);
			exitThread();
		} catch (Exception e) {
			System.err.println("[Server/err]["+Thread.currentThread().getId()+"]:Error while reading from Socket (Excpetion):"+e);
			exitThread();
		}
		finally {
			if(loggedIn)
				try {
					logout();
				} catch (Exception e) {
					System.err.println("[Server/err]["+Thread.currentThread().getId()+"]:Cannot logout (Excpetion):"+e);
					exitThread();
				}
			
			exitThread();
		}
	}
	
	/**
	 * Checks if the this is the requested user
	 * and if the user is logged in -> returns true
	 * otherwise false
	 * 
	 * @param requested user
	 * @return
	 */
	public Boolean checkUser(String user) {
		if(loggedIn && this.user.equals(user))
			return true;
		
		return false;
	}
	
	/**
	 * Checks if the user is logged in
	 * @return
	 */
	public Boolean checkLogin() {
		return loggedIn;
	}
	
	/**
	 * Trys to login the user and returns the
	 * proper responds to the client.
	 * 
	 * @param user
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 */
	private void login(String user) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		if(!loggedIn) {
			
			if(Server.usersTable.containsKey(this.user) && ((User) Server.usersTable.get(this.user)).getOnline()) {
				sendAES("This user has already logged in with another client.");
			} else if(Server.userData.containsKey(this.user)) {
				
				loggedIn = true;
				sendAES("Successfully logged in.");
				//System.out.println(user+" logged in.");
				Server.setUsersTableOnlineStatus(user,true);
			} else {
				sendAES("Wrong username.");
			}
		} else {
			sendAES("Already logged in.");
		}
	}
	
	/**
	 * Sends the msg to all online (logged in)
	 * users in the chatroom.
	 * @param msg
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 */
	private synchronized void send(String msg) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		if(loggedIn) {
			for(ServerTCPUserThread tempThread:Server.threadList ) {
				if(tempThread.checkLogin()) {
					tempThread.sendAES(user+": "+ msg.trim());
				}
			}
		} else {
			sendAES("Cannot send - login first.");
		}
	}
	
	/**
	 * Trys to send a private messag to the requestd user
	 * 
	 * @param receiver/user
	 * @param msg
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 */
	private synchronized void sendPrivate(String user,String sign,String msg) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		if(loggedIn) {
			for(ServerTCPUserThread tempThread:Server.threadList ) {
				if(tempThread.checkUser(user)) {
					tempThread.sendAES("!msg "+this.user+" "+sign+" "+msg);
					sendAES("Private message to "+user+" has been delivered.");
					return;
				}
			}
			sendAES(user+" is not online or unavailable.");
		} else {
			sendAES("Cannot send - login first.");
		}
	}
	
	/**
	 * Sends RSA encrypted with the users key
	 * @param msg
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private synchronized void sendRSA(String msg) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		sendSelf(secAsym.encryptRSAWithClientPublicKey(msg));
	}
	
	/**
	 * Send AES encrypted
	 */
	private synchronized void sendAES(String msg) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		sendSelf(secSym.encryptAES(msg));
	}
	
	/**
	 * This is the main method: it sends
	 * the given message to the sockets/threads user
	 * @param msg
	 */
	private synchronized void sendSelf(String msg) {
		socketOut.println(msg);
	}
	
	/**
	 * Logs out if logged in and displays result to the client
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 */
	private void logout() throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		if(loggedIn) {
			loggedIn=false;
			sendAES("Successfully logged out.");
			Server.setUsersTableOnlineStatus(user,false);
			exitThread();
		} else {
			sendAES("Not logged in currently.");
		}
	}
	
	/**
	 * Exits this thread an closes all resources
	 */
	public void exitThread() {
		active = false;
		
		try {
			socketIn.close();
			socketOut.close();
			//socket.close();
		} catch (IOException e) {
			System.err.println("[Server/err]["+Thread.currentThread().getId()+"]: Error while trying to close threadsocket.");
		}
	}
}
