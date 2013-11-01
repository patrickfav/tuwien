package ticketline.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ticketline.bl.GuiMemory;
import ticketline.cfg.ConfigFactory;

public class VerkaeuferTab extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private static ResourceBundle lang;

	private JPanel pnl_saleRes;
	private JPanel pnl_searchEdit;
	private JPanel pnl_advertisment;
	private JPanel pnl_customer;
	private JPanel pnl_customerNew;
	
	private JButton btn_doIt;
	private JButton btn_ticket;
	private JButton btn_sell;
	private JButton btn_chooseCost;
	private JButton btn_new;
	
	private ImageIcon arrow;
	
	public VerkaeuferTab(){
		
		lang = ConfigFactory.getConfig().getLanguageBundle();
		
		//adds the actionlistener to guimem
		GuiMemory.addActionListener("VerkauferTab",this);
		GuiMemory.addActionListener("setButtonDoit",this);
		GuiMemory.addActionListener("setButtonTicket",this);
		GuiMemory.addActionListener("setButtonSell",this);
		GuiMemory.addActionListener("setButtonChooseCost",this);
		GuiMemory.addActionListener("setButtonNew",this);
		GuiMemory.addActionListener("unSetButtonsAllgemein",this);
		
		pnl_saleRes = new JPanel();
		pnl_searchEdit = new JPanel();
		pnl_advertisment = new JPanel();
		pnl_customer = new JPanel();
		pnl_customerNew = new JPanel();
		
		btn_doIt = new JButton(lang.getString("BTN_SELLER_DOIT"));
		btn_ticket = new JButton(lang.getString("BTN_SELLER_TICKET"));
		btn_sell = new JButton(lang.getString("BTN_SELLER_SELL"));
		btn_chooseCost = new JButton(lang.getString("BTN_SELLER_SEARCH"));
		btn_new = new JButton(lang.getString("BTN_SELLER_NEW"));
		
		//the button icon
		arrow = new ImageIcon("images/arrow.png");
		
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());		
		
		pnl_saleRes.setMinimumSize(new Dimension(184,70));
		pnl_searchEdit.setMinimumSize(new Dimension(184,70));
		pnl_advertisment.setMinimumSize(new Dimension(184,60));
		pnl_customerNew.setMinimumSize(new Dimension(200,60));
		pnl_customer.setMinimumSize(new Dimension(200,270));
		
		pnl_saleRes.setPreferredSize(new Dimension(184,70));
		pnl_searchEdit.setPreferredSize(new Dimension(184,70));
		pnl_advertisment.setPreferredSize(new Dimension(184,60));
		pnl_customerNew.setPreferredSize(new Dimension(200,60));
		pnl_customer.setPreferredSize(new Dimension(200,270));
			
		
		//Sales/Reserveration Panel inside #1.1
		pnl_saleRes.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SALERES")));
		pnl_saleRes.setLayout(new FlowLayout());
		
		btn_doIt.setPreferredSize(new Dimension(150,30));
		btn_doIt.addActionListener(this);
		btn_doIt.setActionCommand("doSaleRes");
		pnl_saleRes.add(btn_doIt);
		
		//Search/Edit Panel inside #1.2
		pnl_searchEdit.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SELLER_SEARCHEDIT")));
		pnl_searchEdit.setLayout(new FlowLayout());
		
		btn_ticket.setPreferredSize(new Dimension(150,30));
		btn_ticket.addActionListener(this);
		btn_ticket.setActionCommand("ticketSE");

		pnl_searchEdit.add(btn_ticket);
		
		
		//Advertisment Panel inside #1.3
		pnl_advertisment.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SELLER_ADVERTISMENT")));
		pnl_advertisment.setLayout(new FlowLayout());
		
		btn_sell.setPreferredSize(new Dimension(150,20));
		btn_sell.addActionListener(this);
		btn_sell.setActionCommand("sellPromoMats");
		pnl_advertisment.add(btn_sell);
		
		//New Customer Panel inside #2
		pnl_customerNew.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SELLER_CUSTOMERNEW")));
		pnl_customerNew.setLayout(new FlowLayout());
		
		btn_new.setPreferredSize(new Dimension(150,20));
		btn_new.addActionListener(this);
		btn_new.setActionCommand("newCustomer");
		
		pnl_customerNew.add(btn_new);
		
		//Choose Costumer #1
		pnl_customer.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SELLER_CUSTOMER")));
		pnl_customer.setLayout(new FlowLayout());
		
		btn_chooseCost.setPreferredSize(new Dimension(150,20));
		btn_chooseCost.addActionListener(this);
		btn_chooseCost.setActionCommand("searchCustomer");

		pnl_customer.add(btn_chooseCost);
		pnl_customer.add(pnl_saleRes);
		pnl_customer.add(pnl_searchEdit);
		pnl_customer.add(pnl_advertisment);
		
		/*
		 * MAIN PANEL
		 */
		
		//Panel Customer - includes sales, res search and ad sells
		c.gridx = 0;
		c.gridy = 0;
		add(pnl_customer, c);
		//Panel Costumer new
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10,0,0,0);
		add(pnl_customerNew, c);	
		
		JLabel empty2 = new JLabel(""); //leeres JLabel um Platz im Panel zu vergrößern
		empty2.setPreferredSize(new Dimension(200,50));
		c.gridx = 0;
		c.gridy = 4;
		c.weightx=1;
		c.weighty=1;
		c.insets = new Insets(0,0,0,0);
		add(empty2, c);
		
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("doSaleRes")){
			Gui.openSaleRes();
			setDefaultBtnBorder();
			btn_doIt = setProminentBtnBorder(btn_doIt);
			GuiMemory.getActionListener("AllgemeinTab").actionPerformed(new ActionEvent(this,new Random().nextInt(),"unSetButtonsAllgemein"));
			setVisible(true);
		} else if(e.getActionCommand().equalsIgnoreCase("ticketSE")) {
			setDefaultBtnBorder();
			btn_ticket = setProminentBtnBorder(btn_ticket);
			GuiMemory.getActionListener("AllgemeinTab").actionPerformed(new ActionEvent(this,new Random().nextInt(),"unSetButtonsAllgemein"));
			Gui.openReservierungTicketsSuchenBearbeiten();
		} else if(e.getActionCommand().equalsIgnoreCase("sellPromoMats")) {
			setDefaultBtnBorder();
			btn_sell = setProminentBtnBorder(btn_sell);
			GuiMemory.getActionListener("AllgemeinTab").actionPerformed(new ActionEvent(this,new Random().nextInt(),"unSetButtonsAllgemein"));
			Gui.openSellPromotionalMaterials1();
		} else if(e.getActionCommand().equalsIgnoreCase("searchCustomer")) {
			setDefaultBtnBorder();
			btn_chooseCost = setProminentBtnBorder(btn_chooseCost);
			GuiMemory.getActionListener("AllgemeinTab").actionPerformed(new ActionEvent(this,new Random().nextInt(),"unSetButtonsAllgemein"));
			Gui.openSearchCustomer();
		} else if(e.getActionCommand().equalsIgnoreCase("newCustomer")) {
			setDefaultBtnBorder();
			btn_new = setProminentBtnBorder(btn_new);
			GuiMemory.getActionListener("AllgemeinTab").actionPerformed(new ActionEvent(this,new Random().nextInt(),"unSetButtonsAllgemein"));
			Gui.openNewCustomer();
		} else if(e.getActionCommand().equalsIgnoreCase("updateCostumerBtn")) {
			if(GuiMemory.isKundeSet()) {
				if(GuiMemory.getKunde().getKartennr().equals(ConfigFactory.getConfig().getDummyUser())) {
					btn_chooseCost.setText(lang.getString("BTN_SELLER_SEARCH"));
				} else {
					btn_chooseCost.setText(GuiMemory.getKunde().getVname()+" "+GuiMemory.getKunde().getNname());
				}
			}
		} else if(e.getActionCommand().equalsIgnoreCase("unSetButtonsVerkaufer")){
			setDefaultBtnBorder();
		} else if(e.getActionCommand().equalsIgnoreCase("btnDoItProminent")) {
			setDefaultBtnBorder();
			btn_doIt = setProminentBtnBorder(btn_doIt);
			GuiMemory.getActionListener("AllgemeinTab").actionPerformed(new ActionEvent(this,new Random().nextInt(),"unSetButtonsAllgemein"));
		} else if(e.getActionCommand().equalsIgnoreCase("btnTicketSeProminent")) {
			setDefaultBtnBorder();
			btn_ticket = setProminentBtnBorder(btn_ticket);
			GuiMemory.getActionListener("AllgemeinTab").actionPerformed(new ActionEvent(this,new Random().nextInt(),"unSetButtonsAllgemein"));
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
		btn_doIt.setIcon(null);
		btn_doIt.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		btn_ticket.setIcon(null);
		btn_ticket.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		btn_sell.setIcon(null);
		btn_sell.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		btn_chooseCost.setIcon(null);
		btn_chooseCost.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		btn_new.setIcon(null);
		btn_new.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
	}
}
