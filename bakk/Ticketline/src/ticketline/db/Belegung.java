package ticketline.db;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Eine Belegung gibt die Sitzplatz- beziehungsweise Stehplatzbelegung einer
 * Auffuehrung fuer eine spezielle Reihe an. Die Daten fuer die Reihe und die
 * Auffuehrung bestimmen die Belegung eindeutig. Zusaetzlich wird noch die
 * Anzahl der freien, der reservierten und der verkauften Plaetze der Reihe
 * angegeben. Optional kann auch ein Belegungsstring fuer die Plaetze der Reihe
 * angegeben werden. Jedes Zeichen dieses Strings steht fuer einen Platz in der
 * Reihe ('F' frei, 'R' reserviert und 'V' verkauft). Dieser String ist fuer
 * Stehplatzreihen natuerlich ueberfluessig.
 *
 */
public class Belegung extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 8231117680297707232L;

	/** identifier field */
	private ticketline.db.BelegungKey comp_id;

	/** nullable persistent field */
	private String belegung;

	/** persistent field */
	private int anzfrei;

	/** persistent field */
	private int anzres;

	/** persistent field */
	private int anzverk;

	/** nullable persistent field */
	private ticketline.db.Reihe reihe;

	/** nullable persistent field */
	private ticketline.db.Auffuehrung auffuehrung;

	/** persistent field */
	private Set transaktionen;

	/** full constructor */
	public Belegung(ticketline.db.BelegungKey comp_id, String belegung,
			int anzfrei, int anzres, int anzverk, ticketline.db.Reihe reihe,
			ticketline.db.Auffuehrung auffuehrung, Set transaktionen) {
		this.comp_id = comp_id;
		this.belegung = belegung;
		this.anzfrei = anzfrei;
		this.anzres = anzres;
		this.anzverk = anzverk;
		this.reihe = reihe;
		this.auffuehrung = auffuehrung;
		this.transaktionen = transaktionen;
	}

	/** default constructor */
	public Belegung() {
		// do nothing
	}

	/** minimal constructor */
	public Belegung(ticketline.db.BelegungKey comp_id, int anzfrei, int anzres,
			int anzverk, Set transaktionen) {
		this.comp_id = comp_id;
		this.anzfrei = anzfrei;
		this.anzres = anzres;
		this.anzverk = anzverk;
		this.transaktionen = transaktionen;
	}

	public ticketline.db.BelegungKey getComp_id() {
		return this.comp_id;
	}

	public void setComp_id(ticketline.db.BelegungKey comp_id) {
		this.comp_id = comp_id;
	}

	/**
	 * Dieses Feld beschreibt den Belegungszustand der Reihe*
	 */
	public String getBelegung() {
		return this.belegung;
	}

	public void setBelegung(String belegung) {
		this.belegung = belegung;
	}

	/**
	 * Anzahl freier Plaetze >=0
	 */
	public int getAnzfrei() {
		return this.anzfrei;
	}

	public void setAnzfrei(int anzfrei) {
		this.anzfrei = anzfrei;
	}

	/**
	 * Anzahl reservierter Plaetze >=0
	 */
	public int getAnzres() {
		return this.anzres;
	}

	public void setAnzres(int anzres) {
		this.anzres = anzres;
	}

	/**
	 * Anzahl verkaufter Plaetze >=0
	 */
	public int getAnzverk() {
		return this.anzverk;
	}

	public void setAnzverk(int anzverk) {
		this.anzverk = anzverk;
	}

	public ticketline.db.Reihe getReihe() {
		return this.reihe;
	}

	public void setReihe(ticketline.db.Reihe reihe) {
		this.reihe = reihe;
	}

	public ticketline.db.Auffuehrung getAuffuehrung() {
		return this.auffuehrung;
	}

	public void setAuffuehrung(ticketline.db.Auffuehrung auffuehrung) {
		this.auffuehrung = auffuehrung;
	}

	public Set getTransaktionen() {
		return this.transaktionen;
	}

	public void setTransaktionen(Set transaktionen) {
		this.transaktionen = transaktionen;
	}

	public String toString() {
		return new ToStringBuilder(this).append("comp_id", getComp_id())
				.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof Belegung))
			return false;
		Belegung castOther = (Belegung) other;
		return new EqualsBuilder().append(this.getComp_id(),
				castOther.getComp_id()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getComp_id()).toHashCode();
	}

	public Belegung clone() {
		Belegung bg = new Belegung();
		bg.setComp_id(this.getComp_id().clone());
		bg.setAnzfrei(this.getAnzfrei());
		bg.setAnzres(this.getAnzres());
		bg.setAnzverk(this.getAnzverk());
		bg.setAuffuehrung(this.getAuffuehrung());
		bg.setBelegung(this.getBelegung());
		bg.setReihe(this.getReihe());
		bg.setTransaktionen(this.getTransaktionen());
		return bg;
	}
	
	public void initSet(){
		if(transaktionen == null){
			transaktionen = new HashSet<Transaktion>();
		}
	}
}
