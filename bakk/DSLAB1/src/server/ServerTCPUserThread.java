package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.regex.Pattern;

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
	private String pswd;
	private String cmd;
	private String[] inputArray;
	private Boolean active;
	private Boolean loggedIn;
	
	private String buffer;
	
	/**
	 * CONSTRUCTOR 
	 * @param socket
	 */
	
	public ServerTCPUserThread(Socket socket) {
		this.socket = socket;
		active = new Boolean(true);
		loggedIn = new Boolean(false);
		
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
			while(active && (buffer = socketIn.readLine()) != null) {
				
				/* Split input with regex and save in array */
				Pattern p = Pattern.compile(" ");
				inputArray = p.split(buffer);
				
				cmd = inputArray[0];
			
				/* the command switch */
				if (cmd.equals("!login")) {
					try {
						login(inputArray[1],inputArray[2]);
					} catch(Exception e) {
						sendSelf("Unknown requset / error while reading login");
					}
				} else if(cmd.equals("!logout")) {
					logout();
				} else if(cmd.equals("!send")) {
					try {
						send(Server.specialConcatStringArray(Arrays.copyOfRange(inputArray, 1, inputArray.length)));
						
					} catch(Exception e) {
						sendSelf("Unknown requset / error while sending");
					}	
				} else if(cmd.equals("!msg")) {
					try {
						sendPrivate(inputArray[1],Server.specialConcatStringArray(Arrays.copyOfRange(inputArray, 2, inputArray.length)));
					} catch(Exception e) {
						sendSelf("Unknown requset / error while sending");
					}	
				} else {
					sendSelf("Unknown requset");
				}
			}
			
		} catch (IOException e) {
			System.err.println("[Server/err]["+Thread.currentThread().getId()+"]:Error while reading from Socket (IO):"+e);
			//e.printStackTrace();
			exitThread();
		} catch (NullPointerException e) {
			System.err.println("[Server/err]["+Thread.currentThread().getId()+"]:Error while reading from Socket (Nullpointer):"+e);
			exitThread();
		} finally {
			if(loggedIn)
				logout();
			
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
	 * @param pswd
	 */
	private void login(String user, String pswd) {
		if(!loggedIn) {
			this.user = user;
			this.pswd = pswd;
			
			if(Server.usersTable.containsKey(this.user) && ((User) Server.usersTable.get(this.user)).getOnline()) {
				sendSelf("This user has already logged in with another client.");
			} else if(Server.userData.containsKey(this.user) && this.pswd.equals(Server.userData.getString(this.user))) {
				loggedIn = true;
				sendSelf("Successfully logged in.");
				Server.setUsersTableOnlineStatus(user,true);
			} else {
				sendSelf("Wrong username or password.");
			}
		} else {
			sendSelf("Already logged in.");
		}
	}
	
	/**
	 * Sends the msg to all online (logged in)
	 * users in the chatroom.
	 * @param msg
	 */
	private synchronized void send(String msg) {
		if(loggedIn) {
			for(ServerTCPUserThread tempThread:Server.threadList ) {
				if(tempThread.checkLogin()) {
					tempThread.sendSelf(user+": "+ msg);
				}
			}
		} else {
			sendSelf("Cannot send - login first.");
		}
	}
	
	/**
	 * Trys to send a private messag to the requestd user
	 * 
	 * @param receiver/user
	 * @param msg
	 */
	private synchronized void sendPrivate(String user,String msg) {
		if(loggedIn) {
			for(ServerTCPUserThread tempThread:Server.threadList ) {
				if(tempThread.checkUser(user)) {
					tempThread.sendSelf(this.user+"[private]: "+msg);
					sendSelf("Private message to "+user+" has been delivered.");
					return;
				}
			}
			sendSelf(user+" is not online or unavailable.");
		} else {
			sendSelf("Cannot send - login first.");
		}
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
	 */
	private void logout() {
		if(loggedIn) {
			loggedIn=false;
			sendSelf("Successfully logged out.");
			Server.setUsersTableOnlineStatus(user,false);
			exitThread();
		} else {
			sendSelf("Not logged in currently.");
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
