package ticketline.db;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Eine Bestellung dokumentiert einen Verkauf eines Werbeartikels fuer eine
 * Veranstaltung an einen Kunden. Fuer jeden gekauften Artikel muss ein eigener
 * Datensatz angelegt werden, auch wenn der Kunde mehrere Artikel auf einmal
 * kauft. Die Bestellung ist durch das Datum des Verkaufs, die Uhrzeit des
 * Verkaufs, die Daten des Artikels und die Daten des Kunden eindeutig bestimmt.
 * Zu jedem bestellten Artikel wird noch die gekaufte Menge und die Zahlart
 * (also bar, Ticketcard, VISA...) angegeben.
 *
 */
public class BestellungKey extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -4207325858581755682L;

	/** identifier field */
	private Date datumuhrzeit;

	/** identifier field */
	private Integer artikelnr;

	/** identifier field */
	private Integer kartennr;

	/** full constructor */
	public BestellungKey(Date datumuhrzeit, Integer artikelnr, Integer kartennr) {
		this.datumuhrzeit = datumuhrzeit;
		this.artikelnr = artikelnr;
		this.kartennr = kartennr;
	}

	/** default constructor */
	public BestellungKey() {
		// do nothing
	}

	/**
	 * eindeutige Bestellzeit
	 */
	public Date getDatumuhrzeit() {
		return this.datumuhrzeit;
	}

	public void setDatumuhrzeit(Date datumuhrzeit) {
		this.datumuhrzeit = datumuhrzeit;
	}

	/**
	 * ~Artikel.ArtikelNr
	 */
	public Integer getArtikelnr() {
		return this.artikelnr;
	}

	public void setArtikelnr(Integer artikelnr) {
		this.artikelnr = artikelnr;
	}

	/**
	 * ~Kunde.KartenNr
	 */
	public Integer getKartennr() {
		return this.kartennr;
	}

	public void setKartennr(Integer kartennr) {
		this.kartennr = kartennr;
	}

	public String toString() {
		return new ToStringBuilder(this).append("datumuhrzeit",
				getDatumuhrzeit()).append("artikelnr", getArtikelnr()).append(
				"kartennr", getKartennr()).toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof BestellungKey))
			return false;
		BestellungKey castOther = (BestellungKey) other;
		return new EqualsBuilder().append(this.getDatumuhrzeit(),
				castOther.getDatumuhrzeit()).append(this.getArtikelnr(),
				castOther.getArtikelnr()).append(this.getKartennr(),
				castOther.getKartennr()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getDatumuhrzeit()).append(
				getArtikelnr()).append(getKartennr()).toHashCode();
	}

}
