package entities;

import java.io.Serializable;
import java.util.Date;

import net.jini.core.entry.Entry;

public class Notification implements Entry, Serializable {
	
	private static final long serialVersionUID = 2457066793778703319L;
	public final static int EDIT_EVENT_PARTICIPANT = 0;
	public final static int EDIT_EVENT_ADMIN = 5;
	public final static int NEW_EVENT_PARTICIPANT = 1;
	public final static int NEW_EVENT_ADMIN = 3;
	public final static int DELETED_EVENT = 2;
	public final static int FIXED_DATE = 4;
	public final static int ALL_REGISTERED = 8;
	public final static int COMMENT_ADDED = 16;
	public final static int UNINVITED = 32;
	public final static int COMMENT_DELETED = 64;
	public final static int SYSTEM_UPDATE = 1024; //wont show in gui
	
	public Date date;
	public String note;
	public Integer type;
	public String eventName;
	public String receiverName;

	
	public Notification(Date date, String note, int type, String eventName,
			String receiverName) {
		super();
		this.date = date;
		this.note = note;
		this.type = type;
		this.eventName = eventName;
		this.receiverName = receiverName;
	}
	
	public Notification(int type, String eventName, String receiverName) {
		super();
		this.date = new Date();
		this.note = "";
		this.type = type;
		this.eventName = eventName;
		this.receiverName = receiverName;
	}
	
	public Notification() {
		super();
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverName() {
		return receiverName;
	}
	
	
	
}
