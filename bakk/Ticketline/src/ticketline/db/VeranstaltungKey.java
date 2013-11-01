package ticketline.db;

import java.io.Serializable;

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
public class VeranstaltungKey extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -1961242934939339476L;

	/** identifier field */
	private String bezeichnung;

	/** identifier field */
	private String kategorie;

	/** full constructor */
	public VeranstaltungKey(String bezeichnung, String kategorie) {
		this.bezeichnung = bezeichnung;
		this.kategorie = kategorie;
	}

	/** default constructor */
	public VeranstaltungKey() {
		this.bezeichnung = null;
		this.kategorie = null;
	}

	public String getBezeichnung() {
		return this.bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getKategorie() {
		return this.kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	public String toString() {
		return new ToStringBuilder(this)
				.append("bezeichnung", getBezeichnung()).append("kategorie",
						getKategorie()).toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof VeranstaltungKey))
			return false;
		VeranstaltungKey castOther = (VeranstaltungKey) other;
		return new EqualsBuilder().append(this.getBezeichnung(),
				castOther.getBezeichnung()).append(this.getKategorie(),
				castOther.getKategorie()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getBezeichnung()).append(
				getKategorie()).toHashCode();
	}

}
