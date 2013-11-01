package entities.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.User;

@Entity
@DiscriminatorValue("ERROR")
public class ErrorEvent extends Event implements Mailable {

	private static final long serialVersionUID = 1L;
	private static final Map<String, List<User>> notificationTargets = new HashMap<String, List<User>>(); 
	
	@Column(length=2500)
	private String errorMsg;
	
	@Column
	private String errorType;

	@Override
	public String getEventString() {
		return EVENTSTRINGBASE + EVENTTYPE.ERROR.toString();
	}
	
	public Map<String, List<User>> getNotificationTargets() {
		return notificationTargets;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	
	public boolean isNotifyAdmin(){
		return true;
	}
	
	public boolean isNotifyAdminOnly() {
		return true;
	}
	
	@Override
	public String getEventText() {
		return errorMsg;
	}
	
	

}
