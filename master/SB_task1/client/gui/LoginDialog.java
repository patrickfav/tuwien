package gui;

import entities.Peer;
import exe.SpaceClient;
import gui.mem.GuiMemory;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.jini.core.lease.Lease;

import org.apache.log4j.Logger;

import util.SpaceUtil;

/**
 * Login Dialog
 * 
 * @author PatrickF,FlorianR
 */

public class LoginDialog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = -6796201706460623868L;
	private static Logger log = Logger.getLogger(LoginDialog.class);
	
	private JLabel lbl_name;
	private JLabel lbl_server;
	private JLabel lbl_err_msg;
	
	private JTextField tf_name;
	private JTextField tf_server;
	
	private JButton bt_login;
	
	
	public LoginDialog(String space_uri){
		
		lbl_name = new JLabel("Name:");
		lbl_server = new JLabel("HostAddr:");
		lbl_err_msg = new JLabel();
		
		tf_name = new JTextField("",20);
		tf_server = new JTextField(space_uri,20);
		
		bt_login = new JButton("Login");
		
		setTitle("SBOODLE Login");
		setSize(new Dimension(350,150));
		setResizable(false);
		setLocationRelativeTo(null);
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
		getContentPane().add(lbl_server,c);
		
		c.gridx = 3;
		getContentPane().add(tf_server,c);
		
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
		
		//pack();
		setVisible(true);
		
		log.info("Login Dialog started");
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("login")){
			
			try {
				log.info("Try to find space "+tf_server.getText());
				GuiMemory.setSpace(SpaceUtil.findSpace(tf_server.getText()));
			} catch (Exception ex) {
				log.error("Could not find space: "+ex.getMessage());
				SpaceClient.exitClient(1);
			}
			
			Peer login = new Peer(tf_name.getText());
			GuiMemory.login(login);
			
			
			try {
				if(GuiMemory.getSpaceInstance().read(login,null,2000) == null) {
					GuiMemory.getSpaceInstance().write(login,null,Lease.FOREVER);
					log.info("Peer does not exist, creating in space.");
				}
			} catch (Exception ex) {
				log.error("Could read or write peer for login: "+ex.getMessage());
				SpaceClient.exitClient(1);
			}
			
			PeerWindow pw = new PeerWindow("SBOODLE (" + tf_name.getText() + ")");
			pw.setVisible(true);
			pw.toFront();
			pw.setFocusable(true);
			pw.setFocusableWindowState(true);
			pw.requestFocus();
			this.dispose();
		}
	}
	
}
