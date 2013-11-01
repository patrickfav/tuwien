package bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.event.ValueChangeEvent;

import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import db.interfaces.ITrialFormDAO;
import entities.TRIALSTATUS;
import entities.Trial;
import entities.TrialForm;
import entities.User;
import entities.event.StatusChangeEvent;

/**
 * @deprecated
 * 
 * @author inso
 *
 */

@Stateful
@Name("TrialStatusManager")
public class TrialStatusManagerBean implements TrialStatusManager {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;
	
	@In
	private SessionInfo sessionInfo;

	private Trial trial;
	
	@In
	private User user;
	
	@In
	private transient Map<String, String> messages;
	
	private TRIALSTATUS oldValue;

	@EJB private ITrialFormDAO trialFormDAO;
	@EJB private EventManager eventManager;
	
	public String changeStatus() {
		this.trial = sessionInfo.getTrial();
		log.info("Switching trialstate from #0 to #1", oldValue, trial.getStatus());
		
		if(oldValue.compareTo(trial.getStatus()) < 0) {
			log.info("Advancing trialstatus to #0", trial.getStatus());
		} else if(oldValue.compareTo(trial.getStatus()) > 0 && trial.getStatus().equals(TRIALSTATUS.CREATE)) {
			log.info("Stepping back to CREATE");
			stepBackToCreate();
		} else if(oldValue.compareTo(trial.getStatus()) > 0 && trial.getStatus().equals(TRIALSTATUS.EXECUTE)) {
			log.info("Stepping back to EXECUTE");
			// TODO: implications ?? 
		}
		StatusChangeEvent sce = new StatusChangeEvent();
		sce.setUser(user);
		sce.setTrial(trial);
		sce.setFrom(oldValue);
		sce.setTo(trial.getStatus());
		eventManager.registerEvent(sce);
		return null;

	}

	private void stepBackToCreate() {
		String duplicates = "";
		int advanceSort = 0;
		for(TrialForm tf : trial.getTrialForms()) {
			if(advanceSort != 0)	// make sure sort stays correct
				tf.setSort(tf.getSort() + advanceSort);
			
			if(!tf.isEditable() && !tf.getTrialSpecific()) {	// time to duplicate
				TrialForm dup = tf.duplicate();
				dup.setSort(tf.getSort() + 1);
				advanceSort++;
				trialFormDAO.persist(dup);
				tf.setName(renameTF(dup.getName()));
				duplicates.concat(dup.getName() + " was duplicated and renamed to " + tf.getName() + "\n");
			}			
		}
		// TODO communicate eventual changes to user...
	}
	
	private String renameTF(String tfName) {
		DateFormat expDate = new SimpleDateFormat(messages.get("dateformat"));
		DateFormat expTime = new SimpleDateFormat(messages.get("timeformat"));
		Date current = new Date(System.currentTimeMillis());
		
		String name = tfName + " " +
			messages.get("label.validuntil") + " " +
			expDate.format(current) + " " +
			expTime.format(current);
		
		return name;
	}

	public void statusChanged(ValueChangeEvent vce) {
		log.info("Trialstatus change registered: old: #0, new #1", vce.getOldValue(), vce.getNewValue());
		this.oldValue = (TRIALSTATUS)vce.getOldValue();
	}
	
	@Remove
	@Destroy
	public void destroy() { }

}
