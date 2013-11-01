package dao;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import dao.abstracts.AbstractDao;
import entities.DateOption;
import entities.Event;
import entities.Peer;
import gui.mem.GuiMemory;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

import org.apache.log4j.Logger;

public class EventDao extends AbstractDao {

	private static Logger log = Logger.getLogger(EventDao.class);
	private PeerDao pDao;
	private JavaSpace space;
	
	public static EventDao createDao() {
		return new EventDao();
	}
	
	public EventDao() {
		super();
		this.space = GuiMemory.getSpaceInstance(); 
		pDao = new PeerDao();
	}
	
	
	public boolean checkIfAllHaveChoosen(Event event) {
		 Event foundEvent = find(event);
		 List<Peer> choosenList = new ArrayList<Peer>();
		 for(DateOption dOpt : foundEvent.getDateOptions()) {
			 choosenList.addAll(dOpt.getPeersForDate());
		 }
		 
		 if(choosenList.size() == foundEvent.getInvitedPeers().size())
			 return true;
		 
		return false;
	}
	
	public boolean checkIfHasChoosen(Event event, Peer peer) {
		 if(getChoosenDateOption(event,peer) == null)
			 return false;
		 
		 return true;
	}
	
	public DateOption getChoosenDateOption(Event event, Peer peer) {
		 Event foundEvent = find(event);
		 for(DateOption dOpt : foundEvent.getDateOptions()) {
			 for(Peer p:dOpt.getPeersForDate()) {
				 if(p.getName().equals(peer.getName()))
					 return dOpt;
			 }
		 }
		return null;
	}
	
	public List<Event> findAllInvitedEventsFromPeer(Peer peer) {
		List<Event> list = new ArrayList<Event>();
		Event eTemp = getEmptyTemplate();
		Event foundEvent;
		Peer temp = pDao.getEmptyTemplate();
		temp.setName(peer.getName());
		Peer found = pDao.find(temp);
		
		if(found.getInvitedEvents() != null)
			for(String name:found.getInvitedEvents()) {
				eTemp.setName(name);
				foundEvent = find(eTemp);
				
				if(foundEvent != null)
					list.add(foundEvent);
			}
		
		return list;
	}
	
	public List<Event> findAllOwnEventsFromPeer(Peer peer) {
		List<Event> list = new ArrayList<Event>();
		Event eTemp = getEmptyTemplate();
		Event foundEvent;
		Peer temp = pDao.getEmptyTemplate();
		temp.setName(peer.getName());
		Peer found = pDao.find(temp);
		
		if(found.getOwnEvents() != null)
			for(String name:found.getOwnEvents()) {
				eTemp.setName(name);
				foundEvent = find(eTemp);
				
				if(foundEvent != null)
					list.add(foundEvent);
			}
		
		return list;
	}
	
	public Event findByParticipant(Peer peer) {
		Event event = new Event();
		ArrayList<Peer> peers = new ArrayList<Peer>();
		peers.add(peer);
		event.setInvitedPeers(peers);
		return find(event);
	}
	
	public Event findByOrganisator(Peer peer) {
		Event event = new Event();
		event.setOrganisator(peer.getName());
		return find(event);
	}
	
	public Event find(Event event) {
		Event temp = getEmptyTemplate();
		temp.setName(event.getName());
		return find(temp, null, DEFAULT_READ_TIME);
	}
	
	public Event find(Event event, Transaction t, long lease) {
		try {
			return (Event)space.readIfExists(event, t, lease);
		} catch (Exception e) {
			log.error("Exception: " + e.getStackTrace());
			return null;
		}
	}
	
	public void write(Event event) {
		write(event, null, Lease.FOREVER);
	}
	
	public void write(Event event, Transaction t, long lease) {
		try {
			space.write(event, t, lease);
		} catch (RemoteException e) {
			log.error("Remote Exception: " + e.getStackTrace());
		} catch (TransactionException e) {
			log.error("Transaction Exception: " + e.getStackTrace());
		}
	}
	
	public void update(Event event) {
		log.debug("Update invited " + event + " ");
		Event temp = getEmptyTemplate();
		temp.setName(event.getName());
		Event e = take(temp, null, Long.MAX_VALUE);
		e.setInvitedPeers(event.getInvitedPeers());
		e.setComments(event.getComments());
		e.setDateOptions(event.getDateOptions());
		e.setFinalDate(event.getFinalDate());
		e.setName(event.getName());
		write(e, null, Lease.FOREVER);
	}
	
	public Event take(Event temp) {
		Event tpl = getEmptyTemplate();
		tpl.setName(temp.getName());
		return take(tpl, null, 0);
		
	}
	public Event take(Event temp, Transaction txn, long lease) {
		try {
			log.debug("Take " + temp + " from space");
			return (Event)space.takeIfExists(temp, txn, lease);
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			return null;
		}
	}
	
	public Event getEmptyTemplate() {
		Event event = new Event();
		event.comments = null;
		event.dateOptions = null;
		event.invitedPeers = null;
		
		return event;
	}
}
