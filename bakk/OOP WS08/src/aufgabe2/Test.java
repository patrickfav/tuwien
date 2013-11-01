package aufgabe2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *	== Aufgabe 2 Allgemeines Konzept ==
 *
 * 	= Record =
 * Die bin Klasse Record speichert einen Messwert (zB. Temperatur 15 C). Es wird keine Einheit gespeichert
 * da in unserem Programm alle Werte zuerst in ihrer basis einheiten umgewandelt werden bevor sie gespeichert
 * werden. Dies hat den Vorteil, dass Statistik Funktionen schneller sind (da nicht jeder wert umgewandelt 
 * werden muss bevor man ihn vergleicht)und die Daten auch ohne Programm konsistenz haben.
 *
 * 	= RecordsFolder =
 * Der RecordsFolder ist eine Sammlung von Records. Es werden Datum, Station und weiteres gespeichert
 * inkl. einer Hashmap mit allen Records Objekten (Schluessel Parameter-Name als String). 
 * Gedanke: Eine Station schickt einen gesamten RecordsFolder der alle Messungen von dieser Station zu dieser Zeit enthaelt.
 * Deshalb werden eben Zeit und Staion nur im RecordsFolder gespeichert und nicht in den Records.
 * 
 * 	= Station =
 * Die Station Klasse ist eine einfache bin klasse die die daten einer station speichert sowie den 
 * id-iterator und das passwort.
 * 
 * 	= Parameter =
 * Ein Parameter ist ein Messtyp (zB Temperatur). Die Klasse Parameter ist eine bin Klasse die die Daten
 * zu einem Paramter speichert. Weiteres werden die Grenzwerte und das Basis-Einheitensymbol (Schluessel
 * f. die Basiseinheit) gespeichert.
 * 
 * 	= Unit =
 * Unit ist die "vater" bin klasse der eiheiten. Als unit werden alle basis einheiten gespeichert. 
 * Die Unit Klasse speichert das Symbol (schluessel der Einheiten), Beschreibung und den zugehoerigen 
 * Parameter.
 * 
 * 	= DerivedUnit =
 * Erbt von Unit. Speichert eine derived/erweiterte Einheit. Dazu ist zusaetzlich ein factor und offset gespeichert.
 * Dies beschreibt den Weg zu dem Wert der Basiseinheit. (D.h. wenn man den wert mit factor multipliziert und mit 
 * offset addiert bekommt man den Wert der Basiseinheit) Das muss natuerlich beim speichern einer Einheite
 * gegeben sein. Mit noch zusaetzlich der Regel "offsetfirst" bestimmt man die Reihenfolge der 2 "Funktionen"
 * So kann man etwa 80% der ueblichen Einheiten umrechnen.
 * 
 * 	= Units =
 * Ist die Verwaltungsklasse der Einheiten. Da die Einheiten ganz unabhaengig von den Messungen existieren ist 
 * dies in einer eigenen Klasse untergebracht. Die Einheiten werden seperat in 2 Hashmaps gespeichert
 * (Basiseinheiten und Erweiterte Einheiten). Enthaelt die wichtigsten Methoden zu den Einheiten (fetch,
 * convert,add,delete,etc.)
 * 
 * 	= RecordsManagement =
 * Die allgemein Verwaltungsklasse der wichtigsten Teile des Programms. Hier laufen die Stations, RecordFolder,
 * Parameter, Units zusammen. Die jeweiligen Objekte werden in Hashmaps gespeichert. Besonders zu erwaehnen,
 * die RecordsFolder die einen doppelten Key haben: Station und Datum; und werden daher auch einer 
 * doppelten Hashmap gespeichert. Hier sind auch alle essentiellen Methoden zu den Paramtern, Stations und
 * RecordsFolder, sowie die gesamte "mach-messwert", "ueberpruefe-messwert", "gib-messwert-aus" Methoden.
 * 
 * 	= Authorisation =
 * Die "login" Klasse. Speichert eine Hasmap mit tickets. Nach einem erfolgreichem login mit "doLogin();"
 * bekommt der user ein ticket mit dem er sich spaeter als valider admin authetifizieren kann.
 * 
 *	= AdminRecordsManagement =
 * Ist eine geerbte Klasse von RecordsManagement (kein subtyping, eher code-reusing und uebersicht des codes erhalten)
 * In dieser Klasse sind alle Methoden der RecordsManagement ueberschrieben mit der selben Methode, nur 
 * das diese Klasse ein ticket als parameter erwartet, das gecheckt wird, bevor die Methode ausgefuehrt
 * werden kann. (Der Konstruktur bekommt DIE Authorisation Instanz uebergeben - ist noetig damit die tickets
 * im gesamten programm gleich sind.). Ein weiterer Grund fuer diese Klasse ist: Wir wollten keine
 * "hard-gecodeden" admin checks in der RecordsManagement haben, damit Sie fuer andere user-losen 
 * automatischen Programme weiterverwendet werden kann. Zusaetzlich (und das finden wird nicht so elegant)
 * haben wir ein bisschen logik "reingehaut" fuer die Speicherung des gesamten Objekts als File. Warum hier?
 * Damit auch die User Daten gespeichert werden koennen (mit den Hashmaps zu Parameter, etc).
 * 
 * = BackupThread,ClassManager =
 * Methoden fuer die Serialisierung der Objekte zur Speicherung und abrufung von einer Datei. Speichert 
 * das gesamte "AdminRecordsManagement" Objekt in einer Datei und kann es wieder laden. Einfach und 
 * praktisch.
 * 
 * = Was wir nicht implementiert haben =
 * Das Protokoll. Da es sehr aufwaendig waere es so umzusetzen wie in der spezifikation vorgegben. Und eine
 * halbherzige Loesung die wahrscheinlich beim naechsten Bsp sowieso ueber Bord geworfen waere wollten wir nicht.
 * 
 * Sonst denke ich haben wir sicher 85% der Spezifikation integriert.
 * 
 */

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		 * Initialize Management and Units
		 */
		ClassManager cm = new ClassManager();
		AdminRecordsManagement adminManagement = null;
		String ticket = null;
		Units unit_management = null;
		adminManagement = (AdminRecordsManagement) cm.getObjectFromFile("AdminRecordsManagement");
		if (adminManagement == null){
			Authorisation login = new Authorisation();
			unit_management = new Units();
			adminManagement = new AdminRecordsManagement(unit_management, login);		
		}
		else{
			unit_management = adminManagement.getUnitManagement();
		}
		ticket = adminManagement.doLogin("admin", "secret");
		adminManagement.startProgram();
		//RecordsManagement management = new RecordsManagement();
		
		/*
		 * Tests if the user is logged in
		 */
		System.out.println("logged in as admin: "+adminManagement.checkLogin(ticket));
		
		/*
		 * Test Units
		 */
		unit_management.addBaseUnit("Standard Temperatureinheit", "C", "Temperatur");
		unit_management.addDerivedUnit("Amerikanischer Standard", "F", "Temperatur", -32.00F, 0.555555555555F, true);
		unit_management.addDerivedUnit("SI Einheit", "K", "Temperatur", 273.15F, 1.0F, true);
		
		unit_management.addBaseUnit("relative Luftfeuchtigkeit", "%", "Luftfeuchtigkeit");
		
		adminManagement.addParameter("Temperatur", 99, -99, -20, "C", ticket);
		adminManagement.addParameter("Luftfeuchtigkeit", 99, 0, 5, "%", ticket);
		adminManagement.addParameter("Luftdruck", 99, 0, 5, "bar", ticket);
		adminManagement.addParameter("Ozongehalt", 99, 0, 5, "mm3", ticket);
		
		System.out.println(adminManagement.getBaseUnitSymbolByParamter("Temperatur", ticket));
		System.out.println(adminManagement.getBaseUnitSymbolByParamter("Luftfeuchtigkeit", ticket));
		
		System.out.println(unit_management.getBaseUnitValue("F", 76.0F));
		System.out.println(unit_management.getBaseUnitValue("K", 177.0F));
		System.out.println(unit_management.getBaseUnitValue("C", 21.45F));
		System.out.println(unit_management.getBaseUnitValue("X", 21.45F)); //smybol not existant
		
		System.out.println(unit_management.getParameterBySymbol("F"));
		System.out.println(unit_management.getParameterBySymbol("K"));
		System.out.println(unit_management.getParameterBySymbol("C"));
		System.out.println(unit_management.getParameterBySymbol("%"));
		System.out.println(unit_management.getParameterBySymbol("X")); //smybol not existant
		
		ArrayList<String> allpars = adminManagement.fetchParameters();
		System.out.println(allpars.toString());
		
		/*
		 * Loeschen
		 */
		
		if(adminManagement.deleteParameter("Ozongehalt"))
			System.out.println("Ozongehalt wurde geloescht.");
		if(adminManagement.deleteParameter("Regenbogendichte"))
			System.out.println("Regenbogendichte wurde geloescht.");
		
		allpars = adminManagement.fetchParameters();
		System.out.println(allpars.toString());
		
		ArrayList<String> allunits = unit_management.fetchAllUnits();
		System.out.println(allunits.toString());
		
		/*
		 * Loeschen
		 */
		
		if(unit_management.deleteUnit("F"))
			System.out.println("F wurde geloescht.");
		
		if(unit_management.deleteUnit("X"))
			System.out.println("X wurde geloescht.");
		
		allunits = unit_management.fetchAllUnits();
		System.out.println(allunits.toString());
		
		
		/*
		 * testing dumping method
		 */
		System.out.println("AdminrecordsManagement-Instanz:"+adminManagement.toString());
		System.out.println("AdminrecordsManagement-Instanz saved: "+cm.addObjectToFile("AdminRecordsManagement", adminManagement));
		System.out.println("AdminrecordsManagement-Instanz deleted.");
		adminManagement.stopProgram();
		adminManagement =null;
		System.out.println("AdminrecordsManagement-Instanz loaded.");
		adminManagement = (AdminRecordsManagement) cm.getObjectFromFile("AdminRecordsManagement");
		adminManagement.startProgram();
		System.out.println("AdminrecordsManagement-Instanz:"+adminManagement.toString());
		
		
		/*
		 * check min/max/threshold
		 */
		System.out.println(adminManagement.checkIfOverMaxValue("Temperatur", 105.0F));//true
		System.out.println(adminManagement.checkIfOverMaxValue("Temperatur", 44.89F));//false
		System.out.println(adminManagement.checkIfUnderMinValue("Temperatur", -105.0F));//true
		System.out.println(adminManagement.checkIfUnderMinValue("Temperatur", 43.0F));//false
		System.out.println(adminManagement.checkIfOverThreshold("Temperatur", -144.0F));//false
		System.out.println(adminManagement.checkIfOverThreshold("Temperatur", 15.0F));//true	
		
		/*
		 * Test Records
		 */
		System.out.println("Add 3 stations:");
		System.out.println(adminManagement.addStation("Hohe Warte", "passwort1", 0));
		System.out.println(adminManagement.addStation("Stephansdom", "passwort2", 0));
		System.out.println(adminManagement.addStation("Kahlenberg", "passwort3", 0));
		
		System.out.println("Add a record folder from Hohe Warte at 01.01.2007 00:00:00:");
		System.out.println(adminManagement.addRecordFolder(strtotime("01.01.2007 00:00:00"), adminManagement.getStation("Hohe Warte")));
		
		System.out.println("Add a record folder from Kahlenberg at 12.07.2007 15:12:56:");
		System.out.println(adminManagement.addRecordFolder(strtotime("12.07.2007 15:12:56"), adminManagement.getStation("Kahlenberg")));
		
		System.out.println("Add records to Hohe Warte at 01.01.2007 00:00:00:");
		System.out.println(adminManagement.addNewRecordToFolder(strtotime("01.01.2007 00:00:00"), "Hohe Warte", 43.0F, "Temperatur", "C"));
		System.out.println(adminManagement.addNewRecordToFolder(strtotime("01.01.2007 00:00:00"), "Hohe Warte", 50.0F, "Luftfeuchtigkeit", "%"));
		
		System.out.println("Add records to Kahlenberg at 12.07.2007 15:12:56:");
		System.out.println(adminManagement.addNewRecordToFolder(strtotime("12.07.2007 15:12:56"), "Kahlenberg", 50.0F, "Temperatur", "K"));
		System.out.println(adminManagement.addNewRecordToFolder(strtotime("12.07.2007 15:12:56"), "Kahlenberg", 20.0F, "Luftfeuchtigkeit", "%"));
		
		System.out.println("Show records from Hohe Warte at 01.01.2007 00:00:00:");
		System.out.println(adminManagement.getRecordFolder(strtotime("01.01.2007 00:00:00"), "Hohe Warte"));
		
		System.out.println("Show records from Kahlenberg at 12.07.2007 15:12:56:");
		System.out.println(adminManagement.getRecordFolder(strtotime("12.07.2007 15:12:56"), "Kahlenberg"));
		
		System.out.println("Show a list of all Records of Station Kahlenberg:");
		System.out.println(adminManagement.StationToString("Kahlenberg"));
		
		/*
		 * loggs user off
		 */
		System.out.println("Logge User aus.");
		System.out.println("User ausgeloggt: "+adminManagement.doLogout(ticket));
		
		/*
		 * stopps program and backupthread
		 */
		adminManagement.stopProgram();
	}

	/**
	 * Transformiert ein String Datum in ein Date Objekt
	 * @param date Stringformat einer Datumseingabe
	 */
	public static Date strtotime(String date) {
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		Date zeitpunkt = new Date();
		try {
			zeitpunkt = new Date(sdf.parse(date).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return zeitpunkt;	
	}
}