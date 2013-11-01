package gui.tabs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import dao.PeerDao;

import entities.Peer;

public class SearchTab extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SearchTab.class);
	private JTextField searchString;
	private JTable searchTable;
	
	private Object[][] data = {};
	private String[] columnNames = {"Name"};
	private JScrollPane scrollpane;

	public SearchTab() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Search Layout
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10,10,10,10);
		
		// Label
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		JLabel searchLabel = new JLabel("Peer Name:");
		this.add(searchLabel, c);
		
		// Text Field
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		searchString = new JTextField(20);
		this.add(searchString, c);
		
		// Search Button
		c.gridx = 2;
		c.gridy = 0;
		c.fill = GridBagConstraints.NONE;
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(this);
		searchButton.setActionCommand("search");
		this.add(searchButton, c);
		
		// Search Results
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.BOTH;
		
		searchTable = new JTable(data, columnNames);
	    scrollpane = new JScrollPane(searchTable);
		this.add(scrollpane, c);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String param = e.getActionCommand();
		
		if(param.equals("search")) {
			Peer searchPeer = new Peer(searchString.getText());
			PeerDao pd = PeerDao.createDao();
			Peer foundPeer = pd.find(searchPeer);
			
			if(foundPeer != null) {
				log.info("Peer gefunden: " + foundPeer.getName());
				
				DefaultTableModel tbModel = new DefaultTableModel(columnNames, 0);
				
				Vector<String> rowData = new Vector<String>();
				
				rowData.add(foundPeer.getName());
				
				tbModel.addRow(rowData);
				
				searchTable.setModel(tbModel);
				
			} else {
				DefaultTableModel tbModel = new DefaultTableModel(columnNames, 0);
				Vector<String> rowData = new Vector<String>();
				rowData.add("Keinen Peer gefunden!");
				tbModel.addRow(rowData);
				searchTable.setModel(tbModel);
				log.info("Keinen Peer gefunden");
			}
		}
		
	}
	
}
