package factory;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import dao.RMIStorageDAOImpl;
import dao.SpaceStorageDAOImpl;
import dao.interfaces.IStorageDAO;

import factory.panels.ElveCreationForm;
import factory.panels.ElveTable;
import factory.panels.GenericList;
import factory.panels.ProductStock;

public class Factory extends JFrame {

	private static final long serialVersionUID = 7241091116731550075L;
	
	public static final long STOCK_REFERESH_TIME = 500;
	public static final long LISTS_REFERESH_TIME = 500;
	public static final long TABLE_REFRESH_TIME = 5000l;
	
	public static final String RMI_MODE = "rmi";
	public static final String SPACE_MODE = "space";
	private static String mode;
	
	private ElveCreationForm elveCreationForm;
	private ElveTable elveTable;
	private IStorageDAO storage;
	private ProductStock productStock;
	private GenericList teddyList;
	private GenericList defectTeddyList;
	private GenericList assembledList;
	private GenericList checkedList;
	private JLabel headingA;
	private JLabel headingD;
	private JLabel headingT;
	private JLabel headingC;
	
	public static ExecutorService executorService = Executors.newCachedThreadPool();

	public Factory(IStorageDAO storage) {
		super("Weihnachtsfabrik");
		this.storage = storage;
		buildGUI();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	public void buildGUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		
		elveTable = new ElveTable(this);
		elveCreationForm = new ElveCreationForm(this);
		productStock = new ProductStock(this);
		assembledList = new GenericList(this,GenericList.ASSEMBLED_MODE);
		checkedList = new GenericList(this,GenericList.CHECKED_MODE);
		teddyList = new GenericList(this,GenericList.SHIPPING_MODE);
		defectTeddyList = new GenericList(this,GenericList.DEFECT_MODE);

		c.gridx = 0;
		c.gridy = 0;
		getContentPane().add(elveCreationForm, c);
		
		c.gridy = 1;
		c.gridheight = 8;
		c.fill = GridBagConstraints.BOTH;
		getContentPane().add(elveTable, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 6;
		getContentPane().add(new JSeparator(SwingConstants.VERTICAL), c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 1;
		getContentPane().add(productStock, c);
		
		c.gridy = 1;
		headingA = new JLabel("Assembled Teddy List");
		getContentPane().add(headingA, c);
		
		c.gridy = 2;
		JScrollPane s1 = new JScrollPane(assembledList);
		s1.setPreferredSize(new Dimension(300, 200));
		getContentPane().add(s1, c);
		
		c.gridy = 3;
		headingT = new JLabel("Shipped Teddy List");
		add(headingT, c);
		
		c.gridy = 4;
		JScrollPane s2 = new JScrollPane(teddyList);
		s2.setPreferredSize(new Dimension(300, 200));
		getContentPane().add(s2, c);
		
		
		c.gridx = 3;
		c.gridy = 1;
		headingC = new JLabel("Checked Teddy List");
		getContentPane().add(headingC, c);
		
		c.gridy = 2;
		JScrollPane s4 = new JScrollPane(checkedList);
		s4.setPreferredSize(new Dimension(300, 200));
		getContentPane().add(s4, c);
		
		c.gridy = 3;
		headingD = new JLabel("Defect Teddy List");
		add(headingD, c);
		
		c.gridy = 4;
		JScrollPane s3 = new JScrollPane(defectTeddyList);
		s3.setPreferredSize(new Dimension(300, 200));
		getContentPane().add(s3, c);
	}
	
	public void redraw() {
		this.removeAll();
		this.buildGUI();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IStorageDAO storage = null;
		
		if(args.length > 0) {
			if(args[0].equals(RMI_MODE)) {
				mode = RMI_MODE;
				storage = new RMIStorageDAOImpl();
			} else if(args[0].equals(SPACE_MODE)) {
				mode = SPACE_MODE;
				storage = new SpaceStorageDAOImpl();
			}
			
			new Factory(storage);
		} else {
			usage();
		}
			
		
	}

	private static void usage() {
		System.out.println("Usage: java factory ("+RMI_MODE+"|"+SPACE_MODE+")");
	}

	public ElveCreationForm getElveCreationForm() {
		return elveCreationForm;
	}

	public void setElveCreationForm(ElveCreationForm elveCreationForm) {
		this.elveCreationForm = elveCreationForm;
	}

	public ElveTable getElveTable() {
		return elveTable;
	}

	public void setElveTable(ElveTable elveTable) {
		this.elveTable = elveTable;
	}

	public IStorageDAO getStorage() {
		return storage;
	}

	public void setStorage(IStorageDAO storage) {
		this.storage = storage;
	}
	public static String getMode() {
		return mode;
	}

}
