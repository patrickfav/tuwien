package factory.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import factory.Factory;
import factory.storage.ElveStorage;

import products.factory.ProductsFactory;
import workers.ProductionElve;

public class ElveCreationForm extends JPanel implements ActionListener {

	private static Logger log = Logger.getLogger(ElveCreationForm.class);
	
	private static final long serialVersionUID = -5702470624305502380L;
	
	private JLabel productListLabel;
	private JLabel quantityLabel;
	private JLabel faultLabel;

	private JComboBox productList;
	private JTextField quantityTxt;
	private JTextField faultTxt;

	private JButton createButton;
	
	private Factory parent;

	private JButton createStartButton;

	public ElveCreationForm(Factory parent) {
		super();
		this.parent = parent;
		buildGUI();
	}

	public void buildGUI() {

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);

		c.gridx = 0;
		c.gridy = 0;
		productListLabel = new JLabel("Product");
		add(productListLabel, c);

		c.gridx = 1;
		quantityLabel = new JLabel("Quantity");
		add(quantityLabel, c);

		c.gridx = 2;
		faultLabel = new JLabel("Fault rate");
		add(faultLabel, c);

		c.gridx = 0;
		c.gridy = 1;
		productList = new JComboBox(getComboBoxProductStrings());
		add(productList, c);

		c.gridx = 1;
		quantityTxt = new JTextField(5);
		quantityTxt.setText("20");
		add(quantityTxt, c);

		c.gridx = 2;
		faultTxt = new JTextField(5);
		faultTxt.setText("0.1");
		add(faultTxt, c);

		c.gridx = 3;
		createButton = new JButton("Create");
		createButton.addActionListener(this);
		createButton.setActionCommand("create");
		add(createButton, c);
		
		c.gridx = 4;
		createStartButton = new JButton("Create & Start");
		createStartButton.addActionListener(this);
		createStartButton.setActionCommand("createAndStart");
		add(createStartButton, c);

	}

	public void redraw() {
		this.removeAll();
		this.buildGUI();
		this.revalidate();
	}

	public Integer[] getComboBoxProducts() {
		return new Integer[] { ProductsFactory.PRODUCE_ARMS,ProductsFactory.PRODUCE_LEGS, ProductsFactory.PRODUCE_BODIES , ProductsFactory.PRODUCE_HEADS, ProductsFactory.PRODUCE_REDCAPS, ProductsFactory.PRODUCE_GREENCAPS};
	}
	
	public String[] getComboBoxProductStrings() {
		return new String[] { "Arm", "Leg", "Body" , "Head", "Red Cap", "Green Cap"};
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (command.equals("create")) {
			
			int p = (Integer) getComboBoxProducts()[(Integer) productList.getSelectedIndex()];
			int q = getValidNumber(quantityTxt.getText());
			double f = getValidDouble(faultTxt.getText());
			
			ProductionElve elve = new ProductionElve("", parent.getStorage().getPartDao(), p, q, f, (String)productList.getSelectedItem());
			ElveStorage.getInstance().addProductionElve(elve);
			
			log.info("ElveCreationForm: Added new ProductionElve");
			parent.getElveTable().redraw();
			
		} else if(command.equals("createAndStart")) {
			
			int p = (Integer) getComboBoxProducts()[(Integer) productList.getSelectedIndex()];
			int q = getValidNumber(quantityTxt.getText());
			double f = getValidDouble(faultTxt.getText());
			
			ProductionElve elve = new ProductionElve("", parent.getStorage().getPartDao(), p, q, f, (String)productList.getSelectedItem());
			ElveStorage.getInstance().addProductionElve(elve);
			
			Factory.executorService.execute(elve);
			
			log.info("ElveCreationForm: Added new ProductionElve");
			parent.getElveTable().redraw();
			
		}
	}

	public int getValidNumber(String number) {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	public double getValidDouble(String number) {
		try {
			return Double.parseDouble(number);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

}
