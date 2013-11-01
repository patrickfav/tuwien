package factory.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.Serializable;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.Operation;

import dao.space.SpacePartDao;

import products.parts.Arm;
import products.parts.Body;
import products.parts.GreenCap;
import products.parts.Head;
import products.parts.Leg;
import products.parts.RedCap;
import products.parts.abstracts.Product;

import factory.Factory;

public class ProductStock extends JPanel implements NotificationListener {

	private static Logger log = Logger.getLogger(ProductStock.class);
	
	private static final long serialVersionUID = -3338442977438889911L;
	private Factory parent;
	private JLabel headLabel;
	private JLabel bodyLabel;
	private JLabel armLabel;
	private JLabel legLabel;
	private JLabel redLabel;
	private JLabel greenLabel;
	private JLabel headValue;
	private JLabel bodyValue;
	private JLabel armValue;
	private JLabel legValue;
	private JLabel redValue;
	private JLabel greenValue;
	private JLabel heading;
	
	public ProductStock(Factory parent) {
		super();
		this.parent = parent;
		buildGUI();
		if(Factory.getMode().equals(Factory.RMI_MODE)) {
			Factory.executorService.execute(new StockRefresher());
		} else if(Factory.getMode().equals(Factory.SPACE_MODE)) {
			refreshData();
			((SpacePartDao) parent.getStorage().getPartDao()).registerPartsListener(this);
		}
	}
	
	public void buildGUI() {
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		
		c.gridy = 0;
		c.gridx = 0;
		c.gridwidth = 6;
		heading = new JLabel("Product Stock");
		add(heading, c);
		
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(new JSeparator(SwingConstants.HORIZONTAL), c);
		
		c.gridwidth = 1;
		c.gridy = 2;
		c.gridx = 0;
		headLabel = new JLabel("Heads:");
		add(headLabel, c);
		
		c.gridx = 2;
		bodyLabel = new JLabel("Bodies:");
		add(bodyLabel, c);
		
		c.gridx = 4;
		armLabel = new JLabel("Arms:");
		add(armLabel, c);
		
		c.gridy = 3;
		
		c.gridx = 0;
		legLabel = new JLabel("Legs:");
		add(legLabel, c);
		
		c.gridx = 2;
		redLabel = new JLabel("Red Caps:");
		add(redLabel, c);
		
		c.gridx = 4;
		greenLabel = new JLabel("Green Caps:");
		add(greenLabel, c);
		
		c.gridy = 2;
		
		c.gridx = 1;
		headValue = new JLabel();
		add(headValue, c);
		
		c.gridx = 3;
		bodyValue = new JLabel();
		add(bodyValue, c);
		
		c.gridx = 5;
		armValue = new JLabel();
		add(armValue, c);
		
		c.gridy = 3;
		
		c.gridx = 1;
		legValue = new JLabel();
		add(legValue, c);
		
		c.gridx = 3;
		redValue = new JLabel();
		add(redValue, c);
		
		c.gridx = 5;
		greenValue = new JLabel();
		add(greenValue, c);
		
	}
	
	public void redraw() {
		this.removeAll();
		this.buildGUI();
		this.revalidate();
	}
	
	public void refreshData() {
		
		int heads = 0;
		int arms = 0;
		int legs = 0;
		int bodies = 0;
		int red = 0;
		int green = 0;
		
		List<Product> products = parent.getStorage().getPartDao().getAllParts();
		
		for(Product p : products) {
			if (p instanceof Head) {
				heads++;
			} else if(p instanceof Body) {
				bodies++;
			} else if(p instanceof Leg) {
				legs++;
			} else if(p instanceof Arm) {
				arms++;
			} else if(p instanceof RedCap) {
				red++;
			} else if(p instanceof GreenCap) {
				green++;
			}
		}
		
		headValue.setText(String.valueOf(heads));
		armValue.setText(String.valueOf(arms));
		legValue.setText(String.valueOf(legs));
		bodyValue.setText(String.valueOf(bodies));
		redValue.setText(String.valueOf(red));
		greenValue.setText(String.valueOf(green));
		heading.setText("Product Stock ("+products.size()+")");
	}
	
	private class StockRefresher extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					log.info("Thread: Refreshing ProductStock Panel");
					refreshData();
					sleep(Factory.STOCK_REFERESH_TIME);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void entryOperationFinished(Notification arg0, Operation arg1, List<? extends Serializable> arg2) {
		log.debug("Parts notification reveived - redraw stock.");
		refreshData();
	}

}
