package bean;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import util.BeanResetter;
import util.JSFNavigationConstants;
import util.SpicsPermissions;
import db.interfaces.ITrialDataDAO;
import db.interfaces.ITrialFormDAO;
import entities.Trial;
import entities.TrialForm;

@Stateful
@Scope(ScopeType.SESSION)
@Name("TrialFormsViewer")
public class TrialFormsViewerBean implements TrialFormsViewer {
	
	private static final long serialVersionUID = 1L;

	public static final String BEANNAME = "TrialFormsViewer";
	
	@DataModel("trialForms")
	private List<TrialForm> trialForms;
	
	@DataModelSelection("trialForms")
	private TrialForm trialForm;
	
	@SuppressWarnings("unused")
	@DataModel("archivedTrialForms")
	private List<TrialForm> archivedTrialForms;
	
	@DataModelSelection("archivedTrialForms")
	private TrialForm archivedTrialForm;
	
	@Out(required=false)
	private Long editTrialFormID;
	
	@Logger
	private Log log;

	private Trial trial;
	
	@In
	private SessionInfo sessionInfo;
	
	@EJB
	private ITrialFormDAO trialFormDAO;
	
	@EJB private ITrialDataDAO trialDataDAO;
	
	private TrialForm newTrialForm = new TrialForm();
	
	@SuppressWarnings("unused")
	@Out(required=false)
	private Long trialFormToArchiveId;
	
	@In(required = false)
	private BeanResetter beanResetter;
	
	private boolean editable;
	
	public TrialFormsViewerBean( ) { }
	
	/* (non-Javadoc)
	 * @see bean.TrialFormsViewer#setUp()
	 */
	@Factory("trialForms")
	public void setUp() {
		
		beanResetter.addGridBean(this);
		
		this.trial = sessionInfo.getTrial();
		
		trialForms = new LinkedList<TrialForm>();
		archivedTrialForms = new LinkedList<TrialForm>();
		
		for(TrialForm tmpTF : trial.getActiveTrialForms()) {
			if(tmpTF.getTrialSpecific())
				continue; // for backward compatibility - until the trialspecific attribute is removed!
			else
				trialForms.add(tmpTF);
		}
		
		archivedTrialForms = trial.getArchivedTrialForms();
		
		Collections.sort(trialForms);
		
		editable = trial.isEditable() && Identity.instance().hasPermission(trial, SpicsPermissions.EDIT_TRIAL_FORMS);
		
	}
	
	/* (non-Javadoc)
	 * @see bean.TrialFormsViewer#createTrialForm()
	 */
	public String createTrialForm() {
		
		if(StringUtils.isBlank(newTrialForm.getName())) {
			FacesMessages.instance().addFromResourceBundle(Severity.ERROR, "error.formname");
			log.warn("could not create TrialForm, no name entered");
			return JSFNavigationConstants.RELOADPAGE;
		}
		
		TrialForm tf = newTrialForm;
		tf.setSort(trial.getTrialForms().isEmpty() ? 0 : Collections.max(trial.getTrialForms()).getSort() + 1);
		tf.setTrial(trial);
		tf.setTrialSpecific(false);
		tf.setLastModified(new Date(System.currentTimeMillis()));
		tf.setArchived(false);
		trial.getTrialForms().add(tf);
		
		trialFormDAO.persist(tf);
		
		this.editTrialFormID = tf.getId();
		
		log.info("created Trialform " + tf.getName() + " successfully");
		
		reset();
		
		return JSFNavigationConstants.EDITTRIALFORM;
	}
	
	/* (non-Javadoc)
	 * @see bean.TrialFormsViewer#viewTrialForm()
	 */
	public String viewTrialForm() {
		
		if(trialForm != null)
			this.editTrialFormID = trialForm.getId();
		else if(archivedTrialForm != null)
			this.editTrialFormID = archivedTrialForm.getId();
		
		if(editTrialFormID == null) {
			log.error("trying to edit trialform, but ID is null");
			throw new NullPointerException("editTrialFormID");
		}
		
		log.info("viewTrialForm for trialformID #0", editTrialFormID);
		
		reset();
		return JSFNavigationConstants.EDITTRIALFORM;
	}
	
	@Remove
	@Destroy
	public void remove() { }
	
	// make sure everything is
	public void reset() {
		archivedTrialForm = null;
		trialForm = null;
		newTrialForm = new TrialForm();
		trialForms = null;
	}

	public String delete() {
		if(trialForm == null)
			return JSFNavigationConstants.RELOADPAGE;
		
		if(trialDataDAO.trialFormHasTrialData(trialForm.getId())) {
			log.info("Cannot delete trialform with id #0, trialdatas exist, redirecting to archive page", trialForm.getId());
			this.trialFormToArchiveId = trialForm.getId();
			reset();
			return JSFNavigationConstants.ARCHIVETRIALFORM;
		}
		
		this.trial.getTrialForms().remove(trialForm);
		
		for(TrialForm tmpTF : trial.getTrialForms()) {
			if(tmpTF.compareTo(trialForm) > 0) {
				tmpTF.setSort(tmpTF.getSort() - 1);
			}
		}
		
		trialFormDAO.remove(trialFormDAO.findByID(trialForm.getId()));
		
		reset();
		
		return JSFNavigationConstants.RELOADPAGE;
	}

	public String moveTfDown() {
		log.info("moveDown called for " + trialForm.getName());
		int i1 = trialForms.indexOf(trialForm);
		if(i1 == trialForms.size() -1) 
			return null;	// avoid arrayindexoutofpoints - should not happen anyway	
		
		trialForms.get(i1 + 1).setSort(trialForm.getSort());	
		trialForm.setSort(trialForm.getSort() + 1);
		
		trialFormDAO.merge(trialForm);
		trialFormDAO.merge(trialForms.get(i1+1));
		
		reset();
		
		return JSFNavigationConstants.RELOADPAGE;
	}

	public String moveTfUp() {
		log.info("moveUp called for " + trialForm.getName());
		int i = trialForms.indexOf(trialForm);
		
		if(i == 0)
			return null;	//cannot be moved - avoid arrayindexoutofpoints
		
		trialForms.get(i - 1).setSort(trialForm.getSort());
		trialForm.setSort(trialForm.getSort() - 1);
	
		trialFormDAO.merge(trialForm);
		trialFormDAO.merge(trialForms.get(i-1));
		
		reset();
		
		return JSFNavigationConstants.RELOADPAGE;
	}

	public TrialForm getNewTrialForm() {
		return newTrialForm;
	}

	public void setNewTrialForm(TrialForm newTrialForm) {
		this.newTrialForm = newTrialForm;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
