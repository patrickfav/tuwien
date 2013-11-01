package gui;


import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.*;

import org.apache.log4j.Logger;


public class SearchTabGui extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SearchTabGui.class);
	
	private JTabbedPane tabs;
	
	public SearchTabGui() {
		super(new GridLayout());
		
		tabs = new JTabbedPane();
		
		SearchGui panel1 = new SearchGui(0);
		tabs.addTab("Schnellsuche",panel1);
		
		SearchGui panel2 = new SearchGui(1);
		tabs.addTab("Erweiterte Suche: Fahrer",panel2);

		SearchGui panel3 = new SearchGui(2);
		tabs.addTab("Erweiterte Suche: Taxi",panel3);

		add(tabs);
		//The following line enables to use scrolling tabs.
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        logger.debug("Search Tabs have been initialized");
	}
}
