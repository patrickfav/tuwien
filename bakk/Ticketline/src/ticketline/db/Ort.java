package ticketline.db;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Eine Entitaet vom Typ Ort kann ein Auffuehrungsort, eine Kiosk und/oder eine
 * Verkaufsstelle sein. Die eindeutige Identifizierung erfolgt durch die
 * Bezeichnung (den Namen) des Ortes und die Ortsangabe (der geographische Ort).
 * Jeder Ort faellt in eine der folgenden Kategorien: Kino, Theater/Oper,
 * Kabarett, Konzertsaal oder Location. Weiters gibt es noch Attribute wie
 * Strasse, Postleitzahl, Bundesland und weiteres um den Ort naeher zu
 * spezifizieren.
 *
 */
public class Ort extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -1996868926110968597L;

	/** identifier field */
	private ticketline.db.OrtKey comp_id;

	/** persistent field */
	private String kategorie;

	/** persistent field */
	private String strasse;

	/** persistent field */
	private String plz;

	/** persistent field */
	private String bundesland;

	/** nullable persistent field */
	private String telnr;

	/** nullable persistent field */
	private String besitzer;

	/** nullable persistent field */
	private String oeffnungszeiten;

	/** persistent field */
	private Boolean kiosk;

	/** persistent field */
	private Boolean verkaufsstelle;

	/** persistent field */
	private Boolean auffuehrungsort;

	/** persistent field */
	private Set ticketcards;

	/** persistent field */
	private Set transaktionen;

	/** persistent field */
	private Set saele;

	/** persistent field */
	private Set news;

	/** full constructor */
	public Ort(ticketline.db.OrtKey comp_id, String kategorie, String strasse,
			String plz, String bundesland, String telnr, String besitzer,
			String oeffnungszeiten, Boolean kiosk, Boolean verkaufsstelle,
			Boolean auffuehrungsort, Set ticketcards, Set transaktionen,
			Set saele, Set news) {
		this.comp_id = comp_id;
		this.kategorie = kategorie;
		this.strasse = strasse;
		this.plz = plz;
		this.bundesland = bundesland;
		this.telnr = telnr;
		this.besitzer = besitzer;
		this.oeffnungszeiten = oeffnungszeiten;
		this.kiosk = kiosk;
		this.verkaufsstelle = verkaufsstelle;
		this.auffuehrungsort = auffuehrungsort;
		this.ticketcards = ticketcards;
		this.transaktionen = transaktionen;
		this.saele = saele;
		this.news = news;
	}

	/** default constructor */
	public Ort() {
		this.comp_id = null;
		this.kategorie = null;
		this.strasse = null;
		this.plz = null;
		this.bundesland = null;
		this.telnr = null;
		this.besitzer = null;
		this.oeffnungszeiten = null;
		this.kiosk = null;
		this.verkaufsstelle = null;
		this.auffuehrungsort = null;
		this.ticketcards = null;
		this.transaktionen = null;
		this.saele = null;
		this.news = null;
	}

	/** minimal constructor */
	public Ort(ticketline.db.OrtKey comp_id, String kategorie, String strasse,
			String plz, String bundesland, Boolean kiosk,
			Boolean verkaufsstelle, Boolean auffuehrungsort, Set ticketcards,
			Set transaktionen, Set saele, Set news) {
		this.comp_id = comp_id;
		this.kategorie = kategorie;
		this.strasse = strasse;
		this.plz = plz;
		this.bundesland = bundesland;
		this.kiosk = kiosk;
		this.verkaufsstelle = verkaufsstelle;
		this.auffuehrungsort = auffuehrungsort;
		this.ticketcards = ticketcards;
		this.transaktionen = transaktionen;
		this.saele = saele;
		this.news = news;
	}

	public ticketline.db.OrtKey getComp_id() {
		return this.comp_id;
	}

	public void setComp_id(ticketline.db.OrtKey comp_id) {
		this.comp_id = comp_id;
	}

	/**
	 * Kino, Theater/Oper, Kabarett, Konzertsaal oder Location
	 */
	public String getKategorie() {
		return this.kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	/**
	 * z.B.: Hauptstrasse 14
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
	 * z.B.: Niederoesterreich, Wien
	 */
	public String getBundesland() {
		return this.bundesland;
	}

	public void setBundesland(String bundesland) {
		this.bundesland = bundesland;
	}

	/**
	 * optionale Telefonnummer
	 */
	public String getTelnr() {
		return this.telnr;
	}

	public void setTelnr(String telnr) {
		this.telnr = telnr;
	}

	/**
	 * z.B.: Kiba Kino GmbH
	 */
	public String getBesitzer() {
		return this.besitzer;
	}

	public void setBesitzer(String besitzer) {
		this.besitzer = besitzer;
	}

	/**
	 * z.B.: Fr. 18:00-23:00
	 */
	public String getOeffnungszeiten() {
		return this.oeffnungszeiten;
	}

	public void setOeffnungszeiten(String oeffnungszeiten) {
		this.oeffnungszeiten = oeffnungszeiten;
	}

	/**
	 * Ist Ort ein Kiosk? TRUE (= ja), FALSE (= nein)
	 */
	public Boolean isKiosk() {
		return this.kiosk;
	}

	public void setKiosk(Boolean kiosk) {
		this.kiosk = kiosk;
	}

	/**
	 * Ist Ort eine Verkaufsstelle? TRUE (= ja), FALSE (= nein)
	 */
	public Boolean isVerkaufsstelle() {
		return this.verkaufsstelle;
	}

	public void setVerkaufsstelle(Boolean verkaufsstelle) {
		this.verkaufsstelle = verkaufsstelle;
	}

	/**
	 * Ist Ort ein Auffuehrungsort? TRUE (= ja), FALSE (= nein)
	 */
	public Boolean isAuffuehrungsort() {
		return this.auffuehrungsort;
	}

	public void setAuffuehrungsort(Boolean auffuehrungsort) {
		this.auffuehrungsort = auffuehrungsort;
	}

	public Set getTicketcards() {
		return this.ticketcards;
	}

	public void setTicketcards(Set ticketcards) {
		this.ticketcards = ticketcards;
	}

	public Set getTransaktionen() {
		return this.transaktionen;
	}

	public void setTransaktionen(Set transaktionen) {
		this.transaktionen = transaktionen;
	}

	public Set getSaele() {
		return this.saele;
	}

	public void setSaele(Set saele) {
		this.saele = saele;
	}

	public Set getNews() {
		return news;
	}

	public void setNews(Set news) {
		this.news = news;
	}

	public String toString() {
		return new ToStringBuilder(this).append("comp_id", getComp_id())
				.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof Ort))
			return false;
		Ort castOther = (Ort) other;
		return new EqualsBuilder().append(this.getComp_id(),
				castOther.getComp_id()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getComp_id()).toHashCode();
	}

}
