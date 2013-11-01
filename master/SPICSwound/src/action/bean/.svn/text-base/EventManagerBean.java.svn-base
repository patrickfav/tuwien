package bean;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Log;

import util.DeploymentAction;
import db.interfaces.IEventDAO;
import entities.event.Event;
import entities.event.Mailable;

@Stateless
@Name("EventManager")
@Scope(ScopeType.APPLICATION)
@Startup
public class EventManagerBean implements EventManager {

	@Logger
	private Log log;
	
	@EJB private IEventDAO eventDAO;
	
	@In(create = false, required = true, value = "DeploymentAction")
	private DeploymentAction deployment;
	
	@In(value = "MailNotification", create = true)
	private MailNotification notification;
	
	public void registerEvent(Event event, boolean noLog) {
		persistEvent(event);
		
		if(!noLog)
			logEvent(event);
		
		raiseSeamEvent(event);
	}
	
	public void registerEvent(Event event) {
		registerEvent(event, false);
	}
	
	private void persistEvent(Event event) {
		eventDAO.persist(event);
		
		if (deployment.isMailNotificationEnabled() && (event instanceof Mailable)) {
			notification.notifyUsers((Mailable)event);
		}
	}

	private void logEvent(Event e) {
		log.info("Spics Event: " + e.getEventString() + " raised for user: " + e.getUser().getUsername());
	}
	
	private void raiseSeamEvent(Event e) {
		Events.instance().raiseEvent(e.getEventString(), e);
	}

}
