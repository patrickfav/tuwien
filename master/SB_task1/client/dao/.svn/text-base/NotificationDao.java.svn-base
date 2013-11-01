package dao;

import java.rmi.MarshalledObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import dao.abstracts.AbstractDao;

import net.jini.core.event.RemoteEventListener;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;
import entities.Notification;
import entities.Peer;
import gui.mem.GuiMemory;

public class NotificationDao extends AbstractDao{

	private static Logger log = Logger.getLogger(NotificationDao.class);
	private JavaSpace space;
	
	public static NotificationDao createDao() {
		return new NotificationDao();
	}
	
	public NotificationDao() {
		super();
		this.space = GuiMemory.getSpaceInstance(); 
	}
	
	public Notification find(Notification notification) {
		return find(notification,null, DEFAULT_READ_TIME);
	}
	
	public List<Notification> takeAllFromPeer(Peer p) {
		Notification n = new Notification();
		List<Notification> list = new ArrayList<Notification>();
		
		while(true) {
			n = takeFromPeer(p);
			if(n != null) {
				list.add(n);
			} else {
				break;
			}
		}
		
		return list;
	}
	
	public Notification takeFromPeer(Peer p) {
		Notification nTmpl = new Notification();
		nTmpl.setReceiverName(p.getName());
		return take(nTmpl,null, 0);
	}
	
	public Notification take(Notification notification) {
		return take(notification,null, 0);
	}
	
	public Notification take(Notification notification,Transaction t, long lease) {
		try {
			log.debug("Take "+notification+" from space.");
			return (Notification)space.takeIfExists(notification, t, lease);
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			return null;
		}
	}
	

	
	public Notification find(Notification notification,Transaction t, long lease) {
		try {
			log.debug("Read "+notification+" from space.");
			return (Notification)space.readIfExists(notification, t, lease);
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			return null;
		}
	}
	
	public void write(Notification notification) {
		write(notification,null,Lease.FOREVER);
	}
	
	public void write(Notification notification,Transaction t, long lease) {
		try {
			space.write(notification, t, lease);
			log.debug("Writing "+notification+" to space.");
		} catch (RemoteException e) {
			log.error("Remote Exception: " + e.getMessage());
		} catch (TransactionException e) {
			log.error("Transaction Exception: " + e.getMessage());
		}
	}
	
	public void notify(Notification tpl,Transaction txn, RemoteEventListener l, long lease, MarshalledObject<?> m) {
		try {
			space.notify(tpl, txn, l, Lease.FOREVER, m);
		} catch (RemoteException e) {
			log.error("Remote Exception while notify: " + e.getMessage());
		} catch (TransactionException e) {
			log.error("Transaction Exception while notify: " + e.getMessage());;
		}
	}
	
}
