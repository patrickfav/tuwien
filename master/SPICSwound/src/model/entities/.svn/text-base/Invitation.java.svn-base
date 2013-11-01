package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="INVITATION")
public class Invitation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	@Column(name="INVITATION_ID")
	private Long invitation;
	
	@Column(name="OPEN")
	private Boolean open;
	
	@Column(name="INVITATION_MESSAGE")
	private String invitationMessage;
	
	@Column(name="ANSWER_MESSAGE")
	private String answerMessage;
	
	@Column(name="INVITATIONDATE")
	private Date invitationDate;
	
	@Column(name="ACCEPTIONDATE")
	private Date acceptionDate;
	
	@OneToOne
	private User sender;
	
	@OneToOne
	private User receiver;
	
	@OneToOne
	private Trial forTrial;

	public Date getAcceptionDate() {
		return acceptionDate;
	}

	public void setAcceptionDate(Date acceptionDate) {
		this.acceptionDate = acceptionDate;
	}

	public String getAnswerMessage() {
		return answerMessage;
	}

	public void setAnswerMessage(String answerMessage) {
		this.answerMessage = answerMessage;
	}

	public Trial getForTrial() {
		return forTrial;
	}

	public void setForTrial(Trial forTrial) {
		this.forTrial = forTrial;
	}

	public Long getInvitation() {
		return invitation;
	}

	public void setInvitation(Long invitation) {
		this.invitation = invitation;
	}

	public Date getInvitationDate() {
		return invitationDate;
	}

	public void setInvitationDate(Date invitationDate) {
		this.invitationDate = invitationDate;
	}

	public String getInvitationMessage() {
		return invitationMessage;
	}

	public void setInvitationMessage(String invitationMessage) {
		this.invitationMessage = invitationMessage;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}
	
	

}
