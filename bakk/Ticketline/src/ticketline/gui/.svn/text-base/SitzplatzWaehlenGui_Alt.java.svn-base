package ticketline.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ticketline.bl.GuiMemory;
import ticketline.bl.ITrail;
import ticketline.bl.Trail;
import ticketline.cfg.ConfigFactory;
import ticketline.db.Belegung;
import ticketline.db.BelegungKey;
import ticketline.db.Kategorie;
import ticketline.db.Reihe;
import ticketline.db.Saal;
import ticketline.db.Transaktion;

/**
 * 
 * Reservation/Sales Step 4 Panel
 * @author FlorianR, ReneN
 * @version 0.1
 *
 */
public class SitzplatzWaehlenGui_Alt extends JPanel implements ActionListener, MouseListener, ITrail  {

	private static final long serialVersionUID = 1L;
	
	private static ResourceBundle lang;
	private static Integer panel_width = ConfigFactory.getConfig().getDefaultPanelWidth();
	private static Integer panel_heigth = ConfigFactory.getConfig().getDefaultPanelHeight();
	
	private Trail trail;
	private Transaktion ta;
	private Kategorie ka;
	private Saal sa;
	private Set<Transaktion> tas;
	private Set<Reihe> rows;
	
	private Iterator iter;
	
	private JScrollPane pn_all;
	private JLayeredPane pn_seats;
	private JLayeredPane pn_legend;
	
	private SeatPanel spn_free;
	private SeatPanel spn_reserved;
	private SeatPanel spn_sold;
	private SeatPanel spn_marked;
	
	private JLabel lb_marked;
    private JLabel lb_free;
    private JLabel lb_reserved;
    private JLabel lb_sold;
	
	private JButton	 btn_back;
    private JButton	 btn_buy;
    private JButton	 btn_reserve;
	
	// Initialize Reihen with State
	private SeatPanel[][] reihen = {{new SeatPanel(1),new SeatPanel(0),new SeatPanel(0),new SeatPanel(0),
									 new SeatPanel(2),new SeatPanel(2),new SeatPanel(0),new SeatPanel(1),
									 new SeatPanel(0),new SeatPanel(1),new SeatPanel(0),new SeatPanel(2),
									 new SeatPanel(0)},
									{new SeatPanel(1),new SeatPanel(0),new SeatPanel(1),new SeatPanel(0),
									 new SeatPanel(2),new SeatPanel(1),new SeatPanel(0),new SeatPanel(0),
									 new SeatPanel(0),new SeatPanel(0),new SeatPanel(0),new SeatPanel(0),
									 new SeatPanel(0)},
									{new SeatPanel(0),new SeatPanel(0),new SeatPanel(2),new SeatPanel(0),
									 new SeatPanel(1),new SeatPanel(1),new SeatPanel(0),new SeatPanel(1),
									 new SeatPanel(0),new SeatPanel(0),new SeatPanel(1),new SeatPanel(0),
									 new SeatPanel(0)},
									{new SeatPanel(0),new SeatPanel(2),new SeatPanel(0),new SeatPanel(0),
									 new SeatPanel(1),new SeatPanel(1),new SeatPanel(1),new SeatPanel(0),
									 new SeatPanel(2),new SeatPanel(0),new SeatPanel(0),new SeatPanel(0),
									 new SeatPanel(1)},
							 };
	
	public SitzplatzWaehlenGui_Alt() {
		
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		// Back
		if(evt.getActionCommand().equals("back")){
			trail.getPrevious(this).setTrail(trail);
			Gui.load((Component)trail.getPrevious(this));
			setVisible(true);
		}
		// Buy
		if(evt.getActionCommand().equals("buy")){
			doStore(1);
		}
		// Reserve
		if(evt.getActionCommand().equals("reserve")){
			doStore(2);
		}
		
	}

