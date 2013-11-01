package gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import javax.swing.*;

import org.apache.log4j.Logger;

import db.HSQLDbHandler;

public class Gui extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Gui.class);
	
	public Gui() {
		
		logger.info("GUI has started");
		
		
		//properly closes the db when closing the window
		addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
				 exit();
			  }
			});
		
      //Create and set up the window.
		setTitle("Taxi Company");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Add main tabs
        add(new TabsGui(), BorderLayout.CENTER);
        
        //Display the window.
        pack();
        setVisible(true);
	}
	
	private void exit(){
		 HSQLDbHandler.closeConnection();
		 System.exit(0);
	}
}