package ticketline.db;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Eine Transaktion dokumentiert eine aktuelle Reservierung oder einen Verkauf,
 * bzw. auch eine bereits stornierte Reservierung oder einen stornierten Verkauf
 * fuer statistische Zwecke. Bei Reservierung bzw. Kauf von Karten in mehreren
 * Kategorien oder Reihen wird jeweils eine eigene Transaktion angelegt. Jede
 * Transaktion ist einer Belegung zugeordnet. Man kann also mehrere direkt
 * hintereinander liegende Plaetze auf einmal reservieren bzw. kaufen. Die
 * Transaktion wird durch die Nummer des Kunden, die Nummer des Mitarbeiters,
 * das Datum der Transaktion und der Uhrzeit derselben eindeutig identifiziert.
 * Wenn Plaetze reserviert werden, wird automatisch vom System eine
 * Reservierungsnummer vergeben.
 *
 */
public class TransaktionKey extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -7238464478186111513L;

	/** identifier field */
	private Date datumuhrzeit;

	/** identifier field */
	private Integer kundennr;

	/** identifier field */
	private Integer mitarbeiternr;

	/** full constructor */
	public TransaktionKey(Date datumuhrzeit, Integer kundennr,
			Integer mitarbeiternr) {
		this.datumuhrzeit = datumuhrzeit;
		this.kundennr = kundennr;
		this.mitarbeiternr = mitarbeiternr;
	}

	/** default constructor */
	public TransaktionKey() {
		// do nothing
	}

	public Date getDatumuhrzeit() {
		return this.datumuhrzeit;
	}

	public void setDatumuhrzeit(Date datumuhrzeit) {
		this.datumuhrzeit = datumuhrzeit;
	}

	public Integer getKundennr() {
		return this.kundennr;
	}

	public void setKundennr(Integer kundennr) {
		this.kundennr = kundennr;
	}

	public Integer getMitarbeiternr() {
		return this.mitarbeiternr;
	}

	public void setMitarbeiternr(Integer mitarbeiternr) {
		this.mitarbeiternr = mitarbeiternr;
	}

	public String toString() {
		return new ToStringBuilder(this).append("datumuhrzeit",
				getDatumuhrzeit()).append("kundennr", getKundennr()).append(
				"mitarbeiternr", getMitarbeiternr()).toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof TransaktionKey))
			return false;
		TransaktionKey castOther = (TransaktionKey) other;
		return new EqualsBuilder().append(this.getDatumuhrzeit(),
				castOther.getDatumuhrzeit()).append(this.getKundennr(),
				castOther.getKundennr()).append(this.getMitarbeiternr(),
				castOther.getMitarbeiternr()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getDatumuhrzeit()).append(
				getKundennr()).append(getMitarbeiternr()).toHashCode();
	}
	
	public TransaktionKey clone(){
		TransaktionKey tk = new TransaktionKey();
		tk.setDatumuhrzeit(new Date());
		tk.setKundennr(this.getKundennr());
		tk.setMitarbeiternr(this.getMitarbeiternr());
		return tk;
	}

}
