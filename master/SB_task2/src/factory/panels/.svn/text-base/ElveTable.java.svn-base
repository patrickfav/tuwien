package factory.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.Operation;

import dao.space.SpacePartDao;

import workers.ProductionElve;

import factory.Factory;
import factory.storage.ElveStorage;

public class ElveTable extends JPanel implements ActionListener,
		ListSelectionListener,NotificationListener {

	private static Logger log = Logger.getLogger(ElveTable.class);
	
	private static final long serialVersionUID = -4571555775715679943L;

	private JTable eventTable;
	private JScrollPane scrollpane;
	private DefaultTableModel dtm;
	@SuppressWarnings("unused")
	private Factory parent;

	private JButton startWorkingButton;

	public ElveTable(Factory parent) {
		super();
		this.parent = parent;
		buildGUI();
		if(Factory.getMode().equals(Factory.RMI_MODE)) {
			Factory.executorService.execute(new TableRefresher());
		} else if(Factory.getMode().equals(Factory.SPACE_MODE)) {
			redraw();
			((SpacePartDao) parent.getStorage().getPartDao()).registerPartsListener(this);
		}
	}

	public void buildGUI() {

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);

		c.gridx = 0;
		c.gridy = 0;

		dtm = new DefaultTableModel();
		eventTable = new JTable(dtm);
		eventTable.setModel(dtm);
		eventTable.getSelectionModel().addListSelectionListener(this);
		eventTable
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollpane = new JScrollPane(eventTable);
		add(scrollpane, c);

		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		startWorkingButton = new JButton("Start Working");
		startWorkingButton.addActionListener(this);
		startWorkingButton.setActionCommand("startWorking");
		add(startWorkingButton, c);

		fillTable();

	}

	public void redraw() {
		this.removeAll();
		this.buildGUI();
		this.revalidate();
	}

	public void fillTable() {
		// Initialize Table Colnames
		String[] colnames = new String[] { "Product", "Quantity", "Fault rate",
				"Working" };
		List<ProductionElve> list = ElveStorage.getInstance().getElves();
		String[][] strings;

		if (list.size() == 0) {
			strings = new String[0][0];
		} else {
			strings = new String[list.size()][4];
			for (int i = 0; i < list.size(); i++) {
				strings[i][0] = String.valueOf(list.get(i).getProductName());
				strings[i][1] = String.valueOf(list.get(i).getAnzahl());
				strings[i][2] = String.valueOf(list.get(i).getFehlerquote());
				strings[i][3] = String.valueOf(list.get(i).isRunning());
			}
		}
		dtm.setDataVector(strings, colnames);
		eventTable.setModel(dtm);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (command.equals("startWorking")) {
			log.debug("ElveTable: # Selected Elves " + eventTable.getSelectedRowCount());
			for (int i : eventTable.getSelectedRows()) {
				ProductionElve e = ElveStorage.getInstance().getElves().get(i);
				log.debug("ElveTable: Started Elve #" + e.getId() + " at position " + i);
				Factory.executorService.execute(e);
			}
			redraw();
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent event) {

	}

	private class TableRefresher extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					log.info("Thread: Refreshing ElveTable Panel");
					redraw();
					sleep(Factory.TABLE_REFRESH_TIME);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void entryOperationFinished(Notification arg0, Operation arg1, List<? extends Serializable> arg2) {
		log.debug("Parts notification reveived - redraw table.");
		redraw();
	}
}
