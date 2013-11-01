package ticketline.db;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class Engagement extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 2423452444675834992L;

	/** identifier field */
	private ticketline.db.EngagementKey comp_id;

	/** nullable persistent field */
	private String funktion;

	/** nullable persistent field */
	private BigDecimal gage;

	/** nullable persistent field */
	private ticketline.db.Kuenstler kuenstler;

	/** nullable persistent field */
	private ticketline.db.Veranstaltung veranstaltung;

	/** full constructor */
	public Engagement(ticketline.db.EngagementKey comp_id, String funktion,
			BigDecimal gage, ticketline.db.Kuenstler kuenstler,
			ticketline.db.Veranstaltung veranstaltung) {
		this.comp_id = comp_id;
		this.funktion = funktion;
		this.gage = gage;
		this.kuenstler = kuenstler;
		this.veranstaltung = veranstaltung;
	}

	/** default constructor */
	public Engagement() {
		// do nothing
	}

	/** minimal constructor */
	public Engagement(ticketline.db.EngagementKey comp_id) {
		this.comp_id = comp_id;
	}

	public ticketline.db.EngagementKey getComp_id() {
		return this.comp_id;
	}

	public void setComp_id(ticketline.db.EngagementKey comp_id) {
		this.comp_id = comp_id;
	}

	/**
	 * Schauspieler, Regie, etc.
	 */
	public String getFunktion() {
		return this.funktion;
	}

	public void setFunktion(String funktion) {
		this.funktion = funktion;
	}

	/**
	 * Gage >= 0
	 */
	public BigDecimal getGage() {
		return this.gage;
	}

	public void setGage(BigDecimal gage) {
		this.gage = gage;
	}

	public ticketline.db.Kuenstler getKuenstler() {
		return this.kuenstler;
	}

	public void setKuenstler(ticketline.db.Kuenstler kuenstler) {
		this.kuenstler = kuenstler;
	}

	public ticketline.db.Veranstaltung getVeranstaltung() {
		return this.veranstaltung;
	}

	public void setVeranstaltung(ticketline.db.Veranstaltung veranstaltung) {
		this.veranstaltung = veranstaltung;
	}

	public String toString() {
		return new ToStringBuilder(this).append("comp_id", getComp_id())
				.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof Engagement))
			return false;
		Engagement castOther = (Engagement) other;
		return new EqualsBuilder().append(this.getComp_id(),
				castOther.getComp_id()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getComp_id()).toHashCode();
	}

}
