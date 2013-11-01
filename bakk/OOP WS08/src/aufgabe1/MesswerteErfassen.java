package aufgabe1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MesswerteErfassen {

	private final int grenzwert;
	private final int obergrenze;
	private final int untergrenze;
	private Messwert wertObjekt;
	private HashMap<Date, Messwert> messwerte = new HashMap<Date, Messwert>();
	List<Date> sortedList;
	
	/**
	 * KONSTRUKTOR
	 * <p>
	 * Erstellt eine Instanz der Messwerterfassungsklasse. Hier werden die grundlegenden Werte
	 * fuer die Messwertobjekte festgelegt.
	 * </p>
	 * @param grenzwert - Grenzwert fuer Ozonwert
	 * @param obergrenze - Obergrenze fuer die Eingabewerte
	 * @param untergrenze - Obergrenze fuer die Eingabewerte
	 */
	public MesswerteErfassen(int grenzwert, int obergrenze, int untergrenze){
		this.grenzwert = grenzwert;
		this.obergrenze = obergrenze;
		this.untergrenze = untergrenze;
	}
	/**
	 * messwertHinzufuegen
	 * <p>
	 * fuegt einen Messwert hinzu
	 * </p>
	 * @param timestamp - Zeitpunkt der Messung
	 * @param wert - Wert der Messung
	 * @return die Meldung, ob das Hinzufuegen erfolgreich war
	 */
	public String messwertHinzufuegen(String timestamp, int wert){
		String meldung = "OK, Wert hinzugefuegt.\n";
		
		//erstellen des Objekts
		if (!erstelleMesswertObject(timestamp, wert)) meldung = "FEHLER, Timestamp nicht korrekt. Formatierung muss sein: dd.MM.yyyy HH:mm:ss\n";
		
		if (meldung.substring(0, 3).equals("OK,")) {
			//BEDINGUNGEN
			if (!isMessungImRahmen(wertObjekt))
				meldung = "FEHLER, Wert nicht im Messrahmen.\n";
			if (!isMessungEinzigartig(wertObjekt))
				meldung = "FEHLER, Messung fuer diesen Zeitpunkt bereits vorhanden.\n";
			if (!isMessungInReihenfolge(wertObjekt))
				meldung = "FEHLER, Es existieren bereits juengere Messdaten.\n";
			
			if (meldung.substring(0, 3).equals("OK,")) {
				//WARNUNGEN
				if (isWertErhoeht(wertObjekt))
					meldung += "ACHTUNG, Messwert ist mehr als doppelt so hoch wie der Grenzwert.\n";
				if (isLetztenBeidenMessungenErhoeht(wertObjekt))
					meldung += "ACHTUNG, die letzten beiden Messwerte haben den Grenzwert ueberschritten.\n";
				if (isGrenzwertverletzungInDerLetztenStunde(wertObjekt))
					meldung += "ACHTUNG, mehr als die Haelfte der innerhalb der letzten Stunde gemessenen Werte uebersteigen den Grenzwert(Bedingung: mind. 5 Messungen).\n";

				messwerte.put(wertObjekt.getZeitpunkt(), wertObjekt);
			}
		}
		return meldung;
	}
	
	/**
	 * erstelleMesswertObject
	 * <p>
	 * erstellt eine Instanz der Messwert Klasse und ueberprueft, ob der angegebene Zeitpunkt korrekt formatiert ist
	 * </p>
	 * @param timestamp - Zeitpunkt der Messung
	 * @param wert - Wert der Messung
	 * @return Boolean, ob das Objekt erstellt werden konnte
	 */
	private boolean erstelleMesswertObject(String timestamp, int wert){
		boolean passed = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		Date zeitpunkt;
		
		try {
			zeitpunkt = new Date(sdf.parse(timestamp).getTime());
			wertObjekt = new Messwert(wert, zeitpunkt);
			passed = true;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return passed;
	}
	
	/**
	 * entferneMesswert
	 * <p>
	 * Loescht die Messung, die anhand des Zeitpunktes identifiziert wird
	 * </p>
	 * @param zeitpunkt - Zeitpunkt der zu entfernenden Messung
	 * @return - Boolean, ob die Messung geloescht werden konnte
	 */
	public boolean entferneMesswert(Date zeitpunkt){
		boolean entfernt = false;
		
		Messwert m = messwerte.remove(zeitpunkt);
		if (m != null) entfernt = true;		
		return entfernt;
	}
	
	/**
	 * getMesswerte
	 * <p>
	 * Liefert die Messwerte des angegebenen Zeitraums zurueck
	 * </p>
	 * @param von - Startpunkt
	 * @param bis - Endpunkt
	 * @return String, der eine Auflistung der gefundenen Messwerte beinhaelt
	 */
	public String getMesswerte(Date von, Date bis){
		String result;
		
		if (bis.after(von)) {
			result = "Gefundene Werte:\n------------------------------------\n";
			Iterator<Date> iterator = getSortiertenIterator();
			while (iterator.hasNext()) {
				Date zeitpunkt = iterator.next();
				if (zeitpunkt.after(von) & zeitpunkt.before(bis)) {
					result += zeitpunkt + " "
							+ messwerte.get(zeitpunkt).getMesswert() + "\n";
				}
			}
			result += "------------------------------------\n";
		}
		else { result = "FEHLER, Endpunkt vor Startpunkt.\n";}
		return result;
	}
	
	/**
	 * getDurchschnitt
	 * <p>
	 * Liefert den Durchschnitt der Messungen des angegebenen Zeitraums zurueck
	 * </p>
	 * @param von - Startpunkt
	 * @param bis - Endpunkt
	 * @return Liefert den Durchschnitt des angegebenen Zeitraums zurueck
	 */
	public String getDurchschnitt(Date von, Date bis){
		int schnitt=0;
		int anzahlGefundeneWerte=0;
		String result;
		
		if (bis.after(von)) {
			Iterator<Date> iterator = getSortiertenIterator();
			while (iterator.hasNext()) {
				Date zeitpunkt = iterator.next();
				if (zeitpunkt.after(von) & zeitpunkt.before(bis)) {
					schnitt += messwerte.get(zeitpunkt).getMesswert();
					anzahlGefundeneWerte++;
				}
			}
			if (anzahlGefundeneWerte > 0) schnitt = schnitt / anzahlGefundeneWerte;
			result =""+schnitt;
		}
		else{ result = "FEHLER, Endpunkt vor Startpunkt.\n";}
		return result;
	}
	
	/**
	 * getSortiertenIterator
	 * <p>
	 * Liefert einen nach der Messzeitpunkten sortierten Iterator
	 * </p>
	 * @return sortierten Iterator
	 */
	private Iterator<Date> getSortiertenIterator(){
		sortedList = new ArrayList<Date>();
		sortedList.addAll(messwerte.keySet());
		Collections.sort(sortedList);
		Iterator<Date> iterator = sortedList.iterator();
		return iterator;
	}
	
	/**
	 * isMessungImRahmen
	 * <p>
	 * Kontrolliert, ob sich der angegebene Messwert innerhalb der angegebenen Grenzen befindet
	 * </p>
	 * @param wertObjekt - Instanz der Messung
	 * @return Boolean, ob der Messwert gueltig ist
	 */
	private boolean isMessungImRahmen(Messwert wertObjekt){
		boolean passed = false;
		
		/*System.out.println("1:" + (wertObjekt.getMesswert() >= untergrenze));
		System.out.println("2:"+ (wertObjekt.getMesswert() <= obergrenze));*/
		if ( wertObjekt.getMesswert() >= untergrenze & wertObjekt.getMesswert() <= obergrenze) passed = true;
		return passed;
	}
	
	/**
	 * isMessungEinzigartig
	 * <p>
	 * Kontrolliert, ob zu dem gegebenen Zeitpunkt bereits ein Messung existiert
	 * </p>
	 * @param wertObjekt - Instanz der Messung
	 * @return Boolean, ob die Messung einzigartig ist
	 */
	private boolean isMessungEinzigartig(Messwert wertObjekt){
		return !messwerte.containsKey(wertObjekt.getZeitpunkt());
	}
	
	/**
	 * isMessungInReihenfolge
	 * <p>
	 * Kontrolliert, ob die Messwerte in aufsteigender Reihenfolge der Zeitpunkte eingegeben werden
	 * </p>
	 * @param wertObjekt - Instanz der Messung
	 * @return Boolean, ob es noch keine neueren Messungen gibt
	 */
	private boolean isMessungInReihenfolge(Messwert wertObjekt){
		boolean passed = false;
		
		getSortiertenIterator();//aktualisiert sortedList ;)
		if (!sortedList.isEmpty()) {
			Date letzterEingetragenerZeitpunkt = sortedList.get(sortedList
					.size() - 1);
			if (wertObjekt.getZeitpunkt().after(letzterEingetragenerZeitpunkt))
				passed = true;
		}
		else passed = true;
		return passed;
	}
	
	/**
	 * isWertErhoeht
	 * <p>
	 * Kontrolliert, ob der Wert groesser als der doppelte Grenzwert ist
	 * </p>
	 * @param wertObjekt - Instanz der Messung
	 * @return Boolean, ob der Wert hoeher ist
	 */
	private boolean isWertErhoeht(Messwert wertObjekt){
		boolean passed = false;
		
		if(wertObjekt.getMesswert()> (grenzwert *2)) passed = true;
		return passed;
	}
	
	/**
	 * isLetzenBeidenMessungenErhoeht
	 * <p>
	 * Kontrolliert, ob die letzen beiden Messungen erhoehte Werte(hoeher als der Grenzwert) hatten
	 * </p>
	 * @param wertObjekt - Instanz der Messung
	 * @return Boolean, ob die Werte erhoeht waren
	 */
	private boolean isLetztenBeidenMessungenErhoeht(Messwert wertObjekt){
		boolean passed = false;
		
		getSortiertenIterator();//aktualisiert sortedList ;)
		if (!sortedList.isEmpty()) {
			Messwert vorlezterWert = messwerte.get(sortedList.get(sortedList.size() - 1));
			if (vorlezterWert.getMesswert() > grenzwert	& wertObjekt.getMesswert() > grenzwert)
				passed = true;
		}
		
		return passed;
	}
	
	/**
	 * isGrenzwertverletzungInDerLetztenStunde
	 * <p>
	 * Kontrolliert, ob mehr als die Haelfte der innerhalb der letzten Stunde gemessenen Werte den Grenzwert uebersteigen,
	 *  vorausgesetzt es hat in diesem Zeitraum mindestens fuenf Messungen gegeben
	 *  </p>
	 * @param wertObjekt - Instanz der Messung
	 * @return Boolean, ob die Grenzwertverletzung aufgetreten ist.
	 */
	private boolean isGrenzwertverletzungInDerLetztenStunde(Messwert wertObjekt){
		boolean passed = false;
		int countMessung=1;
		int countVerletzung=0;
		Date beobachtungsBeginn = new Date();		
		beobachtungsBeginn.setTime(wertObjekt.getZeitpunkt().getTime() - 3600000);
		
		if (wertObjekt.getMesswert()>grenzwert) countVerletzung++;
		sortedList = new ArrayList<Date>();
		sortedList.addAll(messwerte.keySet());
		Collections.sort(sortedList);
		Collections.reverse(sortedList);
		Iterator<Date> iterator = sortedList.iterator();
		while(iterator.hasNext()){
			Date zeitpunkt = iterator.next();
			if (zeitpunkt.after(beobachtungsBeginn)){
				countMessung++;
				if (messwerte.get(zeitpunkt).getMesswert()>grenzwert) countVerletzung++;
			}
			else { break;}
		}
		if( countMessung>4 & (countMessung/2 < countVerletzung)) passed = true;
		return passed;
	}
}