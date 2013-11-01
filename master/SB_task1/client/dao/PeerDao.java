package dao;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import dao.abstracts.AbstractDao;

import net.jini.core.lease.Lease;
import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;
import entities.Event;
import entities.Peer;
import gui.mem.GuiMemory;

public class PeerDao extends AbstractDao {

	private static Logger log = Logger.getLogger(PeerDao.class);
	private JavaSpace space;

	public static PeerDao createDao() {
		return new PeerDao();
	}

	public PeerDao() {
		super();
		this.space = GuiMemory.getSpaceInstance();
	}

	public void updateOwn(Peer peer, Event event) {
		log.debug("Update own " + peer + " ");
		Peer temp = getEmptyTemplate();
		temp.setName(peer.getName());
		Peer p = take(temp, null, Long.MAX_VALUE);
		if (p.getOwnEvents() == null) {
			p.setOwnEvents(new ArrayList<String>());
		}
		if (!p.getOwnEvents().contains(event.getName())) {
			p.getOwnEvents().add(event.getName());
		}
		write(p, null, Lease.FOREVER);
	}

	public void updateInvite(Peer peer, Event event) {
		log.debug("Update invited " + peer + " ");
		Peer temp = getEmptyTemplate();
		temp.setName(peer.getName());
		Peer p = take(temp, null, Long.MAX_VALUE);
		if (p.getInvitedEvents() == null) {
			p.setInvitedEvents(new ArrayList<String>());
		}
		if (!p.getInvitedEvents().contains(event.getName())) {
			p.getInvitedEvents().add(event.getName());
		}
		write(p, null, Lease.FOREVER);
	}
	
	public void updateRemoveInvite(Peer peer, Event event) {
		log.debug("Update removed invited " + peer + " ");
		Peer temp = getEmptyTemplate();
		temp.setName(peer.getName());
		Peer p = take(temp, null, Long.MAX_VALUE);
		if (p.getInvitedEvents() == null) {
			p.setInvitedEvents(new ArrayList<String>());
		}
		if (p.getInvitedEvents().contains(event.getName())) {
			p.getInvitedEvents().remove(event.getName());
		}
		write(p, null, Lease.FOREVER);
	}
	
	public void updateRemoveOwn(Peer peer, Event event) {
		log.debug("Update removed own " + peer + " ");
		Peer temp = getEmptyTemplate();
		temp.setName(peer.getName());
		Peer p = take(temp, null, Long.MAX_VALUE);
		if (p.getOwnEvents() == null) {
			p.setOwnEvents(new ArrayList<String>());
		}
		if (p.getOwnEvents().contains(event.getName())) {
			p.getOwnEvents().remove(event.getName());
		}
		write(p, null, Lease.FOREVER);
	}

	public Peer take(Peer temp, Transaction txn, long lease) {
		try {
			log.debug("Take " + temp + " from space");
			return (Peer) space.takeIfExists(temp, txn, lease);
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			return null;
		}
	}

	public Peer find(Peer peer) {
		return find(peer, null, DEFAULT_READ_TIME);
	}

	public Peer find(Peer peer, Transaction t, long lease) {
		try {
			Peer temp = new Peer(peer.getName());
			log.debug("Read " + peer + " from space");
			return (Peer) space.readIfExists(temp, t, lease);
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public void write(Peer peer, Transaction txn, long lease) {
		try {
			log.debug("Write " + peer + " to space");
			space.write(peer, txn, lease);
		} catch (RemoteException e) {
			log.error("Remote Exception: " + e.getMessage());
		} catch (TransactionException e) {
			log.error("Transaction Exception: " + e.getMessage());
		}
	}

	public Peer getEmptyTemplate() {
		Peer temp = new Peer();
		temp.setInvitedEvents(null);
		temp.setOwnEvents(null);
		return temp;
	}

}
