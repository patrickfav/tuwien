package bean;


import javax.ejb.Local;

import entities.event.Mailable;

@Local
public interface MailNotification {
	
//	@Asynchronous
	public void notifyUsers(Mailable event);
	
	public void init();
	
}
