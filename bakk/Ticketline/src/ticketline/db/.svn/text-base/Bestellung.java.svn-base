package ticketline.db;

import java.io.Serializable;

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
public class Bestellung extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -2047473610304940961L;

	/** identifier field */
	private ticketline.db.BestellungKey comp_id;

	/** persistent field */
	private int menge;

	/** persistent field */
	private String zahlart;

	/** nullable persistent field */
	private ticketline.db.Artikel artikel;

	/** nullable persistent field */
	private ticketline.db.Kunde kunde;

	/** full constructor */
	public Bestellung(ticketline.db.BestellungKey comp_id, int menge,
			String zahlart, ticketline.db.Artikel artikel,
			ticketline.db.Kunde kunde) {
		this.comp_id = comp_id;
		this.menge = menge;
		this.zahlart = zahlart;
		this.artikel = artikel;
		this.kunde = kunde;
	}

	/** default constructor */
	public Bestellung() {
		// do nothing
	}

	/** minimal constructor */
	public Bestellung(ticketline.db.BestellungKey comp_id, int menge,
			String zahlart) {
		this.comp_id = comp_id;
		this.menge = menge;
		this.zahlart = zahlart;
	}

	public ticketline.db.BestellungKey getComp_id() {
		return this.comp_id;
	}

	public void setComp_id(ticketline.db.BestellungKey comp_id) {
		this.comp_id = comp_id;
	}

	/**
	 * Anzahl der Artikel > 0
	 */
	public int getMenge() {
		return this.menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}

	/**
	 * z.B.: bar, TicketCard, VISA, per Nachname
	 */
	public String getZahlart() {
		return this.zahlart;
	}

	public void setZahlart(String zahlart) {
		this.zahlart = zahlart;
	}

	public ticketline.db.Artikel getArtikel() {
		return this.artikel;
	}

	public void setArtikel(ticketline.db.Artikel artikel) {
		this.artikel = artikel;
	}

	public ticketline.db.Kunde getKunde() {
		return this.kunde;
	}

	public void setKunde(ticketline.db.Kunde kunde) {
		this.kunde = kunde;
	}

	public String toString() {
		return new ToStringBuilder(this).append("comp_id", getComp_id())
				.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof Bestellung))
			return false;
		Bestellung castOther = (Bestellung) other;
		return new EqualsBuilder().append(this.getComp_id(),
				castOther.getComp_id()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getComp_id()).toHashCode();
	}

}
