package bean;

import java.util.Date;

import javax.ejb.EJB;
import javax.faces.component.html.HtmlPanelGrid;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;

import util.JSFNavigationConstants;
import db.interfaces.IAttributeGroupDAO;
import db.interfaces.ITrialFormDAO;
import entities.AttributeGroup;
import entities.TrialForm;

public abstract class AbstractTrialFormViewerBean extends AbstractAgRendererBean implements AbstractTrialFormViewer {

	private static final long serialVersionUID = 1L;
	
	public static final String COMINGFROM = "toAgEditcomingFrom";
	
	@Logger
	private Log log;
	
	@EJB protected ITrialFormDAO trialFormDAO;
	@EJB private IAttributeGroupDAO attributeGroupDAO;	
	@EJB protected TrialFormEditorUI ui;
	
	protected transient HtmlPanelGrid mainGrid;
	
	protected TrialForm tf;
	
	protected boolean editExistingAgOnly = false;
	
	protected String beanName;
	
	public AbstractTrialFormViewerBean() {
		
	}

	public void setUp() {
		
		if(tf == null) {
			log.warn("TrialForm null - cannot proceed");
			return;
		}
		
		this.tf = trialFormDAO.findByID(tf.getId());
		
		log.info("AbstractTrialFormViewer started/initialized");		
			
		if(renderFlags == null) {
			log.info("reinitializing renderFlags");
			renderFlags = new Boolean[tf.getAttributeGroups().size()];
			for (int i = 0; i < renderFlags.length; i++) {
				renderFlags[i] = true;
			}
		}
	}
	
	public HtmlPanelGrid getMainGrid() {	
		log.info("AbstractTrialform Editor: getter called...");
		if(wantToReset())	// make sure the grid is reset if necessary
			this.mainGrid = null;
		
		return mainGrid;
	}

	public void setMainGrid(HtmlPanelGrid mainGrid) {
		log.info("AbstractTrialform Editor: setter called... ");
		boolean wasNull = false;
		
		if(wantToReset() || this.mainGrid == null || tf == null ) {
			log.info("mainGrid: " + mainGrid + " trialform: " + tf);
			wasNull = true;
		} else {
			log.info("mainGrid is alive, nothing to do here; mainGrid: " + mainGrid + " trialform: " + tf);
		}
		
		this.mainGrid = mainGrid;
		
		if(wasNull) {
			log.info("(re) initializing bean");
			renderFlags = null;
			setUp();
			updateGrid();
		}
	}

	/**
	 * default implementation - should be overriden by implementing classes
	 * @return true if the grid should be reset!
	 */
	protected boolean wantToReset() {
		log.info("defaultimpl of want to reset");
		return false;
	}

	public String moveAgUp(Integer sortId) {
		log.info("move up clicked for AG " + sortId);

		AttributeGroup firstAg = tf.getAGbySortID(sortId - 1);
		AttributeGroup secondAg = tf.getAGbySortID(sortId);

		if (firstAg == null || secondAg == null) {
			log.warn("ERROR : trying to move ag to illegal position");
			return JSFNavigationConstants.RELOADPAGE;
		}

		firstAg.setSort(sortId);
		secondAg.setSort(sortId - 1);

		attributeGroupDAO.merge(firstAg);
		attributeGroupDAO.merge(secondAg);

		setTrialFormChanged();
		updateGrid();
		
		return JSFNavigationConstants.RELOADPAGE; // reload page
	}

	public String moveAgDown(Integer sortId) {
		
		if(tf == null)
			setUp();
		
		log.info("move down clicked for AG " + sortId);

		AttributeGroup firstAg = tf.getAGbySortID(sortId);
		AttributeGroup secondAg = tf.getAGbySortID(sortId + 1);

		if (firstAg == null || secondAg == null) {
			log.warn("ERROR : trying to move ag to illegal position");
			return JSFNavigationConstants.RELOADPAGE;
		}

		firstAg.setSort(sortId + 1);
		secondAg.setSort(sortId);

		attributeGroupDAO.merge(firstAg);
		attributeGroupDAO.merge(secondAg);
		setTrialFormChanged();
		updateGrid();
		
		return JSFNavigationConstants.RELOADPAGE; // reload page

	}

	public void reset() {
		
		log.info("mainGrid reset, was " + mainGrid);
		tf = null;
		mainGrid = null;
		renderFlags = null;
	}

	protected void setTrialFormChanged() {
		if(this.tf == null) 
			return;
		
		tf.setLastModified(new Date(System.currentTimeMillis()));
		
		this.tf = trialFormDAO.merge(tf);
	}
	
	public String toggleRendering(Integer sortId) {
		if (sortId != null) {
			renderFlags[sortId] = !renderFlags[sortId];
		} else {
			log.warn("Tried to toggle AG, but agId was null");
		}
		log.info("componenet" + sortId
				+ " will " + (renderFlags[sortId] ? "be rendered" : "not be rendered"));
		
		updateGrid();
		
		return JSFNavigationConstants.RELOADPAGE;
	}
	
	protected void updateGrid() {
		ui.updateMainGrid(mainGrid, tf, renderFlags, beanName, editExistingAgOnly);
	}
	
	public TrialForm getTrialForm() {
		return this.tf;
	}

}
