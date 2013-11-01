package ticketline.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import ticketline.bl.GuiMemory;
import ticketline.bl.Trail;
import ticketline.cfg.ConfigFactory;
import ticketline.dao.hibernate.HibernateSessionFactory;
import ticketline.gui.components.DefaultTicketlineJFrame;

/**
 * The Main Gui
 * 
 * @author DanielZ, PatrickF, AndiS
 * @version 0.4
 */
public class Gui extends DefaultTicketlineJFrame implements ActionListener, ComponentListener{
	
	private static final long serialVersionUID = 1563486932024052239L;
	private static ResourceBundle lang;
	private static Logger logger = Logger.getLogger(Gui.class);
	private static JTabbedPane tabbedPane;
	private static JPanel pnl_exitButtons;
	
	//the main panel
	public 	static JPanel pnl_mainArea;
	
	private JLabel lab = new JLabel(new ImageIcon("images/logo.png"));
	
	//constraints for the layout
	private GridBagLayout mainframe_gridbag = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();
	
	//panel and menu
	private Menue menue;
	private JPanel navigation;
	
	//buttons
	private JButton btn_login;
	private JButton btn_exit;
	
	private Dimension userDimension;
	
	public static int width;
	public static int height;
	
	public Gui() {
		
		super();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		lang = ConfigFactory.getConfig().getLanguageBundle();
		
		tabbedPane = new JTabbedPane();
		pnl_exitButtons = new JPanel();
		navigation = new JPanel();
		pnl_mainArea = new JPanel();
		menue = new Menue();
		
		instanziate();
		pnl_mainArea.addComponentListener(this);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				exit();
			}
		});
		
		//Layout
		
		
		setLocationRelativeTo(null);
		setJMenuBar(menue);
		
		//set frame sizes
		setMinimumSize(ConfigFactory.getConfig().getMainFrameMinDimension());
		setPreferredSize(ConfigFactory.getConfig().getMainFramePrefDimension());
		setMaximumSize(userDimension);
		
		setExtendedState(JFrame.NORMAL);
		getContentPane().setLayout(mainframe_gridbag);  	 
		getContentPane().add(tabbedPane);		
		c.insets = new Insets(1,1,1,1);		  
		
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		getContentPane().add(navigation,c);
				
		navigation.setLayout(new GridBagLayout());
		//menu.setPreferredSize(null);
		
		//Logo
		c.gridx=0;
		c.gridy=0;
		c.anchor = GridBagConstraints.NORTHWEST;
		navigation.add(lab, c);
		//JTabbedPane			
		c.gridx = 0;
		c.gridy = 1;
		//c.weightx = 0;
		//c.weighty = 2;
		c.fill = GridBagConstraints.VERTICAL;
		//c.gridheight = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.WEST;
		tabbedPane.setMinimumSize(new Dimension(200,465));
		navigation.add(tabbedPane, c);
		//Panel exitbuttons
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.SOUTHWEST;
		navigation.add(pnl_exitButtons, c);		
		
		//Panel exitbuttons inside
		c.insets = new Insets(2,5,2,2);
		pnl_exitButtons.setMinimumSize(new Dimension(200,60));
		pnl_exitButtons.setPreferredSize(new Dimension(200,60));		
		getContentPane().add(pnl_exitButtons, c);

		//Panel mainArea
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;		
		pnl_mainArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));		
		getContentPane().add(pnl_mainArea, c);
		
		setLocation(0,0);
		
		setVisible(true);
		
		//shows the news
		GuiMemory.getActionListener("AllgemeinTab").actionPerformed(new ActionEvent(this,new Random().nextInt(),"News anzeigen"));				
	}
	private void instanziate() {		
		//pnl_mainArea.add(new NewsAnzeigenGui());
		btn_login = new JButton(lang.getString("BTN_GUI_LOGIN"));
		btn_exit = new JButton(lang.getString("BTN_GUI_EXIT"));
		tabbedPane.addTab(lang.getString("BTN_GUI_GENERAL"), new AllgemeinTab());		 
		
		//the exit buttons
		btn_login.setPreferredSize(new Dimension(180,30));
		pnl_exitButtons.add(btn_login);
		btn_login.addActionListener(this);
		btn_login.setActionCommand("loginButton");
		
		/*btn_exit.setPreferredSize(new Dimension(180,20));		
		pnl_exitButtons.add(btn_exit);
		btn_exit.addActionListener(this);
		btn_exit.setActionCommand("Exit");*/
		
		menue.rbMenuItem.addActionListener(this);
		menue.rbMenuItem.setActionCommand("selectLangDE");
		
		menue.rbMenuItem2.addActionListener(this);
		menue.rbMenuItem2.setActionCommand("selectLangEN");
		
		menue.menuItem2.addActionListener(this);
		menue.menuItem2.setActionCommand("menuAbout");
		
		userDimension = Toolkit.getDefaultToolkit().getScreenSize();
	}

	/*
	 *  The methods for opening the panels in the main gui
	 */
	
	// Reservieren/Verkaufen
	public static void openSaleRes(){		
		Trail trail = new Trail(9);
		trail.getFirstTrail().setTrail(trail);
		load(trail.getFirstComponent());
	}
	
	// looking for free seats (information)
	public static void openFreeSeatsSearch(){		
		AuffuehrungenSuchenGui freeSeatsGui = new AuffuehrungenSuchenGui();
		load(freeSeatsGui);
	}
	
	public static void openKuenstlerSuchen(){		
		KuenstlerSuchenGui saleRes2 = new KuenstlerSuchenGui();
		load(saleRes2);		
	}
	
	public static void openNews() {
		NewsAnzeigenGui newsGui = new NewsAnzeigenGui();
		load(newsGui);
	}
	
	public static void openRegisterWeb() {		
		WebAccountAnmeldenGui registerGui = new WebAccountAnmeldenGui();		
		load(registerGui);
	}
	
	public static void openTopTen() {
		Trail trail = new Trail(10);
		trail.getFirstTrail().setTrail(trail);
		load(trail.getFirstComponent());
	}
	
	public static void openHallEfficency() {
		SaalAuslastungGui auslastungGui = new SaalAuslastungGui();
		load(auslastungGui);
	}
	
	public static void openReservationSE() {
		ReservierungTicketsSuchenBearbeitenGui reservationGui = new ReservierungTicketsSuchenBearbeitenGui();
		load(reservationGui);
	}
	
	public static void openSellPromotionalMaterials1() {
		Trail trail = new Trail(8);
		trail.getFirstTrail().setTrail(trail);
		load(trail.getFirstComponent());
	}
	
	public static void openSellPromotionalMaterials2() {
		WerbematerialWaehlen promomatsGui2 = new WerbematerialWaehlen();
		load(promomatsGui2);
	}
	
	public static void openSearchHall() {
		SaalSuchenGui saalSuchenGui = new SaalSuchenGui();
		load(saalSuchenGui);
	}
	
	public static void openSearchEvent() {
		VeranstaltungSuchenGui veranstSuchenGui = new VeranstaltungSuchenGui();
		load(veranstSuchenGui);
	}
	
	public static void openSearchPlace() {
		OrtSuchenGui ortSuchenGui = new OrtSuchenGui();
		load(ortSuchenGui);
	}
	
	public static void openSearchCustomer() {
		Trail trail = new Trail(1);
		trail.getFirstTrail().setTrail(trail);
		load(trail.getFirstComponent());
	}
	
	public static void openNewCustomer() {
		KundeAnlegenGui kundeAnlegenGui = new KundeAnlegenGui();
		load(kundeAnlegenGui);
	}
	
	public static void openEditCustomer() {
		KundeAendernGui kundeAendernGui = new KundeAendernGui();
		load(kundeAendernGui);
	}
	
	public static void openReservierungTicketsSuchenBearbeiten() {
		ReservierungTicketsSuchenBearbeitenGui reservierungTicketsSuchenBearbeitenGui = new ReservierungTicketsSuchenBearbeitenGui();
		load(reservierungTicketsSuchenBearbeitenGui);
	}
	
	public static void load(Component panel) {
		pnl_mainArea.removeAll();
		pnl_mainArea.add(panel);
		pnl_mainArea.revalidate();
		pnl_mainArea.repaint();
	}
	public static void setPanelSize(){
		ConfigFactory.getConfig().setDefaultPanelHeight(pnl_mainArea.getHeight()-100);
		ConfigFactory.getConfig().setDefaultPanelWidth(pnl_mainArea.getWidth()-100);
	}
	/**
	 * ActionEventListiner
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Login or Logout
		if(e.getActionCommand().equalsIgnoreCase("loginButton")){

			if(GuiMemory.getLogin()==null){
				//opens the login gui
				new LoginGui(this);
				//shows the news
				GuiMemory.getActionListener("AllgemeinTab").actionPerformed(new ActionEvent(this,new Random().nextInt(),"News anzeigen"));				
			
			}
			//logout
			else{
				//deletes the login data from the container
				logger.info("User "+GuiMemory.getLogin().getUserObject().toString()+" logged out.");
				GuiMemory.logout();			
				HibernateSessionFactory.closeSession();	
				
				//reset main panel
				refresh();
				
				//change button text
				btn_login.setText(lang.getString("BTN_GUI_LOGIN"));
			
				//show message
				JOptionPane.showMessageDialog(this, lang.getString("TXT_GUI_LOGOUT"));
				//shows the news
				GuiMemory.getActionListener("AllgemeinTab").actionPerformed(new ActionEvent(this,new Random().nextInt(),"News anzeigen"));				
			
			}
			
		} 

		// Set Language German
		if(e.getActionCommand().equals("selectLangDE")){
			ConfigFactory.getConfig().setLanguage("ticketline.lang.lang_de", new Locale("de", "AT"));
			refresh();
		}
		// Set Language English
		if(e.getActionCommand().equals("selectLangEN")){
			ConfigFactory.getConfig().setLanguage("ticketline.lang.lang_en", new Locale("en", "UK"));
			refresh();
		}
		// Show about
		if(e.getActionCommand().equals("menuAbout")){
			JOptionPane.showMessageDialog(this, ConfigFactory.getConfig().getVersion(),"About",JOptionPane.INFORMATION_MESSAGE);
		}
		
		//Exit
		if(e.getActionCommand().equalsIgnoreCase("exit")){
			exit();
		}

	}

	/**
	 * Closes the Hibernate session and closes the program
	 * @pre program is up and running 
	 * @post all resources are free and the program is closed
	 
	private void exit(){
		logger.info("Closing the application");	
		if(GuiMemory.getLogin() != null) {
			HibernateSessionFactory.closeSession();	
		}
		GuiMemory.logout();
		System.exit(0);
	}*/
	
	private void refresh() {
		lang = ConfigFactory.getConfig().getLanguageBundle();
		menue.removeAll();
		menue.instanziate();
		pnl_mainArea.removeAll();
		tabbedPane.removeAll();
		pnl_exitButtons.removeAll();
		instanziate();
		if(GuiMemory.getLogin() != null) {
			login();
		}
		GuiMemory.getActionListener("AllgemeinTab").actionPerformed(new ActionEvent(this,new Random().nextInt(),"News anzeigen"));
	}
	
	public void login() {
		logger.info("User "+GuiMemory.getLogin().getUserObject().toString()+" logged in.");

		//create tab for seller
		tabbedPane.addTab(lang.getString("BTN_GUI_SELLER"), new VerkaeuferTab());	
		tabbedPane.setSelectedIndex(1);
		
		//change button text
		btn_login.setText(lang.getString("BTN_GUI_LOGOUT"));
	}
	public void componentResized(ComponentEvent e) {
		ConfigFactory.getConfig().setPnl_mainAreaWidth(e.getComponent().getWidth());
		ConfigFactory.getConfig().setPnl_mainAreaHeight(e.getComponent().getHeight());
    }
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
