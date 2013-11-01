package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.jini.core.entry.Entry;

public class DateOption implements Entry, Serializable {

	private static final long serialVersionUID = -2501640958453739807L;
	
	public Date date;
	public List<Peer> peersForDate;
	
	public DateOption() {
		super();
		this.peersForDate = new ArrayList<Peer>();
	}
	
	public DateOption(Date date) {
		super();
		this.date = date;
		this.peersForDate = new ArrayList<Peer>();
	}
	
	public DateOption(Date date, List<Peer> peersForDate) {
		super();
		this.date = date;
		this.peersForDate = peersForDate;
	}
	
	/* GETTER AND SETTER *****************************************/

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<Peer> getPeersForDate() {
		return peersForDate;
	}
	public void setPeersForDate(List<Peer> peersForDate) {
		this.peersForDate = peersForDate;
	}
	
	
	
}
