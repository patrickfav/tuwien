package aufgabe1;

import java.util.Date;



public class Messwert {

	private final int wert;
	private final Date zeitpunkt;
		
	/**
	 * KONSTRUKTOR
	 * <p>
	 * Erstellt Instanz der Messwertklasse
	 * </p>
	 * @param wert - Wert der Messung
	 * @param zeitpunkt - Zeitpunkt der Messung
	 */
	public Messwert(int wert, Date zeitpunkt){
		this.wert = wert;
		this.zeitpunkt = zeitpunkt;
	}
	
	/**
	 * getMesswert
	 * <p>
	 * liefert den Messwert zurueck
	 * </p>
	 * @return Messwert der Instanz
	 */
	public int getMesswert(){
		return wert;
	}
	
	/**
	 * getZeitpunkt
	 * <p>
	 * liefert den Zeitpunkt der Messung
	 * </p>
	 * @return Zeitpunkt der Messung
	 */
	public Date getZeitpunkt(){
		return zeitpunkt;
	}
}
