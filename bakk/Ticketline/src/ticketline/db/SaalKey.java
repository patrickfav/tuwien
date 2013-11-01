package ticketline.db;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Jeder Auffuehrungsort muss mindestens einen Saal beherbergen. Auffuehrungen
 * finden in Saelen statt. Jeder Saal ist weiters in Kategorien und diese
 * wiederum in Reihen eingeteilt. Ein Saal ist eindeutig durch seine Bezeichnung
 * (dem Namen) und den Primaerschluesselfeldern des zugehoerigen Ortes bestimmt.
 * Jeder Saal hat einen zugeordneten Typ wie Kino, Oper, Halle... und
 * zusaetzlich werden noch die Kosten pro Tag fuer die Benutzung und die
 * Maximalzahl der Plaetze gespeichert.
 *
 */
public class SaalKey extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 5933486265704292299L;

	/** identifier field */
	private String bezeichnung;

	/** identifier field */
	private String ortbez;

	/** identifier field */
	private String ort;

	/** full constructor */
	public SaalKey(String bezeichnung, String ortbez, String ort) {
		this.bezeichnung = bezeichnung;
		this.ortbez = ortbez;
		this.ort = ort;
	}

	/** default constructor */
	public SaalKey() {
		this.bezeichnung = null;
		this.ortbez = null;
		this.ort = null;
	}

	public String getBezeichnung() {
		return this.bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
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
				.append("bezeichnung", getBezeichnung()).append("ortbez",
						getOrtbez()).append("ort", getOrt()).toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof SaalKey))
			return false;
		SaalKey castOther = (SaalKey) other;
		return new EqualsBuilder().append(this.getBezeichnung(),
				castOther.getBezeichnung()).append(this.getOrtbez(),
				castOther.getOrtbez())
				.append(this.getOrt(), castOther.getOrt()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getBezeichnung()).append(
				getOrtbez()).append(getOrt()).toHashCode();
	}

}
