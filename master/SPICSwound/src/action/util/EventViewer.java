package util;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.security.Restrict;

import db.impl.EventDAO;
import db.interfaces.IEventDAO;
import entities.event.EVENTTYPE;
import entities.event.Event;

@Name("EventViewer")
@Restrict("#{s:hasRole('admin')}")
@Scope(ScopeType.CONVERSATION)
public class EventViewer {
	
	private static int MAX_RESULTS = 15*20;	// 15 pages x 20 entries
	private List<EVENTTYPE> eventtypes;
	private EVENTTYPE selectedEventType;
	
	/* switching between extended DataTable and "regular" richfaces datatable */
	private boolean useExtendedDataTable = true;
	
	/* for extendedDataTable */
	private String tableState;
	
	/* for datascroller */
	private int page;

	@DataModel
	private List<Event> eventList;
	
	@EJB
	private IEventDAO eventDAO;

	@Factory("eventList")
	public void populateList() {
		if(eventDAO == null)
			eventDAO = (IEventDAO)Component.getInstance(EventDAO.class);
		
		if(eventList == null)	// to init
			filter();		
	}
	
	public List<EVENTTYPE> getEventtypes() {
		if(eventtypes == null) {
			eventtypes = Arrays.asList(EVENTTYPE.values());
		}
		return eventtypes;
	}


	public void setEventtypes(List<EVENTTYPE> eventtypes) {
		this.eventtypes = eventtypes;
	}


	public EVENTTYPE getSelectedEventType() {
		return selectedEventType;
	}


	public void setSelectedEventType(EVENTTYPE selectedEventType) {
		this.selectedEventType = selectedEventType;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getTableState() {
		return tableState;
	}

	public void setTableState(String tableState) {
		this.tableState = tableState;
	}

	public boolean isUseExtendedDataTable() {
		return useExtendedDataTable;
	}

	public void setUseExtendedDataTable(boolean useExtendedDataTable) {
		this.useExtendedDataTable = useExtendedDataTable;
	}

	public void filter() {
		
		// TODO: this might be fairly heavy on the DB - and probably 
		// kill the extendedDataTable if we load several thousand events!
		if(useExtendedDataTable) {
			eventList = eventDAO.findAll();
			return;
		}
		
		if(selectedEventType != null) {
			eventList = eventDAO.findByType(selectedEventType, MAX_RESULTS);
		} else {
			eventList = eventDAO.findNewest(MAX_RESULTS);
		}
		
		// reset page
		page = 1;
		
	}
	
	public void toggleExtendedDataTable() {
		this.useExtendedDataTable = !this.useExtendedDataTable;
		
		page = 1;
		selectedEventType = null;
		eventList = null;
	}
	
}
