package ticketline.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import ticketline.bl.GuiMemory;
import ticketline.dao.DAOFactory;
import ticketline.dao.interfaces.BelegungDAO;
import ticketline.dao.interfaces.MitarbeiterDAO;
import ticketline.dao.interfaces.OrtDAO;
import ticketline.dao.interfaces.TransaktionDAO;
import ticketline.db.Auffuehrung;
import ticketline.db.Belegung;
import ticketline.db.BelegungKey;
import ticketline.db.Kunde;
import ticketline.db.Reihe;
import ticketline.db.Transaktion;

/**
 * 
 * Row Panel
 * @author ReneN
 * v.1
 *
 */
public class RowPanel extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	private Reihe row;
	private LinkedList<SeatPanel> seats;
	private Auffuehrung af;
	private BelegungKey bk;
	private Belegung bg;
	private BelegungDAO belegungDAO;
	private MitarbeiterDAO mitarbeiterDAO;
	private OrtDAO ortDAO;
	private Set<Transaktion> trs;
	private Set<Belegung> bls;
	private LinkedHashSet<Transaktion> tras;
	private Iterator titer;
	private Transaktion ta;
	private TransaktionDAO transaktionDAO;
	private Kunde k;
	private BigDecimal sum;
	private ArrayList<Integer> reservationNumbers;
	
	public RowPanel(Reihe row){
		this.row = row;
		
		Border row_border = BorderFactory.createLineBorder(Color.BLUE, 2);
		this.setBorder(row_border);
		GridBagLayout gb_seats = new GridBagLayout();
		this.setLayout(gb_seats);
		
		seats = new LinkedList<SeatPanel>(); 
			
		GridBagConstraints c = new GridBagConstraints();
 		c.anchor = GridBagConstraints.LINE_START;
 		c.insets = new Insets(5,5,5,5);
 		c.gridy = 0;
 		
 		//add seats to panel
 		for(int i = 0; i < row.getAnzplaetze(); i++){
 			c.gridx = i;
 			SeatPanel seat = new SeatPanel(0);
 			seat.addMouseListener(this);
 			seat.setNumber(i+1);
 			seats.add(seat);
 			gb_seats.setConstraints(seat, c);
 			this.add(seat);
 		}
 		//Get Kunde from GuiMemory
 		k = GuiMemory.getKunde();
 		
 		ortDAO = DAOFactory.getOrtDAO();
 		mitarbeiterDAO = DAOFactory.getMitarbeiterDAO();
 		
 		//System.out.println(row.getComp_id().getBezeichnung());
 		setRowData();
	}

	@Override
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
	public void mouseEntered(MouseEvent e) {
		SeatPanel src = (SeatPanel) e.getSource();
		if(src.getState() < 1)
			src.setImage("images/markedSeat.png");
		src.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		SeatPanel src = (SeatPanel) e.getSource();
		if(src.getState() < 1)
			src.setImage("images/freeSeat.png");
		src.repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public LinkedList<SeatPanel> getSeats(){
		return seats;
	}
	
	public void setRowData(){
		try{
			//Get Auffuehrung from Gui Memory
			af = GuiMemory.detachAuffuehrung();
			GuiMemory.attachAuffuehrung(af);
			
			//Belegungen zur Auffuehrung
			/*bls = af.getBelegungen();
			for(Belegung b : bls){
				if(b.getReihe().getComp_id().equals(row.getComp_id())){
					bg = b;
				}
			}*/
			
			//Set BelegungKey
			bk = new BelegungKey();
			bk.setDatumuhrzeit(af.getComp_id().getDatumuhrzeit());
			bk.setKategoriebez(row.getKategorie().getComp_id().getBezeichnung());
			bk.setOrt(af.getComp_id().getOrt());
			bk.setOrtbez(af.getComp_id().getOrtbez());
			bk.setReihebez(row.getComp_id().getBezeichnung());
			bk.setSaalbez(row.getComp_id().getSaalbez());
		
			
			//Get Belegung with Key
			belegungDAO = DAOFactory.getBelegungDAO();
			bg = belegungDAO.get(bk);
			
			if(bg == null){
				System.out.println("KEINE Belgung!");
			}
			
			//Get Transaktionen from Belegung
			trs = bg.getTransaktionen();
			titer = trs.iterator();
		
			//Iter over Transaktion to set Seat states
			for(int i = 0 ; i < trs.size(); i++){
				ta = (Transaktion)titer.next();
				System.out.println("Transaktion: " + ta);
				if(!ta.isStorniert()){
					if(ta.isVerkauft()){
						setTransaktionsData(ta.getStartplatz(),ta.getAnzplaetze(),2);
					}else{
						setTransaktionsData(ta.getStartplatz(),ta.getAnzplaetze(),1);
					}
				}
			}
		}catch(Exception e){
			
		}
	}
	
	public void setTransaktionsData(int start, int anzahl, int state){
		int counter = anzahl;
		for(int j = 0; j < seats.size(); j++){
			//is actual seat a start seat
			if((j + 1) == start){
				for(int k = 0; k < anzahl; k++){
					seats.get(j+k).setState(state);
					seats.get(j+k).reload();
					if(state == 3){
						seats.get(j+k).addMouseListener(this);
					}else{
						seats.get(j+k).removeMouseListener(this);
					}
				}
				j = j + anzahl;
			}
		}
	}
	
	public Reihe getRow(){
		return this.row;
	}
	
	public void removeMouseListeners(){
		for(int i = 0; i < seats.size(); i++){
			SeatPanel sp = seats.get(i);
			if(sp.getState()  > 0){
				sp.removeMouseListener(this);
			}
		}
	}
	
	public void saveTransaktionen(boolean sold){
		transaktionDAO = DAOFactory.getTransaktionDAO();
		SeatPanel s;
		boolean selected = false;
		int number = 0;
		int start = 0;
		
		sum = new BigDecimal(0);
		reservationNumbers = new ArrayList<Integer>();
		
		//Create new Transaktion Object from existing
		Transaktion tgm = GuiMemory.detachTransaktion();
		GuiMemory.attachTransaktion(tgm);
		Transaktion tra = tgm.clone();
		
		//Iterate over Seats of actual Row
		for(int i = 0; i < seats.size(); i++){
			s = seats.get(i);
			//Check if seat before is marked
			if(selected){
				//Check if seat is marked
				if(s.getState() == 3){
					number++;
				}else{
					//End of Transaktion => add to list
					tra.setBelegung(getBelegung(sold));
					tra.setAnzplaetze(number);
					tra.setStartplatz(start);
					tra.getComp_id().setDatumuhrzeit(new Timestamp(new Date().getTime()));
					tra.setResnr(DAOFactory.getTransaktionDAO().getNextResNr());
					reservationNumbers.add(tra.getResnr());
					transaktionDAO.save(tra);
					
					//Update Belegung 
					tra.getBelegung().initSet();
					tra.getBelegung().getTransaktionen().add(tra);
					belegungDAO.update(tra.getBelegung());
					
					//Update Kunde
					//tra.getKunde().initSet();
					//tra.getKunde().getTransaktionen().add(tra);
					//DAOFactory.getKundeDAO().update(tra.getKunde());
					
					//Reset values
					tra = tgm.clone();
					selected = false;
					number = 0;
					start = 0;
				}		
			}else{
				//Check if seat is marked
				if(s.getState() == 3){
					start = i + 1;
					number++;
					selected = true;
				}
			}
		}
		//Check if selection goes to last seat
		if(selected){
			//End of Transaktion => add to list
			tra.setBelegung(getBelegung(sold));
			tra.setAnzplaetze(number);
			tra.setStartplatz(start);
			tra.getComp_id().setDatumuhrzeit(new Timestamp(new Date().getTime()));
			tra.setResnr(DAOFactory.getTransaktionDAO().getNextResNr());
			reservationNumbers.add(tra.getResnr());
			transaktionDAO.save(tra);
			
			//Belegung aktualisieren
			tra.getBelegung().initSet();
			tra.getBelegung().getTransaktionen().add(tra);
			belegungDAO.update(tra.getBelegung());
		}
	}
	
	public Belegung getBelegung(boolean sold){
		int freeSeats = 0;
		int soldSeats = 0;
		int reservedSeats = 0;
		String belegungString = "";
		
		SeatPanel s;
		
		Belegung bgl = GuiMemory.detachBelegung();
		GuiMemory.attachBelegung(bgl);
		
		for(int i = 0; i < seats.size(); i++){
			s = seats.get(i);
			if(s.getState() == 0){
				freeSeats++;
				belegungString += "F";
			}
			if(s.getState() == 1){
				reservedSeats++;
				belegungString += "R";
			}
			if(s.getState() == 2){
				soldSeats++;
				belegungString += "V";
			}
			if(s.getState() == 3){
				if(sold){
					soldSeats++;
					belegungString += "V";
				}else{
					reservedSeats++;
					belegungString += "R";
				}
			}
		}
		
		//Update Belegung
		if(bg == null){
			bgl.setAnzfrei(freeSeats);
			bgl.setAnzres(reservedSeats);
			bgl.setAnzverk(soldSeats);
			bgl.setBelegung(belegungString);
			bgl.setReihe(row);
			bgl.getComp_id().setReihebez(row.getComp_id().getBezeichnung());
		
			return bgl;
		}else{
			bg.setAnzfrei(freeSeats);
			bg.setAnzres(reservedSeats);
			bg.setAnzverk(soldSeats);
			bg.setBelegung(belegungString);
			belegungDAO.update(bg);
			System.out.println("Taking Belgung from DB");
			return bg;
		}
	}
	
	public BigDecimal getSum(){
		SeatPanel s;
		sum = new BigDecimal(0);
		
		Transaktion tgm = GuiMemory.detachTransaktion();
		GuiMemory.attachTransaktion(tgm);
		
		//Iterate over Seats of actual Row
		for(int i = 0; i < seats.size(); i++){
			s = seats.get(i);
			if(s.getState() == 3){
				sum = sum.add(tgm.getPreis());
			}
		}
		
		return sum;
	}
	
	public ArrayList<Integer> getReservationNumbers(){
		return this.reservationNumbers;
	}
	
	public void loadCustomerSeats(){
		Transaktion temp_trans = GuiMemory.detachTransaktion();
		GuiMemory.attachTransaktion(temp_trans);
		if(temp_trans.getBelegung().getReihe().equals(row)){
			if(temp_trans.getKunde().equals(GuiMemory.getKunde())){
				System.out.println("Loading Customer Data...");
				setTransaktionsData(temp_trans.getStartplatz(),temp_trans.getAnzplaetze(),3);
			}
		}
	}
}
