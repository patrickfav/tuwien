package ticketline.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;

import ticketline.bl.GuiMemory;
import ticketline.bl.ITrail;
import ticketline.bl.Trail;
import ticketline.cfg.ConfigFactory;
import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.BelegungDAO;
import ticketline.dao.interfaces.TransaktionDAO;
import ticketline.db.Auffuehrung;
import ticketline.db.Belegung;
import ticketline.db.BelegungKey;
import ticketline.db.Kategorie;
import ticketline.db.KategorieKey;
import ticketline.db.Kunde;
import ticketline.db.Mitarbeiter;
import ticketline.db.Reihe;
import ticketline.db.Saal;
import ticketline.db.Transaktion;
import ticketline.db.TransaktionKey;

/**
 * 
 * Reservation/Sales Step 4 Panel
 * @author FlorianR, ReneN
 * @version 2.0
 *
 */
public class SitzplatzWaehlenGui extends JPanel implements ITrail, ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private static ResourceBundle lang;
	private static Integer panel_width = ConfigFactory.getConfig().getDefaultPanelWidth();
	private static Integer panel_heigth = ConfigFactory.getConfig().getDefaultPanelHeight();
	private static Logger log = Logger.getLogger(SitzplatzWaehlenGui.class);
	
	private Trail trail;
	
	private Kategorie ka;
	private Transaktion ta;
	private Kunde k;
	private Mitarbeiter m;
	private Saal s;
	private Auffuehrung af;
	
	private TransaktionDAO transaktionDAO;
	private BelegungDAO belegungDAO; 
	
	private Set<Reihe> rows;
	private LinkedList<RowPanel> row_panels;
	
	private Iterator iter;
	private Iterator niter;
	
	private JLayeredPane pn_legend;
	private JScrollPane pn_seats;
	private JPanel pn_rows;
	
	private GridBagLayout gb_selseat;
	private GridBagConstraints c;
	
	private SeatPanel spn_free;
	private SeatPanel spn_reserved;
	private SeatPanel spn_sold;
	private SeatPanel spn_marked;
	
	private JLabel lb_marked;
    private JLabel lb_free;
    private JLabel lb_reserved;
    private JLabel lb_sold;
    private JLabel lb_row;
    
    private JButton	 btn_back;
    private JButton	 btn_buy;
    private JButton	 btn_reserve;
    
    private BigDecimal sum;
    private ArrayList<Integer> reservationNumbers;
	
	public SitzplatzWaehlenGui(){
		lang = ConfigFactory.getConfig().getLanguageBundle();
		
		pn_legend = new JLayeredPane();
		pn_seats = new JScrollPane();
		
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
		
    	transaktionDAO = DAOFactory.getTransaktionDAO();
    	belegungDAO = DAOFactory.getBelegungDAO();
    	
		// Set Layout of Root Panel
		gb_selseat = new GridBagLayout();
    	setLayout(gb_selseat);
    	setSize(panel_width, panel_heigth);
 		c = new GridBagConstraints();
 		c.anchor = GridBagConstraints.LINE_START;
 		c.insets = new Insets(5,5,5,5);
 		
 		//pn_seats.setPreferredSize(new Dimension(520,466));
 		pn_seats.setMinimumSize(new Dimension(600,466));	
 		pn_seats.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), ConfigFactory.getConfig().getDefaultPanelHeight()-50));
 		
 		//Add complete Panel (Seat Layered Pane)
 		c.gridx = 0;
 		c.gridy = 0;
 		c.gridwidth = 3;
 		gb_selseat.setConstraints(pn_seats, c);
 		add(pn_seats);
 		
 		/**
 		 * Legend
 		 */
 		GridBagLayout gb_legend = new GridBagLayout();
 		GridBagConstraints c_legend = new GridBagConstraints();
 		pn_legend.setLayout(gb_legend);
 		pn_legend.setMinimumSize(new Dimension(600,60));	
 		pn_legend.setPreferredSize(new Dimension(ConfigFactory.getConfig().getDefaultPanelWidth(), 60));
 		pn_legend.setBorder(BorderFactory.createTitledBorder(lang.getString("PNL_SELECTSEATS_LEGEND")));
 		
 		// Column 1
 		c_legend.gridwidth = 1;
 		c_legend.anchor = GridBagConstraints.LINE_START;
 		c_legend.gridx = 0;
 		c_legend.gridy = 0;
 		gb_legend.setConstraints(spn_free, c_legend);
 		pn_legend.add(spn_free);
 		c_legend.gridx = 2;
 		gb_legend.setConstraints(spn_reserved, c_legend);
 		pn_legend.add(spn_reserved);
 		c_legend.gridx = 4;
 		gb_legend.setConstraints(spn_sold, c_legend);
 		pn_legend.add(spn_sold);
 		c_legend.gridx = 6;
 		gb_legend.setConstraints(spn_marked, c_legend);
 		pn_legend.add(spn_marked);
 		
 		// Column 2
 		c_legend.gridx = 1;
 		c_legend.gridy = 0;
 		c_legend.insets = new Insets(5,5,5,25);
 		gb_legend.setConstraints(lb_free, c_legend);
 		pn_legend.add(lb_free);
 		c_legend.gridx = 3;
 		gb_legend.setConstraints(lb_reserved, c_legend);
 		pn_legend.add(lb_reserved);
 		c_legend.gridx = 5;
 		gb_legend.setConstraints(lb_sold, c_legend);
 		pn_legend.add(lb_sold);
 		c_legend.gridx = 7;
 		c_legend.insets = new Insets(5,5,5,5);
 		gb_legend.setConstraints(lb_marked, c_legend);
 		pn_legend.add(lb_marked);
 		
 		//add legend to root panel
 		c.gridx = 0;
 		c.gridy = 1;
 		c.gridwidth = 3;
 		c.anchor = GridBagConstraints.LINE_START;
 		gb_selseat.setConstraints(pn_legend, c);
 		add(pn_legend);
 		
 	    // ADD BACK BUTTON
 		c.gridx = 0;
 		c.gridy = 2;
 		c.gridwidth = 1;
 		c.anchor = GridBagConstraints.LINE_START;
 		gb_selseat.setConstraints(btn_back, c);
 		btn_back.addActionListener(this);
 		btn_back.setActionCommand("back");
 		add(btn_back);
 		
 		// ADD RESERVATION BUTTON
 		c.gridx = 1;
 		c.anchor = GridBagConstraints.LINE_END;
 		c.insets = new Insets(5,70,5,5);
 		gb_selseat.setConstraints(btn_reserve, c);
 		btn_reserve.addActionListener(this);
 		btn_reserve.setActionCommand("reserve");
 		add(btn_reserve);
 		
 		// ADD BUY BUTTON
 		c.gridx = 2;
 		c.anchor = GridBagConstraints.LINE_END;
 		c.insets = new Insets(5,5,5,5);
 		gb_selseat.setConstraints(btn_buy, c);
 		btn_buy.addActionListener(this);
 		btn_buy.setActionCommand("buy");
 		add(btn_buy);
	}
	
	@Override
	public void reloadChanges() {
		if(trail != null){
			if((trail.getTrailNumber() == 9) || (trail.getTrailNumber() == 1)||(trail.getTrailNumber() == 10)){
				ka = GuiMemory.detachKategorie();
				GuiMemory.attachKategorie(ka);
				ta = new Transaktion();
				k = GuiMemory.detachKunde();
				GuiMemory.attachKunde(k);
				m = GuiMemory.getLogin().getUserObject();
				af = GuiMemory.detachAuffuehrung();
				GuiMemory.attachAuffuehrung(af);
				s = af.getSaal();
				loadSeats();
				prepareTransaktion();
				prepareBelegung();
			}else{
				ta = new Transaktion();
				KategorieKey kakey = new KategorieKey();
				btn_back.setText(lang.getString("BTN_SELECTSEATS_BACK_RES"));
				ta = GuiMemory.detachTransaktion();
				GuiMemory.attachTransaktion(ta);
				if(ta.isVerkauft()){
					btn_reserve.setEnabled(false);
				}
				k = ta.getKunde();
				m = ta.getMitarbeiter();
				af = ta.getBelegung().getAuffuehrung();
				GuiMemory.attachAuffuehrung(af);
				s = af.getSaal();
				//Get Kategorie
				kakey.setBezeichnung(ta.getBelegung().getComp_id().getKategoriebez());
				kakey.setOrt(ta.getOrt().getComp_id().getOrt());
				kakey.setOrtbez(ta.getOrt().getComp_id().getBezeichnung());
				kakey.setSaalbez(s.getComp_id().getBezeichnung());
				ka = DAOFactory.getKategorieDAO().get(kakey);
				loadSeats();
				loadCustomerSeats();
			}
		}
	}
	
	@Override
	public void setTrail(Trail trail) {
		this.trail = trail;
		reloadChanges();
	}
	
	private void loadSeats(){
		int num;
		int aktnum;
		Reihe r;
		
		pn_rows = new JPanel();
		row_panels = new LinkedList<RowPanel>();
		
		log.info("Building Seat Panel...");
		
		GridBagLayout gb_rows = new GridBagLayout();
		pn_rows.setLayout(gb_rows);
		GridBagConstraints c_rows = new GridBagConstraints();
		c_rows.anchor = GridBagConstraints.LINE_START;
 		c_rows.insets = new Insets(5,5,5,5);
 		
 		rows = ka.getReihen();
 		iter = rows.iterator();
 		
 		for(int i = 0; i < rows.size(); i++){
 			RowPanel rp = null; 
 			num = i + 1;
 			niter = rows.iterator();
 			for(int j = 0; j < rows.size(); j++){
 				r = (Reihe)niter.next();
 				aktnum = new Integer(r.getComp_id().getBezeichnung().substring(5));
 				if(aktnum == num){
 					rp = new RowPanel(r);
 				}
 			}
 			c_rows.gridx = 0;
 			c_rows.gridy = i;
 			lb_row = new JLabel();
 			lb_row.setFont((new Font("Arial", Font.BOLD, +20)));
 			
 			lb_row.setText("R" + (i+1));
 			gb_rows.setConstraints(lb_row,c_rows);
 	 		pn_rows.add(lb_row);
 	 		
 	 		c_rows.gridx = 1;
 			lb_row.setText("R" + rp.getRow().getComp_id().getBezeichnung().substring(5));
 			row_panels.add(rp);
 			gb_rows.setConstraints(rp, c_rows);
 			pn_rows.add(rp);
 		}
 		
 		pn_seats.setViewportView(pn_rows);
 		
 		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		sum = new BigDecimal(0);
		// Back
		if(evt.getActionCommand().equals("back")){
			trail.getPrevious(this).setTrail(trail);
			Gui.load((Component)trail.getPrevious(this));
			setVisible(true);
		}
		// Buy
		if(evt.getActionCommand().equals("buy")){
			RowPanel temp = null;
			
			//When Edit then delete old transaktionen
			if(trail.getTrailNumber() ==  5 || trail.getTrailNumber() == 6){
				ta = GuiMemory.detachTransaktion();
				//DAOFactory.getTransaktionDAO().remove(ta);
				deleteTransaktionen(ta);
				ta.setVerkauft(true);
				GuiMemory.attachTransaktion(ta);
			}else{
				ta = GuiMemory.detachTransaktion();
				ta.setVerkauft(true);
				GuiMemory.attachTransaktion(ta);
			}
			
			//Calculate Sum 
			for(int i = 0; i < row_panels.size(); i++){
				temp = row_panels.get(i);
				//Get Sum
				if(temp.getSum() != null){
					sum = sum.add(temp.getSum());
				}
			}
			
			//Check if sum is 0
			if(sum.compareTo(new BigDecimal(0)) == 0){
				JOptionPane.showMessageDialog(this, lang.getString("MSG_SEL_SEATS"), lang.getString("MSG_SEL_SEATS_TITLE"),JOptionPane.INFORMATION_MESSAGE);
			}else{
				//Ask user if really wants to buy
				String[] yesNoOptions = { lang.getString("MSG_OK"), lang.getString("MSG_CANCEL") }; 
				 
				int n = JOptionPane.showOptionDialog( null, 
						  lang.getString("MSG_SUM") + sum + lang.getString("MSG_SUM_W"),               
						  lang.getString("MSG_SUM_TITLE"),           
				          JOptionPane.OK_CANCEL_OPTION, 
				          JOptionPane.INFORMATION_MESSAGE,  // icon 
				          null, yesNoOptions,yesNoOptions[0] );
				
				if(n == JOptionPane.OK_OPTION){
					for(int i = 0; i < row_panels.size(); i++){
						temp = row_panels.get(i);
						//Get Transaktionen
						temp.saveTransaktionen(true);
					}
				}
				
				loadSeats();
			}
		}
		// Reserve
		if(evt.getActionCommand().equals("reserve")){
			RowPanel temp = null;
			reservationNumbers = new ArrayList<Integer>();
			String resNumbersString = new String();
			System.out.println("Trailnumber: " + trail.getTrailNumber());
			//When Edit then delete old transaktionen
			if(trail.getTrailNumber() ==  5 || trail.getTrailNumber() == 6){
				ta = GuiMemory.detachTransaktion();
				//DAOFactory.getTransaktionDAO().remove(ta);
				deleteTransaktionen(ta);
				ta.setVerkauft(false);
				GuiMemory.attachTransaktion(ta);
			}else{
				ta = GuiMemory.detachTransaktion();
				ta.setVerkauft(false);
				GuiMemory.attachTransaktion(ta);
			}
			
			for(int i = 0; i < row_panels.size(); i++){
				temp = row_panels.get(i);
				//Get Transaktionen
				temp.saveTransaktionen(false);
				//Update Reservation Numbers
				if(temp.getReservationNumbers().size() > 0){
					reservationNumbers.addAll(temp.getReservationNumbers());
				}
			}
			resNumbersString = lang.getString("MSG_RES") + "\n";
			for(int j = 0; j < reservationNumbers.size(); j++){
				resNumbersString += reservationNumbers.get(j) + "\n";
			}
			if(reservationNumbers.size() > 0){
				JOptionPane.showMessageDialog(this, resNumbersString, lang.getString("MSG_RES_TITLE"),JOptionPane.INFORMATION_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(this, lang.getString("MSG_SEL_SEATS"), lang.getString("MSG_SEL_SEATS_TITLE"),JOptionPane.INFORMATION_MESSAGE);
			}	
			loadSeats();
		}
	
	}
	
	public void removeMouseListeners(){
		
		for(int i = 0; i < row_panels.size(); i++){
			RowPanel r = row_panels.get(i);
		    r.removeMouseListeners();
		}
	}
	
	public void prepareTransaktion(){
		log.debug("Preparing Transaktion Object");
		//TransaktionKey
		ta.setComp_id(new TransaktionKey());
		ta.getComp_id().setDatumuhrzeit(new Date());
		ta.getComp_id().setKundennr(k.getKartennr());
		ta.getComp_id().setMitarbeiternr(m.getKartennr());
		//Transaktion
		ta.setKunde(k);
		ta.setMitarbeiter(m);
		ta.setOrt(s.getOrt());
		ta.setPreis(ka.getPreisstd());
		ta.setStorniert(false);
		ta.setZahlart("bar");
		ta.setResnr(10);
		//attach to GuiMemory
		GuiMemory.attachTransaktion(ta);
	}
	
	public void prepareBelegung(){
		log.debug("Preparing Belegungs Object");
		//Createing new Belegung
		Belegung bg = new Belegung();
		//BelegungKey
		bg.setComp_id(new BelegungKey());
		bg.getComp_id().setDatumuhrzeit(af.getComp_id().getDatumuhrzeit());
		bg.getComp_id().setKategoriebez(ka.getComp_id().getBezeichnung());
		bg.getComp_id().setOrt(s.getOrt().getComp_id().getOrt());
		bg.getComp_id().setOrtbez(s.getOrt().getComp_id().getBezeichnung());
		bg.getComp_id().setSaalbez(s.getComp_id().getBezeichnung());
		//Belegung
		bg.setAuffuehrung(af);
		//attach to GuiMemory
		GuiMemory.attachBelegung(bg);
	}

	@Override
	public void dummySearch() {
		DAOFactory.getReiheDAO().getAll();
		DAOFactory.getBelegungDAO().getAll();
	}
	
	public void loadCustomerSeats(){
		for(int i = 0; i < row_panels.size(); i++){
			RowPanel r = row_panels.get(i);
			r.loadCustomerSeats();
		}
	}
	
	public void deleteTransaktionen(Transaktion ta){
		ta.getBelegung().getTransaktionen().remove(ta);
		DAOFactory.getBelegungDAO().update(ta.getBelegung());
		DAOFactory.getTransaktionDAO().remove(ta);
	}
}