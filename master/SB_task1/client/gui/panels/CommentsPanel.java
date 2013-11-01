package gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import dao.EventDao;
import dao.NotificationDao;

import entities.Comment;
import entities.Event;
import entities.Notification;
import entities.Peer;
import gui.mem.GuiMemory;

public class CommentsPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = -633633505692986143L;
	private static Logger log = Logger.getLogger(CommentsPanel.class);
	
	private EventDao eDao;
	private NotificationDao nDao;
	
	private Event event;
	
	public CommentsPanel() {
		eDao = new EventDao();
		nDao = new NotificationDao();
	}
	
	public void buildGUI(Event ev) {	
		event = ev;
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,0,0,0);
		
		for(int i=0;i<event.getComments().size();i++) {
			c.insets = new Insets(5,0,0,0);
			c.gridx = 0;
			c.gridy = i;
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			GridBagConstraints c1 = new GridBagConstraints();
			TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), commentToTitle(event.getComments().get(i)));
			title.setTitleJustification(TitledBorder.LEFT);
			panel.setBorder(title);
			
			JTextArea textArea = new JTextArea(event.getComments().get(i).getText());
			textArea.setWrapStyleWord(true);
			textArea.setLineWrap(true);
			textArea.setColumns(20);
			//textArea.setRows(5);
			textArea.setEditable(false);
			textArea.setMargin(new Insets(2, 2, 2, 2));
			//textArea.setBorder(BorderFactory.createLineBorder(Color.black));
			c1.gridx = 0;
			c1.gridy = 0;
			panel.add(textArea,c1);
			
			c1.insets = new Insets(0,5,0,0);
			c1.gridx = 1;
			JButton del = new JButton("X");
			del.setEnabled(isAdministrator(event.getOrganisator()));
			del.addActionListener(this);
			del.setActionCommand(String.valueOf(i));
			panel.add(del,c1);
			
			this.add(panel,c);
		}
	}
	
	public void redraw(Event e) {
		this.removeAll();
		this.buildGUI(e);
	}
	
	private String commentToTitle(Comment c) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/dd - HH:mm:ss");
		return c.getAuthor().getName()+" ("+sdf.format(c.getDate())+")";
	}
	
	private boolean isAdministrator(String organisator) {
		return organisator.equals(GuiMemory.getLoginUser().getName());
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		if(!ev.getActionCommand().equals("")) {
			try {
				
				Event foundEvent = eDao.find(event);
				
				if(foundEvent != null) {
					log.info("Deleting comment "+Integer.valueOf(ev.getActionCommand())+" - "+foundEvent.getComments().size());
					foundEvent.getComments().remove(Integer.parseInt(ev.getActionCommand()));
					log.info("Deleting comment "+Integer.valueOf(ev.getActionCommand())+" - "+foundEvent.getComments().size());
					eDao.update(foundEvent);
					
					// Update Invited Peers
					for(Peer p : foundEvent.getInvitedPeers()) {
						nDao.write(new Notification(Notification.COMMENT_DELETED,foundEvent.getName(),p.getName()));
					}
					
					//update organisator
					nDao.write(new Notification(Notification.COMMENT_DELETED,foundEvent.getName(),foundEvent.getOrganisator()));
					
					redraw(foundEvent);
				} else {
					log.error("Could not find event "+event.getName()+ " for deleting comment");
					JOptionPane.showMessageDialog(this,"Error while deleting Comment","Warning",JOptionPane.WARNING_MESSAGE);
				}
				
			} catch(Exception e) {
				log.error("Error while deleting comment "+e.getMessage());
			}
		}
		
	}

}
