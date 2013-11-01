package bean;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;
import org.richfaces.model.UploadItem;

import util.JSFNavigationConstants;
import util.SpicsPermissions;
import db.interfaces.IParticipationDAO;
import db.interfaces.ITrialAttachmentDAO;
import db.interfaces.ITrialDAO;
import db.interfaces.IUserDAO;
import entities.Participation;
import entities.TRIALSTATUS;
import entities.Trial;
import entities.TrialAttachment;
import entities.User;
import entities.event.StatusChangeEvent;
import entities.event.TrialCreateEvent;
import entities.event.TrialEditEvent;

@Stateful
@Scope(ScopeType.CONVERSATION)
@Name("trialEditor")
public class TrialEditorBean implements TrialEditor {

	private static final long serialVersionUID = 1L;
	
	public static final String PARAM_TRIAL_ID = "trialId";

	@RequestParameter(PARAM_TRIAL_ID)
	private Long trialId;
	
	@Logger
	private Log log;

	@In(create=true)
	private ITrialDAO trialDAO;
	
	@EJB
	private IParticipationDAO participationDAO;
	@EJB
	private IUserDAO userDAO;
	@EJB
	private ITrialAttachmentDAO trialAttachmentDAO;
	@EJB
	private EventManager eventManager;

	private TRIALSTATUS status = TRIALSTATUS.CREATE;

	@In
	private User user;

	private Trial trial;

	@In("Menu")
	private Menu menu;

	@In
	private SessionInfo sessionInfo;

	private transient List<UploadItem> uploads = new LinkedList<UploadItem>();

	@End
	public String saveTrial() {
		log.info("Saving trial: #0", this.trial.getName());

		if (this.validateDateRange() == false) {
			return JSFNavigationConstants.RELOADPAGE;
		}

		for (UploadItem ui : this.uploads) {
			this.log.info("File uploaded in trial #0, filename: ", this.trial
					.getName(), ui.getFileName());
			TrialAttachment ta = new TrialAttachment();
			ta.setTrial(trial);

			try {
				ta.loadUploadItem(ui);
			} catch (IOException e) {
				log.warn("Loading of file #0 failed... due to #1", ui
						.getFileName(), e.getMessage());
				continue;
			}
			this.trial.getTrialAttachments().add(ta);

		}

		// empty upload list
		uploads = new LinkedList<UploadItem>();

		if (this.trial.getId() == null) {
			log.info("New trial #0", this.trial.getName());
			
			this.trial.setUser(user);

			Participation p = new Participation(); // create Participation
			// between creator and new
			// trial
			p.setParticipatingSince(new Date(System.currentTimeMillis()));
			p.setUser(user);
			p.setTrial(trial);
			p.setCanViewAllPatients(true);
			this.participationDAO.persist(p);
			this.user = userDAO.merge(user);
			trial.getParticipators().add(p);
			user.getParticipations().add(p);

			this.trialDAO.persist(this.trial);
			sessionInfo.setTrialID(this.trial.getId());

			TrialCreateEvent tce = new TrialCreateEvent();
			tce.setUser(user);
			tce.setTrial(this.trial);
			eventManager.registerEvent(tce);

			SpicsPermissions.instance().grantOwner(trial, Identity.instance().getPrincipal());
			
			this.log.info("Trial #0 #1 created", this.trial.getName(),
					this.trial.getId());

		} else {
			log.info("editing trial: new status: #0 old status #1", status,
					trial.getStatus());
			TrialEditEvent tee = new TrialEditEvent();
			if (!trial.getStatus().equals(status)) {
				tee = new StatusChangeEvent(trial.getStatus(), status);
				trial.setStatus(status);
			}
			tee.setUser(user);
			tee.setTrial(this.trial);
			// make sure the correct version is outjected (the reference
			// returned by merge)
			this.trial = this.trialDAO.merge(this.trial);
			this.log.info("Studie #0 #1 wurde aktualisiert", this.trial
					.getName(), this.trial.getId());
			eventManager.registerEvent(tee);
		}

		menu.setActive(JSFNavigationConstants.SHOWMYSTUDIES);
		
		log.info("sessioninfo is set to trial id #0", sessionInfo.getTrialID());
		
		return JSFNavigationConstants.VIEWTRIAL;
	}

	@End(root=true)
	public String cancelTrial() {
		log.info("Cancel editing trial");
		
		menu.setActive(JSFNavigationConstants.SHOWMYSTUDIES);

		if (this.sessionInfo.getTrial() != null) {
			return JSFNavigationConstants.VIEWTRIAL;
		} else {
			return JSFNavigationConstants.SHOWMYSTUDIES;
		}
	}
	
	public void onLoad() {
		if(this.trial != null) {
			log.info("ajax call registered! ignoring call to onLoad since we are already registered! current trialId: #0", this.trial.getId());
			return;
		}
		if(trialId !=  null) {
			this.trial = trialDAO.findByID(trialId);
			log.info("editing trial with id #0", trialId);
			this.status = this.trial.getStatus();
			
		} else {	// new trial
			this.trial = new Trial();
			this.sessionInfo.setTrialID(null);
			log.info("Creating new trial");
		}
	}

	public boolean isNewTrial() {
		return (this.trial == null || this.trial.getId() == null);
	}

	public TRIALSTATUS getStatus() {
		return this.status;
	}

	public void setStatus(TRIALSTATUS status) {
		this.status = status;
	}

	public void deleteTrialAttachment(Long trialAttachmentId) {
		log.info("Deleting trialattachment with id #0", trialAttachmentId);

		// delete from DB
		trialAttachmentDAO.deleteById(trialAttachmentId);
		
		TrialAttachment ta = null;
		
		for(TrialAttachment taIt : this.trial.getTrialAttachments()) {
			if(taIt.getId().equals(trialAttachmentId)) {
				ta = taIt;
			}
		}
				
		this.trial.getTrialAttachments().remove(ta);
		
		log.info("there are #0 attachments left", this.trial.getTrialAttachments().size());
		
	}

	@Remove
	@Destroy
	public void destroy() {
		log.info("destroyed...");
	}

	protected boolean validateDateRange() {
		if ((this.trial.getBeginDate() != null)
				&& (this.trial.getEndDate() != null)
				&& (this.trial.getBeginDate().after(this.trial.getEndDate()))) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"newTrialForm:beginDate", Severity.ERROR,
					"validator.date_range");
			return false;
		}
		return true;
	}

	public Trial getTrial() {
		return this.trial;
	}

	public void setTrial(Trial trial) {
		this.trial = trial;
	}

	// for richfileupload... do not change signature
	public List<UploadItem> getUploads() {
		return uploads;
	}

	public void setUploads(List<UploadItem> uploads) {
		this.uploads = uploads;
	}

}
