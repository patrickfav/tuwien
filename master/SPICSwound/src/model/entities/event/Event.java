package entities.event;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import entities.User;

@Entity
@Table(name="EVENT")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="EVENTTYPE",discriminatorType=DiscriminatorType.STRING)
public abstract class Event implements Serializable, Comparable<Event>{

	private static final long serialVersionUID = 1L;
	
	public static final String EVENTSTRINGBASE = "spics.event.";

	@Id @GeneratedValue
    @Column(name="EVENT_ID")
    private Long id;
	
	@Enumerated(EnumType.STRING)
    @Column(name="EVENTTYPE", insertable=false, updatable=false, nullable=false)
	private EVENTTYPE eventtype;
	
	@ManyToOne
	private User user;
	
	@Column(name="TIMESTAMP")
	private Date timestamp;
	
	public Event() {
		this.timestamp = new Date(System.currentTimeMillis());
	}
	
	public Event(User user) {
		this();
		this.user = user;
	}

	public EVENTTYPE getEventtype() {
		return eventtype;
	}

	public void setEventtype(EVENTTYPE eventtype) {
		this.eventtype = eventtype;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * compares two events, later one comes first (is "smaller"), used
	 * for chronologically sorting an event list
	 */
	public int compareTo(Event e) {
		return this.getTimestamp().compareTo(e.getTimestamp());
	}
	
	public abstract String getEventString();
	
	/**
	 * hook to display additional information about a specific event, default implementation
	 * returns an empty string
	 * @return
	 */
	public String getEventText() {
		return "";
	}
	
}
