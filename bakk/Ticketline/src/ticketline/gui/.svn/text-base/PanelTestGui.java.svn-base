
package ticketline.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import ticketline.cfg.Config;


/**
 
 * Test class for the GUI Panels
 * 
 * @author AndiS
 * @version 0.1
 */

public class PanelTestGui extends JFrame
{
	
	private static final long serialVersionUID = 1L;
	private static Config cfg = new Config();
	private static ResourceBundle lang = cfg.getLanguageBundle();
	
	private static Logger logger = Logger.getLogger(PanelTestGui.class);
	
	public PanelTestGui(){
		logger.debug("PanelTestGui started");
		setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(1000,900));
		//Ask for window decorations provided by the look and feel.
    	JFrame.setDefaultLookAndFeelDecorated(true);
    	
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				exit();
			}
		});
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		getContentPane().add(new WerbematerialWaehlen(),c);
		//getContentPane().add(new KundeSuchenGui(),c);
		c.gridx = 1;
		//getContentPane().add(new KundeAnlegenGui(),c);
		c.gridy = 2;
		//getContentPane().add(new KundeAendernGui(),c);
		
		
		pack();
		setVisible(true);
		logger.debug("PanelTestGui Created Successfully");
	}

	
	private void exit(){
		System.exit(0);
	}
}
