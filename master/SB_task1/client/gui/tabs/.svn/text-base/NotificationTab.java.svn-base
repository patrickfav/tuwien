package gui.tabs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import dao.NotificationDao;

import net.jini.core.event.RemoteEvent;
import net.jini.core.event.RemoteEventListener;
import net.jini.core.event.UnknownEventException;

import entities.Notification;
import gui.mem.GuiMemory;

public class NotificationTab extends JPanel implements RemoteEventListener, ActionListener{
	private static final long serialVersionUID = 7610807725615647984L;
	private static Logger log = Logger.getLogger(NotificationTab.class);
	private static final String NO_ENTRIES = "No entries found.";
	
	private List<Notification> notifications;
	
	private JList notificationList;
	private NotificationDao nDao;
	private DefaultListModel dlm;
	
	public NotificationTab() {
		
		//initialize
		nDao = new NotificationDao();
		
		notifications = getNotificationsFromSpace();
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		notificationList = new JList(); //data has type Object[]
		
		dlm = new DefaultListModel();
		
		if(notifications.size()== 0) {
			dlm.addElement(NO_ENTRIES);
		} else {
			for(Notification n: notifications) {
				if(n.getType() != Notification.SYSTEM_UPDATE)
					dlm.addElement(getNotificationString(n));
			}
		}
		
		notificationList.setModel(dlm);
		notificationList.setLayoutOrientation(JList.VERTICAL);
		notificationList.setVisibleRowCount(-1);
		
		c.insets = new Insets(5,5,0,0);
		c.gridx = 0;
		c.gridy = 0;
		this.add(notificationList,c);
		
		// Add Button
		c.gridx = 0;
		c.gridy = 1;
		JButton debugButton = new JButton("Debug");
		debugButton.addActionListener(this);
		debugButton.setActionCommand("debug");
		//this.add(debugButton, c);
		
	}
	
	private void addToGuiList(Notification n) {
		
		
		notifications.add(n);
		if(dlm.get(0).equals(NO_ENTRIES)) {
			dlm.removeAllElements();
		}
		dlm.addElement(getNotificationString(n));
		notificationList.setModel(dlm);
		//notificationList = new JList(getMessagesArray(notifications));
		
		if(n.getType() == Notification.DELETED_EVENT || n.getType() == Notification.UNINVITED || n.getType() == Notification.EDIT_EVENT_PARTICIPANT) {
			GuiMemory.fireDeleteEvent(n.getEventName());
		}
	}
	
	private String getNotificationString(Notification n) {
		String sentence = "";
		String participant = "[PARTICIPANT]: ";
		String admin = "[ADMINISTRATOR]: ";
		String comment = "[COMMENT]: ";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/dd - HH:mm:ss");
		
		switch(n.getType()) {
			
			case  Notification.DELETED_EVENT:
				sentence = participant+"Your invitation for "+n.getEventName()+" has been canceld. "+n.getNote()+" "+"("+sdf.format(n.getDate())+")";
				break;
			case  Notification.ALL_REGISTERED:
				sentence =  admin+"All participants have choosen a date for event "+n.getEventName()+". "+n.getNote()+" "+"("+sdf.format(n.getDate())+")";
				break;
			case  Notification.EDIT_EVENT_PARTICIPANT:
				sentence =  participant+n.getEventName()+" was edited. "+n.getNote()+" "+"("+sdf.format(n.getDate())+")";
				break;
			case  Notification.EDIT_EVENT_ADMIN:
				sentence =  participant+n.getEventName()+" was edited. "+n.getNote()+" "+"("+sdf.format(n.getDate())+")";
				break;
			case  Notification.FIXED_DATE:
				sentence =  participant+"The date for "+n.getEventName()+" was fixed. "+n.getNote()+" "+"("+sdf.format(n.getDate())+")";
				break;
			case  Notification.NEW_EVENT_PARTICIPANT:
				sentence =  participant+"You have been invited to "+n.getEventName()+". "+n.getNote()+" "+"("+sdf.format(n.getDate())+")";
				break;
			case  Notification.NEW_EVENT_ADMIN:
				sentence =  admin+"You have been invited to "+n.getEventName()+". "+n.getNote()+" "+"("+sdf.format(n.getDate())+")";
				break;
			case  Notification.UNINVITED:
				sentence =  participant+"You have been uninvited from "+n.getEventName()+". "+n.getNote()+" "+"("+sdf.format(n.getDate())+")";
				break;
			case  Notification.COMMENT_ADDED:
				sentence =  comment+"A comment has been added to "+n.getEventName()+". "+n.getNote()+" "+"("+sdf.format(n.getDate())+")";
				break;
			case  Notification.COMMENT_DELETED:
				sentence =  comment+"A comment was deleted from "+n.getEventName()+". "+n.getNote()+" "+"("+sdf.format(n.getDate())+")";
				break;	
			case  Notification.SYSTEM_UPDATE:
				sentence = "";
				break;
			default:
				sentence = "Error: Unkown Notification Type: "+n.getNote()+"("+sdf.format(n.getDate())+")";
				break;
		}
		
		
		return sentence;
	}
	
	/*
	private String[] getMessagesArray(List<Notification> list) {
		String[] arr = new String[list.size()];
		
		for(int i=0;i<list.size();i++) {
			arr[i] = getNotificationString(list.get(i));
		}
		
		return arr;
	}*/
	
	private List<Notification> getNotificationsFromSpace() {		
		return nDao.takeAllFromPeer(GuiMemory.getLoginUser());
	}

	@Override
	public void notify(RemoteEvent arg0) throws UnknownEventException, RemoteException {
		
		log.debug("Notify: New Notification");
		Notification n = nDao.takeFromPeer(GuiMemory.getLoginUser());
		if(n != null) {
			addToGuiList(n);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Notification n = new Notification(Notification.EDIT_EVENT_PARTICIPANT, "Test1", GuiMemory.getLoginUser().getName() );
		nDao.write(n);
	}
}
