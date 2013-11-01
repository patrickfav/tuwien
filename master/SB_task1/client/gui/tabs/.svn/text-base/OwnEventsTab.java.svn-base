package gui.tabs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JButton;
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
import gui.mem.GuiMemory;
import gui.panels.EventAdminPanel;

public class OwnEventsTab extends JPanel implements RemoteEventListener, ActionListener,
		ListSelectionListener {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(OwnEventsTab.class);
	private JTable eventTable;
	private JPanel eventDetails;
	
	private Event selectedEvent;
	private EventAdminPanel adminPanel;
	private boolean updatingTable = false;
	private DefaultTableModel dtm;
	
	public OwnEventsTab() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	
		// Left Side
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.insets = new Insets(10, 10, 10, 10);
		JPanel eventList = new JPanel(new GridBagLayout());
		GridBagConstraints listConstraints = new GridBagConstraints();
		listConstraints.insets = new Insets(10, 10, 10, 10);
		this.add(eventList, c);

		// Event Button
		listConstraints.gridx = 0;
		listConstraints.gridy = 0;
		listConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		JButton addEvent = new JButton("+ Add Event");
		addEvent.addActionListener(this);
		addEvent.setActionCommand("addNew");
		eventList.add(addEvent, listConstraints);

		// Event List
		listConstraints.gridx = 0;
		listConstraints.gridy = 1;
		listConstraints.weightx = 1.0;
		listConstraints.weighty = 1.0;
		listConstraints.fill = GridBagConstraints.BOTH;
		dtm = new DefaultTableModel();
		eventTable = new JTable(dtm);
		eventTable.setModel(dtm);
		eventTable.getSelectionModel().addListSelectionListener(this);
		eventTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		detailsConstraints.insets = new Insets(10, 10, 10, 10);
		detailsConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		detailsConstraints.weightx = 1.0;
		detailsConstraints.weighty = 1.0;
		this.add(eventDetails, c);

		// Event Panel
		detailsConstraints.gridx = 0;
		detailsConstraints.gridy = 0;
		adminPanel = new EventAdminPanel();
		eventDetails.add(adminPanel, detailsConstraints);

		fillTable();

	}
	
	public void fillTable() {
		updatingTable = true;
		// Initialize Table Colnames
		String[] columnNames = { "Event", "Participants", "Dates" };
		EventDao ed = EventDao.createDao();
		List<Event> list = ed.findAllOwnEventsFromPeer(GuiMemory.getLoginUser());
		String[][] strings;
		
		if(list.size()==0) {
			strings = new String[0][0];
		} else {
			strings = new String[list.size()][3];
			for(int i=0;i<list.size();i++) {
				
				strings[i][0] = list.get(i).getName();
				
				if(list.get(i).getInvitedPeers() != null) {
					strings[i][1] = "" + list.get(i).getInvitedPeers().size();
				} else {
					strings[i][1] = "";
				}
				if(list.get(i).getDateOptions() != null) {
					strings[i][2] = "" + list.get(i).getDateOptions().size();
				} else {
					strings[i][2] = "";
				}

			}
		}
		dtm.setDataVector(strings, columnNames);
		eventTable.setModel(dtm);
		
		updatingTable = false;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (!arg0.getValueIsAdjusting() && eventTable.getSelectedRow() != -1
				&& eventTable.getValueAt(eventTable.getSelectedRow(), 1) != null
				&& !eventTable.getValueAt(eventTable.getSelectedRow(), 1)
						.equals("")) {
			
			adminPanel.setVisible(true);
			
			String eventName = (String) eventTable.getValueAt(
					eventTable.getSelectedRow(), 0);
			System.out.println("SELECTION " + eventName);
			EventDao ed = EventDao.createDao();
			Event event = new Event();
			event.setName(eventName);
			Event e = ed.find(event);
			
			selectedEvent = e;
			
			adminPanel.redraw(e);
			eventDetails.repaint();
			eventDetails.revalidate();

		} else if (!updatingTable && !arg0.getValueIsAdjusting() && eventTable.getSelectedRow() == -1){
			selectedEvent = null;
			adminPanel.setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("addNew")) {
			selectedEvent = null;
			eventTable.clearSelection();
			adminPanel.setVisible(true);
			adminPanel.redraw();
			eventDetails.repaint();
			eventDetails.revalidate();
		}
	}

	@Override
	public void notify(RemoteEvent arg0) throws UnknownEventException,
			RemoteException {
		log.info("Lade OwnEventsTab Tabelle neu");
		
		fillTable();
		
		if(selectedEvent != null) {
			adminPanel.setVisible(true);
			adminPanel.redraw(loadEvent(selectedEvent));
		} else {
			adminPanel.setVisible(false);
		}
	}
	
	private Event loadEvent(Event e) {
		EventDao ed = EventDao.createDao();
		Event findEvent = ed.getEmptyTemplate();
		findEvent.setName(e.getName());
		return ed.find(findEvent);
	}

}
