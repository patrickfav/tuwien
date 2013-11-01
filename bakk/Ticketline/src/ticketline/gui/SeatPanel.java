package ticketline.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * Seat Panel
 * @author Florian Rauscha, ReneN
 * v.1
 *
 */
public class SeatPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private int state;
	private int number;
	private Image image;
	
	public SeatPanel(int state) {

		this.state = state;
		setPreferredSize(new Dimension(20,20));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loadImageFromState();
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public void setImage(String img) {
		image = new ImageIcon(img).getImage();
	}
	
	public void loadImageFromState() {
		switch(state) {
 			case 0:
 				image = new ImageIcon("images/freeSeat.png").getImage();
 			break;
 			case 1:
 				image = new ImageIcon("images/reservedSeat.png").getImage();
 			break;
 			case 2:
 				image = new ImageIcon("images/soldSeat.png").getImage();
 			break;
 			case 3:
 				image = new ImageIcon("images/markedSeat.png").getImage();
 			break;
		}
	}
	
	public void paintComponent(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, +11));
	    g.drawImage(image, 0, 0, null);
	    if(number != 0){
	    	g.drawString("S" + number, getXMiddle(g),15);
	    }
	}

	public void setNumber(int number){
		this.number = number;
	}
	
	public int getXMiddle(Graphics g){
		int x = 0;
		x = new Double((this.getWidth()/2)).intValue();
        x = x - (g.getFontMetrics().stringWidth("S" + number)/2);
        return x;
	}
	
	public void reload(){
		loadImageFromState();
		repaint();
	}
}
