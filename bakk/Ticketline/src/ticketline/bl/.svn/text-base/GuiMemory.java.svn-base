package ticketline.bl;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import ticketline.db.Artikel;
import ticketline.db.Auffuehrung;
import ticketline.db.Belegung;
import ticketline.db.Kategorie;
import ticketline.db.Kuenstler;
import ticketline.db.Kunde;
import ticketline.db.Ort;
import ticketline.db.Saal;
import ticketline.db.Transaktion;
import ticketline.db.Veranstaltung;

/**
 * A static container for carrying various entities (see db.Kunde,etc)
 * throughout different panels of the gui. Every distinct entity only exits
 * once in the Container.
 * 
 * @author PatrickF, ReneN
 * @version 0.5
 */
public class GuiMemory {
	
	private static Login login = null;
	private static Transaktion transaction = null;
	private static Kunde cost = null;
	private static Saal hall = null;
	private static Ort location = null;
	private static Veranstaltung event = null;
	private static Auffuehrung act = null;
	private static Kategorie kat = null;
	private static Belegung bg = null;
	private static Kuenstler perf = null;
	private static String signal = null;
	
	private static ArrayList<String> actionListenerDescr = new ArrayList<String>();
	private static ArrayList<ActionListener> actionListenerArray = new ArrayList<ActionListener>();
	
	/*
	 *  GENERAL
	 */
	
	/**
	 * Clears all class var except for the login!
	 * Don't forget to update this!
	 * 
	 */
	public static void clear() {
		transaction = null;
		cost = null;
		hall = null;
		location = null;
		event = null;
		act = null;
		kat = null;
		bg = null;
		perf = null;
		signal = null;
		
		actionListenerDescr = new ArrayList<String>();
		actionListenerArray = new ArrayList<ActionListener>();
	}
	
	/*
	 *  LOGIN
	 */
	/**
	 *  Saves a login, cannot overwrite existing one!
	 *  
	 *  @param bl.login object
	 *  @pre class var is empty
	 *  @post class memeber is assigned
	 */
	public static void saveLogin(Login l) {
		if(GuiMemory.login == null)
			GuiMemory.login = l;
	}
	/**
	 * Simple login getter
	 * @return login object
	 */
	public static Login getLogin() {
		return GuiMemory.login;
	}
	/**
	 * Deletes the existing login and all info in the memory
	 * @post login class var is null
	 */
	public static void logout() {
		GuiMemory.login = null;
		clear();
	}
	
	/*
	 *  TRANSAKTION
	 */
	
	/**
	 *  Attaches (no matter what) the given Entity to the Memory
	 *  @param given entity obj
	 *  @post class variable is set
	 */
	public static void attachTransaktion(Transaktion obj) {
		GuiMemory.transaction = obj;
	}
	
	/**
	 * Checks if the obj is not null
	 * @return
	 */
	public static boolean isTransaktionSet() {
		if(GuiMemory.transaction == null)
			return false;
		
		return true;
	}
	
	/**
	 * Convinient mehtod for updating the attached class member
	 * Compares the param with the class var and updates all null variables
	 * 
	 * @param obj
	 * @post the best (most content) out of the 2 object are added together
	 */
	public static void updateTransaktion(Transaktion obj) {
		if(obj != null) {
			
			if(GuiMemory.transaction == null) {
				GuiMemory.transaction = obj;
			} else {
				if(transaction.getKunde() == null && obj.getKunde() != null) {
					GuiMemory.transaction.setKunde(obj.getKunde());
				}
				if(transaction.getAnzplaetze() == null && obj.getAnzplaetze() != null) {
					GuiMemory.transaction.setAnzplaetze(obj.getAnzplaetze());
				}
				if(transaction.getBelegung() == null && obj.getBelegung() != null) {
					GuiMemory.transaction.setBelegung(obj.getBelegung());
				}
				if(transaction.getComp_id() == null && obj.getComp_id() != null) {
					GuiMemory.transaction.setComp_id(obj.getComp_id());
				}
				if(transaction.getMitarbeiter() == null && obj.getMitarbeiter() != null) {
					GuiMemory.transaction.setMitarbeiter(obj.getMitarbeiter());
				}
				if(transaction.getOrt() == null && obj.getOrt() != null) {
					GuiMemory.transaction.setOrt(obj.getOrt());
				}
				if(transaction.getPreis() == null && obj.getPreis() != null) {
					GuiMemory.transaction.setPreis(obj.getPreis());
				}
				if(transaction.getResnr() == null && obj.getResnr() != null) {
					GuiMemory.transaction.setResnr(obj.getResnr());
				}
				if(transaction.getStartplatz() == null && obj.getStartplatz() != null) {
					GuiMemory.transaction.setStartplatz(obj.getStartplatz());
				}
				if(transaction.getZahlart() == null && obj.getZahlart() != null) {
					GuiMemory.transaction.setZahlart(obj.getZahlart());
				}
			}
		}
	}
	
	/**
	 * Non consuming getter
	 * @return Entity obj
	 */
	public Transaktion getTransaktion() {
		return GuiMemory.transaction;
	}
	
