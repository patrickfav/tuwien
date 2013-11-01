package ticketline.db;

import java.io.Serializable;

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
public class OrtKey extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 7240374500301296277L;

	/** identifier field */
	private String bezeichnung;

	/** identifier field */
	private String ort;

	/** full constructor */
	public OrtKey(String bezeichnung, String ort) {
		this.bezeichnung = bezeichnung;
		this.ort = ort;
	}

	/** default constructor */
	public OrtKey() {
		this.bezeichnung = null;
		this.ort = null;
	}

	public String getBezeichnung() {
		return this.bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getOrt() {
		return this.ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String toString() {
		return new ToStringBuilder(this)
				.append("bezeichnung", getBezeichnung())
				.append("ort", getOrt()).toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof OrtKey))
			return false;
		OrtKey castOther = (OrtKey) other;
		return new EqualsBuilder().append(this.getBezeichnung(),
				castOther.getBezeichnung()).append(this.getOrt(),
				castOther.getOrt()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getBezeichnung()).append(getOrt())
				.toHashCode();
	}

}
