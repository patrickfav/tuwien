package bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;
import org.richfaces.model.UploadItem;

import util.ComponentMapKey;
import util.JSFNavigationConstants;
import util.ValueFactory;
import db.interfaces.IAttributeDAO;
import db.interfaces.ITrialAttachmentDAO;
import db.interfaces.ITrialDataDAO;
import db.interfaces.IValueDAO;
import entities.Attribute;
import entities.AttributeGroup;
import entities.FileSet;
import entities.TrialAttachment;
import entities.TrialData;
import entities.User;
import entities.constraint.Constraint;
import entities.event.ValueChangeEvent;
import entities.event.ValueCreateEvent;
import entities.event.ValueDeleteEvent;
import entities.event.ValueEvent;
import entities.formelement.DATATYPE;
import entities.value.Value;

public abstract class AbstractTrialDataEditorBean extends AbstractTrialFormViewerBean implements AbstractTrialDataEditor {

	private static final long serialVersionUID = 1L;
	@EJB private ITrialDataDAO trialDataDAO;
	@EJB private IValueDAO valueDAO;	
	@EJB private IAttributeDAO attributeDAO;
	@EJB private ITrialAttachmentDAO fileDAO;
	@EJB private ValueFactory valueFactory;
	@EJB private EventManager eventManager;
	
	@Logger private Log log;
	
	// for access to individual trialspecific values
	private Map<String, Object> valueMap = new HashMap<String, Object>();

	protected LinkedList<ComponentMapKey> keyList;
	
	protected TrialData trialData;

	protected User user;
	
	protected List<TrialAttachment> filesToDelete = new LinkedList<TrialAttachment>();
	
	public void setUp() {		
		// renew valuemap
		log.info("resetting valueMap... recreating bindings, keyList: #0, conversationid #1", keyList, Conversation.instance().getId());
		valueMap = new HashMap<String, Object>();
		
		// reattach trialform
		super.tf = trialFormDAO.merge(tf);
		
		for (AttributeGroup ag : tf.getAttributeGroups()) {

			for (Attribute a : ag.getAttributes()) {
				String key = ComponentMapKey.generateKeyString(a.getId(), trialData.getId());
				Value value = trialData.getValueByAttributeId(a.getId());
				if (value != null) {
					log.info("key " + key + " has value " + value.getValueAsObject());
					valueMap.put(key, value.getValueAsObject());
				}else {
					if (a.getFormElement().getDataType().equals(DATATYPE.FILE)) {
						// create TrialAttachment object
						log.info("creating new TrialAttachment for Attribute: "
								+ a.getName());
						valueMap.put(key, new FileSet());
					} else {
						valueMap.put(key, null);
					}
				}
			}
		}
		
		super.setUp();
	}
	
	public Map<String, Object> getDataMap() {
		return valueMap;
	}
	
	public boolean isSavedOnce() {
		Boolean savedOnce = (Boolean) FacesContext.getCurrentInstance()
				.getViewRoot().getAttributes().get(SAVEDONCE);

		if (savedOnce == null) {
			setSavedOnce(false);
			savedOnce = false;
		}
		return savedOnce.booleanValue();
	}

	public void setSavedOnce(boolean savedOnce) {
		FacesContext.getCurrentInstance().getViewRoot().getAttributes().put(
				SAVEDONCE, savedOnce);
	}
	
