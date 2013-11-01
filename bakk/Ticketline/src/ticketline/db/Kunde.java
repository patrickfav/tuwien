package ticketline.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * Die Ticketcard ist eine Karte zur Identifikation von Stammkunden und
 * Mitarbeitern. Die Daten zur Identifikation der Person und eine eindeutige
 * Nummer sind in der Entitaet TicketCard gespeichert. Jeder TicketCard ist
 * entweder ein Kunde oder ein Mitarbeiter zugeordnet (ausschliessendes Oder),
 * der die selbe Nummer zur Identifikation bekommt wie die TicketCard. Jeder
 * TicketCard ist als zusaetzliche Information der Ausstellungsort zugeordnet.
 *
 */
public class Kunde extends Ticketcard implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 4438968516066993490L;

	/** nullable persistent field. */
	private String blz;

	/** nullable persistent field. */
	private String kontonr;

	/** persistent field */
	private Boolean ermaechtigung;

	/** nullable persistent field */
	private String kreditkartennr;

	/** nullable persistent field */
	private Date kkgueltigbis;

	/** nullable persistent field */
	private BigDecimal kontostand;

	/** nullable persistent field */
	private BigDecimal kontolimit;

	/** persistent field */
	private BigDecimal ermaessigung;

	/** persistent field */
	private Date tkgueltigbis;

	/** persistent field */
	private Boolean gesperrt;

	/** nullable persistent field */
	private String onlinepwd;

	/** persistent field */
	private String gruppe;

	/** nullable persistent field */
	private String vorlieben;

	/** persistent field */
	private Set bestellungen;

	/** persistent field */
	private Set transaktionen;

	/** full constructor */
	public Kunde(final String typ, final String nname, String vname,
			String titel, String geschlecht, Date geburtsdatum, String strasse,
			String plz, String ort, String telnr, String email,
			ticketline.db.Ort ortverk, String blz, String kontonr,
			boolean ermaechtigung, String kreditkartennr, Date kkgueltigbis,
			BigDecimal kontostand, BigDecimal kontolimit,
			BigDecimal ermaessigung, Date tkgueltigbis, boolean gesperrt,
			String onlinepwd, String gruppe, String vorlieben,
			Set bestellungen, Set transaktionen) {
		super(typ, nname, vname, titel, geschlecht, geburtsdatum, strasse, plz,
				ort, telnr, email, ortverk);
		this.blz = blz;
		this.kontonr = kontonr;
		this.ermaechtigung = ermaechtigung;
		this.kreditkartennr = kreditkartennr;
		this.kkgueltigbis = kkgueltigbis;
		this.kontostand = kontostand;
		this.kontolimit = kontolimit;
		this.ermaessigung = ermaessigung;
		this.tkgueltigbis = tkgueltigbis;
		this.gesperrt = gesperrt;
		this.onlinepwd = onlinepwd;
		this.gruppe = gruppe;
		this.vorlieben = vorlieben;
		this.bestellungen = bestellungen;
		this.transaktionen = transaktionen;
	}

	/** default constructor - initializes everthing nul */
	public Kunde() {
		
		super();
		
		this.blz = null;
		this.kontonr = null;
		this.ermaechtigung = null;
		this.kreditkartennr = null;
		this.kkgueltigbis = null;
		this.kontostand = null;
		this.kontolimit = null;
		this.ermaessigung = null;
		this.tkgueltigbis = null;
		this.gesperrt = null;
		this.onlinepwd = null;
		this.gruppe = null;
		this.vorlieben = null;
		this.bestellungen = null;
		this.transaktionen = null;
	}

	/** minimal constructor */
	public Kunde(String typ, ticketline.db.Ort ortverk, boolean ermaechtigung,
			BigDecimal ermaessigung, Date tkgueltigbis, boolean gesperrt,
			String gruppe, Set bestellungen, Set transaktionen) {
		super(typ, ortverk);
		this.ermaechtigung = ermaechtigung;
		this.ermaessigung = ermaessigung;
		this.tkgueltigbis = tkgueltigbis;
		this.gesperrt = gesperrt;
		this.gruppe = gruppe;
		this.bestellungen = bestellungen;
		this.transaktionen = transaktionen;
	}

	public String getBlz() {
		return this.blz;
	}

	public void setBlz(String blz) {
		this.blz = blz;
	}

	public String getKontonr() {
		return this.kontonr;
	}

	public void setKontonr(String kontonr) {
		this.kontonr = kontonr;
	}

	public boolean isErmaechtigung() {
		return this.ermaechtigung;
	}

	public void setErmaechtigung(boolean ermaechtigung) {
		this.ermaechtigung = ermaechtigung;
	}

	public String getKreditkartennr() {
		return this.kreditkartennr;
	}

	public void setKreditkartennr(String kreditkartennr) {
		this.kreditkartennr = kreditkartennr;
	}

	public Date getKkgueltigbis() {
		return this.kkgueltigbis;
	}

	public void setKkgueltigbis(Date kkgueltigbis) {
		this.kkgueltigbis = kkgueltigbis;
	}

	public BigDecimal getKontostand() {
		return this.kontostand;
	}

	public void setKontostand(BigDecimal kontostand) {
		this.kontostand = kontostand;
	}

	public BigDecimal getKontolimit() {
		return this.kontolimit;
	}

	public void setKontolimit(BigDecimal kontolimit) {
		this.kontolimit = kontolimit;
	}

	public BigDecimal getErmaessigung() {
		return this.ermaessigung;
	}

	public void setErmaessigung(BigDecimal ermaessigung) {
		this.ermaessigung = ermaessigung;
	}

	public Date getTkgueltigbis() {
		return this.tkgueltigbis;
	}

	public void setTkgueltigbis(Date tkgueltigbis) {
		this.tkgueltigbis = tkgueltigbis;
	}

	public boolean isGesperrt() {
		return this.gesperrt;
	}

	public void setGesperrt(boolean gesperrt) {
		this.gesperrt = gesperrt;
	}

	public String getOnlinepwd() {
		return this.onlinepwd;
	}

	public void setOnlinepwd(String onlinepwd) {
		this.onlinepwd = onlinepwd;
	}

	public String getGruppe() {
		return this.gruppe;
	}

	public void setGruppe(String gruppe) {
		this.gruppe = gruppe;
	}

	public String getVorlieben() {
		return this.vorlieben;
	}

	public void setVorlieben(String vorlieben) {
		this.vorlieben = vorlieben;
	}

	public Set getBestellungen() {
		return this.bestellungen;
	}

	public void setBestellungen(Set bestellungen) {
		this.bestellungen = bestellungen;
	}

	public Set getTransaktionen() {
		return this.transaktionen;
	}

	public void setTransaktionen(Set transaktionen) {
		this.transaktionen = transaktionen;
	}

	public String toString() {
		return new ToStringBuilder(this).append("kartennr", getKartennr())
				.toString();
	}
	
	public void initSet(){
		if(transaktionen == null){
			transaktionen = new HashSet<Transaktion>();
		}
	}

}
