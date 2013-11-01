package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import org.apache.log4j.Logger;

import db.HSQLDbHandler;
import db.dao.GeneralManager;

public class ExitGui extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ExitGui.class);
	
	private GeneralManager gmanager = new GeneralManager();
	private JButton		btn_exit = new JButton("Programm Beenden");
	private JButton		btn_cleardb = new JButton("Datenbank leeren");
	private JButton		btn_resetdb = new JButton("Datenbank neu initialisieren und mit Daten füllen");
	private ErrMsgGui errmsg;
	
	public ExitGui() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(10,0,0,0);
		
		c.gridx = 0;
		c.gridy = 0;
		add(btn_resetdb,c);
		
		btn_resetdb.addActionListener(this);
		btn_resetdb.setActionCommand("reset");
		
		c.gridy = 1;
		add(btn_cleardb,c);
		
		btn_cleardb.addActionListener(this);
		btn_cleardb.setActionCommand("clear");
		
		c.gridy = 2;
		add(btn_exit,c);
		
		btn_exit.addActionListener(this);
		btn_exit.setActionCommand("exit");

		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("exit")){
			exit();
		}
		
		if(e.getActionCommand().equals("clear")){
			gmanager.clearDB();
			
			JOptionPane.showMessageDialog(new JFrame(), "Datenbank wurde geleert - das Programm wird beendet.");
			exit();
		}
		
		if(e.getActionCommand().equals("reset")){
			gmanager.resetDB();
			
			JOptionPane.showMessageDialog(new JFrame(), "Datenbank wurde neu initialisiert - das Programm wird beendet.");
			exit();
		}
		
	}
	
	private void exit(){
		 HSQLDbHandler.closeConnection();
		 logger.info("Application Closed");
		 System.exit(0);
	}
	
	
}
