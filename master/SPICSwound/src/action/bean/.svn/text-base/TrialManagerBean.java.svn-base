package bean;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import util.JSFNavigationConstants;
import db.interfaces.ITrialDAO;
import entities.Trial;
import entities.User;

@Stateful
@Name("trialManager")
@Scope(ScopeType.SESSION)
public class TrialManagerBean implements TrialManager {

	private static final long serialVersionUID = 1L;

	@Logger private Log log;

	@In
	private User user;
	
	@In
	private SessionInfo sessionInfo;
	
	@SuppressWarnings("unused")
	@DataModel(value="trials",scope=ScopeType.PAGE)
	private List<Trial> trials = null;
	
	@DataModelSelection(value="trials")
	private Trial selectedTrial;
	
	@EJB private ITrialDAO trialDAO;

	@RequestParameter("trialid")
	private Long trialId; 
	
	@Factory("trials")
	public void queryTrials()
	{	
		this.log.info("Querying trial list for user #0",this.user.getUsername());
		
		// admins can see all trials
		if (Identity.instance().hasRole("admin")){
			this.trials = this.trialDAO.findAll();
		}
		else{
			this.trials = this.trialDAO.findActiveByUser(this.user);
		}
	}
	
	public String selectTrial() {
		sessionInfo.setTrialID(this.selectedTrial.getId());

		return JSFNavigationConstants.VIEWTRIAL;
	}
	
	public void setTrialByRequestParameter() {
	  if (this.trialId == null) { return; }
	  this.log.info("Found trial id #0 in request",this.trialId);
	  this.sessionInfo.setTrialID(this.trialId);
	}

	@BypassInterceptors
    @Remove 
    @Destroy
	public void destroy() {
    	log.info("trialmanager destroyed...");
    	this.log = null;
	}

}
