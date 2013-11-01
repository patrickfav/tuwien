package ticketline.db;

import java.io.Serializable;
import java.util.Date;

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
public class AuffuehrungKey extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -7430993624450688112L;

	/** identifier field */
	private Date datumuhrzeit;

	/** identifier field */
	private String saalbez;

	/** identifier field */
	private String ortbez;

	/** identifier field */
	private String ort;

	/** full constructor */
	public AuffuehrungKey(Date datumuhrzeit, String saalbez, String ortbez,
			String ort) {
		this.datumuhrzeit = datumuhrzeit;
		this.saalbez = saalbez;
		this.ortbez = ortbez;
		this.ort = ort;
	}

	/** default constructor */
	public AuffuehrungKey() {
		this.datumuhrzeit = null;
		this.saalbez = null;
		this.ortbez = null;
		this.ort = null;
	}

	/**
	 * Beginnzeit und Datum der Auffuehrung
	 */
	public Date getDatumuhrzeit() {
		return this.datumuhrzeit;
	}

	public void setDatumuhrzeit(Date datumuhrzeit) {
		this.datumuhrzeit = datumuhrzeit;
	}

	/**
	 * ~Saal.Bezeichnung
	 */
	public String getSaalbez() {
		return this.saalbez;
	}

	public void setSaalbez(String saalbez) {
		this.saalbez = saalbez;
	}

	/**
	 * ~Ort.Bezeichnung
	 */
	public String getOrtbez() {
		return this.ortbez;
	}

	public void setOrtbez(String ortbez) {
		this.ortbez = ortbez;
	}

	/**
	 * ~Ort.Ort
	 */
	public String getOrt() {
		return this.ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String toString() {
		return new ToStringBuilder(this).append("datumuhrzeit",
				getDatumuhrzeit()).append("saalbez", getSaalbez()).append(
				"ortbez", getOrtbez()).append("ort", getOrt()).toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof AuffuehrungKey))
			return false;
		AuffuehrungKey castOther = (AuffuehrungKey) other;
		return new EqualsBuilder().append(this.getDatumuhrzeit(),
				castOther.getDatumuhrzeit()).append(this.getSaalbez(),
				castOther.getSaalbez()).append(this.getOrtbez(),
				castOther.getOrtbez())
				.append(this.getOrt(), castOther.getOrt()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getDatumuhrzeit()).append(
				getSaalbez()).append(getOrtbez()).append(getOrt()).toHashCode();
	}

}
