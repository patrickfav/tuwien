package gui;

import java.rmi.MarshalledObject;

import dao.NotificationDao;
import entities.Notification;
import entities.Peer;
import gui.logic.NotificationListener;
import gui.mem.GuiMemory;
import gui.tabs.InvitedEventsTab;
import gui.tabs.NotificationTab;
import gui.tabs.OwnEventsTab;
import gui.tabs.SearchTab;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import util.SpaceUtil;

import net.jini.core.event.RemoteEventListener;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.server.TransactionManager;


public class PeerWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(PeerWindow.class);
	
	private NotificationDao nDao;
	private NotificationListener nListener;
	private NotificationTab nTab;
	private InvitedEventsTab ieTab;
	private OwnEventsTab oeTab;
	private SearchTab sTab;
	
	public PeerWindow(String name) {
		super(name);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		nDao = new NotificationDao();
		
		//create tabs
		nTab = new NotificationTab();
		ieTab = new InvitedEventsTab();
		oeTab = new OwnEventsTab();
		sTab = new SearchTab();
		
		//add delete listener
		GuiMemory.addDeleteListener(ieTab);
		
		//add notify listener
		nListener = new NotificationListener();
		nListener.addListner(nTab);
		nListener.addListner(oeTab);
		nListener.addListner(ieTab);
		
		registerNotifies(GuiMemory.getLoginUser(),nListener);
		
		// Build TAB GUI
		JTabbedPane peerTabs = new JTabbedPane();
		peerTabs.add("Notificatios", nTab);
		peerTabs.add("Invited Events", ieTab);
		peerTabs.add("Own Events", oeTab);
		peerTabs.add("Search Peers", sTab);
		
		getContentPane().add(peerTabs);
		
		// Create GUI
		this.pack();
		this.setVisible(true);
	}
	
	private void registerNotifies(Peer peer,RemoteEventListener listener) {	
		Notification n = new Notification();
		n.setReceiverName(peer.getName());
		
		try {
			nDao.notify(n, null, listener, Lease.FOREVER, new MarshalledObject<Peer>(peer));
		} catch (Exception e) {
			log.error("Could not set notify: "+e.getMessage());
			JOptionPane.showMessageDialog(null,"Error in Notify Registerer","Warning",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
}
