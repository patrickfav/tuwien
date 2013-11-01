package ticketline.gui.components;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

import ticketline.bl.GuiMemory;
import ticketline.dao.hibernate.HibernateSessionFactory;

public class DefaultTicketlineJFrame extends JFrame {
	
	private static final long serialVersionUID = 932181364667324L;
	private static Logger logger = Logger.getLogger(DefaultTicketlineJFrame.class);
	
	public DefaultTicketlineJFrame() {
		//super();
		setIconImage(new ImageIcon("images/logo_icon.png").getImage());
		
		setTitle("Ticketline");
	}
	
	/**
	 * Closes the Hibernate session and closes the program
	 * @pre program is up and running 
	 * @post all resources are free and the program is closed
	 */
	protected void exit(){
		logger.info("Closing the application");	
		if(GuiMemory.getLogin() != null) {
			HibernateSessionFactory.closeSession();	
		}
		GuiMemory.logout();
		System.exit(0);
	}
}
