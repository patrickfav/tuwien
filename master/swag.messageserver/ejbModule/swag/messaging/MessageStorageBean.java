package swag.messaging;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import swag.bl.messaging.IMessageManagement;
import swag.models.Message;

@Singleton
@Local
public class MessageStorageBean {
	
	private static Logger log = Logger.getLogger("MessageStorageBean");
	
	@EJB
	private IMessageManagement mmb;
	
	private List<Message> messagesCache;
	
	@PostConstruct
	public void setup() {
		messagesCache = Collections.synchronizedList(new ArrayList<Message>());
	}
	
	public void addToCache(Message msg) {
		log.info("Add new Message to cache: "+msg.getId());
		messagesCache.add(msg);
	}
	
	public Message getFromCache(long msgId) {
		for(Message msg:messagesCache) {
			if(msg.getId().equals(msgId))
				return msg;
		}
		
		return null;
	}
	
	@Lock(LockType.WRITE)
	public void removeFromCache(long msgId) {
		Message found = null;
		for(Message msg:messagesCache) {
			if(msg.getId().equals(msgId)) {
				found = msg;
				break;
			}
		}
		
		if(found != null) {
			messagesCache.remove(found);
			log.info("Successfully removed msg "+found.getId()+ " from cache.");
		}
	}
	
	public void persistToDB(Message msg) {
		mmb.persist(msg);
	}
	
	public void updateToDB(Message msg) {
		mmb.update(msg);
	}
	public Message find(long id) {
		return mmb.find(id);
	}
}
