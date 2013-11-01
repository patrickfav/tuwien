package aufgabe1;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Test {

	/**
	 * Test Klasse
	 * <p>Funktionen werden hier einzeln aufgerufen und ausgegeben</p>
	 */
	public static void main(String[] args) {
		
		int obergrenze = 500;
		int untergrenze = 1;
		int grenzwert = 100;

		// Ein neues Objekt wird erstellt, welches die Messwerte beinhaltet
		MesswerteErfassen messbuch = new MesswerteErfassen(grenzwert,obergrenze,untergrenze);
		
		/**
		 * 5 verschiedene Messwerte werden eingelesen
		 * Diese Messwerte liegen im Messbereich und sind gueltig
		 */
		System.out.print("Messwerte hinzufuegen:\n");
		System.out.print("===============================\n");		

		System.out.print("Messwert: 57 Datum: 14.03.2007 15:13:01\n");	
		System.out.print(messbuch.messwertHinzufuegen("14.03.2007 15:13:01", 57));

		System.out.print("Messwert: 89 Datum: 17.06.2007 11:05:14\n");	
		System.out.print(messbuch.messwertHinzufuegen("17.06.2007 11:05:14", 89));
		
		System.out.print("Messwert: 23 Datum: 21.07.2007 17:13:01\n");	
		System.out.print(messbuch.messwertHinzufuegen("21.07.2007 17:13:01", 23));

		System.out.print("Messwert: 25 Datum:21.07.2007 18:13:01\n");	
		System.out.print(messbuch.messwertHinzufuegen("21.07.2007 18:13:01", 25));

		System.out.print("Messwert: 123 Datum: 31.12.2007 01:00:00\n");	
		System.out.print(messbuch.messwertHinzufuegen("31.12.2007 01:00:00", 123));
		
		/**
		 * Durchschnitt der 5 Messwerte wird ausgegeben
		 */
		System.out.print("===============================\n");
		System.out.print("Durchschnitt: "+messbuch.getDurchschnitt(strtotime("01.01.2007 00:00:00"),strtotime("31.12.2007 23:59:59"))+"\n");		
		System.out.print("===============================\n");

		/**
		 * Weitere Messwerte werden eingelesen. 
		 */
		System.out.print("Messwert: 73 Datum: 21.02.2008 19:15:01\n");	
		System.out.print(messbuch.messwertHinzufuegen("21.02.2008 19:15:01", 73));

		System.out.print("Messwert: 66 Datum: 15.03.2008 12:00:00\n");	
		System.out.print(messbuch.messwertHinzufuegen("15.03.2008 12:00:00", 66));
		
		/**
		 * Durchschnitt der 7 Messwerte wird ausgegeben
		 */
		System.out.print("===============================\n");
		System.out.print("Durchschnitt: "+messbuch.getDurchschnitt(strtotime("01.01.2007 00:00:00"),strtotime("31.12.2008 23:59:59"))+"\n");
		System.out.print("===============================\n");
		
		/**
		 * Ein Messwert wird entfernt
		 */
		System.out.print("Entferne Datum: 21.07.2007 17:13:01\n");
		System.out.print(messbuch.entferneMesswert(strtotime("21.07.2007 17:13:01"))+"\n");

		/**
		 * Durchschnitt der restlichen 6 Messwerte wird ausgegeben
		 */
		System.out.print("===============================\n");
		System.out.print("Durchschnitt: "+messbuch.getDurchschnitt(strtotime("01.01.2007 00:00:00"),strtotime("31.12.2008 23:59:59"))+"\n");
		System.out.print("===============================\n");

		/**
		 * Fehlerhafte bzw. Messwerte mit Warnungen werden eingelesen
		 */
		System.out.print("Messwert: "+(obergrenze+1)+" Datum: 23.07.2008 18:13:01\n");
		System.out.print(messbuch.messwertHinzufuegen("23.07.2008 18:13:01", obergrenze+1));
		System.out.print("Messwert: "+(untergrenze-1)+" Datum: 24.07.2008 18:13:01\n");	
		System.out.print(messbuch.messwertHinzufuegen("24.07.2008 18:13:01", untergrenze-1));
		System.out.print("Messwert: "+(grenzwert+1)+" Datum:25.07.2008 18:13:01\n");
		System.out.print(messbuch.messwertHinzufuegen("25.07.2008 18:13:01", grenzwert+1));
		System.out.print("Messwert: "+(grenzwert+2)+" Datum: 25.07.2008 18:14:01\n");	
		System.out.print(messbuch.messwertHinzufuegen("25.07.2008 18:14:01", grenzwert+2));
		System.out.print("Messwert: "+(grenzwert+3)+" Datum: 25.07.2008 18:15:01\n");	
		System.out.print(messbuch.messwertHinzufuegen("25.07.2008 18:15:01", grenzwert+3));
		System.out.print("Messwert: "+(grenzwert+2)+" Datum: 25.07.2008 18:16:01\n");	
		System.out.print(messbuch.messwertHinzufuegen("25.07.2008 18:16:01", grenzwert+2));
		System.out.print("Messwert: "+(grenzwert+5)+" Datum: 25.07.2008 18:17:01\n");	
		System.out.print(messbuch.messwertHinzufuegen("25.07.2008 18:17:01", grenzwert+5));
		
		/**
		 * Fehlerhafter Durchschnitt - Startzeit ist nach der Endzeit
		 */
		System.out.print("===============================\n");
		System.out.print("Durchschnitt: "+messbuch.getDurchschnitt(strtotime("01.01.2007 00:00:00"),strtotime("31.12.2006 23:59:59"))+"\n");
		System.out.print("===============================\n");
		
		/**
		 * Ein Messwert mit altem Datum wird eingelesen
		 */
		System.out.print("Messwert: 15 Datum: 25.07.2006 18:17:01\n");	
		System.out.print(messbuch.messwertHinzufuegen("25.07.2006 18:17:01", 15));
		
		/**
		 * Ausgabe der eingelesenen Messwerte zwischen einem bestimmten Zeitraum
		 * Korrekte Eingabe - Ausgabe
		 */
		System.out.print("===============================\n");
		System.out.print("Messwerte zwischen 01.01.2007 00:00:00 und 31.12.2008 23:59:59 ausgeben:\n");
		System.out.print(messbuch.getMesswerte(strtotime("01.01.2007 00:00:00"),strtotime("31.12.2008 23:59:59")));
		
		/**
		 * Ausgabe der eingelesenen Messwerte zwischen einem bestimmten Zeitraum
		 * Falsche Eingabe - Startzeit ist nach der Endzeit
		 */
		System.out.print("===============================\n");
		System.out.print("Messwerte zwischen 31.12.2008 23:59:59 und 01.01.2007 00:00:00 ausgeben:\n");
		System.out.print(messbuch.getMesswerte(strtotime("31.12.2008 23:59:59"),strtotime("01.01.2007 00:00:00")));
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
