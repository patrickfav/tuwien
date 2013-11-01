package gui;


import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.*;

import org.apache.log4j.Logger;




public class TabsGui extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TabsGui.class);
	
	private JTabbedPane tabs;
	
	public TabsGui() {
		super(new GridLayout());
		
		tabs = new JTabbedPane();
		
		BrowserGui panel1 = new BrowserGui();
		tabs.addTab("Data Browser",panel1);
		tabs.setMnemonicAt(0, KeyEvent.VK_1);
		
		ExitGui panel2 = new ExitGui();
		tabs.addTab("Beenden",panel2);
		tabs.setMnemonicAt(0, KeyEvent.VK_2);
		
		add(tabs);
		//The following line enables to use scrolling tabs.
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        logger.debug("Main Tabs have been initialized");
	}
}
