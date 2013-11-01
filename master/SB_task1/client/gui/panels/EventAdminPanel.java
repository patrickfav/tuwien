package gui.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractButton;
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
import javax.swing.JTextField;

import dao.EventDao;
import dao.NotificationDao;
import dao.PeerDao;

import entities.Comment;
import entities.DateOption;
import entities.Event;
import entities.Notification;
import entities.Peer;
import gui.mem.GuiMemory;

public class EventAdminPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -1326957048331776596L;

	public int flag;
	public Event event;

	private JLabel eventLabel;
	private JLabel invitesLabel;
	private JLabel optionsLabel;

	private JComboBox optionsCombo;
	private DefaultComboBoxModel cbm;

	private JButton eventSave;

	private JTextField eventString;
	private EventOptionsPanel optionsPanel;
	private EventInvitesPanel peerPanel;

	private JButton eventDelete;

	private JLabel comboLabel;

	private JLabel commentsLabel;

	private CommentsPanel commentsPanel;

	private JTextArea commentText;

	private AbstractButton addComment;

	public EventAdminPanel() {
		super();
		redraw();
	}

	/*public EventAdminPanel(Event event) {
		flag = 1;
		this.event = event;
		buildGUI();
		eventString.setEditable(false);
		eventSave.setText("Edit event");
		eventSave.addActionListener(this);
		eventSave.setActionCommand("edit");

		eventDelete = new JButton("Delete");
		eventDelete.addActionListener(this);
		eventDelete.setActionCommand("delete");
		this.add(eventDelete, new GridBagConstraints(0, 4, 1, 1, 0, 0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));

		fillComboBox(event.getDateOptions());
	}*/

	public void buildGUI() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);

		// Name Label
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		eventLabel = new JLabel("Event Name:");
		this.add(eventLabel, c);

		// Text Field
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		eventString = new JTextField(event.getName(), 20);
		this.add(eventString, c);

		// Invites Label
		c.gridx = 0;
		c.gridy = 1;
		invitesLabel = new JLabel("Invites:");
		this.add(invitesLabel, c);

		// Peers
		c.gridx = 1;
		c.gridy = 1;
		peerPanel = new EventInvitesPanel(event.getInvitedPeers());
		this.add(peerPanel, c);

		// Options Label
		c.gridx = 0;
		c.gridy = 2;
		optionsLabel = new JLabel("Options:");
		this.add(optionsLabel, c);

		// Options
		c.gridx = 1;
		c.gridy = 2;
		optionsPanel = new EventOptionsPanel(event.getDateOptions());
		this.add(optionsPanel, c);

		if (flag == 1) {
			// Options Label
			c.gridx = 0;
			c.gridy = 3;
			comboLabel = new JLabel("Finalisieren:");
			this.add(comboLabel, c);

			// Final
			c.gridx = 1;
			c.gridy = 3;
			cbm = new DefaultComboBoxModel();
			optionsCombo = new JComboBox(cbm);
			this.add(optionsCombo, c);
		}

		// Save Button
		c.gridx = 1;
		c.gridy = 4;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		eventSave = new JButton();
		this.add(eventSave, c);

		if (flag == 1) {
			
			// Seperator
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 2;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			JSeparator seperator = new JSeparator(JSeparator.HORIZONTAL);
			this.add(seperator, c);
			
			// Comments Label
			c.gridx = 0;
			c.gridy = 6;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.NONE;
			commentsLabel = new JLabel("Comments:");
			this.add(commentsLabel, c);

			// Comments Panel
			c.gridx = 1;
			c.gridy = 6;
			// c.fill = GridBagConstraints.HORIZONTAL;
			// c.anchor = GridBagConstraints.FIRST_LINE_END;
			commentsPanel = new CommentsPanel();
			commentsPanel.buildGUI(event);
			this.add(commentsPanel, c);

			// comment textarea
			c.gridx = 1;
			c.gridy = 8;
			c.anchor = GridBagConstraints.FIRST_LINE_END;
			commentText = new JTextArea(5, 25);
			commentText.setWrapStyleWord(true);
			commentText.setLineWrap(true);
			commentText.setBorder(BorderFactory.createLineBorder(Color.black));
			this.add(commentText, c);

			// Add Button
			c.gridx = 1;
			c.gridy = 9;
			c.fill = GridBagConstraints.NONE;

			addComment = new JButton("Add");
			addComment.addActionListener(this);
			addComment.setActionCommand("addComment");
			this.add(addComment, c);
		}

	}

	public void redraw() {
		flag = 0;
		this.event = new Event();
		removeAll();
		buildGUI();
		eventSave.setText("Save new event");
		eventSave.addActionListener(this);
		eventSave.setActionCommand("save");
	}
	
	public void redraw(Event e) {
		if(e != null) {
			flag = 1;
			this.event = e;
			removeAll();
			buildGUI();
			eventString.setEditable(false);
			eventSave.setText("Edit event");
			eventSave.addActionListener(this);
			eventSave.setActionCommand("edit");
	
			eventDelete = new JButton("Delete");
			eventDelete.addActionListener(this);
			eventDelete.setActionCommand("delete");
			this.add(eventDelete, new GridBagConstraints(0, 4, 1, 1, 0, 0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE,
					new Insets(0, 0, 0, 0), 0, 0));
	
			fillComboBox(event.getDateOptions());
		} else {
			redraw();
		}
	}
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		EventDao ed = EventDao.createDao();
		PeerDao pd = PeerDao.createDao();
		NotificationDao nd = NotificationDao.createDao();

		event.setName(eventString.getText());
		event.setOrganisator(GuiMemory.getLoginUser().getName());
		event.setDateOptions(optionsPanel.getDateOptions());
		event.setInvitedPeers(peerPanel.getPeers());

		// SAVE
		if (command.equals("save")) {
			if (!eventString.getText().equals("")
					&& optionsPanel.getDateOptions().size() > 0
					&& peerPanel.getPeers().size() > 0) {

				ed.write(event);

				// Update own
				pd.updateOwn(GuiMemory.getLoginUser(), event);
				nd.write(new Notification(Notification.NEW_EVENT_ADMIN, event
						.getName(), GuiMemory.getLoginUser().getName()));

				// Update Invited Peers
				for (Peer p : event.getInvitedPeers()) {
					pd.updateInvite(p, event);
					nd.write(new Notification(Notification.NEW_EVENT_PARTICIPANT, event
							.getName(), p.getName()));
				}

				// Success
				JOptionPane.showMessageDialog(new JFrame(),
						"Event wurde gespeichert!", "Erfolg",
						JOptionPane.INFORMATION_MESSAGE);

				// Clear Fields
				clearFields();

			} else {
				// Error
				JOptionPane.showMessageDialog(new JFrame(),
						"Es sind nicht alle Felder ausgefüllt!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

			// EDIT
		} else if (command.equals("edit")) {
			if (!eventString.getText().equals("")
					&& optionsPanel.getDateOptions().size() > 0
					&& peerPanel.getPeers().size() > 0) {

				ed.update(event);

				// Update own
				pd.updateOwn(GuiMemory.getLoginUser(), event);
				nd.write(new Notification(Notification.EDIT_EVENT_ADMIN, event
						.getName(), GuiMemory.getLoginUser().getName()));

				// Update Invited Peers
				for (Peer p : event.getInvitedPeers()) {
					if (peerPanel.getInvited().containsKey(p.getName())) {
						pd.updateInvite(p, event);
						nd.write(new Notification(Notification.EDIT_EVENT_PARTICIPANT,
								event.getName(), p.getName()));
						peerPanel.getInvited().remove(p.getName());
					} else {
						pd.updateInvite(p, event);
						nd.write(new Notification(Notification.NEW_EVENT_PARTICIPANT, event
								.getName(), p.getName()));
					}
				}

				Set<String> s = peerPanel.getInvited().keySet();
				Iterator<String> i = s.iterator();
				while (i.hasNext()) {
					Peer p = new Peer(i.next());
					pd.updateRemoveInvite(p, event);
					nd.write(new Notification(Notification.UNINVITED, event
							.getName(), p.getName()));
				}

				// Success
				JOptionPane.showMessageDialog(new JFrame(),
						"Event wurde gespeichert!", "Erfolg",
						JOptionPane.INFORMATION_MESSAGE);

				// Clear Fields
				//clearFields();

			} else {
				// Error
				JOptionPane.showMessageDialog(new JFrame(),
						"Es sind nicht alle Felder ausgefüllt!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (command.equals("delete")) {
			
			//actual delete
			EventDao.createDao().take(event);
			
			// Update own
			pd.updateRemoveOwn(GuiMemory.getLoginUser(), event);
			nd.write(new Notification(Notification.DELETED_EVENT, event
					.getName(), GuiMemory.getLoginUser().getName()));

			// Unregister Invited Peers
			for (Peer p : event.getInvitedPeers()) {
				pd.updateRemoveInvite(p, event);
				nd.write(new Notification(Notification.DELETED_EVENT, event
						.getName(), p.getName()));
			}
			
			

			
			// Success
			JOptionPane.showMessageDialog(new JFrame(),
					"Event wurde gelöscht!", "Erfolg",
					JOptionPane.INFORMATION_MESSAGE);

			// Clear Fields
			clearFields();
			setVisible(false);
		} else if (command.equals("addComment")) {
			
			Comment c = new Comment();
			c.setAuthor(GuiMemory.getLoginUser());
			c.setDate(new Date());
			c.setText(commentText.getText());

			Event foundEvent = ed.find(event);

			if (foundEvent != null) {
				foundEvent.getComments().add(c);
				ed.update(foundEvent);

				// Update Invited Peers
				for (Peer p : foundEvent.getInvitedPeers()) {
					nd.write(new Notification(Notification.COMMENT_ADDED,
							foundEvent.getName(), p.getName()));
				}

				// update organisator
				nd.write(new Notification(Notification.COMMENT_ADDED,
						foundEvent.getName(), foundEvent.getOrganisator()));
				
				commentsPanel.removeAll();
				commentsPanel.buildGUI(event);

			} else {
				JOptionPane.showMessageDialog(this,
						"Error while adding Comment", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		}

	}

	private void clearFields() {

		event = new Event();

		this.removeAll();

		buildGUI();

		eventSave.setText("Save new event");
		eventSave.addActionListener(this);
		eventSave.setActionCommand("save");

	}

	private void fillComboBox(List<DateOption> options) {
		cbm.removeAllElements();
		if (options != null) {
			for (DateOption d : options) {
				cbm.addElement(d.getDate());
			}
			optionsCombo.setModel(cbm);
		}
	}

}
