package swag.models;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;
import swag.models.enums.*;
import swag.util.*;

@Entity
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1078325012297691956L;

	@Id
	@GeneratedValue
	private Long id;
	@OneToOne()
	private User sender;
	private String subject;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	private User receiver;
	@Column(length=10000)
	private String text;
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendDate;
	private Boolean error;
	private boolean alreadySent;
	private boolean sentByMail;
	
	public Message() {
		alreadySent = false;
		sentByMail = false;
		error = false;
	}
	
	

	public boolean isAlreadySent() {
		return alreadySent;
	}

	public void setAlreadySent(boolean alreadySent) {
		this.alreadySent = alreadySent;
	}

	public boolean isSentByMail() {
		return sentByMail;
	}

	public void setSentByMail(boolean sentByMail) {
		this.sentByMail = sentByMail;
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	public void setSender(User sender) {
		this.sender = sender;

	}

	public User getSender() {
		return sender;
	}

	public void setSubject(String subject) {
		this.subject = subject;

	}

	public String getSubject() {
		return subject;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;

	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setReceiver(User reciever) {
		this.receiver = reciever;

	}

	public User getReceiver() {
		return receiver;
	}

	public void setText(String text) {
		this.text = text;

	}

	public String getText() {
		return text;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;

	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setError(Boolean error) {
		this.error = error;

	}

	public Boolean getError() {
		return error;
	}
	
	@Override
	public boolean equals(Object cmp) {
		if(cmp instanceof Message) {
			return this.getId().equals(((Message) cmp).getId());
		}
		
		return false;
	}
	
}
