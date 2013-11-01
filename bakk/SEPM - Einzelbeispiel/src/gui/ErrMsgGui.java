package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ErrMsgGui extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public ErrMsgGui(String errmsg) {
		JOptionPane.showMessageDialog(this, errmsg,"Fehlerbericht",JOptionPane.WARNING_MESSAGE);
	}
	

}
