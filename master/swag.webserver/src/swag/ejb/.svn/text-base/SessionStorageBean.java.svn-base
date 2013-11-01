package swag.ejb;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import swag.gui.SessionManager;
import swag.models.Message;

@Singleton
@Startup
@Local
public class SessionStorageBean {
	
	private static Logger log = Logger.getLogger("SessionStorageBean");
	
	private static final int LOGIN_TIMEOUT = 20*60*1000; //20 min in milliseconds
	
	private  Map<Long,SessionManager> sessionInstances;
	
	
	@PostConstruct
	public void setup() {
		sessionInstances = Collections.synchronizedMap(new HashMap<Long,SessionManager>());
	}
	
	@Lock(LockType.WRITE)
	public void addInstance(Long userId, SessionManager instance) {
		log.info("Adding new SessionManager for User "+userId);
		sessionInstances.put(userId, instance);
	}
	
	@Lock(LockType.WRITE)
	public void removeInstance(Long userId) {
		if(sessionInstances.containsKey(userId))
			sessionInstances.remove(userId);
	}
	
	public boolean deliverMessage(Long userId, Message msg) {
		if(sessionInstances.containsKey(userId)) {
			log.info("Message ready to deliver to "+userId+" (Msg:"+msg.getId()+")");
			sessionInstances.get(userId).addNewDeliveredMessage(msg);
			return true;
		}
		
		return false;
	}
	
	@Lock(LockType.WRITE)
	public void checkAllInstances() {
		log.info("Checking all sessions, if login has timed out...");
		Date timestamp;
		for(long id: sessionInstances.keySet()) {
			timestamp = sessionInstances.get(id).getLastActivity();
			
			/* if last activity too old, the system logs the user out */
			if((timestamp.getTime()+LOGIN_TIMEOUT) <= (new Date()).getTime()) {
				log.info("User "+id+" was logged out due to time out.");
				sessionInstances.get(id).logoutInternal();
				sessionInstances.remove(id);
			}
		}
	}
	
	
}
