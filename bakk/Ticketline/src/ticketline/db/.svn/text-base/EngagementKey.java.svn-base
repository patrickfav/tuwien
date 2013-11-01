package ticketline.db;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Ein Engagement beschreibt die Mitwirkung eines Kuenstlers an einer
 * Veranstaltung. Neben der Nummer des Kuenstlers und den Daten fuer die
 * Veranstaltung, welche zusammen den Primaerschluessel bilden) werden noch die
 * Funktion des Kuenstlers in der Veranstaltung und seine Gage gespeichert.
 *
 */
public class EngagementKey extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1736611738815430070L;

	/** identifier field */
	private Integer kuenstlernr;

	/** identifier field */
	private String bezeichnung;

	/** identifier field */
	private String kategorie;

	/** full constructor */
	public EngagementKey(Integer kuenstlernr, String bezeichnung,
			String kategorie) {
		this.kuenstlernr = kuenstlernr;
		this.bezeichnung = bezeichnung;
		this.kategorie = kategorie;
	}

	/** default constructor */
	public EngagementKey() {
		// do nothing
	}

	public Integer getKuenstlernr() {
		return this.kuenstlernr;
	}

	public void setKuenstlernr(Integer kuenstlernr) {
		this.kuenstlernr = kuenstlernr;
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
				.append("kuenstlernr", getKuenstlernr()).append("bezeichnung",
						getBezeichnung()).append("kategorie", getKategorie())
				.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof EngagementKey))
			return false;
		EngagementKey castOther = (EngagementKey) other;
		return new EqualsBuilder().append(this.getKuenstlernr(),
				castOther.getKuenstlernr()).append(this.getBezeichnung(),
				castOther.getBezeichnung()).append(this.getKategorie(),
				castOther.getKategorie()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getKuenstlernr()).append(
				getBezeichnung()).append(getKategorie()).toHashCode();
	}

}
