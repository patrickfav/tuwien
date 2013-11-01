package ticketline.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ticketline.bl.GuiMemory;
import ticketline.cfg.ConfigFactory;

public class AllgemeinTab extends JPanel implements ActionListener{ 

	private static final long serialVersionUID = 1L;
	private static ResourceBundle lang;
	
	private JPanel pnl_information;
	private JPanel pnl_online;
	private JPanel pnl_statistic;
	
	private JButton btn_search;
	private JButton btn_news;
	private JButton btn_web;
	private JButton btn_topten;
	private JButton btn_efficency;
	
	private ImageIcon arrow;
	
	private JComboBox cb_dropdown;
	private GridBagConstraints c;
	
	public AllgemeinTab(){
		
		lang = ConfigFactory.getConfig().getLanguageBundle();
		
		String[] options = { lang.getString("LBL_GENERAL_FREESEATS"), lang.getString("LBL_GENERAL_SEARCHARTIST")};
		
		//adds the actionlistener to guiMem
		GuiMemory.addActionListener("AllgemeinTab",this);
		
		pnl_information = new JPanel();
		pnl_online = new JPanel();
		pnl_statistic = new JPanel();
		
		btn_search = new JButton(lang.getString("BTN_GENERAL_SEARCH"));
		btn_news = new JButton(lang.getString("BTN_GENERAL_NEWS"));
		btn_web = new JButton(lang.getString("BTN_GENERAL_WEB"));
		btn_topten = new JButton(lang.getString("BTN_GENERAL_TOPTEN"));
		btn_efficency = new JButton(lang.getString("BTN_GENERAL_EFFICENCY"));
		
		cb_dropdown = new JComboBox(options);
		
		//the button icon
		arrow = new ImageIcon("images/arrow.png");
		
		c = new GridBagConstraints();
		
		setLayout(new GridBagLayout());			
	
		pnl_information.setMinimumSize(new Dimension(200,100));
		pnl_online.setMinimumSize(new Dimension(200,90));
		pnl_statistic.setMinimumSize(new Dimension(200,90));
		pnl_information.setPreferredSize(new Dimension(200,100));
		pnl_online.setPreferredSize(new Dimension(200,90));
		pnl_statistic.setPreferredSize(new Dimension(200,90));
		
		//Panel Information
		c.gridx = 0;
		c.gridy = 0;
		add(pnl_information, c);
		//Panel Online
		c.gridx = 0;
		c.gridy = 1;
		add(pnl_online, c);
		//Panel Statistic
		c.gridx = 0;
		c.gridy = 2;
		add(pnl_statistic, c);
		
		//Information Panel inside
		pnl_information.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_GENERAL_INFORMATION")));
		pnl_information.setLayout(new GridBagLayout());
		
		c.gridx=0;
		c.gridy=0;		
		//c.anchor = GridBagConstraints.WEST;
		pnl_information.add(cb_dropdown, c);
		c.gridx=0;
		c.gridy=1;
		c.weightx=1;
		c.weighty=1;
		c.anchor = GridBagConstraints.EAST;
		pnl_information.add(btn_search, c);
		btn_search.setPreferredSize(new Dimension(100,20));
		btn_search.addActionListener(this);
		btn_search.setActionCommand("search");
		
		//Online Panel inside
		pnl_online.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_GENERAL_ONLINE")));
		pnl_online.setLayout(new FlowLayout());
		
		btn_news.setPreferredSize(new Dimension(180,20));			
		pnl_online.add(btn_news);
		btn_news.addActionListener(this);
		btn_news.setActionCommand("News anzeigen");
		
		btn_web.setPreferredSize(new Dimension(180,20));	
		pnl_online.add(btn_web);
		btn_web.addActionListener(this);
		btn_web.setActionCommand("Register Webaccount");
		
		//Statistics Panel inside
		pnl_statistic.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_GENERAL_STATISTIC")));
		pnl_statistic.setLayout(new FlowLayout());
		
		btn_topten.setPreferredSize(new Dimension(180,20));
		pnl_statistic.add(btn_topten);
		btn_topten.addActionListener(this);
		btn_topten.setActionCommand("TopTen");
		
		btn_efficency.setPreferredSize(new Dimension(180,20));
		pnl_statistic.add(btn_efficency);
		btn_efficency.addActionListener(this);
		btn_efficency.setActionCommand("efficency");
		
		JLabel empty = new JLabel("");//leeres JLabel um Platz im Panel zu vergrößern
		empty.setPreferredSize(new Dimension(200,70));
		c.gridx = 0;
		c.gridy = 3;
		add(empty, c);	
		
		

		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("News anzeigen")){		
			Gui.openNews();
			setDefaultBtnBorder();
			btn_news = setProminentBtnBorder(btn_news);
			checkVerkaufer();
			setVisible(true);
		} else if(e.getActionCommand().equalsIgnoreCase("Register Webaccount")){	
			Gui.openRegisterWeb();
			setDefaultBtnBorder();
			btn_web = setProminentBtnBorder(btn_web);
			checkVerkaufer();
			setVisible(true);
		} else if(e.getActionCommand().equalsIgnoreCase("TopTen")){		
			Gui.openTopTen();
			setDefaultBtnBorder();
			btn_topten = setProminentBtnBorder(btn_topten);
			checkVerkaufer();
			setVisible(true);
		} else if(e.getActionCommand().equalsIgnoreCase("efficency")){		
			Gui.openHallEfficency();
			setDefaultBtnBorder();
			btn_efficency = setProminentBtnBorder(btn_efficency);
			checkVerkaufer();
			setVisible(true);
		} else if(e.getActionCommand().equalsIgnoreCase("search")){		
			Object selected = cb_dropdown.getSelectedItem();
			if(selected.toString().equals(lang.getString("LBL_GENERAL_SEARCHARTIST"))) {				
				Gui.openKuenstlerSuchen();
			} else if(selected.toString().equals(lang.getString("LBL_GENERAL_FREESEATS"))) {
				Gui.openFreeSeatsSearch();
			}
			setDefaultBtnBorder();
			btn_search = setProminentBtnBorder(btn_search);
			checkVerkaufer();
			setVisible(true);
		}
		else if(e.getActionCommand().equalsIgnoreCase("unSetButtonsAllgemein")){
			setDefaultBtnBorder();
		}
	}
	/**
	 * Decorates the given button with prominent border
	 * @param btn
	 * @return decorated btn
	 */
	private JButton setProminentBtnBorder(JButton btn) {
		btn.setIcon(arrow);
		btn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(61, 72, 80)));
		return btn;
	}
	
	/**
	 * Sets the default buttons to all buttons
	 */
	private void setDefaultBtnBorder() {
		btn_news.setIcon(null);
		btn_news.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		btn_web.setIcon(null);
		btn_web.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		btn_topten.setIcon(null);
		btn_topten.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		btn_efficency.setIcon(null);
		btn_efficency.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		btn_search.setIcon(null);
		btn_search.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
	}
	/**
	 * When User is not logged in, there is no VerkauferTab which would throw an Exception
	 * 
	 */
	private void checkVerkaufer(){
		try{
			GuiMemory.getActionListener("VerkauferTab").actionPerformed(new ActionEvent(this,new Random().nextInt(),"unSetButtonsVerkaufer"));
		}
		catch(NullPointerException ex){}
	}
}
