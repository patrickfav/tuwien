package ticketline.db;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Jeder Veranstaltungssaal besteht aus mindestens einer im Regelfall aber aus
 * mehreren Kategorien. Eine Kategorie ist nur mit ihrer Bezeichnung (also dem
 * Namen der Kategorie) und den Primaerschluesselfeldern des zugehoerigen Saales
 * eindeutig definiert. Fuer jede Kategorie gibt es weiters einen Minimalpreis,
 * einen Standardpreis und einen Maximalpreis. Fuer jede Auffuehrung in einem
 * Saal kann man eine Preisklasse (Minimum, Standard, Maximum) festlegen. Der
 * Preis fuer die Karten berechnet sich dann aus den Preisen in den Kategorien.
 *
 */
public class KategorieKey extends Entity implements Serializable {

	/**u
	 * UID.
	 */
	private static final long serialVersionUID = 5004908082090870318L;

	/** identifier field */
	private String bezeichnung;

	/** identifier field */
	private String saalbez;

	/** identifier field */
	private String ortbez;

	/** identifier field */
	private String ort;

	/** full constructor */
	public KategorieKey(String bezeichnung, String saalbez, String ortbez,
			String ort) {
		this.bezeichnung = bezeichnung;
		this.saalbez = saalbez;
		this.ortbez = ortbez;
		this.ort = ort;
	}

	/** default constructor */
	public KategorieKey() {
		// do nothing
	}

	public String getBezeichnung() {
		return this.bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getSaalbez() {
		return this.saalbez;
	}

	public void setSaalbez(String saalbez) {
		this.saalbez = saalbez;
	}

	public String getOrtbez() {
		return this.ortbez;
	}

	public void setOrtbez(String ortbez) {
		this.ortbez = ortbez;
	}

	public String getOrt() {
		return this.ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String toString() {
		return new ToStringBuilder(this)
				.append("bezeichnung", getBezeichnung()).append("saalbez",
						getSaalbez()).append("ortbez", getOrtbez()).append(
						"ort", getOrt()).toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof KategorieKey))
			return false;
		KategorieKey castOther = (KategorieKey) other;
		return new EqualsBuilder().append(this.getBezeichnung(),
				castOther.getBezeichnung()).append(this.getSaalbez(),
				castOther.getSaalbez()).append(this.getOrtbez(),
				castOther.getOrtbez())
				.append(this.getOrt(), castOther.getOrt()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getBezeichnung()).append(
				getSaalbez()).append(getOrtbez()).append(getOrt()).toHashCode();
	}

}
