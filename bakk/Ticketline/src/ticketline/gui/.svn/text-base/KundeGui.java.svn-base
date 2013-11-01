package ticketline.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.apache.log4j.Logger;

import com.toedter.calendar.JDateChooser;

import ticketline.bl.Trail;
import ticketline.cfg.ConfigFactory;

import ticketline.db.Kunde;


/**
 * Costumer GUI, contains all similarities of Costumer Create and Edit
 * 
 * @author AndiS
 * @version 0.2
 */

public class KundeGui extends JPanel implements ActionListener, ComponentListener{
	
	protected static final long serialVersionUID = 1L;

	protected static ResourceBundle lang;
	protected static Integer panel_width = ConfigFactory.getConfig().getDefaultPanelWidth();
	protected static Integer panel_heigth = ConfigFactory.getConfig().getDefaultPanelHeight();
		
	protected static Logger logger = Logger.getLogger(KundeGui.class);
	
	protected JLabel lbl_cardnumber;
	protected JLabel lbl_firstname;
	protected JLabel lbl_surname;
	protected JLabel lbl_gender;
	protected JLabel lbl_birthday;
	protected JLabel lbl_street;
	protected JLabel lbl_plz;
	protected JLabel lbl_mail;
	protected JLabel lbl_location;
	protected JLabel lbl_phone;
	protected JLabel lbl_account;
	protected JLabel lbl_balance;
	protected JLabel lbl_reduction;
	protected JLabel lbl_validDate;
	protected JLabel lbl_preferences;

	protected JTextField tf_cardnumber;
	protected JTextField tf_firstname;
	protected JTextField tf_surname;
	protected JTextField tf_street;
	protected JTextField tf_plz;
	protected JTextField tf_mail;
	protected JTextField tf_location;
	protected JTextField tf_phone;
	protected JTextField tf_account;
	protected JTextField tf_balance;
	protected JTextField tf_reduction;	
	protected JTextField tf_preferences;
	
	protected JRadioButton rb_gender_male;
	protected JRadioButton rb_gender_female;
	
	protected JDateChooser jdc_birthday;
	protected JDateChooser jdc_validDate;
	
	protected JButton btn_execute;
	protected JButton btn_reset;
	protected JButton btn_back;
	
	protected Kunde k;
	
	protected Trail trail;
	
