package gui.tabs;

import gui.logic.DeleteEvent;
import gui.logic.DeleteListener;
import gui.mem.GuiMemory;
import gui.panels.EventParticipantPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import net.jini.core.event.RemoteEvent;
import net.jini.core.event.RemoteEventListener;
import net.jini.core.event.UnknownEventException;

import org.apache.log4j.Logger;

import dao.EventDao;
import entities.Event;

public class InvitedEventsTab extends JPanel implements ListSelectionListener,RemoteEventListener,DeleteListener{

	private static final long serialVersionUID = -187858269791970541L;
	private static Logger log = Logger.getLogger(InvitedEventsTab.class);
	
	private JTable eventTable;
	private EventParticipantPanel eventParticipantPanel;
	private DefaultTableModel dtm;
	private EventDao eDao;
	private Event selectedEvent;
	private boolean updatingTable;
	private JPanel eventDetails;
	
	public InvitedEventsTab() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		eventParticipantPanel = new EventParticipantPanel();
		eDao = new EventDao();
		
		// Left Side
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.insets = new Insets(10,10,10,10);
		JPanel eventList = new JPanel(new GridBagLayout());
		GridBagConstraints listConstraints = new GridBagConstraints();
		listConstraints.insets = new Insets(10,10,10,10);
		this.add(eventList, c);
	
		
		// Event List
		listConstraints.gridx = 0;
		listConstraints.gridy = 0;
		listConstraints.weightx = 1.0;
		listConstraints.weighty = 1.0;
		listConstraints.fill = GridBagConstraints.BOTH;
		dtm = new DefaultTableModel();
		eventTable = new JTable(dtm);
		eventTable.getSelectionModel().addListSelectionListener(this);
		eventTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fillTable();
	    JScrollPane scrollpane = new JScrollPane(eventTable);
		eventList.add(scrollpane, listConstraints);
		
		// Seperator
		c.gridx = 1;
		c.gridy = 0;
		JSeparator seperator = new JSeparator(JSeparator.VERTICAL);
		this.add(seperator, c);
		
		// Right Side
		c.gridx = 2;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		eventDetails = new JPanel(new GridBagLayout());
		GridBagConstraints detailsConstraints = new GridBagConstraints();
		detailsConstraints.insets = new Insets(10,10,10,10);
		detailsConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		detailsConstraints.weightx = 1.0;
		detailsConstraints.weighty = 1.0;
		this.add(eventDetails, c);
		
		// Event Panel
		detailsConstraints.gridx = 0;
		detailsConstraints.gridy = 0;
		eventDetails.add(eventParticipantPanel, detailsConstraints);
		eventParticipantPanel.setVisible(false);
	}
	
	public void fillTable() {
		updatingTable = true;
		// Initialize Table Colnames
		String[] colnames = new String[]{"Name","Organisator","Final Date"};
		List<Event> list = getInvitedEventsFromPeer();
		String[][] strings;
		
		if(list.size()==0) {
			strings = new String[0][0];
		} else {
			strings = new String[list.size()][3];
			for(int i=0;i<list.size();i++) {
				strings[i][0] = list.get(i).getName();
				strings[i][1] = list.get(i).getOrganisator();
				
				if(list.get(i).getFinalDate() != null) {
					strings[i][2] = list.get(i).getFinalDate().toString();
				} else {
					strings[i][2] = "";
				}
			}
		}
		dtm.setDataVector(strings, colnames);
		eventTable.setModel(dtm);
		
		updatingTable = false;
	}
	
	public List<Event> getInvitedEventsFromPeer() {
		return eDao.findAllInvitedEventsFromPeer(GuiMemory.getLoginUser());
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		//log.debug("SelectionEvent: row = "+eventTable.getSelectedRow()+"; firstindex="+arg0.getFirstIndex()+"; lastindex="+arg0.getLastIndex()+"; adjusting="+arg0.getValueIsAdjusting());
		if(!arg0.getValueIsAdjusting() && eventTable.getSelectedRow() != -1 && eventTable.getValueAt(eventTable.getSelectedRow(),1) !=null && !eventTable.getValueAt(eventTable.getSelectedRow(),1).equals("")) {
			
			eventParticipantPanel.setVisible(true);
			
			String eventName = (String) eventTable.getValueAt(eventTable.getSelectedRow(),0);
			log.debug(eventName+" selected");
			
			Event findEvent = eDao.getEmptyTemplate();
			findEvent.setName(eventName);
			findEvent = loadEvent(findEvent);
			
			
			
			if(findEvent != null) {
				selectedEvent = findEvent;
				eventParticipantPanel.redraw(selectedEvent);
				eventDetails.repaint();
				eventDetails.revalidate();
			} else {
				JOptionPane.showMessageDialog(new JFrame(),"Event was deleted!", "Warning",JOptionPane.WARNING_MESSAGE);
				selectedEvent = null;
				eventParticipantPanel.setVisible(false);
			}
		} else if (!updatingTable && !arg0.getValueIsAdjusting() && eventTable.getSelectedRow() == -1){
			selectedEvent = null;
			eventParticipantPanel.setVisible(false);
		}
	}

	@Override
	public void notify(RemoteEvent arg0) throws UnknownEventException,RemoteException {
		fillTable();
		
		if(selectedEvent != null) {
			eventParticipantPanel.setVisible(true);
			eventParticipantPanel.redraw(loadEvent(selectedEvent));
			eventDetails.repaint();
			eventDetails.revalidate();
		} else {
			eventParticipantPanel.setVisible(false);
		}
	}
	
	private Event loadEvent(Event e) {
		eDao = EventDao.createDao();
		Event findEvent = eDao.getEmptyTemplate();
		findEvent.setName(e.getName());
		return eDao.find(findEvent);
	}

	@Override
	public void deletePerformed(DeleteEvent arg0) {
		log.debug("Delete Event detected");
		
		if(selectedEvent != null && selectedEvent.getName() != null && arg0 != null && arg0.getEventName() != null)
			if(selectedEvent.getName().equals(arg0.getEventName())) {
				selectedEvent = null;
				eventParticipantPanel.setVisible(false);
			}
		
	}
}
