package ticketline.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

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
public class Saal extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -3634135667841924175L;

	/** identifier field */
	private ticketline.db.SaalKey comp_id;

	/** persistent field */
	private String typ;

	/** persistent field */
	private Integer anzplaetze;

	/** nullable persistent field */
	private BigDecimal kostenprotag;

	/** nullable persistent field */
	private ticketline.db.Ort ort;

	/** persistent field */
	private Set auffuehrungen;

	/** persistent field */
	private Set kategorien;

	/** full constructor */
	public Saal(ticketline.db.SaalKey comp_id, String typ, Integer anzplaetze,
			BigDecimal kostenprotag, ticketline.db.Ort ort, Set auffuehrungen,
			Set kategorien) {
		this.comp_id = comp_id;
		this.typ = typ;
		this.anzplaetze = anzplaetze;
		this.kostenprotag = kostenprotag;
		this.ort = ort;
		this.auffuehrungen = auffuehrungen;
		this.kategorien = kategorien;
	}

	/** default constructor */
	public Saal() {
		this.comp_id = null;
		this.typ = null;
		this.anzplaetze = null;
		this.kostenprotag = null;
		this.ort = null;
		this.auffuehrungen = null;
		this.kategorien = null;
	}

	/** minimal constructor */
	public Saal(ticketline.db.SaalKey comp_id, String typ, Integer anzplaetze,
			Set auffuehrungen, Set kategorien) {
		this.comp_id = comp_id;
		this.typ = typ;
		this.anzplaetze = anzplaetze;
		this.auffuehrungen = auffuehrungen;
		this.kategorien = kategorien;
	}

	public ticketline.db.SaalKey getComp_id() {
		return this.comp_id;
	}

	public void setComp_id(ticketline.db.SaalKey comp_id) {
		this.comp_id = comp_id;
	}

	/**
	 * z.B.: Kino, Halle
	 */
	public String getTyp() {
		return this.typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	/**
	 * Anzahl der Plaetze > 0
	 */
	public Integer getAnzplaetze() {
		return this.anzplaetze;
	}

	public void setAnzplaetze(Integer anzplaetze) {
		this.anzplaetze = anzplaetze;
	}

	/**
	 * Kosten pro Tag >= 0
	 */
	public BigDecimal getKostenprotag() {
		return this.kostenprotag;
	}

	public void setKostenprotag(BigDecimal kostenprotag) {
		this.kostenprotag = kostenprotag;
	}

	public ticketline.db.Ort getOrt() {
		return this.ort;
	}

	public void setOrt(ticketline.db.Ort ort) {
		this.ort = ort;
	}

	public Set getAuffuehrungen() {
		return this.auffuehrungen;
	}

	public void setAuffuehrungen(Set auffuehrungen) {
		this.auffuehrungen = auffuehrungen;
	}

	public Set getKategorien() {
		return this.kategorien;
	}

	public void setKategorien(Set kategorien) {
		this.kategorien = kategorien;
	}

	public String toString() {
		return new ToStringBuilder(this).append("comp_id", getComp_id())
				.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof Saal))
			return false;
		Saal castOther = (Saal) other;
		return new EqualsBuilder().append(this.getComp_id(),
				castOther.getComp_id()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getComp_id()).toHashCode();
	}

}
