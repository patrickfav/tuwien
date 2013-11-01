package aufgabe3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *	== Aufgabe 3 Verbesserungen ==
 *
 *	- Protokoll System hinzugefügt
 *	- Dateispeicherung mit Datumspriorität
 *	- Abstract klasse units -> baseunits ->derivedunits
 *	- Statistik Avg Methode
 *	- Zusicherungen
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
		RecordsManagement management = null;
		String ticket = null;
		Units unit_management = null;
		management = (RecordsManagement) cm.getObjectFromFile("RecordsManagement");
		if (management == null){
			Authorisation login = new Authorisation();
			unit_management = new Units();
			management = new RecordsManagement(unit_management, login);		
		}
		else{
			unit_management = management.getUnitManagement();
		}
		ticket = management.doLogin("admin", "secret");
		management.startProgram();
		//RecordsManagement management = new RecordsManagement();
		
		/*
		 * Tests if the user is logged in
		 */
		System.out.println("logged in as admin: "+management.checkLogin(ticket));
		
		/*
		 * Test Units
		 */
		unit_management.addBaseUnit("Standard Temperatureinheit", "C", "Temperatur");
		unit_management.addDerivedUnit("Amerikanischer Standard", "F", "Temperatur", -32.00F, 0.555555555555F, true);
		unit_management.addDerivedUnit("SI Einheit", "K", "Temperatur", 273.15F, 1.0F, true);
		
		unit_management.addBaseUnit("relative Luftfeuchtigkeit", "%", "Luftfeuchtigkeit");
		
		management.addParameter("Temperatur", 99, -99, 40, "C", ticket);
		management.addParameter("Luftfeuchtigkeit", 99, 0, 80, "%", ticket);
		management.addParameter("Luftdruck", 99, 0, 3, "bar", ticket);
		management.addParameter("Ozongehalt", 99, 0, 5, "mm3", ticket);
		/*
		System.out.println(management.getBaseUnitSymbolByParamter("Temperatur"));
		System.out.println(management.getBaseUnitSymbolByParamter("Luftfeuchtigkeit"));
		
		System.out.println(unit_management.getBaseUnitValue("F", 76.0F));
		System.out.println(unit_management.getBaseUnitValue("K", 177.0F));
		System.out.println(unit_management.getBaseUnitValue("C", 21.45F));
		System.out.println(unit_management.getBaseUnitValue("X", 21.45F)); //smybol not existant
		
		System.out.println(unit_management.getParameterBySymbol("F"));
		System.out.println(unit_management.getParameterBySymbol("K"));
		System.out.println(unit_management.getParameterBySymbol("C"));
		System.out.println(unit_management.getParameterBySymbol("%"));
		System.out.println(unit_management.getParameterBySymbol("X")); //smybol not existant
		*/
		//ArrayList<String> allpars = management.fetchParameters();
		/*System.out.println(allpars.toString());*/
		
		/*
		 * Loeschen
		 */
		
		if(management.deleteParameter("Ozongehalt", ticket))
			//System.out.println("Ozongehalt wurde geloescht.");
		if(management.deleteParameter("Regenbogendichte", ticket))
			//System.out.println("Regenbogendichte wurde geloescht.");
		
		//allpars = management.fetchParameters();
		//System.out.println(allpars.toString());
		
		//ArrayList<String> allunits = unit_management.fetchAllUnits();
		//System.out.println(allunits.toString());
		
		/*
		 * Loeschen
		 */
		
		if(unit_management.deleteUnit("F"))
			//System.out.println("F wurde geloescht.");
		
		if(unit_management.deleteUnit("X"))
			//System.out.println("X wurde geloescht.");
		
		//allunits = unit_management.fetchAllUnits();
		//System.out.println(allunits.toString());
		
		
		/*
		 * testing dumping method
		 */
		System.out.println("RecordsManagement-Instanz:"+management.toString());
		System.out.println("RecordsManagement-Instanz saved: "+cm.addObjectToFile("RecordsManagement", management));
		System.out.println("RecordsManagement-Instanz deleted.");
		management.stopProgram();
		management =null;
		System.out.println("RecordsManagement-Instanz loaded.");
		management = (RecordsManagement) cm.getObjectFromFile("RecordsManagement");
		management.startProgram();
		System.out.println("RecordsManagement-Instanz:"+management.toString());
		
		/*
		 *  testing log methods
		 
		
		Date now = new Date();
		
		Date now1 = new Date(now.getTime()+1);
		Date now2 = new Date(now.getTime()+2);
		Date now3 = new Date(now.getTime()+3);
		Date now4 = new Date(now.getTime()+4);
		
		management.addLogEntry(now1, "A basic log 1", "");
		management.addLogEntry(now2, "A basic log 2", "");
		management.addLogEntry(now3, "A basic log 3", "");
		management.addLogEntry(now4, "A basic log 4", "");
		
		
		
		ArrayList<Log> alllog = management.fetchLog();
		for(int i=0;i<alllog.size();i++) {
			System.out.print(alllog.get(i).getId()+" ");
			System.out.print(alllog.get(i).getTimestamp().toString()+" ");
			System.out.println(alllog.get(i).getDescription());
		}
		
		
		//returns the log with the highest id
		//System.out.println(management.getLatestLogEntry().getId());
		*/
		
		
		/*
		 * check min/max/threshold
		 
		System.out.println(management.checkIfOverMaxValue("Temperatur", 105.0F));//true
		System.out.println(management.checkIfOverMaxValue("Temperatur", 44.89F));//false
		System.out.println(management.checkIfUnderMinValue("Temperatur", -105.0F));//true
		System.out.println(management.checkIfUnderMinValue("Temperatur", 43.0F));//false
		System.out.println(management.checkIfOverThreshold("Temperatur", -144.0F));//false
		System.out.println(management.checkIfOverThreshold("Temperatur", 15.0F));//true	
		*/
		
		/*
		 * Add stations
		 */
		System.out.println("Add 3 stations:");
		System.out.println(management.addStation("Hohe Warte", "passwort1", 0, ticket));
		System.out.println(management.addStation("Stephansdom", "passwort2", 0, ticket));
		System.out.println(management.addStation("Kahlenberg", "passwort3", 0, ticket));
		
		/*
		 * Record Folders with Records
		 */
		System.out.println("Add a record folder to Hohe Warte at 05.01.2007 00:00:00:");
		System.out.println(management.addRecordFolder(strtotime("05.01.2007 00:00:00"), management.getStation("Hohe Warte")));
		System.out.println("Add records to Hohe Warte at 05.01.2007 00:00:00:");
		System.out.println(management.addNewRecordToFolder(strtotime("05.01.2007 00:00:00"), "Hohe Warte", 43.0F, "Temperatur", "C"));
		System.out.println(management.addNewRecordToFolder(strtotime("05.01.2007 00:00:00"), "Hohe Warte", 50.0F, "Luftfeuchtigkeit", "%"));
				
		System.out.println("Add a record folder to Kahlenberg at 12.07.2007 15:12:56:");
		System.out.println(management.addRecordFolder(strtotime("12.07.2007 15:12:56"), management.getStation("Kahlenberg")));	
		System.out.println("Add records to Kahlenberg at 12.07.2007 15:12:56:");
		System.out.println(management.addNewRecordToFolder(strtotime("12.07.2007 15:12:56"), "Kahlenberg", 50.0F, "Temperatur", "K"));
		System.out.println(management.addNewRecordToFolder(strtotime("12.07.2007 15:12:56"), "Kahlenberg", 20.0F, "Luftfeuchtigkeit", "%"));
		
		System.out.println("Add a record folder to Hohe Warte at 25.01.2007 00:00:00:");
		System.out.println(management.addRecordFolder(strtotime("25.01.2007 00:00:00"), management.getStation("Hohe Warte")));
		System.out.println("Add records to Hohe Warte at 25.01.2007 00:00:00:");
		System.out.println(management.addNewRecordToFolder(strtotime("25.01.2007 00:00:00"), "Hohe Warte", 20.0F, "Temperatur", "C"));
		System.out.println(management.addNewRecordToFolder(strtotime("25.01.2007 00:00:00"), "Hohe Warte", 25.0F, "Luftfeuchtigkeit", "%"));
		
		System.out.println("Add a record folder to Kahlenberg at 25.07.2007 15:12:56:");
		System.out.println(management.addRecordFolder(strtotime("25.07.2007 15:12:56"), management.getStation("Kahlenberg")));	
		System.out.println("Add records to Kahlenberg at 25.07.2007 15:12:56:");
		System.out.println(management.addNewRecordToFolder(strtotime("25.07.2007 15:12:56"), "Kahlenberg", 15.0F, "Temperatur", "C"));
		System.out.println(management.addNewRecordToFolder(strtotime("25.07.2007 15:12:56"), "Kahlenberg", 35.0F, "Luftfeuchtigkeit", "%"));
		
		System.out.println("Add a record folder to Stephansdom at 13.07.2007 16:12:56:");
		System.out.println(management.addRecordFolder(strtotime("13.07.2007 16:12:56"), management.getStation("Stephansdom")));	
		System.out.println("Add records to Stephansdom at 13.07.2007 16:12:56:");
		System.out.println(management.addNewRecordToFolder(strtotime("13.07.2007 16:12:56"), "Stephansdom", 22.5F, "Temperatur", "C"));
		System.out.println(management.addNewRecordToFolder(strtotime("13.07.2007 16:12:56"), "Stephansdom", 66.0F, "Luftfeuchtigkeit", "%"));
	
		/*
		 * Test Query Output
		 */
		
		System.out.println("Show records from Hohe Warte at 05.01.2007 00:00:00:");
		System.out.println(management.getRecordFolder(strtotime("05.01.2007 00:00:00"), "Hohe Warte"));
		
		System.out.println("Show records from Kahlenberg at 12.07.2007 15:12:56:");
		System.out.println(management.getRecordFolder(strtotime("12.07.2007 15:12:56"), "Kahlenberg"));
		
		System.out.println("Show a list of all Records of Stations:");
		System.out.println(management.showRecordFoldersBetweenDates("Alle",strtotime("01.01.2007 00:00:00"),strtotime("31.12.2007 24:00:00")));
		
		System.out.println("Show average of Temperatur from Hohe Warte between 23.12.2006 00:00:00 and 31.12.2007 24:00:00");
		System.out.println(management.showAverage("Hohe Warte","Temperatur",strtotime("23.12.2006 00:00:00"),strtotime("31.12.2007 24:00:00")));
		/*
		 * loggs user off
		 */
		System.out.println("Logge User aus.");
		System.out.println("User ausgeloggt: "+management.doLogout(ticket));
		
		
		/*
		 * Outputing logs
		 */
		System.out.println("========== LOG OUTPUT =============");
		ArrayList<Log> alllog = management.fetchLog();
		for(int i=0;i<alllog.size();i++) {
			System.out.print(alllog.get(i).getId()+": ");
			System.out.print(alllog.get(i).getTimestamp().toString()+" - ");
			System.out.println(alllog.get(i).getDescription());
		}
		System.out.println("===================================");
		
		/*
		 * stopps program and backupthread
		 */
		management.stopProgram();
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