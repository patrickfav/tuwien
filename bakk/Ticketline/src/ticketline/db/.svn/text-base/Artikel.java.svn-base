package ticketline.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Fuer jede Veranstaltung des Systems koennen Werbeartikel definiert werden.
 * Diese Artikel werden dann im Online-Shop und in den Ticketline-Kassen
 * verkauft. Dies Artikel sind eindeutig durch eine Nummer identifiziert und
 * werden durch eine Kurzbezeichnung (Name des Artikels) und eine Beschreibung
 * naeher identifiziert. Jeder Artikel ist genau einer Kategorie zugeordnet: '0' =
 * T-Shirt '1' = Poster, '2' = CD/LP, '3' = Video/DVD, '4' = Sonstiges.
 *
 */
public class Artikel extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 5239407186115538897L;

	/** identifier field */
	private Integer artikelnr;

	/** persistent field */
	private String kurzbezeichnung;

	/** nullable persistent field */
	private String beschreibung;

	/** persistent field */
	private BigDecimal preis;

	/** persistent field */
	private String kategorie;

	/** nullable persistent field */
	private String abbildung;

	/** persistent field */
	private ticketline.db.Veranstaltung veranstaltung;

	/** persistent field */
	private Set bestellungen;

	/** full constructor */
	public Artikel(String kurzbezeichnung, String beschreibung,
			BigDecimal preis, String kategorie, String abbildung,
			ticketline.db.Veranstaltung veranstaltung, Set bestellungen) {
		this.kurzbezeichnung = kurzbezeichnung;
		this.beschreibung = beschreibung;
		this.preis = preis;
		this.kategorie = kategorie;
		this.abbildung = abbildung;
		this.veranstaltung = veranstaltung;
		this.bestellungen = bestellungen;
	}

	/** default constructor */
	public Artikel() {
		// do nothing
	}

	/** minimal constructor */
	public Artikel(String kurzbezeichnung, BigDecimal preis, String kategorie,
			ticketline.db.Veranstaltung veranstaltung, Set bestellungen) {
		this.kurzbezeichnung = kurzbezeichnung;
		this.preis = preis;
		this.kategorie = kategorie;
		this.veranstaltung = veranstaltung;
		this.bestellungen = bestellungen;
	}

	public Integer getArtikelnr() {
		return this.artikelnr;
	}

	public void setArtikelnr(Integer artikelnr) {
		this.artikelnr = artikelnr;
	}

	/**
	 * Name des Artikels
	 */
	public String getKurzbezeichnung() {
		return this.kurzbezeichnung;
	}

	public void setKurzbezeichnung(String kurzbezeichnung) {
		this.kurzbezeichnung = kurzbezeichnung;
	}

	/**
	 * Beschreibung des Artikels
	 */
	public String getBeschreibung() {
		return this.beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	/**
	 * `0' = T_Shirt, `1' = Poster, `2' = CD/LP, '3'= Video/DVD, '4' =
	 * Sonstiges.
	 */
	public BigDecimal getPreis() {
		return this.preis;
	}

	public void setPreis(BigDecimal preis) {
		this.preis = preis;
	}

	/**
	 * Preis des Artikels; Preis >= 0
	 */
	public String getKategorie() {
		return this.kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	/**
	 * Speicherort eines Bilds des Artikels
	 */
	public String getAbbildung() {
		return this.abbildung;
	}

	public void setAbbildung(String abbildung) {
		this.abbildung = abbildung;
	}

	public ticketline.db.Veranstaltung getVeranstaltung() {
		return this.veranstaltung;
	}

	public void setVeranstaltung(ticketline.db.Veranstaltung veranstaltung) {
		this.veranstaltung = veranstaltung;
	}

	public Set getBestellungen() {
		return this.bestellungen;
	}

	public void setBestellungen(Set bestellungen) {
		this.bestellungen = bestellungen;
	}

	public String toString() {
		return new ToStringBuilder(this).append("artikelnr", getArtikelnr())
				.toString();
	}

}
