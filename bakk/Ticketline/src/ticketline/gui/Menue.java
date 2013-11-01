package ticketline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.JHelp;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import ticketline.cfg.ConfigFactory;

public class Menue extends JMenuBar implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private static ResourceBundle lang;
	private JMenu menu, menu2;
	
	public JMenuItem menuItem,menuItem2;
	public JRadioButtonMenuItem rbMenuItem, rbMenuItem2;
	
	private ButtonGroup group;
	
	private URL hsURL;
	private HelpSet hs;
	
	public Menue() {

		instanziate();

	}
	
	public void instanziate() {
		
		lang = ConfigFactory.getConfig().getLanguageBundle();
		menu = new JMenu(lang.getString("MENU_LANG"));
		menu2 = new JMenu(lang.getString("MENU_HELP"));
		menuItem = new JMenuItem(lang.getString("MENU_HELP_BOOK"), new ImageIcon("images/book.png"));
		menuItem2 = new JMenuItem(lang.getString("MENU_HELP_ABOUT"), new ImageIcon("images/info.png"));
		menuItem.addActionListener(this);
		menuItem.setActionCommand("help");
		rbMenuItem = new JRadioButtonMenuItem(lang.getString("MENU_GERMAN"), new ImageIcon("images/de.png"));
		rbMenuItem2 = new JRadioButtonMenuItem(lang.getString("MENU_ENGLISH"), new ImageIcon("images/en.png"));

		// Switch selected
		if(ConfigFactory.getConfig().getLocale().getLanguage().equals("en")){
			rbMenuItem2.setSelected(true);		
		} else {
			rbMenuItem.setSelected(true);
		}
		
		group = new ButtonGroup();

		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		group.add(rbMenuItem2);
		menu.add(rbMenuItem2);
		
		add(menu);
		
		menu2.add(menuItem);
		menu2.addSeparator();
		menu2.add(menuItem2);
		
		add(menu2);
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// Show Help
		if(evt.getActionCommand().equalsIgnoreCase("help")){
			JHelp helpViewer = null;
			try {
				ClassLoader cl = Menue.class.getClassLoader();
				URL url = HelpSet.findHelpSet(cl, "jhelpset.hs");
				helpViewer = new JHelp(new HelpSet(cl, url));
			} catch (Exception e) {
				System.err.println("Help Set not found");
			}
			JFrame frame = new JFrame();
			frame.setTitle("Ticketline Hilfe");
			frame.setSize(800,600);
			frame.getContentPane().add(helpViewer);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
		}
	}

}