	public KundeGui(){
		Gui.setPanelSize();
		//addComponentListener(this);
		lang = ConfigFactory.getConfig().getLanguageBundle();
		
		lbl_cardnumber = new JLabel(lang.getString("LBL_CUSTOMER_CARDNUMBER"));
		lbl_firstname = new JLabel(lang.getString("LBL_CUSTOMER_FIRSTNAME"));
		lbl_surname = new JLabel(lang.getString("LBL_CUSTOMER_SURNAME"));
		lbl_gender = new JLabel(lang.getString("LBL_CUSTOMER_GENDER"));
		lbl_birthday = new JLabel(lang.getString("LBL_CUSTOMER_BIRTHDAY"));
		lbl_street = new JLabel(lang.getString("LBL_CUSTOMER_STREET"));
		lbl_plz = new JLabel(lang.getString("LBL_CUSTOMER_PLZ"));
		lbl_mail = new JLabel(lang.getString("LBL_CUSTOMER_MAIL"));
		lbl_location = new JLabel(lang.getString("LBL_CUSTOMER_LOCATION"));
		lbl_phone = new JLabel(lang.getString("LBL_CUSTOMER_PHONE"));
		lbl_account = new JLabel(lang.getString("LBL_CUSTOMER_ACCOUNT"));
		lbl_balance = new JLabel(lang.getString("LBL_CUSTOMER_BALANCE"));
		lbl_reduction = new JLabel(lang.getString("LBL_CUSTOMER_REDUCTION"));
		lbl_validDate = new JLabel(lang.getString("LBL_CUSTOMER_VALIDDATE"));
		lbl_preferences = new JLabel(lang.getString("LBL_CUSTOMER_PREFERENCES"));

		tf_cardnumber = new JTextField(20);
		tf_firstname = new JTextField(20);
		tf_surname = new JTextField(20);
		rb_gender_male = new JRadioButton(lang.getString("RB_CUSTOMER_MALE"),true);
		rb_gender_female = new JRadioButton(lang.getString("RB_CUSTOMER_FEMALE"),false);
		tf_street = new JTextField(20);
		tf_plz = new JTextField(20);
		tf_mail = new JTextField(20);
		tf_location = new JTextField(20);
		tf_phone = new JTextField(20);
		tf_account = new JTextField(20);
		tf_balance = new JTextField(20);
		tf_reduction = new JTextField(20);
		tf_preferences = new JTextField(20);
		
		jdc_birthday = new JDateChooser();
		jdc_birthday.setDateFormatString("yyyy-MM-dd");
		jdc_validDate = new JDateChooser();
		jdc_validDate.setDateFormatString("yyyy-MM-dd");
		
		btn_execute = new JButton();
		btn_reset = new JButton(lang.getString("BTN_CUSTOMER_RESET"));
		
		btn_reset.addActionListener(this);
		btn_reset.setActionCommand("reset");
		
		btn_back = new JButton(lang.getString("BTN_SEARCHCUSTOMER_BACK"));
		btn_back.setActionCommand("back");
		
		//Setting unEditable				
		((javax.swing.JTextField)jdc_birthday.getDateEditor()).setEditable(false);
		((javax.swing.JTextField)jdc_validDate.getDateEditor()).setEditable(false);
		
		// Layout
		jdc_birthday.setPreferredSize(new Dimension(120,20));
		jdc_validDate.setPreferredSize(new Dimension(120,20));
		
    	GridBagLayout gb_customer = new GridBagLayout();
    	setLayout(gb_customer);
    	
    	setMinimumSize(new Dimension(570,600));	
 		setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()));
 		GridBagConstraints c = new GridBagConstraints();		
	    c.insets = new Insets(4,4,4,4);
	    c.anchor = GridBagConstraints.LINE_START;
	    
		//first column: labels
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		add(lbl_cardnumber,c);
		c.gridy = 2;
		add(lbl_firstname,c);
		c.gridy = 3;
		add(lbl_surname,c);
		c.gridy = 4;
		add(lbl_gender,c);
		c.gridy = 5;
		add(lbl_birthday,c);
		c.gridy = 6;
		add(lbl_street,c);
		c.gridy = 7;
		add(lbl_plz,c);
		c.gridy = 8;
		add(lbl_mail,c);
		c.gridy = 9;
		add(lbl_location,c);
		c.gridy = 10;
		add(lbl_phone,c);
		c.gridy = 11;
		add(lbl_account,c);
		c.gridy = 12;
		add(lbl_balance,c);
		c.gridy = 13;
		add(lbl_reduction,c);
		c.gridy = 14;
		add(lbl_validDate,c);		
		// special line 15
		c.gridx = 0;
		c.gridy = 16;
		add(lbl_preferences,c);
		
		//second column: edit fields
		c.gridwidth = 3;  
		c.gridx = 1;
		c.gridy = 0;
		add(tf_cardnumber,c);
		c.gridy = 2;
		add(tf_firstname,c);
		c.gridy = 3;
		add(tf_surname,c);
		c.gridy = 4;
		c.anchor = GridBagConstraints.LINE_START;
		add(rb_gender_male,c);
		c.anchor = GridBagConstraints.LINE_END;
		add(rb_gender_female,c);
		c.anchor = GridBagConstraints.LINE_START;
		c.gridy = 5;
		add(jdc_birthday,c);
		c.gridy = 6;
		add(tf_street,c);
		c.gridy = 7;
		add(tf_plz,c);
		c.gridy = 8;
		add(tf_mail, c);
		c.gridy = 9;
		add(tf_location,c);
		c.gridy = 10;
		add(tf_phone,c);
		c.gridy = 11;
		add(tf_account,c);
		c.gridy = 12;
		add(tf_balance,c);
		c.gridy = 13;
		add(tf_reduction,c);
		c.gridy = 14;
		add(jdc_validDate,c);		
		// Special line 15
		c.gridy = 16;
		add(tf_preferences,c);		
				
		c.gridwidth = 1;
		//last line: reset and execute button
		c.anchor = GridBagConstraints.LINE_START;
		c.gridy = 20;
		c.gridx = 0;
		add(btn_reset,c);
		c.anchor= GridBagConstraints.LINE_START;
		c.gridy = 20;
		c.gridx = 0;
		add(btn_back,c);
		c.gridx = 2;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.LINE_END;
		add(btn_execute,c);

		//Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(rb_gender_male);
	    group.add(rb_gender_female);	
	}

	public void actionPerformed(ActionEvent e) {
		reset(e);		
	}
	protected void reset(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("reset")){
			tf_cardnumber.setText("");
			tf_firstname.setText("");
			tf_surname.setText("");
			rb_gender_male.setSelected(true);
			rb_gender_female.setSelected(false);
			tf_street.setText("");
			tf_plz.setText("");
			tf_mail.setText("");
			tf_location.setText("");
			tf_phone.setText("");
			tf_account.setText("");
			tf_balance.setText("");
			tf_reduction.setText("");
			tf_preferences.setText("");
			
			jdc_birthday.setDate(null);
			jdc_validDate.setDate(null);
			
			tf_firstname.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			tf_surname.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			tf_plz.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray)); 
			tf_street.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			tf_mail.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			tf_location.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			tf_phone.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			tf_account.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			tf_balance.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			tf_reduction.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			tf_preferences.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			jdc_birthday.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			jdc_validDate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
			
			logger.info("Textfields resetted");
		}
		
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
	public void componentResized(ComponentEvent e) {
		e.getComponent().setLocation(1,1);
		setSize(new Dimension(ConfigFactory.getConfig().getPnl_mainAreaWidth(),ConfigFactory.getConfig().getPnl_mainAreaHeight()));
		paintAll(this.getGraphics());
    }

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

