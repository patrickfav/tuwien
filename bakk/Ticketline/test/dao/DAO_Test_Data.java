package dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.Set;

import ticketline.db.Artikel;
import ticketline.db.Auffuehrung;
import ticketline.db.AuffuehrungKey;
import ticketline.db.Belegung;
import ticketline.db.BelegungKey;
import ticketline.db.Bestellung;
import ticketline.db.BestellungKey;
import ticketline.db.Engagement;
import ticketline.db.EngagementKey;
import ticketline.db.Kategorie;
import ticketline.db.KategorieKey;
import ticketline.db.Kuenstler;
import ticketline.db.Kunde;
import ticketline.db.Mitarbeiter;
import ticketline.db.Ort;
import ticketline.db.OrtKey;
import ticketline.db.Reihe;
import ticketline.db.ReiheKey;
import ticketline.db.Saal;
import ticketline.db.SaalKey;
import ticketline.db.Ticketcard;
import ticketline.db.Transaktion;
import ticketline.db.TransaktionKey;
import ticketline.db.Veranstaltung;
import ticketline.db.VeranstaltungKey;
/**
 * 
 * DAO Test Data
 * This class creates all objects, which 
 * are needed for the DAO-Tests and fill it
 * with data.
 * @author AndiS
 * @version 0.1
 *
 */
public class DAO_Test_Data 
{
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    
	protected Veranstaltung veranstaltung = null;
	protected Belegung belegung = null;
	protected Ort ort = null;
	protected Transaktion transaktion = null;
	protected Saal saal = null;
	protected Engagement engagement = null;
	protected Bestellung bestellung = null;
	protected Artikel artikel = null;
	protected Ticketcard ticketcard = null;
	protected Kategorie kategorie = null;
	protected Kuenstler kuenstler = null;
	protected Mitarbeiter mitarbeiter =null;
	protected Reihe reihe = null;
	protected Auffuehrung auffuehrung = null;
	protected Kunde kunde = null;
	
	protected Set<Auffuehrung> auffuehrungen = new LinkedHashSet<Auffuehrung>();
	protected Set<Bestellung> bestellungen = new LinkedHashSet<Bestellung>();
	protected Set<Belegung> belegungen = new LinkedHashSet<Belegung>();
	protected Set<Transaktion> transaktionen = new LinkedHashSet<Transaktion>();
	protected Set<Artikel> artikeln = new LinkedHashSet<Artikel>();
	protected Set<Engagement> engagements = new LinkedHashSet<Engagement>();
	protected Set<Kategorie> kategorien = new LinkedHashSet<Kategorie>();
	protected Set<Reihe> reihen = new LinkedHashSet<Reihe>();

	public DAO_Test_Data() 
	{
		
		transaktion = createTestTransaktion();
		ort = createTestOrt();
		belegung = createTestBelegung();
		saal = createTestSaal();
		engagement = createTestEngagement();
		bestellung = createTestBestellung();
		artikel = createTestArtikel();
		ticketcard = createTestTicketcard();
		kategorie = createTestKategorie();
		kuenstler = createTestKuenstler();
		mitarbeiter = createTestMitarbeiter();
		reihe = createTestReihe();
		auffuehrung = createTestAuffuehrung();
		kunde = createTestKunde();
		veranstaltung = createTestVeranstaltung();
		
		belegungen.add(belegung);
		auffuehrungen.add(createTestAuffuehrung());
		bestellungen.add(bestellung);
		belegungen.add(belegung);
		transaktionen.add(transaktion);
		artikeln.add(artikel);
		engagements.add(engagement);
		kategorien.add(kategorie);
		reihen.add(reihe);
		
	}
	
