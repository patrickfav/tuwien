package ticketline.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import ticketline.bl.GuiMemory;
import ticketline.bl.ITrail;
import ticketline.bl.Trail;
import ticketline.cfg.ConfigFactory;
import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.VeranstaltungDAO;
import ticketline.db.Auffuehrung;
import ticketline.db.Veranstaltung;

/**
 * TopTen Anzeigen Gui
 * 
 * @author PatrickF
 * @version 0.1
 */
public class TopTenAnzeigenGui extends JPanel implements ActionListener,MouseListener, ComponentListener, ITrail {
	private static final long serialVersionUID = 1L;
		
	private static ResourceBundle lang;
	private static Logger logger = Logger.getLogger(TopTenAnzeigenGui.class);
	
	// Trail
	private Trail trail;
	private Trail subtrailKundeSuchen;
	
	private JTextPane topTen;
	private JScrollPane scrl_pane;
	private JPanel inside_panel = new JPanel();
	
	private JButton btn_resBuy;
	
	private List<Veranstaltung> veranstaltungen;
	private Veranstaltung veranstltung;
	
	public  TopTenAnzeigenGui() {
		Gui.setPanelSize();
		
		lang = ConfigFactory.getConfig().getLanguageBundle();
		
		addComponentListener(this);
		//Customize the Panel
		//setPreferredSize(new Dimension(1000,1000));
		setBorder(new TitledBorder(lang.getString("PNL_TOPTEN")));
		GridBagLayout gbl_topten = new GridBagLayout();
		setLayout(gbl_topten);
		GridBagConstraints c = new GridBagConstraints();
 		c.anchor = GridBagConstraints.LINE_START;
 		c.insets = new Insets(5,5,5,5);
 		c.fill = GridBagConstraints.HORIZONTAL;
		
		
		btn_resBuy = new JButton(lang.getString("BTN_TOPTEN_RESBUY"));
		btn_resBuy.addActionListener(this);
		btn_resBuy.setActionCommand("next");
		btn_resBuy.setEnabled(false);
		
		//setMinimumSize(new Dimension(570,600));
		//setSize(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight());
		
		inside_panel.setLayout(new GridLayout(10,1));
		
		VeranstaltungDAO vdao = DAOFactory.getVeranstaltungDAO();
		veranstaltungen = vdao.find("1=1");
		
	    //TODO - simple loop implementation -> DOA expected
	    for(int i=0;i<10;i++) {
			
	    	Veranstaltung currentV = (Veranstaltung) veranstaltungen.get(i);
	    	Set auf = currentV.getAuffuehrungen();
			Object[] auffuehrungen = auf.toArray();
			String nextShows = "";
			for(int a = 0; a < auffuehrungen.length && a < 3; a++) {
				Auffuehrung currentAuffuehrung = (Auffuehrung) auffuehrungen[a];
				if(a > 0)
					nextShows += ", ";
				nextShows += currentAuffuehrung.getComp_id().getDatumuhrzeit().toLocaleString().substring(0,10);
			}
			
			topTen = new JTextPane();
			topTen.setEditable(false);
			topTen.setMinimumSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth()-100,75));
			if(ConfigFactory.getConfig().getDefaultPanelHeight()>600){
				topTen.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth()-100,60));
			}
			else{
				topTen.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth()-100,50));
			}
			topTen.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			
			topTen.setContentType("text/html");
			topTen.setText("<div style='font-family: Verdana,Vera Sans, Arial, Helvetica, sans-serif;font-size:small;line-height:10px;font-size:95%;color:#333;line-height:1.0em;'>" +
					"		" +
					"			<div style='float:right; padding-left:8px;'>" +
					"				<b>"+(i+1)+") "+ currentV.getComp_id().getBezeichnung() +"</b>" +
					"					<p>Vorstellungen am: "+ nextShows +"</p>" +
					"			</div>" +
					"		</div>");
			
			topTen.addMouseListener(this);
			
			inside_panel.add(topTen);
	    }
	    
	    //the scroll pane
		scrl_pane = new JScrollPane(inside_panel);
		c.gridx = 0;
		c.gridy = 0;
	    add(scrl_pane, c);
	    if(GuiMemory.getLogin() != null) {
	    	c.gridy = 1;
	    	c.anchor = GridBagConstraints.LINE_END;
	    	add(btn_resBuy, c);
	    }
	    logger.info("TopTenAnzeigen has started");
	}
	
	/*
     * ACTION PERFORMED - ACTION LISTENER
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// select Event
		if(e.getActionCommand().equals("next")){
			GuiMemory.attachVeranstaltung(veranstltung);
			GuiMemory.attachSignal("SIGEVTSEARCH");
			trail.getNext(this).setTrail(trail);
			Gui.load((Component)trail.getNext(this));
			setVisible(true);
		}
		
	}
	
	
	/*
	 * ITRAIL METHODS
	 * @see ticketline.bl.ITrail#setTrail(ticketline.bl.Trail)
	 */
	
	@Override
	public void setTrail(Trail trail) {
		this.trail = trail;	
	}
	@Override
	public void dummySearch() {
	}
	@Override
	public void reloadChanges() {
	}
	
	
	/*
	 * MOUSE EVENT LITENER
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		Component temp = e.getComponent();
		if(temp.getBackground() != Color.CYAN)
			temp.setBackground(Color.LIGHT_GRAY);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Component temp = e.getComponent();
		if(temp.getBackground() == Color.LIGHT_GRAY)
			temp.setBackground(Color.WHITE);	
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(GuiMemory.getLogin() != null) {
			Component temp = e.getComponent();
			Component[] comps = inside_panel.getComponents();
			for(int i = 0; i < comps.length; i++) {
				comps[i].setBackground(Color.WHITE);
			}
			temp.setBackground(Color.CYAN);
			int j = inside_panel.getComponentZOrder(temp);
			Veranstaltung v = (Veranstaltung)veranstaltungen.get(j);
			btn_resBuy.setEnabled(true);
			btn_resBuy.setText(new MessageFormat(lang.getString("BTN_TOPTEN_RESBUY_SELECT")).format(new String[] { v.getComp_id().getBezeichnung()}));
			veranstltung = v;
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	/*
	 * COMPONENT LISTENER
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentHidden(ComponentEvent arg0) {
	}
	@Override
	public void componentMoved(ComponentEvent arg0) {
	}
	@Override
	public void componentResized(ComponentEvent e) {
		e.getComponent().setLocation(0,1);
		setSize(new Dimension(ConfigFactory.getConfig().getPnl_mainAreaWidth(),ConfigFactory.getConfig().getPnl_mainAreaHeight()));
		paintAll(this.getGraphics());
    }


	@Override
	public void componentShown(ComponentEvent arg0) {
	}

}
