package ticketline.db;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Jedes Theaterstueck, jede Oper, jeder Kinofilm usw. des Systems wird in Form
 * einer Veranstaltung gespeichert. Von jeder Veranstaltung werden dann mehrere
 * Auffuehrungen in verschiedenen Saelen zu verschiedenen Zeiten verwaltet. Eine
 * Veranstaltung wird eindeutig durch ihre Bezeichnung (den Namen) und ihre
 * Kategorie (Kino, Theater...) bestimmt. Zur naeheren Bestimmung der
 * Veranstaltung wird noch eine ganze Reihe an zusaetzlichen Informationen
 * gespeichert.
 *
 */
public class Veranstaltung extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -2376468057851018229L;

	/** identifier field */
	private ticketline.db.VeranstaltungKey comp_id;

	/** nullable persistent field */
	private String subkategorie;

	/** nullable persistent field */
	private Integer jahrerstellung;

	/** nullable persistent field */
	private String spracheton;

	/** nullable persistent field */
	private String spracheut;

	/** persistent field */
	private Integer dauer;

	/** nullable persistent field */
	private String freigabe;

	/** nullable persistent field */
	private String abbildung;

	/** persistent field */
	private String inhalt;

	/** nullable persistent field */
	private String kritik;

	/** nullable persistent field */
	private String bewertung;

	/** nullable persistent field */
	private String hinweis;

	/** persistent field */
	private Set artikel;

	/** persistent field */
	private Set auffuehrungen;

	/** persistent field */
	private Set engagements;

	/** full constructor */
	public Veranstaltung(ticketline.db.VeranstaltungKey comp_id,
			String subkategorie, Integer jahrerstellung, String spracheton,
			String spracheut, Integer dauer, String freigabe, String abbildung,
			String inhalt, String kritik, String bewertung, String hinweis,
			Set artikel, Set auffuehrungen, Set engagements) {
		this.comp_id = comp_id;
		this.subkategorie = subkategorie;
		this.jahrerstellung = jahrerstellung;
		this.spracheton = spracheton;
		this.spracheut = spracheut;
		this.dauer = dauer;
		this.freigabe = freigabe;
		this.abbildung = abbildung;
		this.inhalt = inhalt;
		this.kritik = kritik;
		this.bewertung = bewertung;
		this.hinweis = hinweis;
		this.artikel = artikel;
		this.auffuehrungen = auffuehrungen;
		this.engagements = engagements;
	}

	/** default constructor */
	public Veranstaltung() {
		this.comp_id = null;
		this.subkategorie = null;
		this.jahrerstellung = null;
		this.spracheton = null;
		this.spracheut = null;
		this.dauer = null;
		this.freigabe = null;
		this.abbildung = null;
		this.inhalt = null;
		this.kritik = null;
		this.bewertung = null;
		this.hinweis = null;
		this.artikel = null;
		this.auffuehrungen = null;
		this.engagements = engagements;
	}

	/** minimal constructor */
	public Veranstaltung(ticketline.db.VeranstaltungKey comp_id, Integer dauer,
			String inhalt, Set artikel, Set auffuehrungen, Set engagements) {
		this.comp_id = comp_id;
		this.dauer = dauer;
		this.inhalt = inhalt;
		this.artikel = artikel;
		this.auffuehrungen = auffuehrungen;
		this.engagements = engagements;
	}

	public ticketline.db.VeranstaltungKey getComp_id() {
		return this.comp_id;
	}

	public void setComp_id(ticketline.db.VeranstaltungKey comp_id) {
		this.comp_id = comp_id;
	}

	/**
	 * Unterteilung von Kategorie; z.B. Thriller, Drama
	 */
	public String getSubkategorie() {
		return this.subkategorie;
	}

	public void setSubkategorie(String subkategorie) {
		this.subkategorie = subkategorie;
	}

	/**
	 * Erstellungsjahr <= Heuer
	 */
	public Integer getJahrerstellung() {
		return this.jahrerstellung;
	}

	public void setJahrerstellung(Integer jahrerstellung) {
		this.jahrerstellung = jahrerstellung;
	}

	/**
	 * z.B.: Deutsch, Englisch
	 */
	public String getSpracheton() {
		return this.spracheton;
	}

	public void setSpracheton(String spracheton) {
		this.spracheton = spracheton;
	}

	/**
	 * Sprache des Untertitels; z.B.: Deutsch, English
	 */
	public String getSpracheut() {
		return this.spracheut;
	}

	public void setSpracheut(String spracheut) {
		this.spracheut = spracheut;
	}

	/**
	 * 0 < Dauer in Minuten <= 999
	 */
	public Integer getDauer() {
		return this.dauer;
	}

	public void setDauer(Integer dauer) {
		this.dauer = dauer;
	}

	/**
	 * Altersfreigabe; z.B.: ab 18
	 */
	public String getFreigabe() {
		return this.freigabe;
	}

	public void setFreigabe(String freigabe) {
		this.freigabe = freigabe;
	}

	/**
	 * Speicherort eines Bilds zur Veranstaltung
	 */
	public String getAbbildung() {
		return this.abbildung;
	}

	public void setAbbildung(String abbildung) {
		this.abbildung = abbildung;
	}

	/**
	 * kurze Inhaltsangabe zur Veranstaltung
	 */
	public String getInhalt() {
		return this.inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}

	/**
	 * kurze Kritik zur Veranstaltung
	 */
	public String getKritik() {
		return this.kritik;
	}

	public void setKritik(String kritik) {
		this.kritik = kritik;
	}

	/**
	 * kurze Bewertung zur Veranstaltung
	 */
	public String getBewertung() {
		return this.bewertung;
	}

	public void setBewertung(String bewertung) {
		this.bewertung = bewertung;
	}

	/**
	 * kurzer Hinweis zur Veranstaltung
	 */
	public String getHinweis() {
		return this.hinweis;
	}

	public void setHinweis(String hinweis) {
		this.hinweis = hinweis;
	}

	public Set getArtikel() {
		return this.artikel;
	}

	public void setArtikel(Set artikel) {
		this.artikel = artikel;
	}

	public Set getAuffuehrungen() {
		return this.auffuehrungen;
	}

	public void setAuffuehrungen(Set auffuehrungen) {
		this.auffuehrungen = auffuehrungen;
	}

	public Set getEngagements() {
		return this.engagements;
	}

	public void setEngagements(Set engagements) {
		this.engagements = engagements;
	}

	public String toString() {
		return new ToStringBuilder(this).append("comp_id", getComp_id())
				.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof Veranstaltung))
			return false;
		Veranstaltung castOther = (Veranstaltung) other;
		return new EqualsBuilder().append(this.getComp_id(),
				castOther.getComp_id()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getComp_id()).toHashCode();
	}

}
