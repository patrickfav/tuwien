package factory.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.Operation;

import products.Teddybear;

import dao.interfaces.IStorageDAO;
import dao.space.SpaceTeddybearDao;

import factory.Factory;

public class GenericList extends JPanel implements NotificationListener {

	private static Logger log = Logger.getLogger(GenericList.class);
	private static final long serialVersionUID = 3242768274187723565L;
	
	public static final int ASSEMBLED_MODE = 0;
	public static final int CHECKED_MODE = 1;
	public static final int SHIPPING_MODE = 2;
	public static final int DEFECT_MODE = 3;
	
	private Factory parent;
	private int mode;
	private JList teddyGuiList;
	private DefaultListModel dlm;
	
	public GenericList(Factory parent,int mode) {
		super();
		this.parent = parent;
		this.mode = mode;
		buildGUI();
		if(Factory.getMode().equals(Factory.RMI_MODE)) {
			Factory.executorService.execute(new ListRefresher());
		} else if(Factory.getMode().equals(Factory.SPACE_MODE)) {
			redraw();
			registerListener();
		}
	}

	public void buildGUI() {
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);
		c.fill = GridBagConstraints.HORIZONTAL;
		
		teddyGuiList = new JList();
		dlm = new DefaultListModel();
		
		rebuildList();
		
		teddyGuiList.setLayoutOrientation(JList.VERTICAL);
		teddyGuiList.setVisibleRowCount(-1);
		
		add(teddyGuiList);
		
	}
	
	public void redraw() {
		this.removeAll();
		this.buildGUI();
		this.revalidate();
	}
	
	private void rebuildList() {
		dlm.removeAllElements();
		
		List<Teddybear> teddies = null;
		teddies = getList();
 		if(teddies == null || teddies.size() == 0) {
			teddies = new ArrayList<Teddybear>();
			dlm.addElement("                                                                                        ");
			dlm.remove(0);
			dlm.addElement("                                        EMPTY                                       ");
		} else {
			for(int i = 0; i < teddies.size(); i++) {
				dlm.addElement((i+1)+". "+teddies.get(i).toString());
			}
		}
		teddyGuiList.setModel(dlm);
	}
	
	private class ListRefresher extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					log.info("Thread: Refreshing Panel Type "+mode);
					redraw();
					sleep(Factory.LISTS_REFERESH_TIME);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private List<Teddybear> getList() {
		IStorageDAO storage = parent.getStorage();
		
		switch(mode) {
			case 0: 
				return storage.getTeddybearDao().getAssembled();
			case 1: 
				return storage.getTeddybearDao().getChecked();
			case 2: 
				return storage.getTeddybearDao().getShipped();
			case 3: 
				return storage.getTeddybearDao().getDefect();
			default:
				return new ArrayList<Teddybear>();
		}
	}
	
	private void registerListener() {
		switch(mode) {
		case 0: 
			((SpaceTeddybearDao) parent.getStorage().getTeddybearDao()).registerAssembledListener(this);
			break;
		case 1: 
			((SpaceTeddybearDao) parent.getStorage().getTeddybearDao()).registerCheckedListener(this);
			break;
		case 2: 
			((SpaceTeddybearDao) parent.getStorage().getTeddybearDao()).registerShippedListener(this);
			break;
		case 3: 
			((SpaceTeddybearDao) parent.getStorage().getTeddybearDao()).registerDefectListener(this);
			break;
		default:
			break;
	}
	}
	

	@Override
	public void entryOperationFinished(Notification arg0, Operation arg1,List<? extends Serializable> arg2) {
		log.debug("Parts notification reveived - redraw list "+mode);
		redraw();	
	}
}
