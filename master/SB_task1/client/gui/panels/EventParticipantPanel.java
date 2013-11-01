package gui.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import dao.EventDao;
import dao.NotificationDao;
import entities.Comment;
import entities.DateOption;
import entities.Event;
import entities.Notification;
import entities.Peer;
import gui.mem.GuiMemory;

public class EventParticipantPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 4131826833586621858L;
	private static Logger log = Logger.getLogger(EventParticipantPanel.class);
	
	
	private Event event;
	
	private EventDao eDao;
	private NotificationDao nDao;
	
	private JLabel eventLabel;
	private JLabel eventName;
	private JLabel adminLabel;
	private JLabel adminName;
	private JLabel optionsLabel;
	private JLabel commentsLabel;
	
	private JComboBox optionsCombo;
	private JTextArea commentText;
	private JButton addComment;
	private JButton saveOption;
	private CommentsPanel commentsPanel;
	
	private DefaultComboBoxModel cbm;
	
	public EventParticipantPanel() {
		super();
		this.event = new Event();
		buildGUI();
	}
	
	public EventParticipantPanel(Event event) {
		super();
		buildGUI();
		redraw(event);
	}
	
	public void buildGUI() {
		
		eDao = new EventDao();
		nDao = new NotificationDao();
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,10,5,10);
		
		// Name Label
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		eventLabel = new JLabel("Event Name:");
		this.add(eventLabel, c);
		
		// Name
		c.gridx = 1;
		c.gridy = 0;
		eventName = new JLabel("");
		this.add(eventName, c);
		
		// Admin Label
		c.gridx = 0;
		c.gridy = 1;
		adminLabel = new JLabel("Administrator:");
		this.add(adminLabel, c);
		
		// Admin
		c.gridx = 1;
		c.gridy = 1;
		adminName = new JLabel("");
		this.add(adminName, c);
		
		// Options Label
		c.gridx = 0;
		c.gridy = 2;
		optionsLabel = new JLabel("Options:");
		this.add(optionsLabel, c);
		
		// Options Label
		c.gridx = 1;
		c.gridy = 2;
		cbm = new DefaultComboBoxModel();
		optionsCombo = new JComboBox(cbm);
		this.add(optionsCombo, c);
		
		// Save Button
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		//c.fill = GridBagConstraints.HORIZONTAL;
		saveOption = new JButton("Save");
		saveOption.addActionListener(this);
		saveOption.setActionCommand("saveOption");
		this.add(saveOption, c);
		
		// Seperator
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		JSeparator seperator = new JSeparator(JSeparator.HORIZONTAL);
		this.add(seperator, c);
		
		// Comments Label
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		commentsLabel = new JLabel("Comments:");
		this.add(commentsLabel, c);
		
		// Comments Panel
		c.gridx = 1;
		c.gridy = 6;
		//c.fill = GridBagConstraints.HORIZONTAL;
		//c.anchor = GridBagConstraints.FIRST_LINE_END;
		commentsPanel = new CommentsPanel();
		this.add(commentsPanel, c);
		
		//comment textarea
		c.gridx = 1;
		c.gridy = 7;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		commentText = new JTextArea(5,25);
		commentText.setWrapStyleWord(true);
		commentText.setLineWrap(true);
		commentText.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(commentText, c);
		
		
		// Add Button
		c.gridx = 1;
		c.gridy = 8;
		c.fill = GridBagConstraints.NONE;
		
		addComment = new JButton("Add");
		addComment.addActionListener(this);
		addComment.setActionCommand("addComment");
		this.add(addComment, c);
	}
	
	public void redraw(Event event) {

		this.event = event;
		eventName.setText(event.getName());
		adminName.setText(event.getOrganisator());
		fillComboBox(event.getDateOptions());
		commentsPanel.redraw(event);
		commentText.setText("");
		checkIfChoosen();
	}
	
	private void fillComboBox(List<DateOption> options) {
		cbm.removeAllElements();
		if(options != null) {
			for(DateOption d:options) {
				cbm.addElement(d.getDate());
			}
			optionsCombo.setModel(cbm);
		}
		
	}
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if(ev.getActionCommand().equals("saveOption")) {
			Date selectedOption = (Date) optionsCombo.getSelectedItem();
			Integer index = null;
			
			Event foundEvent = eDao.find(event);
			try {
				if(foundEvent != null) {
					for(int i=0;i<foundEvent.getDateOptions().size();i++) {
						if(foundEvent.getDateOptions().get(i).getDate().getTime() == selectedOption.getTime()) {
							index = i;
							break;
						}
					}
					
					if(index != null) {
						foundEvent.getDateOptions().get(index).getPeersForDate().add(GuiMemory.getLoginUser());
						eDao.update(foundEvent);
						optionsCombo.setEnabled(false);
						saveOption.setEnabled(false);
						//update organisator
						nDao.write(new Notification(Notification.SYSTEM_UPDATE,foundEvent.getName(),foundEvent.getOrganisator()));
						
						//all have registered
						if(eDao.checkIfAllHaveChoosen(foundEvent)) {
							nDao.write(new Notification(Notification.ALL_REGISTERED,foundEvent.getName(),foundEvent.getOrganisator()));
						}
					}
				} 
			} catch(Exception e) {
				JOptionPane.showMessageDialog(new JFrame(),"Event was deleted!", "Warning",JOptionPane.WARNING_MESSAGE);
				setVisible(false);
			}
			
		} else if(ev.getActionCommand().equals("addComment")) {
			Comment c = new Comment();
			c.setAuthor(GuiMemory.getLoginUser());
			c.setDate(new Date());
			c.setText(commentText.getText());
		
			Event foundEvent = eDao.find(event);
			
			if(foundEvent != null) {
				foundEvent.getComments().add(c);
				eDao.update(foundEvent);
				
				// Update Invited Peers
				for(Peer p : foundEvent.getInvitedPeers()) {
					nDao.write(new Notification(Notification.COMMENT_ADDED,foundEvent.getName(),p.getName()));
				}
				
				//update organisator
				nDao.write(new Notification(Notification.COMMENT_ADDED,foundEvent.getName(),foundEvent.getOrganisator()));
				
				redraw(foundEvent);
			} else {
				log.error("Could not find event "+event.getName()+ " for adding comment");
				JOptionPane.showMessageDialog(this,"Error while adding Comment","Warning",JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}
	
	
	private void checkIfChoosen() {
		//already choosen
		if(eDao.checkIfHasChoosen(event,GuiMemory.getLoginUser())) {
			optionsCombo.setEnabled(false);
			saveOption.setEnabled(false);
			optionsCombo.setSelectedItem(eDao.getChoosenDateOption(event,GuiMemory.getLoginUser()).getDate());
		} else {
			optionsCombo.setEnabled(true);
			saveOption.setEnabled(true);
		}
	}
}
