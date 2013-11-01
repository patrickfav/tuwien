package ticketline.db;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * Die Ticketcard ist eine Karte zur Identifikation von Stammkunden und
 * Mitarbeitern. Die Daten zur Identifikation der Person und eine eindeutige
 * Nummer sind in der Entitaet TicketCard gespeichert. Jeder TicketCard ist
 * entweder ein Kunde oder ein Mitarbeiter zugeordnet (ausschliessendes Oder),
 * der die selbe Nummer zur Identifikation bekommt wie die TicketCard. Jeder
 * TicketCard ist als zusaetzliche Information der Ausstellungsort zugeordnet.
 *
 */
public class Ticketcard extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 7257677923410786298L;

	/** identifier field */
	private Integer kartennr;

	/** persistent field */
	private String typ;

	/** nullable persistent field */
	private String nname;

	/** nullable persistent field */
	private String vname;

	/** nullable persistent field */
	private String titel;

	/** nullable persistent field */
	private String geschlecht;

	/** nullable persistent field */
	private Date geburtsdatum;

	/** nullable persistent field */
	private String strasse;

	/** nullable persistent field */
	private String plz;

	/** nullable persistent field */
	private String ort;

	/** nullable persistent field */
	private String telnr;

	/** nullable persistent field */
	private String email;

	/** persistent field */
	private ticketline.db.Ort ortverk;

	/** full constructor */
	public Ticketcard(String typ, String nname, String vname, String titel,
			String geschlecht, Date geburtsdatum, String strasse, String plz,
			String ort, String telnr, String email, ticketline.db.Ort ortverk) {
		this.typ = typ;
		this.nname = nname;
		this.vname = vname;
		this.titel = titel;
		this.geschlecht = geschlecht;
		this.geburtsdatum = geburtsdatum;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
		this.telnr = telnr;
		this.email = email;
		this.ortverk = ortverk;
	}

	/** default constructor */
	public Ticketcard() {
		this.typ = null;
		this.nname = null;
		this.vname = null;
		this.titel = null;
		this.geschlecht = null;
		this.geburtsdatum = null;
		this.strasse = null;
		this.plz = null;
		this.ort = null;
		this.telnr = null;
		this.email = null;
		this.ortverk = null;
	}

	/** minimal constructor */
	public Ticketcard(String typ, ticketline.db.Ort ortverk) {
		this.typ = typ;
		this.ortverk = ortverk;
	}

	public Integer getKartennr() {
		return this.kartennr;
	}

	public void setKartennr(Integer kartennr) {
		this.kartennr = kartennr;
	}

	/**
	 * Kunde `K', Mitarbeiter `M'
	 */
	public String getTyp() {
		return this.typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	/**
	 * Nachname des Kartenbesitzers
	 */
	public String getNname() {
		return this.nname;
	}

	public void setNname(String nname) {
		this.nname = nname;
	}

	/**
	 * Vorname des Kartenbesitzers
	 */
	public String getVname() {
		return this.vname;
	}

	public void setVname(String vname) {
		this.vname = vname;
	}

	/**
	 * Anrede des Kartenbesitzers
	 */
	public String getTitel() {
		return this.titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	/**
	 * weiblich (`W'), maennlich (`M') oder juristisch (`J')
	 */
	public String getGeschlecht() {
		return this.geschlecht;
	}

	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}

	/**
	 * Heute - 120 Jahre < Geburtsdatum < Heute - 3 Jahre
	 */
	public Date getGeburtsdatum() {
		return this.geburtsdatum;
	}

	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	/**
	 * z.B.: Hauptstrasse 11
	 */
	public String getStrasse() {
		return this.strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	/**
	 * z.B.: 1100; 0000 <= Postleitzahl <= 9999
	 */
	public String getPlz() {
		return this.plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	/**
	 * z.B.: Wien
	 */
	public String getOrt() {
		return this.ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	/**
	 * Telefonnummer des Kartenbesitzers
	 */
	public String getTelnr() {
		return this.telnr;
	}

	public void setTelnr(String telnr) {
		this.telnr = telnr;
	}

	/**
	 * Email-Adresse des Karteninhabers
	 */
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ticketline.db.Ort getOrtverk() {
		return this.ortverk;
	}

	public void setOrtverk(ticketline.db.Ort ortverk) {
		this.ortverk = ortverk;
	}

	public String toString() {
		return new ToStringBuilder(this).append("kartennr", getKartennr())
				.toString();
	}

}
