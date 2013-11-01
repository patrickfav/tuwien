package swag.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import swag.models.Message;
import swag.models.User;

public class MessageDTO implements Serializable {

	private static final long serialVersionUID = 2815694677346179383L;
	
	private Long id;
	private User sender;
	private String subject;
	private Date timestamp;
	private User reciever;
	private String text;
	private Date sendDate;
	private Boolean error;
	private boolean alreadySent;
	private boolean sentByMail;

	
	public MessageDTO() {
	}

    public MessageDTO(Message msg) {
    	
		if(msg.getId() != null)
			id = msg.getId();
		
		if(msg.getSender() != null)
			sender = msg.getSender();
		
		if(msg.getSubject() != null)
			subject = msg.getSubject();
		
		if(msg.getTimestamp() != null)
			timestamp = msg.getTimestamp();
		
		if(msg.getReceiver() != null)
			reciever = msg.getReceiver();
		
		if(msg.getText() != null)
			text = msg.getText();
		
		if(msg.getSendDate() != null)
			sendDate = msg.getSendDate();
		
		if(msg.getError() != null)
			error = msg.getError();
		
		alreadySent = msg.isAlreadySent();
		sentByMail = msg.isSentByMail();
		
	}
	
    public Message getAsMessage() {
    	Message msg = new Message();
    	msg.setError(error);
    	msg.setId(id);
    	msg.setReceiver(reciever);
    	msg.setSubject(subject);
    	msg.setTimestamp(new Date());
    	msg.setSender(sender);
    	msg.setText(text);
    	msg.setSendDate(sendDate);
    	msg.setAlreadySent(alreadySent);
    	msg.setSentByMail(sentByMail);
    	
    	return msg;
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

	public void setReciever(User reciever) {
		this.reciever = reciever;

	}

	public User getReciever() {
		return reciever;
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
}
