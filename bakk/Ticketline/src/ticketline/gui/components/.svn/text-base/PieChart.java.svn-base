package ticketline.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PieChart extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PieItem[] wedges; // The data for the pie
	double total = 0.0; // Total of all wedges
	static final int ncolors = 5;
	Color wedgeColor[] = new Color[5];
	int pieViewSize; // size of square to incise pie into
	static final int pieBorderWidth = 10; // pixels from circle edge to side
	int pieDiameter; // derived from the view size
	int pieRadius; // ..
	int pieCenterPos; // .
	
	public PieChart(int asize, PieItem[] avec) {
		setPreferredSize(new Dimension(250,200));
		
		this.pieViewSize = asize; // copy args
		this.wedges = avec;
		pieDiameter = pieViewSize-2*pieBorderWidth;
		pieRadius = pieDiameter/2;
		pieCenterPos = pieBorderWidth+pieRadius;
		this.setFont(new Font("Helvetica",Font.BOLD,12));


		for (int i = 0; i<wedges.length; i++) {
		    total += wedges[i].frac;
		}

		wedgeColor[0] = Color.green; // colors that black looks good on
		wedgeColor[1] = Color.red;
		wedgeColor[2] = Color.cyan;
		wedgeColor[3] = Color.pink;
		wedgeColor[4] = Color.yellow;

		
		setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g); // JPanel draws background
		int startDeg = 0;
		int arcDeg;
		int x, y;
		double angleRad;
		g.setColor(Color.lightGray); // shadow
		g.fillOval(pieBorderWidth+3,pieBorderWidth+3,pieDiameter,pieDiameter);
		g.setColor(Color.gray); // "other" is gray
		g.fillOval(pieBorderWidth,pieBorderWidth,pieDiameter,pieDiameter);
		int wci = 0;
		int i;
		for (i = 0; i<this.wedges.length; i++) { // draw wedges
		arcDeg = (int)((this.wedges[i].frac / total) * 360);
		g.setColor(wedgeColor[wci++]);
		g.fillArc(pieBorderWidth,pieBorderWidth,pieDiameter,pieDiameter,
				startDeg, arcDeg);


		    if (wci >= ncolors) {
		    	wci = 0; // rotate colors
		}

		startDeg += arcDeg;
		} // draw wedges

		startDeg = 0; // do labels so they go on top of the wedges.


		for (i = 0; i<this.wedges.length; i++) {
		arcDeg = (int)((this.wedges[i].frac / total) * 360);
		if (arcDeg > 3) { // don't label small wedges
			g.setColor(Color.black);
			angleRad = (float) (startDeg+(arcDeg/2))* java.lang.Math.PI / 180.0;
			x = pieCenterPos + (int)((pieRadius/1.3)*java.lang.Math.cos(angleRad));
			y = pieCenterPos - (int)((pieRadius/1.3)*java.lang.Math.sin(angleRad))
		+ 5; // 5 is about half the height of the text
			g.drawString(this.wedges[i].label, x, y);
		} // don't label small wedges

		startDeg += arcDeg;
		} // for
	}
}
