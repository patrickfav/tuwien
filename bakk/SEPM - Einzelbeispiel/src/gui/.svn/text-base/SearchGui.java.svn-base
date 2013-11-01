package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import db.dao.*;
import entities.*;

public class SearchGui extends JPanel implements ActionListener,ListSelectionListener{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SearchGui.class);
	
	//all
	private Vector<Driver>  all_driver;
	private Vector<Taxi> all_taxis;
	private Vector<Driver>  search_driver;
	private Vector<Taxi> search_taxis;
	private Vector<String[]> special_search;
	private DriverManager driver_manager = new DriverManager();
	private TaxiManager taxi_manager = new TaxiManager();
	private GeneralManager general_manager = new GeneralManager();
	private boolean ignoreTableEvent = false;
	
	private GridBagConstraints c = new GridBagConstraints();
	private JLabel 		lbl_search = new JLabel("");
	private JTextField 	tf_search = new JTextField(15);
	private JButton		btn_search = new JButton("Finden");
	
	private String[] data = {"Nachname", "SVNR", "Kennzeichen", "Typbezeichnung"};
	private JComboBox ddl_search = new JComboBox(data);
	private JTable		table = new JTable(6,9);

	private JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JScrollPane scrl_table, detail_pane = new JScrollPane(new DetailViewGui(null,null));
	private JPanel main_content = new JPanel();
	
	//driver
	private JLabel lbl_svnr, lbl_fname, lbl_sname, lbl_sex, lbl_vehicle, lbl_covers;
	private JTextField tf_svnr, tf_fname, tf_sname;
	private JComboBox ddl_vehicle, ddl_covers;
	private JRadioButton rb_male, rb_female, rb_any;
	private ButtonGroup group;
	private String[]	d_colnames = {"Svnr", "Vorname", "Nachname","Tel","maennlich","faehrt","vetritt","update","erstellt"};
	private Object[][] 	d_tabledata;
	
	
	//taxi
	private JLabel lbl_id, lbl_brand, lbl_type, lbl_color, lbl_seats, lbl_plate, lbl_consumption, lbl_disabled;
	private JTextField tf_id, tf_type,tf_plate,tf_consumption;
	private JComboBox ddl_brand,ddl_color, ddl_seats;
	private JRadioButton rb_disabled, rb_able;
	private String[]	t_colnames = {"ID", "Marke", "Typ","Farbe","Sitze","Kennzeichen","Verbrauch","defekt","update","erstellt"};
	private Object[][] 	t_tabledata;
	
	//special
	private JRadioButton rb_query1, rb_query2,rb_query3,rb_query4,rb_query5,rb_query6;
	private String[]	s_colnames = {"Svnr/Id", "Vorname", "Nachname","Marke","Typ","Kennzeichen","€/km"};
	private Object[][] s_tabledata;
	
	public SearchGui(int mode) {
		 main_content.setLayout(new GridBagLayout());
		
		//gets all taxis and drivers for dropdownbox
		all_driver = new Vector<Driver>(driver_manager.search(new Driver()));
		all_taxis = new Vector<Taxi>(taxi_manager.search(new Taxi()));
		
		//adds the "any" option to the drop down lists
		Driver anyd = new Driver(0);
		anyd.setSurname("- egal -");
		all_driver.add(anyd);
		
		Taxi anyt = new Taxi(0);
		anyt.setPlate("- egal -");
		all_taxis.add(anyt);
		
		//Table adjustments
		table.setAutoCreateRowSorter(true);
	    table.setDragEnabled(true);
	    //table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	    //table.setSize(600,250);
	    table.setPreferredScrollableViewportSize(table.getPreferredSize());
	    table.getSelectionModel().addListSelectionListener(this);
		scrl_table = new JScrollPane(table);
		//scrl_table.setSize(300,250);
		
	    //general gridbaglayout adjustments
	    c.insets = new Insets(5,5,0,0);
	    
		//MODE 1 = DRIVER SEARCH
		if(mode == 1) {
			
			//DEFINITIONS
			
			lbl_svnr = new JLabel("SVNR:");
			tf_svnr = new JTextField(10);

			
			lbl_fname = new JLabel("Vorname:");
			tf_fname = new JTextField(15);
			
			lbl_sname = new JLabel("Nachname:");
			tf_sname = new JTextField(15);
			
			lbl_vehicle = new JLabel("faehrt: ");
			ddl_vehicle = new JComboBox(all_taxis);
			ddl_vehicle.setSelectedItem(anyt);
			
			lbl_covers = new JLabel("Vertritt:");
			ddl_covers = new JComboBox(all_driver);
			ddl_covers.setSelectedItem(anyd);
			
			lbl_sex = new JLabel("Geschlecht:");
			rb_any = new JRadioButton ("egal",true);
			rb_male = new JRadioButton ("maennlich",false);
			rb_female = new JRadioButton ("weiblich",false);
			group = new ButtonGroup();
		    group.add(rb_male);
		    group.add(rb_female);
		    group.add(rb_any);
		    
		    //LAYOUT
		    
		    //svnr
			c.gridx = 0;
			c.gridy = 0;
			main_content.add(lbl_svnr,c);
			c.gridx = 1;
			main_content.add(tf_svnr,c);
			
			//geschlecht
			c.gridx = 0;
			c.gridy = 1;
			main_content.add(lbl_sex,c);
			c.gridx = 1;
			main_content.add(rb_any,c);
			c.gridx = 2;
			main_content.add(rb_male,c);
			c.gridx = 3;
			main_content.add(rb_female,c);
			
			//vor und nachname
			c.gridx = 0;
			c.gridy = 2;
			main_content.add(lbl_fname,c);
			c.gridx = 1;
			main_content.add(tf_fname,c);
			
			c.gridx = 2;
			c.gridy = 2;
			main_content.add(lbl_sname,c);
			c.gridx = 3;
			main_content.add(tf_sname,c);
			
			//vetritt und fhrt
			c.gridx = 0;
			c.gridy = 3;
			main_content.add(lbl_vehicle,c);
			c.gridx = 1;
			main_content.add(ddl_vehicle,c);
			
			c.gridx = 2;
			c.gridy = 3;
			main_content.add(lbl_covers,c);
			c.gridx = 3;
			main_content.add(ddl_covers,c);
			
			c.gridx = 3;
			c.gridy = 4;
			main_content.add(btn_search,c);
			btn_search.addActionListener(this);
			btn_search.setActionCommand("searchdriver");
			
			//search result table
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 4;
			c.fill=GridBagConstraints.BOTH;
			main_content.add(scrl_table,c);
			
			
			logger.debug("Driver Search Gui has been initialized");
		}
		
		
		//MODE 2 = TAXI SEARCH
		else if (mode == 2) {

			Taxi t = new Taxi(0);
			
			// main_content.add the any values for the drop down list
			Vector<String> barr = new Vector<String>(Arrays.asList(t.getBrandArray()));
			Vector<String> carr = new Vector<String>(Arrays.asList(t.getColorArray()));
			Vector<Integer> sarr = new Vector<Integer>(Arrays.asList(t.getSeatsArray()));
			
			barr.add("- egal -");
			carr.add("- egal -");
			sarr.add(0);
			
			//DEFINITIONS
			
			lbl_id = new JLabel("ID:");
			tf_id = new JTextField(4);

			
			lbl_brand = new JLabel("Marke:");
			ddl_brand = new JComboBox(barr);
			ddl_brand.setSelectedItem("- egal -");
			
			lbl_type = new JLabel("Typbezeichnung:");
			tf_type = new JTextField(15);
			
			lbl_color = new JLabel("Farbe:");
			ddl_color = new JComboBox(carr);
			ddl_color.setSelectedItem("- egal -");
			
			lbl_seats = new JLabel("Sitze:");
			ddl_seats = new JComboBox(sarr);
			ddl_seats.setSelectedItem(0);
			
			lbl_plate = new JLabel("Kennzeichen:");
			tf_plate = new JTextField(10);
			
			lbl_consumption = new JLabel("Typbezeichnung:");
			tf_consumption = new JTextField(5);
			
			lbl_disabled = new JLabel("Status");
			rb_any = new JRadioButton ("egal",true);
			rb_disabled = new JRadioButton ("defekt",false);
			rb_able = new JRadioButton ("fahrtuechtig",false);
			group = new ButtonGroup();
		    group.add(rb_any);
		    group.add(rb_disabled);
		    group.add(rb_able);
		    
		    //LAYOUT
		    
		    //plate and id
			c.gridx = 0;
			c.gridy = 0;
			main_content.add(lbl_plate,c);
			c.gridx = 1;
			main_content.add(tf_plate,c);
			c.gridx = 2;
			c.gridy = 0;
			main_content.add(lbl_id,c);
			c.gridx = 3;
			main_content.add(tf_id,c);
			
			
			//car status
			c.gridx = 0;
			c.gridy = 1;
			main_content.add(lbl_disabled,c);
			c.gridx = 1;
			main_content.add(rb_any,c);
			c.gridx = 2;
			main_content.add(rb_disabled,c);
			c.gridx = 3;
			main_content.add(rb_able,c);
			
			//brand and type
			c.gridx = 0;
			c.gridy = 2;
			main_content.add(lbl_brand,c);
			c.gridx = 1;
			main_content.add(ddl_brand,c);
			
			c.gridx = 2;
			c.gridy = 2;
			main_content.add(lbl_type,c);
			c.gridx = 3;
			main_content.add(tf_type,c);
			
			//color and seats
			c.gridx = 0;
			c.gridy = 3;
			main_content.add(lbl_color,c);
			c.gridx = 1;
			main_content.add(ddl_color,c);
			
			c.gridx = 2;
			c.gridy = 3;
			main_content.add(lbl_seats,c);
			c.gridx = 3;
			main_content.add(ddl_seats,c);
			
			c.gridx = 3;
			c.gridy = 4;
			main_content.add(btn_search,c);
			btn_search.addActionListener(this);
			btn_search.setActionCommand("searchtaxi");
			
			//search result table
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 4;
			c.fill=GridBagConstraints.HORIZONTAL;
			main_content.add(scrl_table,c);
			
			logger.debug("Taxi Search Gui has been initialized");
			
		}
		//MODE 3 = SPECIAL SEARCH
		else if (mode == 3) {
			ddl_covers = new JComboBox(driver_manager.search(new Driver()));
			rb_query1 = new JRadioButton ("Alle Fahrer mit defekten Autos",true);
			rb_query2 = new JRadioButton ("Billigste Fahrer (nach Verbrauch)",false);
			rb_query3 = new JRadioButton ("Mehrfachbelegungen bei Taxis",false);
			rb_query4 = new JRadioButton ("Fahrer ohne Vertretung",false);
			rb_query5 = new JRadioButton ("Nicht zugewiesene Taxis",false);
			rb_query6 = new JRadioButton ("Vertretungen für Fahrer: ",false);
			group = new ButtonGroup();
		    group.add(rb_query1);
		    group.add(rb_query2);
		    group.add(rb_query3);
		    group.add(rb_query4);
		    group.add(rb_query5);
		    group.add(rb_query6);
		    
		    //query select
			c.gridx = 0;
			c.gridy = 0;
			main_content.add(rb_query1,c);
			c.gridx = 1;
			main_content.add(rb_query2,c);
			c.gridx = 2;
			main_content.add(rb_query3,c);
			
			c.gridx = 0;
			c.gridy = 1;
			main_content.add(rb_query4,c);
			c.gridx = 1;
			main_content.add(rb_query5,c);
			
			c.gridx = 0;
			c.gridy = 2;
			main_content.add(rb_query6,c);
			c.gridx = 1;
			main_content.add(ddl_covers,c);
			
			//search button
			c.gridx = 2;
			c.gridy = 3;
			main_content.add(btn_search,c);
			btn_search.addActionListener(this);
			btn_search.setActionCommand("specialsearch");
			
			//search result table
			c.gridx = 0;
			c.gridy = 4;
			c.gridwidth = 4;
			c.fill=GridBagConstraints.HORIZONTAL;
			main_content.add(scrl_table,c);
			
			logger.debug("Special View Gui has been initialized");
		}
		//MODE 0 = QUICK SEARCH 
		else {
			lbl_search.setText("Suche:");
			
			c.gridx = 0;
			c.gridy = 0;
			main_content.add(lbl_search,c);
			
			c.gridx = 1;
			main_content.add(tf_search,c);
			
			c.gridx = 2;
			main_content.add(ddl_search,c);
			ddl_search.addActionListener(this);
			ddl_search.setActionCommand("changesearch");
			
			c.gridx = 3;
			main_content.add(btn_search,c);
			
			btn_search.addActionListener(this);
			btn_search.setActionCommand("searchdriver");
			
			//seperator
			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 4;
			main_content.add(new JSeparator(JSeparator.HORIZONTAL),c);

			
			//search result table
			c.gridx = 0;
			c.gridy = 3;
			c.gridwidth = 4;
			c.fill=GridBagConstraints.BOTH;
			main_content.add(scrl_table,c);
			
			logger.debug("Quick Search Gui has been initialized");
		}
		
	    splitPane.setTopComponent(main_content);
	    splitPane.setBottomComponent(detail_pane);
        splitPane.setDividerLocation(300); 
        splitPane.setPreferredSize(new Dimension(700, 550));
	    
	    add(splitPane);
	    
	    setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//disables the possibility of table events to prevent error messages
		ignoreTableEvent = true;
		
		//SEARCH FOR DRIVES
		if(e.getActionCommand().equals("searchdriver")){
			Driver d = new Driver();
			
			//quicksearch
			if(!tf_search.getText().equals("") || lbl_search.getText().equals("Suche:")) {
				
				if(ddl_search.getSelectedItem().equals("SVNR") && !tf_search.getText().equals(""))
					d.setSvnr(new Integer(tf_search.getText().trim()));
				
				if(ddl_search.getSelectedItem().equals("Nachname"))
					d.setSurname(tf_search.getText().trim());
				
				logger.debug("ActionEvent: Quick Search Driver");
			} else { //fullsearch	
				if(!tf_svnr.getText().equals(""))
					d.setSvnr(new Integer(tf_svnr.getText().trim()));
				
				if(!tf_fname.getText().equals(""))
					d.setFirstname(tf_fname.getText().trim());
				
				if(!tf_sname.getText().equals(""))
					d.setSurname(tf_sname.getText().trim());
				
				if(!((Taxi) ddl_vehicle.getSelectedItem()).getPlate().equals("- egal -"))
					d.setVehicle(((Taxi) ddl_vehicle.getSelectedItem()).getId());
				
				if(!((Driver) ddl_covers.getSelectedItem()).getSurname().equals("- egal -"))
					d.setCovers(((Driver) ddl_covers.getSelectedItem()).getId());
				
				if(rb_any.getSelectedObjects() == null)
					if(rb_male.getSelectedObjects() == null) {
						d.setMale(false);
					} else {
						d.setMale(true);
					}
				logger.debug("ActionEvent: Search Driver");
			}
			search_driver = driver_manager.search(d);
			
			//if nothing found
			if(search_driver.size() == 0) {
				JOptionPane.showMessageDialog(new JFrame(), "Es konnten leider keine Fahrer zu dieser Suche gefunden werden.");
			} else {
			
				d_tabledata = new Object[search_driver.size()][9];
				int i = 0;
				for(Driver ds: search_driver){
					d_tabledata[i][0] = ds.getId();
					d_tabledata[i][1] = ds.getFirstname();
					d_tabledata[i][2] = ds.getSurname();
					d_tabledata[i][3] = ds.getTel();
					d_tabledata[i][4] = ds.getMale().toString();
					d_tabledata[i][5] = taxi_manager.load(ds.getVehicle()).toString();
					d_tabledata[i][6] = driver_manager.load(ds.getCovers()).toString();
					d_tabledata[i][7] = ds.getUpdate().toString();
					d_tabledata[i][8] = ds.getCreate().toString();
					i++;
				}
				DefaultTableModel tm = new DefaultTableModel(d_tabledata, d_colnames);
				table.setModel(tm);
				
				logger.debug("ActionEvent: Search Driver - updating Table");
			}
		}
		
		// SEARCH FOR TAXIS
		if(e.getActionCommand().equals("searchtaxi")){
			Taxi t = new Taxi();
			
			//quicksearch
			if(!tf_search.getText().equals("")|| lbl_search.getText().equals("Suche:")) {
				
				if(ddl_search.getSelectedItem().equals("Kennzeichen"))
					t.setPlate(tf_search.getText().trim());
				
				if(ddl_search.getSelectedItem().equals("Typbezeichnung"))
					t.setType(tf_search.getText().trim());
				
				logger.debug("ActionEvent: Quick Search Taxi");
			} else { //fullsearch	
				if(!tf_id.getText().equals(""))
						t.setId(new Integer(tf_id.getText().trim()));
				
				if(!ddl_brand.getSelectedItem().toString().equals("- egal -"))
					t.setBrand(ddl_brand.getSelectedItem().toString());
				
				if(!tf_type.getText().equals(""))
					t.setType(tf_type.getText().trim());
				
				if(!ddl_color.getSelectedItem().toString().equals("- egal -"))
					t.setColor(ddl_color.getSelectedItem().toString());
				
				if(!ddl_seats.getSelectedItem().equals(0))
					t.setSeats(new Integer(ddl_seats.getSelectedItem().toString()));
				
				if(!tf_consumption.getText().equals(""))
					t.setConsumption(new Double(tf_id.getText().trim()));
				
				if(rb_any.getSelectedObjects() == null)
					if(rb_disabled.getSelectedObjects() == null) {
						t.setDisabled(false);
					} else {
						t.setDisabled(true);
					}
				logger.debug("ActionEvent: Search Taxi");
			}
			
			search_taxis = taxi_manager.search(t);
			
			//if nothing found
			if(search_taxis.size() == 0) {
				JOptionPane.showMessageDialog(new JFrame(), "Es konnten leider keine Taxis zu dieser Suche gefunden werden.");
			} else {
			
				t_tabledata = new Object[search_taxis.size()][10];
				int i = 0;
				for(Taxi ts: search_taxis){
					t_tabledata[i][0] = ts.getId();
					t_tabledata[i][1] = ts.getBrand();
					t_tabledata[i][2] = ts.getType();
					t_tabledata[i][3] = ts.getColor();
					t_tabledata[i][4] = ts.getSeats().toString();
					t_tabledata[i][5] = ts.getPlate();
					t_tabledata[i][6] = ts.getConsumption();
					t_tabledata[i][7] = ts.getDisabled().toString();
					t_tabledata[i][8] = ts.getUpdate().toString();
					t_tabledata[i][9] = ts.getCreate().toString();
					i++;
				}
				DefaultTableModel tm = new DefaultTableModel(t_tabledata, t_colnames);
				table.setModel(tm);
				
				logger.debug("ActionEvent: Search Taxi - updating Table");
			}
		}
		//CHANGE ACTION - QUICKSEARCH
		if(e.getActionCommand().equals("changesearch")){
			if(ddl_search.getSelectedItem().equals("Nachname") || ddl_search.getSelectedItem().toString().equals("SVNR"))
				btn_search.setActionCommand("searchdriver");
			if(ddl_search.getSelectedItem().equals("Kennzeichen") || ddl_search.getSelectedItem().toString().equals("Typbezeichnung"))
				btn_search.setActionCommand("searchtaxi");
			logger.debug("ActionEvent: Change quicksearchmode");
		}
		//change detail view when selecting a row in the table
		if(e.getActionCommand().equals("changesearch")){
			table.getSelectedRows();
			logger.debug("ActionEvent: select detail view (in search table)");
		}
		
		
		//SPECIAL SEARCH
		if(e.getActionCommand().equals("specialsearch")){
			if(rb_query1.getSelectedObjects() != null) {
				special_search = general_manager.getDamagedCarDriver();
				
				s_tabledata = new Object[special_search.size()][6];
				int i = 0;
				for(String[] s: special_search){
				
					s_tabledata[i][0] = s[0];
					s_tabledata[i][1] = s[1];
					s_tabledata[i][2] = s[2];
					s_tabledata[i][3] = s[3];
					s_tabledata[i][4] = s[4];
					s_tabledata[i][5] = s[5];
					i++;
				}
				logger.debug("ActionEvent: View 1");
			} else if(rb_query2.getSelectedObjects() != null) {
				special_search = general_manager.getCheapestCarDriver();
				
				s_tabledata = new Object[special_search.size()][7];
				int i = 0;
				for(String[] s: special_search){
					//calculate costs
					Double consum = new Double(s[6]);
					consum = consum*1.2 / 100;
					
					s_tabledata[i][0] = s[0];
					s_tabledata[i][1] = s[1];
					s_tabledata[i][2] = s[2];
					s_tabledata[i][3] = s[3];
					s_tabledata[i][4] = s[4];
					s_tabledata[i][5] = s[5];
					s_tabledata[i][6] = consum.toString();
					i++;
				}
				logger.debug("ActionEvent: View 2");
			} else if(rb_query3.getSelectedObjects() != null) {
				special_search = general_manager.getDoubleCarDriver();
				
				s_tabledata = new Object[special_search.size()][6];
				int i = 0;
				for(String[] s: special_search){
					
					s_tabledata[i][0] = s[0];
					s_tabledata[i][1] = s[1];
					s_tabledata[i][2] = s[2];
					s_tabledata[i][3] = s[3];
					s_tabledata[i][4] = s[4];
					s_tabledata[i][5] = s[5];
					i++;
				}
				logger.debug("ActionEvent: View 3");
			} else if(rb_query4.getSelectedObjects() != null) {
				special_search = general_manager.getNoCoveredDriver();
				
				s_tabledata = new Object[special_search.size()][3];
				int i = 0;
				for(String[] s: special_search){
					s_tabledata[i][0] = s[0];
					s_tabledata[i][1] = s[1];
					s_tabledata[i][2] = s[2];
					i++;
				}
				logger.debug("ActionEvent: View 4");
			}else if(rb_query5.getSelectedObjects() != null) {
				special_search = general_manager.getUnusedCars();
				
				s_tabledata = new Object[special_search.size()][6];
				int i = 0;
				for(String[] s: special_search){
					s_tabledata[i][0] = s[0];
					s_tabledata[i][3] = s[1];
					s_tabledata[i][4] = s[2];
					s_tabledata[i][5] = s[3];
					i++;
				}
				logger.debug("ActionEvent: View 5");
			}else if(rb_query6.getSelectedObjects() != null) {
				special_search = general_manager.getDriverCovering(((Entity) ddl_covers.getSelectedItem()).getId());
				
				s_tabledata = new Object[special_search.size()][3];
				int i = 0;
				for(String[] s: special_search){
					s_tabledata[i][0] = s[0];
					s_tabledata[i][1] = s[1];
					s_tabledata[i][2] = s[2];
					i++;
				}
				logger.debug("ActionEvent: View 6");
			}
			
			DefaultTableModel tm = new DefaultTableModel(s_tabledata, s_colnames);
			table.setModel(tm);
			logger.debug("ActionEvent: View - updating Table");
		}
		
		//reenables the possibility of table events
		ignoreTableEvent = false;
	}
	

	//EVENT: Table Selection
	public void valueChanged(ListSelectionEvent e) {
		logger.debug("TableListener: entering...");
		
		//taking care, that event will be ignored when invalid
		if(!ignoreTableEvent && table.getSelectedRow() != -1 && table.getValueAt(table.getSelectedRow(),0) !=null && !table.getValueAt(table.getSelectedRow(),0).equals("") ) {
			Integer id;
			try{
				if(table.getValueAt(table.getSelectedRow(),0) instanceof String) {
					id = new Integer((String) table.getValueAt(table.getSelectedRow(),0));
				} else {
					id = (Integer) table.getValueAt(table.getSelectedRow(),0);
				}
				
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(new JFrame(), "Die Tabelle wurde verändern - Details können nicht angezeigt werden.","Fehler in der Tabelle",JOptionPane.WARNING_MESSAGE);
				logger.warn("TableListener: error - "+ex.getMessage());
				return;
			}
			
			Driver d = driver_manager.load(id);
			Taxi t = taxi_manager.load(id);
			
			if(d.getId() != null) {
				detail_pane = new JScrollPane(new DetailViewGui(d,null));
				splitPane.setBottomComponent(detail_pane);
				logger.debug("TableListener: load driver");
			}
			else if(t.getId() != null) {
				detail_pane = new JScrollPane(new DetailViewGui(t,null));
				splitPane.setBottomComponent(detail_pane);
				logger.debug("TableListener: load taxi");
			}
		}
	}

}


