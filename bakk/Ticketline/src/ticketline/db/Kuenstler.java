package ticketline.db;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Ein Kuenstler ist eine Person, die in irgendeiner Weise an einer
 * Veranstaltung mitgewirkt hat. Dabei kann es sich um Schauspieler, Regisseure,
 * Kameramaenner usw. handeln. In der Entitaet Kuenstler werden die
 * persoenlichen Daten gespeichert. Jeder Kuesnstler hat eine eindeutige Nummer.
 * Zusaetzlich werden noch sein Vorname, Nachname, Titel, das Geschlecht, sein
 * Geburtsdatum und eine Biographie gespeichert
 *
 */
public class Kuenstler extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -4137918784573189760L;

	/** identifier field */
	private Integer kuenstlernr;

	/** persistent field */
	private String nname;

	/** persistent field */
	private String vname;

	/** nullable persistent field */
	private String titel;

	/** persistent field */
	private String geschlecht;

	/** nullable persistent field */
	private Date geburtsdatum;

	/** nullable persistent field */
	private String biographie;

	/** persistent field */
	private Set engagements;

	/** full constructor */
	public Kuenstler(String nname, String vname, String titel,
			String geschlecht, Date geburtsdatum, String biographie,
			Set engagements) {
		this.nname = nname;
		this.vname = vname;
		this.titel = titel;
		this.geschlecht = geschlecht;
		this.geburtsdatum = geburtsdatum;
		this.biographie = biographie;
		this.engagements = engagements;
	}

	/** default constructor */
	public Kuenstler() {
		// do nothing
	}

	/** minimal constructor */
	public Kuenstler(String nname, String vname, String geschlecht,
			Set engagements) {
		this.nname = nname;
		this.vname = vname;
		this.geschlecht = geschlecht;
		this.engagements = engagements;
	}

	public Integer getKuenstlernr() {
		return this.kuenstlernr;
	}

	public void setKuenstlernr(Integer kuenstlernr) {
		this.kuenstlernr = kuenstlernr;
	}

	/**
	 * Nachname oder Kuenstlername
	 */
	public String getNname() {
		return this.nname;
	}

	public void setNname(String nname) {
		this.nname = nname;
	}

	/**
	 * Vorname und/oder Kuenstlername
	 */
	public String getVname() {
		return this.vname;
	}

	public void setVname(String vname) {
		this.vname = vname;
	}

	/**
	 * z.B.: Dr., DI
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
	 * Geburtsdatum kleiner Heute
	 */
	public Date getGeburtsdatum() {
		return this.geburtsdatum;
	}

	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	/**
	 * Biographie des Kuenstlers
	 */
	public String getBiographie() {
		return this.biographie;
	}

	public void setBiographie(String biographie) {
		this.biographie = biographie;
	}

	public Set getEngagements() {
		return this.engagements;
	}

	public void setEngagements(Set engagements) {
		this.engagements = engagements;
	}

	public String toString() {
		return new ToStringBuilder(this)
				.append("kuenstlernr", getKuenstlernr()).toString();
	}

}
