package bean;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.log.Log;

import util.JSFNavigationConstants;
import util.ValueFactory;
import db.interfaces.IAttributeDAO;
import db.interfaces.IEventDAO;
import db.interfaces.IValueDAO;
import entities.Attribute;
import entities.TrialData;
import entities.event.EVENTTYPE;
import entities.event.ValueCreateEvent;
import entities.event.ValueDeleteEvent;
import entities.event.ValueEvent;
import entities.value.Value;

@Stateful
@Scope(ScopeType.CONVERSATION)
@Name("TrialDataEditUndo")
public class TrialDataEditUndoBean implements TrialDataEditUndo{

	@EJB
	private IEventDAO eventDAO;
	
	@EJB
	private IValueDAO valueDAO;
	
	@EJB
	private IAttributeDAO attributeDAO;
	
	@EJB
	private ValueFactory valueFactory;

	@Logger
	private Log log;
	
	@In
	private Long undoTDId;
	
	@SuppressWarnings("unused")
	@Out(required=false, scope=ScopeType.SESSION)
	private Boolean resetTDViewer;
	
	@DataModel
	private List<ValueEvent> changes;
	
	private Set<Long> resolvedAttributeIds;
	
	@Begin(join=true)
	@Factory("changes")
	public void getChanges() {

		changes = eventDAO.findByTrialData(undoTDId);
		Collections.sort(changes);
		
		resolvedAttributeIds = new HashSet<Long>();
	}
	
	public String getEventIcon(EVENTTYPE type) {
		
		if(type==null) return "";
		
		switch(type) {
		case VALUECHANGE: 
			return "change.png";
		case VALUECREATE:
			return "add.png";
		case VALUEDELETE:
			return "delete.png";
		default:
			return "";
		}
	}
	
	public String getAttributeName(Long id) {

		if(resolvedAttributeIds.contains(id))
			return "";
		
		Attribute a = attributeDAO.findByID(id);
		resolvedAttributeIds.add(id);
		if(a == null)
			return "";
		
		return a.getName();
	}
	
	public String undo(ValueEvent ve) {
		List<ValueEvent> newer = eventDAO.getNewerEvents(ve);
		Collections.sort(newer);
		
		if(stillExists(newer)) {			
			Value v = valueDAO.findByID(new Value.ValueId(ve.getAttributeId(), ve.getTrialdataId()));
			
			if (ve instanceof ValueCreateEvent) {	
				log.debug("Removing Value #0/#1", v.getId().getAttributeId(), v.getId().getTrialDataId());
				valueDAO.remove(v);
			} else {
				log.debug("reversing value of #0/#1 to #2", v.getId().getAttributeId(), v.getId().getTrialDataId(), ve.getOldValue());
				v.setValueObject(ve.getOldValue());
			}
		} else {
			
			if(!(ve instanceof ValueCreateEvent)) {
				log.debug("restoring value #0/#1 with value #2", ve.getAttributeId(), ve.getTrialdataId(), ve.getOldValue());
				Attribute a = attributeDAO.findByID(ve.getAttributeId());
				if(a == null)
					throw new IllegalArgumentException("Could not restore value, attribute has been removed!");
				
				Value v = valueFactory.getValueObject(a.getFormElement().getDataType());
				v.setAttribute(a);
				TrialData td = new TrialData();
				td.setId(ve.getTrialdataId());
				v.setTrialData(td);
				v.setValueObject(ve.getOldValue());
				
				valueDAO.persist(v);
			}
		}
		
		// remove all newer Events
		for(ValueEvent ve1 : newer) {
			eventDAO.remove(ve1);
		}
		
		// reset datamodel
		changes = null;
		
		return null;
	}
	
	@End(root=true)
	public String back() {
		log.info("done... navigating back to viewTrialData");
		changes = null;
		resetTDViewer = true;
		return JSFNavigationConstants.EDITPATIENTTRIALDATA;
	}
	
	private boolean stillExists(List<ValueEvent> ves) {
		int size = ves.size();
		
		if(ves.get(size - 1) instanceof ValueDeleteEvent)
			return false;
		
		return true;
	}
	
	@Remove
	@Destroy
	public void remove() { }
}
