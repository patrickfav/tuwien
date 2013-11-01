package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.jini.core.entry.Entry;

public class Event implements Entry, Serializable {
	
	private static final long serialVersionUID = 7954842085200185994L;
	
	public String name;
	public String organisator;
	public List<Peer> invitedPeers;
	public List<DateOption> dateOptions;
	public List<Comment> comments;
	public Date finalDate;
	
	public Event() {
		super();
		this.invitedPeers = new ArrayList<Peer>();
		this.dateOptions =  new ArrayList<DateOption>();
		this.comments = new ArrayList<Comment>();
	}
	
	public Event(String name, String organisator, List<Peer> invitedPeers,
			List<DateOption> dateOptions, List<Comment> comments, Date finalDate) {
		super();
		this.name = name;
		this.organisator = organisator;
		this.invitedPeers = invitedPeers;
		this.dateOptions = dateOptions;
		this.comments = comments;
		this.finalDate = finalDate;
	}
	
	/* GETTER AND SETTER *****************************************/

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrganisator() {
		return organisator;
	}
	public void setOrganisator(String organisator) {
		this.organisator = organisator;
	}
	public List<Peer> getInvitedPeers() {
		return invitedPeers;
	}
	public void setInvitedPeers(List<Peer> invitedPeers) {
		this.invitedPeers = invitedPeers;
	}
	public List<DateOption> getDateOptions() {
		return dateOptions;
	}
	public void setDateOptions(List<DateOption> dateOptions) {
		this.dateOptions = dateOptions;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Date getFinalDate() {
		return finalDate;
	}
	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}
	
	
	
}