	protected Kunde createTestKunde()
	{
		Kunde kunde = new Kunde();
		
		kunde.setKartennr(78658);
		kunde.setTyp("typ1");
		kunde.setOrtverk(ort);
		kunde.setErmaechtigung(false);
		kunde.setErmaessigung(new BigDecimal(100));
		try { kunde.setTkgueltigbis(sdf.parse("01.01.2001")); } 
		catch (ParseException ex2) { ex2.printStackTrace(); }
		kunde.setGesperrt(false);
		kunde.setGruppe("gruppe1");
		kunde.setBestellungen(bestellungen);
		kunde.setTransaktionen(transaktionen);
		
		return kunde;
	}
	
	
	protected Mitarbeiter createTestMitarbeiter()
	{
		Mitarbeiter mitarbeiter = new Mitarbeiter();
		
		mitarbeiter.setKartennr(9658);
		
		mitarbeiter.setNname("nname1");
		mitarbeiter.setVname("vnname1");
		mitarbeiter.setTitel("titel1");
		mitarbeiter.setGeschlecht("m");
		try { mitarbeiter.setGeburtsdatum(sdf.parse("01.01.2001")); } 
		catch (ParseException ex2) { ex2.printStackTrace(); }	
		mitarbeiter.setStrasse("strasse1");
		mitarbeiter.setPlz("plz1");
		mitarbeiter.setOrt("ort1");
		mitarbeiter.setTelnr("telnr1");
		mitarbeiter.setEmail("email1");

		
		mitarbeiter.setTyp("typ1");
		//mitarbeiter.setOrtverk(ort);
		mitarbeiter.setBlz("blz1");
		mitarbeiter.setKontonr("kontonr1");
		mitarbeiter.setPin("pin1");
		mitarbeiter.setBerechtigung("berechtigung1");
		mitarbeiter.setPasswort("passwort1");
		mitarbeiter.setTransaktionen(transaktionen);
		mitarbeiter.setUsername("username1");
		
		return mitarbeiter;
	}
	protected Kuenstler createTestKuenstler()
	{
		Kuenstler kuenstler = new Kuenstler();
		
		kuenstler.setKuenstlernr(3254);
		kuenstler.setNname("nname1");
		kuenstler.setVname("vname1");
		kuenstler.setGeschlecht("geschlecht1");
		kuenstler.setEngagements(engagements);
		
		return kuenstler;
	}
	protected Reihe createTestReihe()
	{
		Reihe reihe = new Reihe();
		ReiheKey reiheKey = new ReiheKey();
		
		reiheKey.setBezeichnung("bezeichnung");
		reiheKey.setKategoriebez("kategoriebez");
		reiheKey.setSaalbez("saalbez");
		reiheKey.setOrtbez("ortbez");
		reiheKey.setOrt("ort1");
		
		reihe.setComp_id(reiheKey);
		reihe.setStartplatz(12);
		reihe.setAnzplaetze(10);
		reihe.setSitzplatz(true);
		reihe.setBelegungen(belegungen);
		
		return reihe;
	}
	protected Kategorie createTestKategorie()
	{
		Kategorie kategorie = new Kategorie();
		KategorieKey kategorieKey = new KategorieKey();
		
		kategorieKey.setBezeichnung("bezeichnung1");
		kategorieKey.setSaalbez("bezeichnung1");
		kategorieKey.setOrtbez("ortbez1");
		kategorieKey.setOrt("ort1");
		
		kategorie.setComp_id(kategorieKey);
		kategorie.setPreismin(new BigDecimal(10));
		kategorie.setPreisstd(new BigDecimal(50));
		kategorie.setPreismax(new BigDecimal(100));
		kategorie.setReihen(reihen);
		
		return kategorie;
	}
	protected Saal createTestSaal()
	{
		Saal saal = new Saal();
		SaalKey saalKey = new SaalKey();
		
		saalKey.setBezeichnung("bezeichnung1");
		saalKey.setOrtbez("ortbez1");
		saalKey.setOrt("ort1");
		
		saal.setComp_id(saalKey);
		saal.setTyp("kategorie1");
		saal.setAnzplaetze(10);
		saal.setKostenprotag(new BigDecimal(8675));
		saal.setOrt(ort);
		saal.setAuffuehrungen(auffuehrungen);
		saal.setKategorien(kategorien);

		return saal;
	}
	
	protected Ort createTestOrt()
	{
		Ort ort = new Ort();
		OrtKey ortKey = new OrtKey();
		
		ortKey.setBezeichnung("ortbez1");
		ortKey.setOrt("ort1");
		
		ort.setComp_id(ortKey);
		ort.setKategorie("kategorie1");
		ort.setStrasse("strasse");
		ort.setPlz("plz");
		ort.setBundesland("bundesland");
		ort.setKiosk(true);
		ort.setVerkaufsstelle(false);
		ort.setAuffuehrungsort(false);
	
		
		return ort;
	}
	
	protected Engagement createTestEngagement()
	{
		Engagement engagement = new Engagement();
		EngagementKey engagementKey = new EngagementKey();

		engagementKey.setKuenstlernr(10);
		engagementKey.setBezeichnung("bezeichnung1");
		engagementKey.setKategorie("kategorie1");
		
		engagement.setComp_id(engagementKey);
		
		return engagement;
	}
	
	protected Transaktion createTestTransaktion()
	{
		//create
		Transaktion transaktion = new Transaktion();
		TransaktionKey transaktionKey = new TransaktionKey();
		
		//fill with data
		try { transaktionKey.setDatumuhrzeit(sdf.parse("01.01.2001")); } 
		catch (ParseException ex2) { ex2.printStackTrace(); }
		transaktionKey.setKundennr(10);
		transaktionKey.setMitarbeiternr(11);
		
		transaktion.setComp_id(transaktionKey);
		transaktion.setVerkauft(false);
		transaktion.setStorniert(false);
		transaktion.setPreis(new BigDecimal(1000));
		transaktion.setStartplatz(1);
		transaktion.setAnzplaetze(12);
		transaktion.setBelegung(belegung);
		transaktion.setOrt(ort);
		
		return transaktion;
	}
	
