package ticketline.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import ticketline.cfg.ConfigFactory;
import ticketline.dao.DAOFactory;
import ticketline.dao.hibernate.TicketcardDAOHibernate;
import ticketline.dao.interfaces.KundeDAO;
import ticketline.db.Kunde;

/**
 * Web Account anmelden Gui
 * 
 * @author PatrickF
 * @version 0.1
 */
public class WebAccountAnmeldenGui extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private static ResourceBundle lang;
	
	private static Logger logger = Logger.getLogger(WebAccountAnmeldenGui.class);
	
	private JLayeredPane pn_form;
		
	private JLabel lbl_cardnumber;
	private JLabel lbl_password;
	private JLabel lbl_password2;
	
	private JTextField tf_cardnumber;
	private JTextField tf_password;
	private JTextField tf_password2;
		
	private JButton btn_register;	
	
	public WebAccountAnmeldenGui() {
		Gui.setPanelSize();
		
		lang = ConfigFactory.getConfig().getLanguageBundle();
		
		pn_form = new JLayeredPane();
		
		lbl_cardnumber = new JLabel(lang.getString("LBL_WEB_CARDNUMBER"));
		lbl_password = new JLabel(lang.getString("LBL_WEB_PASSWORD"));
		lbl_password2 = new JLabel(lang.getString("LBL_WEB_PASSWORD2"));
		
		tf_cardnumber = new JTextField(20);
		tf_password = new JTextField(4);
		tf_password2 = new JTextField(4);

		btn_register = new JButton(lang.getString("BTN_WEB_REGISTER"));
		
		// Layout
    	GridBagLayout gb_webAcc = new GridBagLayout();
    	setLayout(gb_webAcc);    
		
    	setMinimumSize(new Dimension(600,650));
    	
 		GridBagConstraints c = new GridBagConstraints();
 		c.anchor = GridBagConstraints.LINE_START;
 		c.insets = new Insets(5,5,5,5);

 		/**
 		 * Form Panel
 		 */
 		GridBagLayout gb_form = new GridBagLayout();
 		pn_form.setLayout(gb_form);
 		pn_form.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_WEBACCOUNT")));
 		pn_form.setMinimumSize(new Dimension(570,600));	
 		pn_form.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()));
 		 		
 		// Column 1
	    c.gridx = 0;	
		c.gridy = 0;
		gb_form.setConstraints(lbl_cardnumber, c);
 		pn_form.add(lbl_cardnumber); 		
 		c.gridy = 1;
		gb_form.setConstraints(lbl_password, c);
 		pn_form.add(lbl_password); 		
 		c.gridy = 2;
 		gb_form.setConstraints(lbl_password2, c);
 		pn_form.add(lbl_password2);
 		
 		// Column 2
 		c.gridx = 1;		
 		c.gridy = 0;
 		gb_form.setConstraints(tf_cardnumber, c);
 		pn_form.add(tf_cardnumber); 		
 		c.gridy = 1;
		gb_form.setConstraints(tf_password, c);
 		pn_form.add(tf_password); 		
 		c.gridy = 2;	
		gb_form.setConstraints(tf_password2, c);
 		pn_form.add(tf_password2); 		
 		
 		// ADD FORM PANEL
 		c.gridx = 0;
 		c.gridy = 0;
 		//c.gridheight = 1;
 		//c.gridwidth = 1;
 		gb_webAcc.setConstraints(pn_form, c);
 		add(pn_form);
 		
 		// ADD REGISTER BUTTON
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.gridx = 0;
		c.gridy = 1;
		add(btn_register,c);
		btn_register.addActionListener(this);
		btn_register.setActionCommand("register");		
 		
		logger.info("WebAccountAnmelden has started");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent ae) {
		//SEARCH FOR DRIVES
		if(ae.getActionCommand().equals("register")){
			String justNumbersCheck = "[0-9]+";
			TicketcardDAOHibernate dao = new TicketcardDAOHibernate();			
			KundeDAO kDAO = DAOFactory.getKundeDAO();
			List<Kunde> list = kDAO.getAll();			
			boolean ok = true;
			
			try{
				Kunde customer = kDAO.get(new Integer(tf_cardnumber.getText()));		
				
				if(customer.getOnlinepwd() == null){
					if(tf_cardnumber.getText().matches(justNumbersCheck)){
						tf_cardnumber.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));									
					}
					else{
						ok = false;
						tf_cardnumber.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red)); //change border to show that field is required
					}
					if(tf_password.getText().equals(tf_password2.getText()) &&(!tf_password.getText().equals("") || !tf_password2.getText().equals(""))){
						if(tf_password.getText().length() == 4 && tf_password2.getText().length() == 4){
							customer.setOnlinepwd(tf_password.getText());
						}
						else{
							ok = false;
							JOptionPane.showMessageDialog(this, lang.getString("OPT_CREATE_PASSWORDLENGTHFAIL"), lang.getString("OPH_CREATE_PASSWORDLENGTHFAIL"), JOptionPane.WARNING_MESSAGE);
						}
					}
					else{
						ok = false;
						JOptionPane.showMessageDialog(this, lang.getString("OPT_CREATE_PASSWORDFAIL"), lang.getString("OPH_CREATE_PASSWORDFAIL"), JOptionPane.WARNING_MESSAGE);
					}
				
					if(ok){
						for(Kunde k: list){					
							if(k.getKartennr().equals(customer.getKartennr())){
								dao.update(customer);
								JOptionPane.showMessageDialog(this, lang.getString("OPT_CREATE_CUSTOMERSUCESS"), lang.getString("OPH_CREATE_CUSTOMERSUCESS2"), JOptionPane.WARNING_MESSAGE);
								break;
							}
						}
					}
				}
				else{
					JOptionPane.showMessageDialog(this, lang.getString("OPT_CREATE_CUSTOMERFAILONLINEPWD"), lang.getString("OPH_CREATE_CUSTOMERFAILONLINEPWD"), JOptionPane.WARNING_MESSAGE);
				}
				
			}
			catch(NumberFormatException ex2){
				JOptionPane.showMessageDialog(this, lang.getString("OPT_SEARCH_FAILLETTER"), lang.getString("OPH_SEARCH_FAILLETTER2"), JOptionPane.INFORMATION_MESSAGE);
				logger.warn("WebAccountAnmeldenGui:Wrong Cardnumber input");
			}
			catch(ObjectNotFoundException ex){
				JOptionPane.showMessageDialog(this, lang.getString("OPT_CREATE_CUSTOMERFAIL"), lang.getString("OPH_CREATE_CUSTOMERFAIL2"), JOptionPane.INFORMATION_MESSAGE);
				logger.warn("WebAccountAnmeldenGui:Cardnumber not available");
			}
		}	
	}
}
