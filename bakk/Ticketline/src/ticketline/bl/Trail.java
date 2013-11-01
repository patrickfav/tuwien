package ticketline.bl;

import java.awt.Component;
import java.util.LinkedList;
import java.util.ListIterator;

import ticketline.gui.AuffuehrungenSuchenGui;
import ticketline.gui.KategorieWaehlenGui;
import ticketline.gui.KundeAendernGui;
import ticketline.gui.KundeSuchenGui;
import ticketline.gui.OrtSuchenGui;
import ticketline.gui.ReservierungTicketsSuchenBearbeitenGui;
import ticketline.gui.SaalSuchenGui;
import ticketline.gui.SitzplatzWaehlenGui;
import ticketline.gui.TopTenAnzeigenGui;
import ticketline.gui.VeranstaltungSuchenGui;
import ticketline.gui.WerbematerialWaehlen;

/**
 * This class defines the trail through a
 * selected trail. So it is possible to get the
 * next and the previous panel.
 * 
 * @author ReneN, PatrickF
 * @version 0.2
 *
 */
public class Trail {
	private int trailnumber;
	private LinkedList<ITrail> trail;
	private ListIterator<ITrail> iterator;
	
	/**
	 * Constructor to set the wanted path
	 * @param trailnumber path number
	 */
	public Trail(int trailnumber){
		
		this.trailnumber = trailnumber;
		trail = new LinkedList<ITrail>();
		switch(trailnumber){
		case 1://Kunde suchen + Durchführen
			trail.add(new KundeSuchenGui());
			trail.add(new AuffuehrungenSuchenGui());
			trail.add(new KategorieWaehlenGui());
			trail.add(new SitzplatzWaehlenGui());
			break;
		case 2://Subtrail(Durchführen) Saal suchen 
			trail.add(new SaalSuchenGui());
			break;
		case 3://Subtrail(Durchführen) Ort suchen
			trail.add(new OrtSuchenGui());
			break;
		case 4://Subtrail(Durchführen) Event suchen
			trail.add(new VeranstaltungSuchenGui());
			break;
		case 5://Reservierung Bearbeiten
			trail.add(new SitzplatzWaehlenGui());
			break;
		case 6://ReservierungTicketsBearbeiten
			trail.add(new ReservierungTicketsSuchenBearbeitenGui());
			trail.add(new SitzplatzWaehlenGui());
			break;
		case 7://Subtrail KundeBearbeiten
			trail.add(new KundeAendernGui());
			break;
		case 8://Werbematerial
			//trail.add(new KundeSuchenGui());
			trail.add(new WerbematerialWaehlen());
			break;
		case 9://Main path "Durchführen" (without "KundeSuchen")
			trail.add(new AuffuehrungenSuchenGui());
			trail.add(new KategorieWaehlenGui());
			trail.add(new SitzplatzWaehlenGui());
			break;
		case 10://Main path TopTen
			trail.add(new TopTenAnzeigenGui());
			trail.add(new AuffuehrungenSuchenGui());
			trail.add(new KategorieWaehlenGui());
			trail.add(new SitzplatzWaehlenGui());
			break;
		}
		
		//makes a time delay, but search in the guis gets faster
		executeDummySearch();
		
		iterator = trail.listIterator();
	}
	
	/**
	 * Method for getting the previous Panel
	 * @param actual panel
	 * @return previous panel
	 */
	public ITrail getPrevious(Component actual){
		iterator = trail.listIterator();
		for(int i = 0; i != trail.indexOf(actual);i++){
			iterator.next();
		}
		iterator.previous().reloadChanges();
		iterator.next();
		return iterator.previous();
	}
	
	/**
	 * Method for getting the next Panel
	 * @param actual panel
	 * @return next panel
	 */
	public ITrail getNext(Component actual){
		iterator = trail.listIterator();
		for(int i = 0; i != trail.indexOf(actual);i++){
			iterator.next();
		}
		iterator.next();
		iterator.next().reloadChanges();
		iterator.previous();
		return iterator.next();
	}
	
	/**
	 * Method for getting the first Component of the trail
	 * @return first component
	 */
	public Component getFirstComponent(){
		return (Component)trail.getFirst();
	}
	
	/**
	 * Method for getting the first Panel of the trail
	 * @return first panel
	 */
	public ITrail getFirstTrail(){
		return trail.getFirst();
	}
	
	/**
	 * Method for getting the actual trail number
	 * @return trail number
	 */
	public int getTrailNumber(){
		return this.trailnumber;
	}
	
	/**
	 * Method for adding a panel at the beginning and the end of the trail
	 * @param panel to add
	 */
	public void addNode(ITrail node){
		trail.addFirst(node);
		trail.addLast(node);
	}
	
	/**
	 * Method for executing the dummy Search for all panels in the actual trail
	 */
	public void executeDummySearch(){
		iterator = trail.listIterator();
		while(iterator.hasNext()){
			iterator.next().dummySearch();
		}
	}
}