	protected Belegung createTestBelegung()
	{
		//create
		Belegung belegung = new Belegung();
		BelegungKey belegungKey = new BelegungKey();
		
		//fill with data
		belegungKey.setReihebez("reihebez1");
		belegungKey.setKategoriebez("kategoriebez1");
		belegungKey.setSaalbez("saalbez1");
		belegungKey.setOrtbez("ortbez1");
		belegungKey.setOrt("ort1");
		try { belegungKey.setDatumuhrzeit(sdf.parse("01.01.2001")); } 
		catch (ParseException ex2) { ex2.printStackTrace(); }
		
		belegung.setComp_id(belegungKey);
		belegung.setAnzfrei(10);
		belegung.setAnzres(12);
		belegung.setAnzverk(20);
		belegung.setTransaktionen(transaktionen);
		
		return belegung;
	}
	
	protected Bestellung createTestBestellung()
	{
		Bestellung bestellung = new Bestellung();
		BestellungKey bestellungKey = new BestellungKey();
		
		try { bestellungKey.setDatumuhrzeit(sdf.parse("01.01.2001")); } 
		catch (ParseException ex2) { ex2.printStackTrace(); }
		bestellungKey.setArtikelnr(1);
		bestellungKey.setKartennr(10);
		
		bestellung.setComp_id(bestellungKey);
		bestellung.setMenge(10);
		bestellung.setZahlart("Bar");
		
		return bestellung;
	}
	
	
	protected Artikel createTestArtikel()
	{
		//create
		Artikel artikel = new Artikel();
		
		//fill with data
		artikel.setKurzbezeichnung("kurzbezeichnung1");
		artikel.setPreis(new BigDecimal(100));
		artikel.setKategorie("kategorie1");
		artikel.setVeranstaltung(veranstaltung);
		artikel.setBestellungen(bestellungen);
		
		return artikel;
	}
	
	protected Auffuehrung createTestAuffuehrung()
	{
		//create
		Auffuehrung auffuehrung = new Auffuehrung();
		AuffuehrungKey auffuehrungKey = new AuffuehrungKey(); 
		
		//fill with data
		try { auffuehrungKey.setDatumuhrzeit(sdf.parse("01.01.2001")); } 
		catch (ParseException ex2) { ex2.printStackTrace(); }
		auffuehrungKey.setSaalbez("saalbez1");
		auffuehrungKey.setOrtbez("ortbez1");
		auffuehrungKey.setOrt("ort1");
		
		auffuehrung.setComp_id(auffuehrungKey);
		auffuehrung.setStorniert(false);
		auffuehrung.setPreis("100");  
		auffuehrung.setVeranstaltung(veranstaltung);
		auffuehrung.setBelegungen(belegungen);
		
		return auffuehrung;
	}
	
	protected Veranstaltung createTestVeranstaltung()
	{
		//create
		Veranstaltung veranstaltung = new Veranstaltung();
		VeranstaltungKey veranstaltungKey = new VeranstaltungKey(); 
		
		//fill with data
		veranstaltungKey.setBezeichnung("bezeichnung1");
		veranstaltungKey.setKategorie("kategorie1");
		
		veranstaltung.setComp_id(veranstaltungKey);
		veranstaltung.setDauer(10);
		veranstaltung.setInhalt("inhalt1");
		veranstaltung.setArtikel(artikeln);
		veranstaltung.setAuffuehrungen(auffuehrungen);
		veranstaltung.setEngagements(engagements);
		
		return veranstaltung;
	}
	
	protected Ticketcard createTestTicketcard()
	{
		//create
		Ticketcard ticketcard = new Ticketcard();

		//fill with data
		ticketcard.setKartennr(216673);
		ticketcard.setTyp("typ1");
		ticketcard.setNname("nname1");
		ticketcard.setVname("vnname1");
		ticketcard.setTitel("titel1");
		ticketcard.setGeschlecht("geschlecht1");
		try { ticketcard.setGeburtsdatum(sdf.parse("01.01.2001")); } 
		catch (ParseException ex2) { ex2.printStackTrace(); }	
		ticketcard.setStrasse("strasse1");
		ticketcard.setPlz("plz1");
		ticketcard.setOrt("ort1");
		ticketcard.setTelnr("telnr1");
		ticketcard.setEmail("email1");
		ticketcard.setOrtverk(ort);

		return ticketcard;
	}


}
