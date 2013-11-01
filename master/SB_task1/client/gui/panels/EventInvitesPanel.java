package gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entities.Peer;
import gui.dialogs.SearchDialog;

public class EventInvitesPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1009892006267330834L;

	public List<Peer> peers;
	public HashMap<String, Peer> invited = new HashMap<String, Peer>();
	public JPanel container;

	private GridBagConstraints c;

	private JButton addButton;

	private SearchDialog dialog;
	
	public EventInvitesPanel(List<Peer> peers) {
		this.peers = peers;
		
		for(Peer p : peers) {
			invited.put(p.getName(), p);
		}
		
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.insets = new Insets(5, 10, 5, 10);
		
		// Add Peer Container
		c.gridx = 0;
		c.gridy = 0;
		container = new JPanel();
		container.setLayout(new GridBagLayout());
		paintPeers();
		this.add(container, c);

		// Add Button
		c.gridx = 1;
		c.gridy = 1;
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		addButton.setActionCommand("addPeer");
		this.add(addButton, c);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("addPeer")) {
			dialog = new SearchDialog(this);
			dialog.setAlwaysOnTop(true);
			dialog.pack();
			dialog.setVisible(true);
		} else if(command.equals("select")) {
			Peer peer = dialog.getSelectedPeer();
			if(peer != null && proofPeers(peer.getName())) {
				peers.add(peer);
			}
			dialog.dispose();
		} else {
			peers.remove(Integer.parseInt(command));
		}
		paintPeers();
	}
	
	public void paintPeers() {
		container.removeAll();
		// Display Options
		for (int i = 0; i < peers.size(); i++) {
			c.gridx = 0;
			c.gridy = i;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			Peer peer = peers.get(i);
			JLabel pL = new JLabel(peer.getName());
			container.add(pL, c);
			c.gridx = 1;
			c.gridy = i;
			JButton optionButton = new JButton("X");
			optionButton.addActionListener(this);
			optionButton.setActionCommand(i + "");
			container.add(optionButton, c);
		}
		container.revalidate();
	}

	public boolean proofPeers(String name) {
		for(Peer p : peers) {
			if(p.getName().equals(name)) {
				return false;
			}
		}
		return true;
	}

	public List<Peer> getPeers() {
		return peers;
	}

	public void setPeers(List<Peer> peers) {
		this.peers = peers;
	}

	public HashMap<String, Peer> getInvited() {
		return invited;
	}

	public void setInvited(HashMap<String, Peer> invited) {
		this.invited = invited;
	}
	
}
