package ticketline.db;

import java.io.Serializable;
import java.util.Date;
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
public class Mitarbeiter extends Ticketcard implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 3949640748689103253L;

	/** persistent field */
	private String blz;

	/** persistent field */
	private String kontonr;

	/** persistent field */
	private String pin;

	/** persistent field */
	private String berechtigung;

	/** persistent field */
	private String passwort;

	/** persistent field */
	private Set transaktionen;

	/** persistent field */
	private String username;

	/** full constructor */
	public Mitarbeiter(String typ, String nname, String vname, String titel,
			String geschlecht, Date geburtsdatum, String strasse, String plz,
			String ort, String telnr, String email, ticketline.db.Ort ortverk,
			String blz, String kontonr, String pin, String berechtigung,
			String passwort, Set transaktionen, String username) {
		super(typ, nname, vname, titel, geschlecht, geburtsdatum, strasse, plz,
				ort, telnr, email, ortverk);
		this.blz = blz;
		this.kontonr = kontonr;
		this.pin = pin;
		this.berechtigung = berechtigung;
		this.passwort = passwort;
		this.transaktionen = transaktionen;
		this.username = username;
	}

	/** default constructor */
	public Mitarbeiter() {
		// do nothing
	}

	/** minimal constructor */
	public Mitarbeiter(String typ, ticketline.db.Ort ortverk, String blz,
			String kontonr, String pin, String berechtigung, String passwort,
			Set transaktionen, String username) {
		super(typ, ortverk);
		this.blz = blz;
		this.kontonr = kontonr;
		this.pin = pin;
		this.berechtigung = berechtigung;
		this.passwort = passwort;
		this.transaktionen = transaktionen;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getPin() {
		return this.pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getBerechtigung() {
		return this.berechtigung;
	}

	public void setBerechtigung(String berechtigung) {
		this.berechtigung = berechtigung;
	}

	public String getPasswort() {
		return this.passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
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

}
