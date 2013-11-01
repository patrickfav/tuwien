package bean;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import util.BeanResetter;
import util.JSFNavigationConstants;
import util.SpicsPermissions;
import db.interfaces.IAttributeGroupDAO;
import db.interfaces.ITrialDataDAO;
import db.interfaces.ITrialFormDAO;
import entities.AttributeGroup;

@Stateful
@Scope(ScopeType.SESSION)
@Name("TrialFormEditor")
public class TrialFormEditorBean extends AbstractTrialFormViewerBean implements TrialFormEditor {

	private static final long serialVersionUID = 1L;
	
	public static final String BEANNAME = "TrialFormEditor";

	@Logger
	private Log log;
	
	@EJB private ITrialFormDAO trialFormDAO;
	@EJB private IAttributeGroupDAO attributeGroupDAO;
	@EJB private ITrialDataDAO trialDataDAO;
	
	@SuppressWarnings("unused")
	@Out
	private Boolean attributeGroupEditorReset = false;
	
	@SuppressWarnings("unused")
	@Out(required=false)
	private Long trialFormToArchiveId;
	
	@In
	private Long editTrialFormID;
	
	@SuppressWarnings("unused")	// only used for outjecting to agedit
	@Out(required=false)
	private Long editAttributeGroupId;
	
	@SuppressWarnings("unused")
	@Out(required=false)
	private String comingFrom = BEANNAME;
	
	private String newAttributeGroupName;
	
	private String newAGnach;
	
	@In(required = false)
	private BeanResetter beanResetter;
	
	private boolean noResetOnLoad = false;
	
	private boolean formEditable;

	/* (non-Javadoc)
	 * @see bean.TrialFormEditor#setUp()
	 */
	
	public TrialFormEditorBean() {
		super();
		super.beanName = BEANNAME;
	}
	
	public void setUp() {
		beanResetter.addGridBean(this);
		
		if(editTrialFormID == null) {
			log.error("no trialform injected...");
			return;
		}

		super.tf = trialFormDAO.findByID(editTrialFormID);
		super.setUp();
		
		// set to last AttributeGroup in TrialForm
		newAGnach = String.valueOf(super.tf.getAttributeGroups().size()-1);
		
		formEditable = super.tf.isEditable() && Identity.instance().hasPermission(tf.getTrial(), SpicsPermissions.EDIT_TRIAL_FORMS);
	
	}
	
	/* (non-Javadoc)
	 * @see bean.TrialFormEditor#destroy()
	 */
	@Remove
	@Destroy
	public void destroy() {
		mainGrid = null;
		reset();
		log.info("destroyed...");
	}
	

	/* (non-Javadoc)
	 * @see bean.TrialFormEditor#deleteAg()
	 */
	public String deleteAg(Integer sortId) {
		log.info("delete for AG " + sortId + " called");

		AttributeGroup toDelete = attributeGroupDAO.findByTrialFormIDandSort(tf.getId(), sortId);

		if (toDelete == null) {
			log.error("ERROR - trying to delete inexisting Attribute Group "
							+ sortId);
			return JSFNavigationConstants.RELOADPAGE;
		}
		
		if(trialDataDAO.attributeGroupHasTrialData(toDelete.getId())) {
			log.info("Cannot delete AttributeGroup - already has TrialData - redirecting to archive page");
			trialFormToArchiveId = toDelete.getTrialForm().getId();
			return JSFNavigationConstants.ARCHIVETRIALFORM;
		}

		Boolean[] tmpFlags = new Boolean[renderFlags.length - 1];
		
		for(int i = 0; i < tmpFlags.length; i++) {
			if(i < toDelete.getSort()) {
				tmpFlags[i] = renderFlags[i];
			} else {
				tmpFlags[i] = renderFlags[i+1];
			}
		}
		
		renderFlags = tmpFlags;
		
		List<AttributeGroup> agList = tf.getAttributeGroups();
		Collections.sort(agList);
		agList.remove(sortId.intValue());
		
		for(AttributeGroup ag: agList) {
			if(ag.getSort() > sortId) {
				ag.setSort(ag.getSort() - 1);
				attributeGroupDAO.merge(ag);
			}
		}

		attributeGroupDAO.remove(toDelete);
		setTrialFormChanged();
		
		newAGnach = String.valueOf(super.tf.getAttributeGroups().size()-1);
		
		super.updateGrid();
		
		noResetOnLoad = true;
		return JSFNavigationConstants.RELOADPAGE; // reload page
	}
	
