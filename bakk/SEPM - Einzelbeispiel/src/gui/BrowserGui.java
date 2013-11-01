package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.apache.log4j.Logger;

import db.dao.DriverManager;
import db.dao.TaxiManager;
import entities.*;

public class BrowserGui extends JPanel implements TreeSelectionListener, ActionListener{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(BrowserGui.class);
	
	//tree variables
	private JTree tree;
	private DefaultTreeModel treemodel;
	private DefaultMutableTreeNode new_cat,tasks_cat,taxi_cat,driver_cat,search_cat,view_cat,root;
	//private ImageIcon search_icon, new_icon1, new_icon2, taxi_icon,user_icon;
	//private DefaultTreeCellRenderer trenderer;
	 
	private DriverManager driver_manager;
	private TaxiManager taxi_manager;
	private Vector<Taxi> all_taxis;
	private Vector<Driver> all_drivers;
	private DefaultMutableTreeNode n;
	private JSplitPane splitPane;
	private JScrollPane detailView,treeView;
	
	public BrowserGui() {
		setLayout(new GridLayout(1,1));
		
		buildTree();
        
	    treeView = new JScrollPane(tree);
	    detailView = new JScrollPane(new DetailViewGui(null,this));

	    //make a splitpane
	    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	    splitPane.setLeftComponent(treeView);
	    splitPane.setRightComponent(detailView);
	    
	    Dimension minimumSize = new Dimension(250, 300);
	    detailView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(250); 
        splitPane.setPreferredSize(new Dimension(1000, 600));
	    
	    add(splitPane);
	    
		setVisible(true);
	}

	
	
	//builds the tree and saves it into the class variable
	private void buildTree() {
		
		logger.debug("builiding JTree");
		
		all_taxis = null;
		all_drivers = null;
		
		//specify icons
		/*search_icon = createImageIcon("img/search.png");
		new_icon1 = createImageIcon("img/add1.png");
		new_icon2 = createImageIcon("img/add2.png");
		taxi_icon = createImageIcon("img/taxi.png");
		user_icon = createImageIcon("img/user.png");*/
		
		//TREE
		root = new DefaultMutableTreeNode("Taxi Manager");
		
		//TASK NODES
		tasks_cat = new DefaultMutableTreeNode("Aufgaben");
		root.add(tasks_cat);
		
		//SEARCH NODES
		search_cat = new DefaultMutableTreeNode("Suchen");
		tasks_cat.add(search_cat);
		
		//VIEW NODES
		view_cat = new DefaultMutableTreeNode("Datenaufbereitung");
		tasks_cat.add(view_cat);
		
		//NEW NODES
		new_cat = new DefaultMutableTreeNode("Neu");
		root.add(new_cat);

		
		n = new DefaultMutableTreeNode("neues Taxi");
		new_cat.add(n);
		n = new DefaultMutableTreeNode("neuer Fahrer");
		new_cat.add(n);
		
		//TAXI MAIN NODE
		taxi_cat = new DefaultMutableTreeNode("Taxis");
		root.add(taxi_cat);
		
		taxi_manager = new TaxiManager();
		all_taxis = taxi_manager.search(new Taxi());
			
		//adds all taxis   
		for(int i=0; i< all_taxis.size();i++) {
		 	n = new DefaultMutableTreeNode(all_taxis.get(i));
		 	taxi_cat.add(n);
		}
			
		//DRIVER MAIN NODE
		driver_cat = new DefaultMutableTreeNode("Fahrer");
		root.add(driver_cat);
			
		driver_manager = new DriverManager();	
		all_drivers = driver_manager.search(new Driver());
			
		//adds all driver nodes
		for(int i=0; i< all_drivers.size();i++) {
		   n = new DefaultMutableTreeNode(all_drivers.get(i));
		   driver_cat.add(n);
		}
			
			
	    tree = new JTree(root);
	    treemodel = (DefaultTreeModel)tree.getModel();
	    
	    //
	    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	    
	    //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
        tree.expandRow(1);
        tree.expandRow(4);
	}
	
	/** Returns an ImageIcon, or null if the path was invalid.
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = BrowserGui.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            //System.err.println("Couldn't find file: " + path);
            return null;
        }
    }*/

	
	// TREE LISTENER
	public void valueChanged(TreeSelectionEvent t) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

		if (node == null || !node.isLeaf()) return;
		
		//changes the splitpane and updates the data
		if(node.isLeaf()) {
			//if new or search
			if(node.getUserObject() instanceof String) {
				String nodeEntity = (String)node.getUserObject();
				if(nodeEntity.equals("Suchen")) {
					detailView = new JScrollPane(new SearchTabGui());
					logger.debug("entering leaf: search");
				}
				if(nodeEntity.equals("Datenaufbereitung")) {
					detailView = new JScrollPane(new SearchGui(3));
					logger.debug("entering leaf: view");
				}
				else if(nodeEntity.equals("neues Taxi")) {
					detailView = new JScrollPane(new DetailViewGui(new Taxi(),this));
					logger.debug("entering leaf: new taxi");
				}
				else if(nodeEntity.equals("neuer Fahrer")) {
					detailView = new JScrollPane(new DetailViewGui(new Driver(),this));
					logger.debug("entering leaf: new driver");
				}
			} else { //else  show clicked node
				Entity nodeEntity = (Entity)node.getUserObject();
				
				if (nodeEntity instanceof Driver) {
					Entity e = driver_manager.load(nodeEntity.getId());
					node.setUserObject(e);
					detailView = new JScrollPane(new DetailViewGui(e,this));
					logger.debug("entering leaf: driver detail - id:"+nodeEntity.getId());
				}
				else if (nodeEntity instanceof Taxi) {
					Entity e = taxi_manager.load(nodeEntity.getId());
					node.setUserObject(e);
					detailView = new JScrollPane(new DetailViewGui(e,this));
					logger.debug("entering leaf: taxi detail - id:"+nodeEntity.getId());
				}
			}
			splitPane.setRightComponent(detailView);
			logger.debug("update Splitpane");
		}
		
		
	}
	
	//ACTION LISTENER
	public void actionPerformed(ActionEvent e) {
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null || !node.isLeaf()) return;
		
		//Immediately removes the node from the tree(model)
		if(e.getActionCommand().equals("deldriver2") || e.getActionCommand().equals("deltaxi2")){
			treemodel.removeNodeFromParent(node);
			detailView = new JScrollPane(new DetailViewGui(null,this));
			splitPane.setRightComponent(detailView);
			logger.debug("ActionEvent: delete driver/taxi (updateing tree)");
		}
		//update tree when new inserting
		if(e.getActionCommand().equals("createdriver2")){
			n = new DefaultMutableTreeNode(driver_manager.getLastInserted());
			treemodel.insertNodeInto(n,driver_cat,driver_cat.getChildCount());
			logger.debug("ActionEvent: create driver (updateing tree)");
		}
		if(e.getActionCommand().equals("createtaxi2")){
			n = new DefaultMutableTreeNode(taxi_manager.getLastInserted());
			treemodel.insertNodeInto(n,taxi_cat,taxi_cat.getChildCount());
			logger.debug("ActionEvent: create taxi (updateing tree)");
		}
	}
}
