package ticketline.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

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
public class Kategorie extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 3694953274460755957L;

	/** identifier field */
	private ticketline.db.KategorieKey comp_id;

	/** persistent field */
	private BigDecimal preismin;

	/** persistent field */
	private BigDecimal preisstd;

	/** persistent field */
	private BigDecimal preismax;

	/** nullable persistent field */
	private ticketline.db.Saal saal;

	/** persistent field */
	private Set reihen;

	/** full constructor */
	public Kategorie(ticketline.db.KategorieKey comp_id, BigDecimal preismin,
			BigDecimal preisstd, BigDecimal preismax, ticketline.db.Saal saal,
			Set reihen) {
		this.comp_id = comp_id;
		this.preismin = preismin;
		this.preisstd = preisstd;
		this.preismax = preismax;
		this.saal = saal;
		this.reihen = reihen;
	}

	/** default constructor */
	public Kategorie() {
		// do nothing
	}

	/** minimal constructor */
	public Kategorie(ticketline.db.KategorieKey comp_id, BigDecimal preismin,
			BigDecimal preisstd, BigDecimal preismax, Set reihen) {
		this.comp_id = comp_id;
		this.preismin = preismin;
		this.preisstd = preisstd;
		this.preismax = preismax;
		this.reihen = reihen;
	}

	public ticketline.db.KategorieKey getComp_id() {
		return this.comp_id;
	}

	public void setComp_id(ticketline.db.KategorieKey comp_id) {
		this.comp_id = comp_id;
	}

	/**
	 * Minimalpreis* dieser Kategorie >= 0
	 */
	public BigDecimal getPreismin() {
		return this.preismin;
	}

	public void setPreismin(BigDecimal preismin) {
		this.preismin = preismin;
	}

	/**
	 * Standardpreis* dieser Kategorie >= 0
	 */
	public BigDecimal getPreisstd() {
		return this.preisstd;
	}

	public void setPreisstd(BigDecimal preisstd) {
		this.preisstd = preisstd;
	}

	/**
	 * Maximalpreis* dieser Kategorie >= 0
	 */
	public BigDecimal getPreismax() {
		return this.preismax;
	}

	public void setPreismax(BigDecimal preismax) {
		this.preismax = preismax;
	}

	public ticketline.db.Saal getSaal() {
		return this.saal;
	}

	public void setSaal(ticketline.db.Saal saal) {
		this.saal = saal;
	}

	public Set getReihen() {
		return this.reihen;
	}

	public void setReihen(Set reihen) {
		this.reihen = reihen;
	}

	public String toString() {
		return new ToStringBuilder(this).append("comp_id", getComp_id())
				.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof Kategorie))
			return false;
		Kategorie castOther = (Kategorie) other;
		return new EqualsBuilder().append(this.getComp_id(),
				castOther.getComp_id()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getComp_id()).toHashCode();
	}

}
