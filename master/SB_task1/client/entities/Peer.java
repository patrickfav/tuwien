package entities;

import java.io.Serializable;
import java.util.ArrayList;

import net.jini.core.entry.Entry;

public class Peer implements Entry, Serializable {

	private static final long serialVersionUID = 9175116740426137759L;
	
	public String name;
	public ArrayList<String> invitedEvents;
	public ArrayList<String> ownEvents;
	
	public Peer() {
		this.invitedEvents = new ArrayList<String>();
		this.ownEvents = new ArrayList<String>();
	}
	
	public Peer(String name) {
		super();
		this.name = name;
	}
	
	/* GETTER AND SETTER *****************************************/
	
	public String getName(){
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getInvitedEvents() {
		return invitedEvents;
	}

	public void setInvitedEvents(ArrayList<String> invitedEvents) {
		this.invitedEvents = invitedEvents;
	}

	public ArrayList<String> getOwnEvents() {
		return ownEvents;
	}

	public void setOwnEvents(ArrayList<String> ownEvents) {
		this.ownEvents = ownEvents;
	}
	
}