	/**
	 * Consumingly retrieves the attached Entity. 
	 * 
	 * @return Entity object
	 * @pre Entity class var must be set
	 * @post Enitity is returned and class var deleted
	 */
	public static Transaktion detachTransaktion() {
		Transaktion temp = GuiMemory.transaction;
		GuiMemory.transaction = null;
		
		return temp;
	}
	
	/*
	 *  KUNDE
	 */
	
	/**
	 *  Attaches (no matter what) the given Entity to the Memory
	 *  @param given entity obj
	 *  @post class variable is set
	 */
	public static void attachKunde(Kunde obj) {
		GuiMemory.cost = obj;
	}
	
	/**
	 * Checks if the obj is not null
	 * @return
	 */
	public static boolean isKundeSet() {
		if(GuiMemory.cost == null)
			return false;
		
		return true;
	}
	
	/**
	 * Non consuming getter
	 * @return Entity obj
	 */
	public static Kunde getKunde() {
		return GuiMemory.cost;
	}
	
	/**
	 * Consumingly retrieves the attached Entity. 
	 * 
	 * @return Entity object
	 * @pre Entity class var must be set
	 * @post Enitity is returned and class var deleted
	 */
	public static Kunde detachKunde() {
		Kunde temp = GuiMemory.cost;
		GuiMemory.cost = null;
		
		return temp;
	}
	
	/*
	 *  SAAL
	 */
	
	/**
	 *  Attaches (no matter what) the given Entity to the Memory
	 *  @param given entity obj
	 *  @post class variable is set
	 */
	public static void attachSaal(Saal obj) {
		GuiMemory.hall = obj;
		//if(GuiMemory.room != null)
		//System.out.println("GuiMemory:attachSaal:"+GuiMemory.room.toString()+" (param:"+obj.toString()+")");
	}
	
	/**
	 * Checks if the saal is not null
	 * @return
	 */
	public static boolean isSaalSet() {
		if(GuiMemory.hall == null)
			return false;
		
		return true;
	}
	
	/**
	 * Non consuming getter
	 * @return Entity obj
	 */
	public static Saal getSaal() {
		return GuiMemory.hall;
	}
	
	/**
	 * Consumingly retrieves the attached Entity. 
	 * 
	 * @return Entity object
	 * @pre Entity class var must be set
	 * @post Enitity is returned and class var deleted
	 */
	public static Saal detachSaal() {
		Saal temp = GuiMemory.hall;
		GuiMemory.hall = null;
		//if(GuiMemory.room != null)
			//System.out.println("GuiMemory:attachSaal:"+temp.toString()+" (classvar: "+GuiMemory.room.toString()+" )");
		return temp;
	}
	
	/*
	 * Kuenstler
	 */
	
	public static void attachKuenstler(Kuenstler obj) {
		GuiMemory.perf = obj;
		//if(GuiMemory.room != null)
		//System.out.println("GuiMemory:attachSaal:"+GuiMemory.room.toString()+" (param:"+obj.toString()+")");
	}
	
	/**
	 * Checks if the kuenstler is not null
	 * @return
	 */
	public static boolean isKuenstlerSet() {
		if(GuiMemory.perf == null)
			return false;
		
		return true;
	}
	
	/**
	 * Non consuming getter
	 * @return Entity obj
	 */
	public static Kuenstler getKuenstler() {
		return GuiMemory.perf;
	}
	
	/**
	 * Consumingly retrieves the attached Entity. 
	 * 
	 * @return Entity object
	 * @pre Entity class var must be set
	 * @post Enitity is returned and class var deleted
	 */
	public static Kuenstler detachKuenstler() {
		Kuenstler temp = GuiMemory.perf;
		GuiMemory.perf = null;
		//if(GuiMemory.room != null)
			//System.out.println("GuiMemory:attachSaal:"+temp.toString()+" (classvar: "+GuiMemory.room.toString()+" )");
		return temp;
	}
	
	/*
	 *  ORT
	 */
	
	/**
	 *  Attaches (no matter what) the given Entity to the Memory
	 *  @param given entity obj
	 *  @post class variable is set
	 */
	public static void attachOrt(Ort obj) {
		GuiMemory.location = obj;
	}
	
	/**
	 * Checks if the obj is not null
	 * @return
	 */
	public static boolean isOrtSet() {
		if(GuiMemory.location == null)
			return false;
		
		return true;
	}
	
	/**
	 * Non consuming getter
	 * @return Entity obj
	 */
	public static Ort getOrt() {
		return GuiMemory.location;
	}
	
	/**
	 * Consumingly retrieves the attached Entity. 
	 * 
	 * @return Entity object
	 * @pre Entity class var must be set
	 * @post Enitity is returned and class var deleted
	 */
	public static Ort detachOrt() {
		Ort temp = GuiMemory.location;
		GuiMemory.location = null;
		
		return temp;
	}
	
	/*
	 *  VERANSTALTUNG
	 */
	
	/**
	 *  Attaches (no matter what) the given Entity to the Memory
	 *  @param given entity obj
	 *  @post class variable is set
	 */
	public static void attachVeranstaltung(Veranstaltung obj) {
		GuiMemory.event = obj;
	}
	
