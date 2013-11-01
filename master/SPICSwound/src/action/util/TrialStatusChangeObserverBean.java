package util;

import javax.ejb.Stateless;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.log.Log;

import entities.event.StatusChangeEvent;

/**
 * This is just a demonstration on how to observe Spics events
 * 
 * @author inso
 *
 */
@Stateless
@Name("TrialStatusChangeObserver")
public class TrialStatusChangeObserverBean implements TrialStatusChangeObserver {

	private static final long serialVersionUID = 1L;
	
	@Logger
	private Log log;
	
	@Observer("spics.event.trial.STATUSCHANGE")
	public void handleStatusChange(StatusChangeEvent e) {
		log.info("Trialstatuschange event observed - change from: " + e.getFrom() + " to: " + e.getTo());
		
	}

}
