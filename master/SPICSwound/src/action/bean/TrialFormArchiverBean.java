package bean;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import util.JSFNavigationConstants;

import db.interfaces.ITrialFormDAO;
import entities.TrialForm;

@Stateful
@Scope(ScopeType.SESSION)
@Name("TrialFormArchiver")
public class TrialFormArchiverBean implements TrialFormArchiver {

	private static final long serialVersionUID = 1L;

	private boolean stillFillable;
	
	@In
	private Long trialFormToArchiveId;
	
	@EJB private ITrialFormDAO trialFormDAO;
	
	public String cancelArchive() {
		return JSFNavigationConstants.VIEWTRIALFORM;
	}

	public String doArchive() {
		TrialForm trialFormToArchive = trialFormDAO.findByID(trialFormToArchiveId);
		
		TrialForm duplicate = trialFormToArchive.duplicate();
		duplicate.setPredecessor(trialFormToArchive);
		
		trialFormToArchive.setArchived(true);
		trialFormToArchive.setArchivedSince(new Date(System.currentTimeMillis()));
		trialFormToArchive.setFillableAfterArchive(isStillFillable());
		
		trialFormDAO.persist(duplicate);
		
		return JSFNavigationConstants.VIEWTRIALFORM;
	}
	
	public boolean isStillFillable() {
		return stillFillable;
	}

	public void setStillFillable(boolean stillFillable) {
		this.stillFillable = stillFillable;
	}

	@Remove
	@Destroy
	public void destroy() { }
}