	public void mouseEntered(MouseEvent e) {
		SeatPanel src = (SeatPanel) e.getSource();
		if(src.getState() < 1)
			src.setImage("images/markedSeat.png");
		src.repaint();
	}
	public void mouseExited(MouseEvent e) {
		SeatPanel src = (SeatPanel) e.getSource();
		if(src.getState() < 1)
			src.setImage("images/freeSeat.png");
		src.repaint();
	}
	public void mouseClicked(MouseEvent e) {
		SeatPanel src = (SeatPanel) e.getSource();
		if(src.getState() < 1) {
			src.setState(3);
			src.setImage("images/markedSeat.png");
			src.repaint();
		} else {
			src.setState(0);
			src.setImage("images/freeSeat.png");
			src.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTrail(Trail trail) {
		this.trail = trail;
		loadSeats();
	}

	@Override
	public void reloadChanges() {
		// TODO Auto-generated method stub
		
	}
	
	public void doStore(int option){
		int i,j;
		//Number of selected Seats
		int seatCounter = 0;
		int start = 0;
		int freeSeats = 0;
		int reservedSeats = 0;
		int soldSeats = 0;
		boolean selected = false;
		
		for(i = 0; i < reihen.length; i++){
			for(j= 0; j < reihen[i].length; j++){
				//Seat is selected
				if(reihen[i][j].getState() == 3){
					if(selected){//is there already a seat selected in this row
						seatCounter++;
					}else{
						selected = true;
						start = j;
						seatCounter = 1;
					}
				}
				//Seat is not selected
				if(reihen[i][j].getState() != 3){
					if(reihen[i][j].getState() == 0){
						freeSeats ++;
					}
					if(reihen[i][j].getState() == 1){
						reservedSeats++;
					}
					if(reihen[i][j].getState() == 2){
						soldSeats++;
					}
					//Seats selected before actual seat ?
					if(selected){
						//add Transaktion to Set
						addTransaktion(i,start,seatCounter,option);
						selected = false;
						start = 0;
						seatCounter = 0;
					}
				}
			}
			//Check if selection goes to last seat of row
		   if(selected){
			   //add Transaktion to set
			   addTransaktion(i,start,seatCounter,option);
		   }
		   //Store Transaktion and Belegung
		   
		   selected = false;
		   start = 0;
		   seatCounter = 0;
		}
	}
	
	/**
	 * Add Transaktion to Transaktion Set
	 * @param row Selected Row
	 * @param start Start Seatnumber
	 * @param count Number of Seats
	 * @param option 1=Buy, 2=Reserve
	 */
	public void addTransaktion(int row, int start, int count, int option){
		Transaktion trans = ta.clone();
		trans.setAnzplaetze(count);
		trans.setStartplatz(start);
		if(option == 1){
			trans.setVerkauft(true);
		}else{
			trans.setVerkauft(false);
		}
		trans.setZahlart("bar");
		tas.add(trans);
	}
	
	public void saveData(int row, int freeSeats, int reservedSeats, int soldSeats){
		Belegung ba = new Belegung();
		ba.setComp_id(new BelegungKey());
		ba.getComp_id().setDatumuhrzeit(new Date());
		ba.getComp_id().setKategoriebez(ka.getComp_id().getBezeichnung());
		ba.getComp_id().setOrt(ta.getOrt().getComp_id().getOrt());
		ba.getComp_id().setOrtbez(ta.getOrt().getComp_id().getBezeichnung());
		ba.getComp_id().setReihebez("Reihe " + row);
		ba.getComp_id().setSaalbez(sa.getComp_id().getBezeichnung());
		ba.setAnzfrei(freeSeats);
		ba.setAnzres(reservedSeats);
		ba.setAnzverk(soldSeats);
	}
	
	private void loadSeats(){
		lang = ConfigFactory.getConfig().getLanguageBundle();
		
		tas = new LinkedHashSet<Transaktion>();
		ta = GuiMemory.detachTransaktion();
		ka = GuiMemory.detachKategorie();
		sa = GuiMemory.detachSaal();
		
		pn_all = new JScrollPane();
		pn_seats = new JLayeredPane();
		pn_legend = new JLayeredPane();
		
		spn_free = new SeatPanel(0);
		spn_reserved = new SeatPanel(1);
		spn_sold = new SeatPanel(2);
		spn_marked = new SeatPanel(3);
		
		lb_marked = new JLabel(lang.getString("LBL_SELECTSEATS_MARKED"));
    	lb_free = new JLabel(lang.getString("LBL_SELECTSEATS_FREE"));
    	lb_reserved = new JLabel(lang.getString("LBL_SELECTSEATS_RESERVED"));
    	lb_sold = new JLabel(lang.getString("LBL_SELECTSEATS_SOLD"));
		
		btn_back = new JButton(lang.getString("BTN_SELECTSEATS_BACK"));
    	btn_buy = new JButton(lang.getString("BTN_SELECTSEATS_BUY"));
    	btn_reserve = new JButton(lang.getString("BTN_SELECTSEATS_RESERVATION"));

		// Layout
		GridBagLayout gb_reskauf4 = new GridBagLayout();
    	setLayout(gb_reskauf4);
    	setSize(panel_width, panel_heigth);
 		GridBagConstraints c = new GridBagConstraints();
 		c.anchor = GridBagConstraints.LINE_START;
 		c.insets = new Insets(5,5,5,5);
 		
 		/**
 		 * Seats
 		 */
 		GridBagLayout gb_seats = new GridBagLayout();
 		pn_seats.setLayout(gb_seats);
 		pn_seats.setPreferredSize(new Dimension(520,466));
 		pn_seats.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SELECTSEATS")));
 		
 		/*for(int i = 0; i < reihen.length; i++) {
 			c.gridy = i;
 			for(int j = 0; j < reihen[i].length; j++) {
 				c.gridx = j;				
 		 		if(reihen[i][j].getState() ==0) reihen[i][j].addMouseListener(this);
 		 		gb_seats.setConstraints(reihen[i][j], c);
 		 		pn_seats.add(reihen[i][j]);
 			}
 		}*/
 		rows = ka.getReihen();
 		iter = ka.getReihen().iterator();
 		for(int j = 0; j < ka.getReihen().size(); j++){
 			c.gridy = j;
 			c.gridx = 0;
 			RowPanel rp = new RowPanel((Reihe)iter.next());
 			gb_seats.setConstraints(rp, c);
 			pn_seats.add(rp);
 		}
 		
 		// ADD SEATS PANEL
 		c.gridx = 0;
 		c.gridy = 0;
 		c.gridwidth = 3;
 		c.anchor = GridBagConstraints.LINE_START;
 		gb_reskauf4.setConstraints(pn_seats, c);
 		//add(pn_seats);
 		pn_all.add(pn_seats);
 		pn_all.setVisible(true);
 		add(pn_all);
 		/**
 		 * Legend
 		 */
 		GridBagLayout gb_legend = new GridBagLayout();
 		pn_legend.setLayout(gb_legend);
 		pn_legend.setPreferredSize(new Dimension(520,60));
 		pn_legend.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SELECTSEATS_LEGEND")));
 		
 		// Column 1
 		c.gridwidth = 1;
 		c.anchor = GridBagConstraints.LINE_START;
 		c.gridx = 0;
 		c.gridy = 0;
 		gb_legend.setConstraints(spn_free, c);
 		pn_legend.add(spn_free);
 		c.gridx = 2;
 		gb_legend.setConstraints(spn_reserved, c);
 		pn_legend.add(spn_reserved);
 		c.gridx = 4;
 		gb_legend.setConstraints(spn_sold, c);
 		pn_legend.add(spn_sold);
 		c.gridx = 6;
 		gb_legend.setConstraints(spn_marked, c);
 		pn_legend.add(spn_marked);
 		
 		// Column 2
 		c.gridx = 1;
 		c.gridy = 0;
 		c.insets = new Insets(5,5,5,25);
 		gb_legend.setConstraints(lb_free, c);
 		pn_legend.add(lb_free);
 		c.gridx = 3;
 		gb_legend.setConstraints(lb_reserved, c);
 		pn_legend.add(lb_reserved);
 		c.gridx = 5;
 		gb_legend.setConstraints(lb_sold, c);
 		pn_legend.add(lb_sold);
 		c.gridx = 7;
 		c.insets = new Insets(5,5,5,5);
 		gb_legend.setConstraints(lb_marked, c);
 		pn_legend.add(lb_marked);
 		
 		// ADD LEGEND
 		c.gridx = 0;
 		c.gridy = 1;
 		c.gridwidth = 3;
 		c.anchor = GridBagConstraints.LINE_START;
 		gb_reskauf4.setConstraints(pn_legend, c);
 		add(pn_legend);
 		
 		// ADD BACK BUTTON
 		c.gridx = 0;
 		c.gridy = 2;
 		c.gridwidth = 1;
 		c.anchor = GridBagConstraints.LINE_START;
 		gb_reskauf4.setConstraints(btn_back, c);
 		btn_back.addActionListener(this);
 		btn_back.setActionCommand("back");
 		add(btn_back);
 		
 		// ADD RESERVATION BUTTON
 		c.gridx = 1;
 		c.anchor = GridBagConstraints.LINE_END;
 		c.insets = new Insets(5,70,5,5);
 		gb_reskauf4.setConstraints(btn_reserve, c);
 		btn_reserve.addActionListener(this);
 		btn_reserve.setActionCommand("reserve");
 		add(btn_reserve);
 		
 		// ADD BUY BUTTON
 		c.gridx = 2;
 		c.anchor = GridBagConstraints.LINE_END;
 		c.insets = new Insets(5,5,5,5);
 		gb_reskauf4.setConstraints(btn_buy, c);
 		btn_buy.addActionListener(this);
 		btn_buy.setActionCommand("buy");
 		add(btn_buy);
 		
 		setVisible(true);
	}

	@Override
	public void dummySearch() {
		// TODO Auto-generated method stub
		
	}
}
