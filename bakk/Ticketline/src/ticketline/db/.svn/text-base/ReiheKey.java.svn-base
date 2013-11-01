package ticketline.db;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Jeder Saal eines Auffuehrungsortes besteht aus mehreren Reihen, wobei die
 * Reihen nicht direkt den Saelen zugeordnet sind sondern den Kategorien in den
 * Saelen. Jede Reihe wird eindeutig durch die Primaerschluesselfelder der
 * zugehoerigen Kategorie und der eigenen Bezeichnung (dem Name) bestimmt. Fuer
 * jede Reihe wird gespeichert, wie viele Plaetze sie hat, und welche Nummer der
 * erste Platz der Reihe hat. Ausserdem wird gespeichert ob es sich um eine
 * Reihe mit Sitzplaetzen oder mit Stehplaetzen handelt.
 *
 */
public class ReiheKey extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -5641967473090205295L;

	/** identifier field */
	private String bezeichnung;

	/** identifier field */
	private String kategoriebez;

	/** identifier field */
	private String saalbez;

	/** identifier field */
	private String ortbez;

	/** identifier field */
	private String ort;

	/** full constructor */
	public ReiheKey(String bezeichnung, String kategoriebez, String saalbez,
			String ortbez, String ort) {
		this.bezeichnung = bezeichnung;
		this.kategoriebez = kategoriebez;
		this.saalbez = saalbez;
		this.ortbez = ortbez;
		this.ort = ort;
	}

	/** default constructor */
	public ReiheKey() {
		// do nothing
	}

	public String getBezeichnung() {
		return this.bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getKategoriebez() {
		return this.kategoriebez;
	}

	public void setKategoriebez(String kategoriebez) {
		this.kategoriebez = kategoriebez;
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
				.append("bezeichnung", getBezeichnung()).append("kategoriebez",
						getKategoriebez()).append("saalbez", getSaalbez())
				.append("ortbez", getOrtbez()).append("ort", getOrt())
				.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof ReiheKey))
			return false;
		ReiheKey castOther = (ReiheKey) other;
		return new EqualsBuilder().append(this.getBezeichnung(),
				castOther.getBezeichnung()).append(this.getKategoriebez(),
				castOther.getKategoriebez()).append(this.getSaalbez(),
				castOther.getSaalbez()).append(this.getOrtbez(),
				castOther.getOrtbez())
				.append(this.getOrt(), castOther.getOrt()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getBezeichnung()).append(
				getKategoriebez()).append(getSaalbez()).append(getOrtbez())
				.append(getOrt()).toHashCode();
	}

}
