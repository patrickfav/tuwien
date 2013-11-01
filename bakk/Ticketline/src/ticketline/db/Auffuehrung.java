package ticketline.db;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Unter einer Auffuehrung versteht man eine Darbietung einer Veranstaltung an
 * einem bestimmten Datum, zu einer bestimmten Uhrzeit und in einem bestimmten
 * Saal. Die Auffuehrung ist genau durch diese Daten (Datum, Uhrzeit und Saal)
 * eindeutig definiert. Eine Auffuehrung ist natuerlich genau einer
 * Veranstaltung zugeordnet. Von einer Veranstaltung koennen beliebig viele
 * Auffuehrungen gemacht werden. Zusaetzlich wird fuer jede Auffuehrung ein
 * Hinweis, ein Flag zum bestimmen, ob die Auffuehrung storniert wurde, und eine
 * Preiskategorie (entweder Minimalpreis, Standardpreis oder Maximalpreis)
 * gespeichert.
 *
 */
public class Auffuehrung extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 7953200412305670075L;

	/** identifier field */
	private ticketline.db.AuffuehrungKey comp_id;

	/** persistent field */
	private Boolean storniert;

	/** persistent field */
	private String preis;

	/** nullable persistent field */
	private String hinweis;

	/** nullable persistent field */
	private ticketline.db.Saal saal;

	/** persistent field */
	private ticketline.db.Veranstaltung veranstaltung;

	/** persistent field */
	private Set belegungen;

	/** full constructor */
	public Auffuehrung(ticketline.db.AuffuehrungKey comp_id, boolean storniert,
			String preis, String hinweis, ticketline.db.Saal saal,
			ticketline.db.Veranstaltung veranstaltung, Set belegungen) {
		this.comp_id = comp_id;
		this.storniert = storniert;
		this.preis = preis;
		this.hinweis = hinweis;
		this.saal = saal;
		this.veranstaltung = veranstaltung;
		this.belegungen = belegungen;
	}

	/** default constructor */
	public Auffuehrung() {
		this.comp_id = null;
		this.storniert = null;
		this.preis = null;
		this.hinweis = null;
		this.saal = null;
		this.veranstaltung = null;
		this.belegungen = null;
	}

	/** minimal constructor */
	public Auffuehrung(ticketline.db.AuffuehrungKey comp_id, boolean storniert,
			String preis, ticketline.db.Veranstaltung veranstaltung,
			Set belegungen) {
		this.comp_id = comp_id;
		this.storniert = storniert;
		this.preis = preis;
		this.veranstaltung = veranstaltung;
		this.belegungen = belegungen;
	}

	public ticketline.db.AuffuehrungKey getComp_id() {
		return this.comp_id;
	}

	public void setComp_id(ticketline.db.AuffuehrungKey comp_id) {
		this.comp_id = comp_id;
	}

	/**
	 * true (storniert), false (nicht storniert)
	 */
	public Boolean isStorniert() {
		return this.storniert;
	}

	public void setStorniert(Boolean storniert) {
		this.storniert = storniert;
	}

	/**
	 * Preiskategorie: `0' = Mindestpreis, `1' = Standardpreis, `2' =
	 * Maximalpreis
	 *
	 */
	public String getPreis() {
		return this.preis;
	}

	public void setPreis(String preis) {
		this.preis = preis;
	}

	/**
	 * Hinweis zur Auffuehrung Darbietung einer Veranstaltung an einem
	 * bestimmten Ort zu einer bestimmten Zeit.
	 *
	 */
	public String getHinweis() {
		return this.hinweis;
	}

	public void setHinweis(String hinweis) {
		this.hinweis = hinweis;
	}

	public ticketline.db.Saal getSaal() {
		return this.saal;
	}

	public void setSaal(ticketline.db.Saal saal) {
		this.saal = saal;
	}

	public ticketline.db.Veranstaltung getVeranstaltung() {
		return this.veranstaltung;
	}

	public void setVeranstaltung(ticketline.db.Veranstaltung veranstaltung) {
		this.veranstaltung = veranstaltung;
	}

	public Set getBelegungen() {
		return this.belegungen;
	}

	public void setBelegungen(Set belegungen) {
		this.belegungen = belegungen;
	}

	public String toString() {
		return new ToStringBuilder(this).append("comp_id", getComp_id())
				.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof Auffuehrung))
			return false;
		Auffuehrung castOther = (Auffuehrung) other;
		return new EqualsBuilder().append(this.getComp_id(),
				castOther.getComp_id()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getComp_id()).toHashCode();
	}
	
	public void initSet(){
		if(belegungen == null){
			belegungen = new HashSet<Belegung>();
		}
	}

}
