package gui;

import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import db.dao.DriverManager;
import db.dao.TaxiManager;

import entities.*;

public class DetailViewGui extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DetailViewGui.class);
	
	//ALL
	private Random rn = new Random();
	 
	private JLabel lbl_id;
	private JLabel tf_id;
	private JTextField tf_id2;
	private JLabel lbl_update =new JLabel("Letzes Update:");
	private JLabel lbl_update2;
	private JLabel lbl_create= new JLabel("Erstellt:");
	private JLabel lbl_create2;
	
	private JLabel lbl_save = new JLabel();
	private JButton btn_save = new JButton("Speichern");
	private JButton btn_delete = new JButton("Löschen");
	private JButton btn_create = new JButton("Erstellen");
	private GridBagConstraints c = new GridBagConstraints();
	
	//DRIVER SPECIFIC
	private JLabel lbl_fname, lbl_sname, lbl_tel, lbl_vehicle, lbl_covers, lbl_sex;
	private JTextField tf_fname, tf_sname, tf_tel;
	private JComboBox ddl_vehicle , ddl_covers;
	private JRadioButton rb_male, rb_female;
	private ButtonGroup group;
	
	//TAXI SPECIFIC
	private JLabel lbl_brand, lbl_type, lbl_color, lbl_seats,lbl_plate, lbl_consumption, lbl_disabled,lbl_drivers,lbl_driverlabel;
	private JTextField tf_type, tf_plate,tf_consumption;
	private JComboBox ddl_brand, ddl_color, ddl_seats,ddl_drivers;
	private JCheckBox cb_disabled;
	
	
	private DriverManager driver_manager = new DriverManager();
	private TaxiManager taxi_manager = new TaxiManager();
	private Vector<Driver>  all_driver;
	private Vector<Taxi> all_taxis;
	private boolean isEmpty = false;
	private ActionListener parent_listener;
	
	public DetailViewGui(Entity e, ActionListener parent) {
		super(new GridBagLayout(),true);
		
		//sends action events to a parent listener
		parent_listener = parent;
		
		//gets all taxis and drivers for dropdownbox
		all_driver = new Vector<Driver>(driver_manager.search(new Driver()));
		all_taxis = new Vector<Taxi>(taxi_manager.search(new Taxi()));
		
		//general gridbaglayout adjustments
	    c.insets = new Insets(5,5,0,0);
		
		//if is driver
		if(e instanceof Driver) {
			
			Driver d = (Driver) e;
			
			//DEFINITIONS
			
			//checks if emtpy object is given for create
			if(e.getId() == null) {
				isEmpty = true;
				d = new Driver(0);
			}
			
			lbl_id = new JLabel("SVNR:");
			if(!isEmpty) {
				tf_id = new JLabel(d.getSvnr().toString());
			} else {
				tf_id2 = new JTextField(d.getSvnr().toString(),7);
			}
			
			lbl_fname = new JLabel("Vorname:");
			tf_fname = new JTextField(d.getFirstname(),8);
			
			lbl_sname = new JLabel("Nachname:");
			tf_sname = new JTextField(d.getSurname(),8);
			
			lbl_tel = new JLabel("Telefon:");
			tf_tel = new JTextField(d.getTel(),8);
			
			lbl_vehicle = new JLabel("fährt: ");
			ddl_vehicle = new JComboBox(all_taxis);
			if(!isEmpty) {
				ddl_vehicle.setEditable(true); //a workaround to get the right selected
				ddl_vehicle.setSelectedItem(taxi_manager.load(d.getVehicle()));
				ddl_vehicle.setEditable(false);
			}
			
			lbl_covers = new JLabel("Vertritt:");
			ddl_covers = new JComboBox(all_driver);
			if(!isEmpty) {
				ddl_covers.setEditable(true); //a workaround to get the right selected
				ddl_covers.setSelectedItem(driver_manager.load(d.getCovers()));
				ddl_covers.setEditable(false);
			}
			
			lbl_sex = new JLabel("Geschlecht:");
			rb_male = new JRadioButton ("maennlich",d.getMale());
			rb_female = new JRadioButton ("weiblich",!d.getMale());
			group = new ButtonGroup();
		    group.add(rb_male);
		    group.add(rb_female);

		    
			lbl_update2 = new JLabel(d.getUpdate().toString());
			lbl_create2 = new JLabel(d.getCreate().toString());
			
			//if not empty save and delete button will be displayed
			if(!isEmpty) {
				btn_save.addActionListener(this);
				btn_save.addActionListener(parent_listener);
				btn_save.setActionCommand("savedriver");
				btn_delete.addActionListener(this);
				btn_delete.addActionListener(parent_listener);
				btn_delete.setActionCommand("deldriver");
			} else {
				btn_create.addActionListener(this);
				btn_create.addActionListener(parent_listener);
				btn_create.setActionCommand("createdriver");
			}
			
			//LAYOUT
			
			
			
			c.gridx = 0;
			c.gridy = 0;
			add(lbl_id,c);
			
			if(!isEmpty) {
				c.gridx = 1;
				add(tf_id,c);
			} else {
				c.gridx = 1;
				add(tf_id2,c);
			}
			
			c.gridx = 0;
			c.gridy = 1;
			add(lbl_sex,c);
			c.gridx = 1;
			add(rb_male,c);
			c.gridx = 2;
			add(rb_female,c);
			
			c.gridx = 0;
			c.gridy = 2;
			add(lbl_fname,c);
			c.gridx = 1;
			add(tf_fname,c);
			
			c.gridx = 2;
			c.gridy = 2;
			add(lbl_sname,c);
			c.gridx = 3;
			add(tf_sname,c);
			
			c.gridx = 0;
			c.gridy = 3;
			add(lbl_tel,c);
			c.gridx = 1;
			add(tf_tel,c);
			
			c.gridx = 0;
			c.gridy = 4;
			add(lbl_vehicle,c);
			c.gridx = 1;
			add(ddl_vehicle,c);
			
			c.gridx = 2;
			c.gridy = 4;
			add(lbl_covers,c);
			c.gridx = 3;
			add(ddl_covers,c);
			
			if(!isEmpty) {
				c.gridx = 0;
				c.gridy = 5;
				add(lbl_update,c);
				c.gridx = 1;
				add(lbl_update2,c);
				
				c.gridx = 2;
				c.gridy = 5;
				add(lbl_create,c);
				c.gridx = 3;
				add(lbl_create2,c);
			}
			
			c.insets = new Insets(50,5,0,0);
			
			c.gridx = 1;
			c.gridy = 6;
			add(lbl_save,c);
			
			//if not empty save and delete button will be displayed
			if(!isEmpty) {
				c.gridx = 2;
				add(btn_save,c);
				//only show delete button in tree view mode
				if(parent_listener != null) {
					c.gridx = 3;
					add(btn_delete,c);
				}
			}
			else {
				c.gridx = 3;
				add(btn_create,c);
			}
			
		}
		//else if taxi
		else if (e instanceof Taxi) {
			
			Taxi t = (Taxi) e;
			Vector<Driver> cardrivers = new Vector<Driver>();
			
			if(!isEmpty) {
				//for the driver list for taxi
				Driver d = new Driver();
				d.setVehicle(t.getId());
				cardrivers = driver_manager.search(d);
				//adds "niemand" if no drivers
				if(cardrivers.size()==0) {
					Driver empty = new Driver(0);
					empty.setSurname("niemand");
					cardrivers.add(empty);
				}
			}
			
			//DEFINITIONS
			
			//checks if emtpy object is given for create
			if(e.getId() == null) {
				isEmpty = true;
				t = new Taxi(0);
			}
			
			if(!isEmpty) {
				lbl_id = new JLabel("ID:");
				tf_id = new JLabel(t.getId().toString());
			} else  {
				lbl_id = new JLabel("");
				tf_id = new JLabel("");
			}
			
			lbl_brand = new JLabel("Marke:");
			ddl_brand = new JComboBox(t.getBrandArray());
			if(!isEmpty) ddl_brand.setSelectedItem(t.getBrand());

			
			lbl_type = new JLabel("Typbezeichnung:");
			tf_type = new JTextField(t.getType(),8);
			
			lbl_color = new JLabel("Farbe:");
			ddl_color = new JComboBox(t.getColorArray());
			if(!isEmpty) ddl_color.setSelectedItem(t.getColor());
			
			lbl_plate = new JLabel("Kennzeichen:");
			tf_plate = new JTextField(t.getPlate(),6);
			
			lbl_seats = new JLabel("Sitze:");
			ddl_seats = new JComboBox(t.getSeatsArray());
			if(!isEmpty) ddl_seats.setSelectedItem(t.getSeats());
			
			lbl_consumption = new JLabel("Verbrauch (l/100km):");
			tf_consumption = new JTextField(t.getConsumption().toString(),3);
			
			lbl_disabled = new JLabel("Status:");
			cb_disabled = new JCheckBox("defekt",t.getDisabled());
			
			lbl_update2 = new JLabel(t.getUpdate().toString());
			lbl_create2 = new JLabel(t.getCreate().toString());
			
			if(!isEmpty) {
				lbl_driverlabel = new JLabel("wird verwendet von:");
				ddl_drivers = new JComboBox(cardrivers);
			}
			
			//if not empty save and delete button will be displayed
			if(!isEmpty) {
				btn_save.addActionListener(this);
				btn_save.addActionListener(parent_listener);
				btn_save.setActionCommand("savetaxi");
				btn_delete.addActionListener(this);
				btn_delete.addActionListener(parent_listener);
				btn_delete.setActionCommand("deltaxi");
				
			} else {
				btn_create.addActionListener(this);
				btn_create.addActionListener(parent_listener);
				btn_create.setActionCommand("createtaxi");
			}
			
			//LAYOUT
			
			c.gridx = 0;
			c.gridy = 0;
			add(lbl_plate,c);
			c.gridx = 1;
			add(tf_plate,c);
			
			c.gridx = 2;
			add(lbl_id,c);
			c.gridx = 3;
			add(tf_id,c);
			
			
			c.gridx = 0;
			c.gridy = 1;
			add(lbl_brand,c);
			c.gridx = 1;
			add(ddl_brand,c);
			
			c.gridx = 2;
			add(lbl_type,c);
			c.gridx = 3;
			add(tf_type,c);
			
			c.gridx = 0;
			c.gridy = 2;
			add(lbl_color,c);
			c.gridx = 1;
			add(ddl_color,c);
			
			c.gridx = 2;
			add(lbl_seats,c);
			c.gridx = 3;
			add(ddl_seats,c);
			
			
			
			c.gridx = 0;
			c.gridy = 3;
			add(lbl_consumption,c);
			c.gridx = 1;
			add(tf_consumption,c);
			
			c.gridx = 2;
			add(lbl_disabled,c);
			c.gridx = 3;
			add(cb_disabled,c);
			
			if(!isEmpty) {
				
				c.gridx = 0;
				c.gridy = 4;
				add(lbl_driverlabel,c);
				c.gridx = 1;
				//c.gridwidth = 2;
				add(ddl_drivers,c);
			
			
				c.gridx = 0;
				c.gridy = 5;
				add(lbl_update,c);
				c.gridx = 1;
				add(lbl_update2,c);
				
				c.gridx = 2;
				add(lbl_create,c);
				c.gridx = 3;
				add(lbl_create2,c);
			}
			
			c.insets = new Insets(50,5,0,0);
			c.gridx = 1;
			c.gridy = 11;
			add(lbl_save,c);
			
			//if not empty save and delete button will be displayed
			if(!isEmpty) {
				c.gridx = 2;
				add(btn_save,c);
				//only show delete button in tree view mode
				if(parent_listener != null) {
					c.gridx = 3;
					add(btn_delete,c);
				}
			}
			else {
				c.gridx = 3;
				add(btn_create,c);
			}
		}
		else if (e == null) {
			
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		/*
		 * DRIVERS 
		 */
		//save
		if(e.getActionCommand().equals("savedriver")){
			
			Driver d = driver_manager.load(new Integer(tf_id.getText().trim()));
			
			d.setFirstname(tf_fname.getText().trim());
			d.setSurname(tf_sname.getText().trim());
			d.setTel(tf_tel.getText().trim());
			d.setVehicle(((Taxi) ddl_vehicle.getSelectedItem()).getId());
			d.setCovers(((Driver) ddl_covers.getSelectedItem()).getId());
			
			if(rb_male.getSelectedObjects() == null) {
				d.setMale(false);
			} else {
				d.setMale(true);
			}
			driver_manager .save(d);
			lbl_save.setText("erfolgreich gespeichert");
			
			logger.debug("ActionEvent: saving Driver (detailview)");

		}
		//create
		if(e.getActionCommand().equals("createdriver")){
			Integer id;
			
			try {
				id = new Integer(tf_id2.getText().trim());
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(new JFrame(), "Bitte geben Sie eine korrekte SVNR Nummer ein.","Fehler bei der Eingabe",JOptionPane.WARNING_MESSAGE);
				logger.warn("Inputerror: "+ex.getMessage());
				return;
			}
			
			if(driver_manager.exists(id)) {
				JOptionPane.showMessageDialog(new JFrame(), "Ein Eintrag mit dieser Svnr existiert bereits!","Doppelter Eintrag",JOptionPane.WARNING_MESSAGE);
				logger.warn("Inputerror: Prevent double key entry - entry already exists.");
				return;
			}
			
			Driver d = new Driver();
			try {
				d.setSvnr(id);
				d.setFirstname(tf_fname.getText().trim());
				d.setSurname(tf_sname.getText().trim());
				d.setTel(tf_tel.getText().trim());
				d.setVehicle(((Taxi) ddl_vehicle.getSelectedItem()).getId());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(new JFrame(), "Fehler beim Eintragen","Fehler",JOptionPane.WARNING_MESSAGE);
				logger.warn("Inputerror: "+ex.getMessage());
				return;
			}
			if(ddl_covers.getSelectedItem() != null)
				d.setCovers(((Driver) ddl_covers.getSelectedItem()).getId());
			
			if(rb_male.getSelectedObjects() == null) {
				d.setMale(false);
			} else {
				d.setMale(true);
			}
			driver_manager.create(d);
			logger.debug("ActionEvent: creating Driver (detailview)");
			
			ActionEvent event = new ActionEvent(this,rn.nextInt(),"createdriver2");
			parent_listener.actionPerformed(event);
			
			JOptionPane.showMessageDialog(new JFrame(), "Fahrer wurde erfolgreich in die Datenbank eingefügt.");
			
		}
		//delete
		if(e.getActionCommand().equals("deldriver")){
			Driver d = driver_manager.load(new Integer(tf_id.getText().trim()));
			
			if(d.getId() != null) {
				if(JOptionPane.showConfirmDialog(null,"Soll der Fahrer wirklich gelöscht werden?", "Löschen", JOptionPane.YES_NO_OPTION) == 0) {
					driver_manager.delete(d);
					logger.debug("ActionEvent: deleting Driver (detailview)");
					ActionEvent event = new ActionEvent(this,rn.nextInt(),"deldriver2");
					parent_listener.actionPerformed(event);
					JOptionPane.showMessageDialog(null, "Fahrer wurde erfolgreich gelöscht.");
				}
			}
			
		}
		
		/*
		 * TAXIS
		 */
		//save
		if(e.getActionCommand().equals("savetaxi")){
			
			Taxi t = taxi_manager.load(new Integer(tf_id.getText().trim()));
			
			t.setBrand(ddl_brand.getSelectedItem().toString());
			t.setType(tf_type.getText().trim());
			t.setColor(ddl_color.getSelectedItem().toString());
			t.setPlate(tf_plate.getText().trim());
			t.setSeats(new Integer(ddl_seats.getSelectedItem().toString()));
			
			//catches wrong doubles
			try{
				Double consumption = new Double(tf_consumption.getText().trim());
			}catch(NumberFormatException ne){
				JOptionPane.showMessageDialog(new JFrame(), "Bitte geben Sie einen korrekten Wert als Verbrauch ein. Als Komma bitte einen Punkt verwenden.","Fehler bei der Eingabe",JOptionPane.WARNING_MESSAGE);
				logger.warn("Input Error: "+ne.getMessage());
				return;
			}
			
			t.setConsumption( new Double(tf_consumption.getText().trim()));
			
			if(cb_disabled.getSelectedObjects() == null) {
				t.setDisabled(false);
			} else {
				t.setDisabled(true);
			}
			taxi_manager.save(t);
			
			lbl_save.setText("erfolgreich gespeichert");
			
			logger.debug("ActionEvent: saving Taxi (detailview)");

		}
		//create
		if(e.getActionCommand().equals("createtaxi")){
			
			Taxi t = new Taxi();
			
			t.setBrand(ddl_brand.getSelectedItem().toString());
			t.setType(tf_type.getText().trim());
			t.setColor(ddl_color.getSelectedItem().toString());
			t.setPlate(tf_plate.getText().trim());
			t.setSeats(new Integer(ddl_seats.getSelectedItem().toString()));
			
			//catches wrong doubles
			try{
				Double consumption = new Double(tf_consumption.getText().trim());
			}catch(NumberFormatException ne){
				JOptionPane.showMessageDialog(new JFrame(), "Bitte geben Sie einen korrekten Wert als Verbrauch ein. Als Komma bitte einen Punkt verwenden.","Fehler bei der Eingabe",JOptionPane.WARNING_MESSAGE);
				logger.warn("Input Error: "+ne.getMessage());
				return;
			}
			t.setConsumption( new Double(tf_consumption.getText().trim()));
			
			if(cb_disabled.getSelectedObjects() == null) {
				t.setDisabled(false);
			} else {
				t.setDisabled(true);
			}
			taxi_manager.create(t);
			
			logger.debug("ActionEvent: creating Taxi (detailview)");
			ActionEvent event = new ActionEvent(this,rn.nextInt(),"createtaxi2");
			parent_listener.actionPerformed(event);
			
			JOptionPane.showMessageDialog(new JFrame(), "Taxi wurde erfolgreich in die Datenbank eingefügt.");
			
		}
		//delete
		if(e.getActionCommand().equals("deltaxi")){
			Taxi t = taxi_manager.load(new Integer(tf_id.getText().trim()));
			
			if(t.getId() != null) {
				if(JOptionPane.showConfirmDialog(null,"Soll des Taxi wirklich gelöscht werden?", "Löschen", JOptionPane.YES_NO_OPTION) == 0) {
					taxi_manager.delete(t);
					ActionEvent event = new ActionEvent(this,rn.nextInt(),"deltaxi2");
					parent_listener.actionPerformed(event);
					logger.debug("ActionEvent: deleting Taxi (detailview)");
					JOptionPane.showMessageDialog(new JFrame(), "Taxi wurde erfolgreich gelöscht.");
				}
			}
			
			
		}
	}

}