	/* (non-Javadoc)
	 * @see bean.TrialFormEditor#createAg()
	 */
	public String createAg() {
		log.info("creating new Attribute Group with name " + newAttributeGroupName);
		
		Integer sortId = 0;
		if(newAGnach != null && !"".equals(newAGnach))
			sortId = Integer.valueOf(newAGnach) + 1;
			
		log.info("setting sort id for AG " + newAttributeGroupName + " to " + sortId);
		
		AttributeGroup newAg = new AttributeGroup();
		newAg.setName(newAttributeGroupName);
		newAg.setSort(sortId);
		newAg.setTrialForm(tf);
		
		attributeGroupDAO.persist(newAg);
		
		if(sortId.intValue() < tf.getAttributeGroups().size()) { // increase all sortIds after the new one
			for(AttributeGroup ag : tf.getAttributeGroups()) {
				if(ag.getSort().intValue() >= sortId.intValue()) {
					ag.setSort(ag.getSort() + 1);
					log.info("increasing sortid of " + ag.getName());
					attributeGroupDAO.merge(ag);
				}
			}
		}
		
		tf.getAttributeGroups().add(newAg);
		tf.setLastModified(new Date(System.currentTimeMillis()));
		this.tf = trialFormDAO.merge(tf);
		
		log.info("newly created ag got id " + newAg.getId());
		editAttributeGroupId = newAg.getId();
		
		attributeGroupEditorReset = true;
		
		newAttributeGroupName = "";
		reset();	// force reinit
		
		return JSFNavigationConstants.EDITATTRIBUTEGROUP;
	}

	/* (non-Javadoc)
	 * @see bean.TrialFormEditor#getNewAttributeGroupName()
	 */
	public String getNewAttributeGroupName() {
		return newAttributeGroupName;
	}

	/* (non-Javadoc)
	 * @see bean.TrialFormEditor#setNewAttributeGroupName(java.lang.String)
	 */
	public void setNewAttributeGroupName(String newAttributeGroupName) {
		this.newAttributeGroupName = newAttributeGroupName;
	}

	// TODO: deprecated ?!?
	public String editAttributeGroup() {
		log.info("AG bearbeiten called...");
		destroy();	// force reinit
		
		// force attributeGroupEditor reset
		attributeGroupEditorReset = true;
	
		return JSFNavigationConstants.EDITATTRIBUTEGROUP;
	}
	
	public String editAttributeGroup(Long agId) {
		log.info("AG bearbeiten called... with parameter agId #0", agId);
		destroy();	// force reinit
		
		editAttributeGroupId = agId;
		
		// force attributeGroupEditor reset
		attributeGroupEditorReset = true;
	
		return JSFNavigationConstants.EDITATTRIBUTEGROUP;
	}
	
	public void reset() {
		log.info("reset called");
		newAttributeGroupName = "";
		super.reset();
	}


	public String getNewAGnach() {
		return newAGnach;
	}

	public void setNewAGnach(String newAGnach) {
		this.newAGnach = newAGnach;
	}
	
	public String back() {
		reset();
		return "editTFdone";
	}

	public void setEditAttributeGroupId(long editAttributeGroupId) {
		this.editAttributeGroupId = editAttributeGroupId;
	}

	public void resetOnLoad() {
		log.info("reset on load called");
		if(noResetOnLoad)
			noResetOnLoad = false;
		else
			this.reset();
	}

	@Override
	public String toggleRendering(Integer sortId) {
		noResetOnLoad = true;
		return super.toggleRendering(sortId);
	}
	
	@Override
	public String moveAgUp(Integer sortId) {
		noResetOnLoad = true;
		return super.moveAgUp(sortId);
	}
	
	@Override
	public String moveAgDown(Integer sortId) {
		noResetOnLoad = true;
		return super.moveAgDown(sortId);
	}

	public String saveNameAndDescription() {
		noResetOnLoad = true;
		setTrialFormChanged();	// does a merge for us
		updateGrid();
		return null;
	}

	public boolean isFormEditable() {
		return formEditable;
	}

	public void setFormEditable(boolean formEditable) {
		this.formEditable = formEditable;
	}
	
	
}