	/**
	 * Checks if the obj is not null
	 * @return
	 */
	public static boolean isVeranstaltungSet() {
		if(GuiMemory.event == null)
			return false;
		
		return true;
	}
	
	/**
	 * Non consuming getter
	 * @return Entity obj
	 */
	public static Veranstaltung getVeranstaltung() {
		return GuiMemory.event;
	}
	
	/**
	 * Consumingly retrieves the attached Entity. 
	 * 
	 * @return Entity object
	 * @pre Entity class var must be set
	 * @post Enitity is returned and class var deleted
	 */
	public static Veranstaltung detachVeranstaltung() {
		Veranstaltung temp = GuiMemory.event;
		GuiMemory.event = null;
		
		return temp;
	}
	
	/*
	 *  AUFFUEHRUNG
	 */
	
	/**
	 *  Attaches (no matter what) the given Entity to the Memory
	 *  @param given entity obj
	 *  @post class variable is set
	 */
	public static void attachAuffuehrung(Auffuehrung obj) {
		GuiMemory.act = obj;
	}
	
	/**
	 * Checks if the obj is not null
	 * @return
	 */
	public static boolean isAuffuehrungSet() {
		if(GuiMemory.act == null)
			return false;
		
		return true;
	}
	
	/**
	 * Non consuming getter
	 * @return Entity obj
	 */
	public static Auffuehrung getAuffuehrung() {
		return GuiMemory.act;
	}
	
	/**
	 * Consumingly retrieves the attached Entity. 
	 * 
	 * @return Entity object
	 * @pre Entity class var must be set
	 * @post Enitity is returned and class var deleted
	 */
	public static Auffuehrung detachAuffuehrung() {
		Auffuehrung temp = GuiMemory.act;
		GuiMemory.act = null;
		
		return temp;
	}
	
	/*
	 *  Kategorie
	 */
	
	/**
	 *  Attaches (no matter what) the given Entity to the Memory
	 *  @param given entity obj
	 *  @post class variable is set
	 */
	public static void attachKategorie(Kategorie obj) {
		GuiMemory.kat = obj;
	}
	
	/**
	 * Checks if the obj is not null
	 * @return
	 */
	public static boolean isKategorieSet() {
		if(GuiMemory.kat == null)
			return false;
		
		return true;
	}
	
	/**
	 * Non consuming getter
	 * @return Entity obj
	 */
	public static Kategorie getKategorie() {
		return GuiMemory.kat;
	}
	
	/**
	 * Consumingly retrieves the attached Entity. 
	 * 
	 * @return Entity object
	 * @pre Entity class var must be set
	 * @post Enitity is returned and class var deleted
	 */
	public static Kategorie detachKategorie() {
		Kategorie temp = GuiMemory.kat;
		GuiMemory.kat = null;
		
		return temp;
	}
	
	
	/*
	 * ACTION LISTENER
	 */
	
	/**
	 * Saves an Actionlistener. The AL is identified by its key String.
	 * @param String key to identify, ActionListener to save
	 */
	public static void addActionListener(String key, ActionListener al) {
		actionListenerDescr.add(key);
		actionListenerArray.add(al);
	}
	
	/**
	 * Searches in the intern List for the key and returns
	 * the first found actionlistener. If nothing found ->
	 * returns null.
	 * 
	 * @param key
	 * @return found action listener
	 */
	public static ActionListener getActionListener(String key) {
		int i;
		
		for(i=0;i<actionListenerDescr.size();i++) {
			if(actionListenerDescr.get(i).equals(key))
				return actionListenerArray.get(i);
		}
		return null;
	}
	
	/**
	 *  Attaches (no matter what) the given Entity to the Memory
	 *  @param given entity obj
	 *  @post class variable is set
	 */
	public static void attachBelegung(Belegung obj) {
		GuiMemory.bg = obj;
	}
	
	/**
	 * Consumingly retrieves the attached Entity. 
	 * 
	 * @return Entity object
	 * @pre Entity class var must be set
	 * @post Enitity is returned and class var deleted
	 */
	public static Belegung detachBelegung() {
		Belegung temp = GuiMemory.bg;
		GuiMemory.bg = null;
		
		return temp;
	}
	
	/*
	 *  SIGNAL
	 */
	
	/**
	 *  Attaches (no matter what) the given signal to the Memory
	 *  @param given entity signal
	 *  @post class variable is set
	 */
	public static void attachSignal(String sig) {
		GuiMemory.signal = sig;
	}
	
	/**
	 * Checks if the signal is not null
	 * @return
	 */
	public static boolean isSignalSet() {
		if(GuiMemory.signal == null)
			return false;
		
		return true;
	}
	
	/**
	 * Non consuming getter
	 * @return signal
	 */
	public static String getSignal() {
		return GuiMemory.signal;
	}
	
	/**
	 * Consumingly retrieves the attached Signal. 
	 * 
	 * @return Entity object
	 * @pre Entity class var must be set
	 * @post Enitity is returned and class var deleted
	 */
	public static String detachSignal() {
		String temp = GuiMemory.signal;
		GuiMemory.signal = null;
		
		return temp;
	}
}
