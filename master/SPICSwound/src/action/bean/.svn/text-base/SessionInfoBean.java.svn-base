package bean;

import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import db.interfaces.ITrialDAO;
import entities.Trial;

@Stateful
@Scope(ScopeType.SESSION)
@Name("SessionInfo")
public class SessionInfoBean implements SessionInfo {

	private static final long serialVersionUID = 1L;

	@In
	private ITrialDAO trialDAO;
	
	private Long trialID;
	
	private Trial unpersisted;
	
	public Trial getTrial() {
		if(trialID != null)
			return trialDAO.findByID(trialID);
		else
			return unpersisted;
	}

	public Long getTrialID() {
		return trialID;
	}

	public void setTrialID(Long id) {
		this.trialID = id;
		unpersisted = null;
	}
	
	public void newTrial() {
		this.trialID = null;
		unpersisted = new Trial();
	}
	
	@Remove
	@Destroy
	public void destroy() {	}


}
