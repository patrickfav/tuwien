package ticketline.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import ticketline.bl.GuiMemory;
import ticketline.bl.Login;
import ticketline.cfg.ConfigFactory;
import ticketline.dao.DAOFactory;

/**
 * Login Gui
 * 
 * A standalone Frame, opens the main gui when logged in correctly
 * 
 * @author ReneN, PatrickF, AndiS
 * @version 0.5
 */

public class LoginGui extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private static ResourceBundle lang;
	private static Logger logger = Logger.getLogger(LoginGui.class);
	
	private JLabel lbl_name;
	private JLabel lbl_pwd;
	private JLabel lbl_err_msg;
	
	private JPasswordField tf_pwd;
	private JTextField tf_name;
	
	private JButton bt_login;
	private JButton bt_otheruser;
	//private JButton bt_abort;
	
	private Login login;
	private Gui mainGui;
	
	public LoginGui(Gui mainGui){
		
		this.mainGui = mainGui;
		
		lang = ConfigFactory.getConfig().getLanguageBundle();
		
		lbl_name = new JLabel(lang.getString("LBL_LOGIN_USER"));
		lbl_pwd = new JLabel(lang.getString("LBL_LOGIN_PASS"));
		lbl_err_msg = new JLabel();
		
		tf_pwd = new JPasswordField("OEPy",20);
		tf_name = new JTextField("pTPrJhgZMs",20);
		
		bt_login = new JButton(lang.getString("BTN_LOGIN_LOGIN"));
		bt_otheruser = new JButton("debug");
		//bt_abort = new JButton(lang.getString("BTN_CANCEL"));
		
		
		setTitle(lang.getString("TTL_LOGINGUI"));
		setSize(new Dimension(350,150));
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("images/logo_icon.png").getImage());
		setLayout(new GridBagLayout());
    	setModal(true);
    	
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,0,0);
		c.gridx = 0;
		c.gridy = 0;
		getContentPane().add(lbl_name,c);
		
		c.gridx = 3;
		getContentPane().add(tf_name,c);
		
		c.gridx = 0;
		c.gridy = 1;
		getContentPane().add(lbl_pwd,c);
		
		c.gridx = 3;
		getContentPane().add(tf_pwd,c);
		
		c.gridx = 3;
		c.gridy = 2;
		c.gridwidth=3;
		c.anchor = GridBagConstraints.LINE_END;
		getContentPane().add(lbl_err_msg,c);
		
		c.gridx = 3;
		c.gridy = 3;
		c.gridwidth =1;
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.LINE_END;
		bt_login.setActionCommand("login");
		bt_login.addActionListener(this);
		getContentPane().add(bt_login,c);
		
		c.gridx = 0;
		bt_otheruser.setActionCommand("otheruser");
		bt_otheruser.addActionListener(this);
		c.anchor = GridBagConstraints.LINE_END;
		getContentPane().add(bt_otheruser,c);
		
		//pack();
		setVisible(true);
		
		logger.info("Login Gui started");
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("otheruser")){
			if(tf_name.getText().equals("pTPrJhgZMs")) {
				tf_name.setText("UaWgVXXGxJ");
				tf_pwd.setText("oYUGW");
			} else {
				tf_name.setText("pTPrJhgZMs");
				tf_pwd.setText("OEPy");
			}
		}
		if(e.getActionCommand().equals("login")){
			
			try{
				login = new Login(tf_name.getText().trim(),new String(tf_pwd.getPassword()));
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this,lang.getString("TXT_LOGIN_ERROR_MSG"),lang.getString("LBL_LOGIN_ERROR"),JOptionPane.ERROR_MESSAGE);
			}
			
			if(login!=null)
			{
				//if the login is correct
				if(login.isLoggedIn()) {
					logger.info("User "+tf_name.getText().trim() + " has been successfully logged in.");
					//closes this frame
					dispose();
					//sets the Dummy Kunde
					GuiMemory.attachKunde(DAOFactory.getKundeDAO().get(ConfigFactory.getConfig().getDummyUser()));
					//consult the main gui
					mainGui.login();
				} else {
					logger.debug("Incorrect Login: User "+tf_name.getText().trim());
					//displays an error msg
					lbl_err_msg.setText(lang.getString("LBL_LOGIN_ERROR"));
				}
			}
		}
		
		/*if(e.getActionCommand().equals("abort")){
			exit();
		}*/
	}
	
}