	public String save() {
		
//		 sort keylist, to make sure we handle everything in the right order
		Collections.sort(keyList);	

		boolean hasChanged = false;	// TODO: replace by checks of size for all three *Value Lists
		boolean showWarnings = false;

		List<Value> updValues = new LinkedList<Value>();
		List<Value> newValues = new LinkedList<Value>();
		List<Value> remValues = new LinkedList<Value>();
		List<ValueEvent> events = new LinkedList<ValueEvent>();
		
		this.trialData = trialDataDAO.findByID(trialData.getId()); // to avoid LazyInitException
		
		for (ComponentMapKey key : keyList) {
			Object o = valueMap.get(key.getKeyString());
			Serializable val = null;
			if (o instanceof Serializable) {
				val = (Serializable) o;
			} else if(o != null) {
				log.error("#0 has to be Serializable", o);
				throw new IllegalArgumentException(o.toString() + " has to be Serializable");
			}
			log.info("*********** current value for key " + key.getKeyString() + " is "
							+ val + " and class is "
							+ (val == null ? "n/a" : val.getClass()));

			if (val instanceof TrialAttachment) {
				TrialAttachment ta = (TrialAttachment) val;
				log.info("uploaddata size: #0", ta.getUploads().size());
				
				if(ta.getUploads().size() > 0) {
					UploadItem item = ta.getUploads().get(0);
					log.info("uploaded item filename: #0 bytes: #1 file: #2", item.getFileName(), (item.getData() != null ? item.getData().length : " empty"), item.getFile());
					
					try {
						ta.loadUploadItem(item);
					} catch (IOException e) {
						log.warn("Failed to read in temp file (of uploaded file) #0 \n msg: #1", item.getFileName(), e.getMessage());
					}
				}
			}

			Attribute a = attributeDAO.findByID(key.getAttributeId());

			try {
				Value oldValue = trialData.getValueByAttributeId(key.getAttributeId());
				// TODO: performance opt: load oldValue after checkEmptyAnd Constraints
				if (checkEmptyAndConstraints(a, val, key.getComponentName())) {

					if (oldValue != null) { // value existed before
						if (oldValue.valueEquals(val)) {
							log.info("Value #0 for attribute #1 hasn't changed", val, a.getName());
						} else { // value changed
							log.info("Value of attribute #0 was updated", a.getName());
							Serializable old = oldValue.getValueAsObject();
							oldValue.setValueObject(val);
							updValues.add(oldValue);
							hasChanged = true;
							events.add(new ValueChangeEvent(user,old, oldValue));
						}
					} else { // no previous value available
						log.info("creating valueObject for id (#0 / #1)", key.getAttributeId(), key.getTrialDataId());
						Value v = valueFactory.getValueObject(a
								.getFormElement().getDataType());
						v.setAttribute(a);
						v.setTrialData(trialData);
						v.setValueObject(val);
						newValues.add(v);
						hasChanged = true;
						events.add(new ValueCreateEvent(user, v));
					}
				} else {	// is empty, so remove value
					if(oldValue != null) {
						remValues.add(oldValue);
						events.add(new ValueDeleteEvent(user, oldValue));
						hasChanged = true;
					}
				}
			} catch (WarningProducedException e) {
				if (!isSavedOnce()) { // if we haven't warned before...
					showWarnings = true;
				}
			}
		}

		if (showWarnings) { // do NOT save changes...
			log.info("redisplaying to display warnings!");
			
			setSavedOnce(true);
			return JSFNavigationConstants.RELOADPAGE;
		}

		log.info("Done checking values - now persisting changes...");
		if (hasChanged) { // persist changes
			
			for(TrialAttachment ta : filesToDelete) {
				log.info("deleting file #0", ta.getFileName());
				fileDAO.remove(fileDAO.merge(ta));
			}

			for (Value v : updValues) {
				log.info("updating value: (#0 / #1)", v.getId().getAttributeId(), v.getId().getTrialDataId());
				valueDAO.merge(v);
			}

			for (Value v : newValues) {
				log.info("persisting value: (#0 / #1)", v.getId().getAttributeId(), v.getId().getTrialDataId());
				valueDAO.persist(v);
				trialData.getValues().add(v);
			}
			
			for(Value v : remValues) {
				log.info("removing value: (#0 / #1)", v.getId().getAttributeId(), v.getId().getTrialDataId());
				valueDAO.remove(v);
				trialData.getValues().remove(v);
			}
			
			for(ValueEvent ve : events) {
				eventManager.registerEvent(ve, false);
			}

			trialData.setLastModified(new Date(System.currentTimeMillis()));
			trialData.setLastModifiedBy(user);
			
			trialDataDAO.merge(trialData);
			
		} else if(trialData.getValues().size() == 0) {	// check if it is an empty trialdata object - no values have been created
			trialDataDAO.remove(trialData);
			
		}
				
		setSavedOnce(false);
		
		filesToDelete = new LinkedList<TrialAttachment>();
		
		return JSFNavigationConstants.FINISHEDEDITINGTRIALDATA;
	}
	
	@Override
	public void reset() {
		if(isSavedOnce()) {	// do not reset... 
			log.info("not resetting due to: saved_once: #0", isSavedOnce());
			return;
		}
		
		super.reset();
	}
	
	/**
	 * this method now does all the constraint checking which result in the
	 * output of a warning message
	 * 
	 */
	private boolean checkEmptyAndConstraints(Attribute a, Object val, String componentId)
			throws WarningProducedException {
		log.info("checkConstraints called for Attribute " + a.getName()
				+ " with value " + val);
		// check if empty (and recommended)
		if (val == null || isEmptyString(val) || FileSet.isEmptyFileset(val)) {
			if (a.getRecommended()) { // it should NOT be emtpy
				if (!isSavedOnce()) {
					log.info("recommending to save because of: " + a.getName()
							+ " being empty");
					FacesMessages.instance().addToControlFromResourceBundle(componentId, Severity.WARN, "warn.empty", a.getName());
					throw new WarningProducedException();
				}
			}
			// just empty, so do not process the command
			return false;
		}

		// check for conformation to constraints
		Constraint c = a.getFormElement().getConstraint();
		if (c != null) { // there is a constraint
			String result = c.validate(val);

			if (result != null) { // validationerror/warning
				if (!isSavedOnce()) {
					FacesMessages.instance().addToControl(componentId, Severity.WARN, a.getName()+": " + result);
					throw new WarningProducedException(); // done
				}
			}
		}

		return true; // valid value, ready for further processing
	}

	private boolean isEmptyString(Object val) {
		if (val instanceof String) {
			return StringUtils.isBlank((String) val);
		}
		return false;
	}
	
	protected void updateGrid() {
		log.info("updateGrid called");
		keyList = ui.updateTrialDataEdit(super.mainGrid, trialData.getTrialform(), trialData, renderFlags, beanName);
	}
	
}

class WarningProducedException extends Exception {

	private static final long serialVersionUID = 1L;

	public WarningProducedException() {
		super();
	}
}
