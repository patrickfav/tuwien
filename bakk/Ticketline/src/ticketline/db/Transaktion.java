package ticketline.db;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class Transaktion extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -1109025925662055935L;

	/** identifier field */
	private ticketline.db.TransaktionKey comp_id;

	/** persistent field */
	private Boolean verkauft;

	/** persistent field */
	private Boolean storniert;

	/** nullable persistent field */
	private Integer resnr;

	/** persistent field */
	private BigDecimal preis;

	/** persistent field */
	private Integer startplatz;

	/** persistent field */
	private Integer anzplaetze;

	/** nullable persistent field */
	private String zahlart;

	/** nullable persistent field */
	private ticketline.db.Kunde kunde;

	/** nullable persistent field */
	private ticketline.db.Mitarbeiter mitarbeiter;

	/** persistent field */
	private ticketline.db.Belegung belegung;

	/** persistent field */
	private ticketline.db.Ort ort;

	/** full constructor */
	public Transaktion(ticketline.db.TransaktionKey comp_id, boolean verkauft,
			boolean storniert, Integer resnr, BigDecimal preis, Integer startplatz,
			Integer anzplaetze, String zahlart, ticketline.db.Kunde kunde,
			ticketline.db.Mitarbeiter mitarbeiter,
			ticketline.db.Belegung belegung, ticketline.db.Ort ort) {
		this.comp_id = comp_id;
		this.verkauft = verkauft;
		this.storniert = storniert;
		this.resnr = resnr;
		this.preis = preis;
		this.startplatz = startplatz;
		this.anzplaetze = anzplaetze;
		this.zahlart = zahlart;
		this.kunde = kunde;
		this.mitarbeiter = mitarbeiter;
		this.belegung = belegung;
		this.ort = ort;
	}

	/** default constructor */
	public Transaktion() {
		this.comp_id = null;
		this.verkauft = null;
		this.storniert = null;
		this.resnr = null;
		this.preis = null;
		this.startplatz = null;
		this.anzplaetze = null;
		this.zahlart = null;
		this.kunde = null;
		this.mitarbeiter = null;
		this.belegung = null;
		this.ort = null;
	}

	/** minimal constructor */
	public Transaktion(ticketline.db.TransaktionKey comp_id, boolean verkauft,
			boolean storniert, BigDecimal preis, Integer startplatz,
			Integer anzplaetze, ticketline.db.Belegung belegung,
			ticketline.db.Ort ort) {
		this.comp_id = comp_id;
		this.verkauft = verkauft;
		this.storniert = storniert;
		this.preis = preis;
		this.startplatz = startplatz;
		this.anzplaetze = anzplaetze;
		this.belegung = belegung;
		this.ort = ort;
	}

	public ticketline.db.TransaktionKey getComp_id() {
		return this.comp_id;
	}

	public void setComp_id(ticketline.db.TransaktionKey comp_id) {
		this.comp_id = comp_id;
	}

	/**
	 * verkauft (TRUE) oder nicht verkauft (FALSE)
	 */
	public boolean isVerkauft() {
		return this.verkauft;
	}

	public void setVerkauft(boolean verkauft) {
		this.verkauft = verkauft;
	}

	/**
	 * storniert (TRUE) oder nicht storniert (FALSE)
	 */
	public boolean isStorniert() {
		return this.storniert;
	}

	public void setStorniert(boolean storniert) {
		this.storniert = storniert;
	}

	/**
	 * Nummer mit welcher sich der Kunde die reservierten Karten abholt; 0 <
	 * Reservierungsnummer <= 9999
	 *
	 */
	public Integer getResnr() {
		return this.resnr;
	}

	public void setResnr(Integer resnr) {
		this.resnr = resnr;
	}

	/**
	 * Preis fuer Kategorie * AnzPlaetze * ~TicketCard.Ermaessigung
	 */
	public BigDecimal getPreis() {
		return this.preis;
	}

	public void setPreis(BigDecimal preis) {
		this.preis = preis;
	}

	/**
	 * erster Platz, der reserviert bzw. verkauft wurde; Startplatz > 0
	 */
	public Integer getStartplatz() {
		return this.startplatz;
	}

	public void setStartplatz(Integer startplatz) {
		this.startplatz = startplatz;
	}

	/**
	 * Anzahl der Plaetze vom Startplatz weg; AnzPlaetze > 0
	 */
	public Integer getAnzplaetze() {
		return this.anzplaetze;
	}

	public void setAnzplaetze(Integer anzplaetze) {
		this.anzplaetze = anzplaetze;
	}

	/**
	 * z.B.: bar, TicketCard, VISA...
	 */
	public String getZahlart() {
		return this.zahlart;
	}

	public void setZahlart(String zahlart) {
		this.zahlart = zahlart;
	}

	public ticketline.db.Kunde getKunde() {
		return this.kunde;
	}

	public void setKunde(ticketline.db.Kunde kunde) {
		this.kunde = kunde;
	}

	public ticketline.db.Mitarbeiter getMitarbeiter() {
		return this.mitarbeiter;
	}

	public void setMitarbeiter(ticketline.db.Mitarbeiter mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}

	public ticketline.db.Belegung getBelegung() {
		return this.belegung;
	}

	public void setBelegung(ticketline.db.Belegung belegung) {
		this.belegung = belegung;
	}

	public ticketline.db.Ort getOrt() {
		return this.ort;
	}

	public void setOrt(ticketline.db.Ort ort) {
		this.ort = ort;
	}

	public String toString() {
		return new ToStringBuilder(this).append("comp_id", getComp_id())
				.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof Transaktion))
			return false;
		Transaktion castOther = (Transaktion) other;
		return new EqualsBuilder().append(this.getComp_id(),
				castOther.getComp_id()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getComp_id()).toHashCode();
	}
	
	public Transaktion clone(){
		Transaktion ta = new Transaktion();
		ta.setComp_id(this.getComp_id().clone());
		ta.setAnzplaetze(this.getAnzplaetze());
		ta.setBelegung(this.getBelegung());
		ta.setKunde(this.getKunde());
		ta.setMitarbeiter(this.getMitarbeiter());
		ta.setOrt(this.getOrt());
		ta.setPreis(this.getPreis());
		ta.setResnr(this.getResnr());
		ta.setStartplatz(this.getStartplatz());
		ta.setStorniert(this.isStorniert());
		ta.setVerkauft(this.isVerkauft());
		ta.setZahlart(this.getZahlart());
		return ta;
	}

}
