package ticketline.db;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Jeder Saal eines Auffuehrungsortes besteht aus mehreren Reihen, wobei die
 * Reihen nicht direkt den Saelen zugeordnet sind sondern den Kategorien in den
 * Saelen. Jede Reihe wird eindeutig durch die Primaerschluesselfelder der
 * zugehoerigen Kategorie und der eigenen Bezeichnung (dem Name) bestimmt. Fuer
 * jede Reihe wird gespeichert, wie viele Plaetze sie hat, und welche Nummer der
 * erste Platz der Reihe hat. Ausserdem wird gespeichert ob es sich um eine
 * Reihe mit Sitzplaetzen oder mit Stehplaetzen handelt.
 *
 */
public class Reihe extends Entity implements Serializable,Comparable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -7882540772780561589L;

	/** identifier field */
	private ticketline.db.ReiheKey comp_id;

	/** persistent field */
	private int startplatz;

	/** persistent field */
	private int anzplaetze;

	/** persistent field */
	private boolean sitzplatz;

	/** nullable persistent field */
	private ticketline.db.Kategorie kategorie;

	/** persistent field */
	private Set belegungen;

	/** full constructor */
	public Reihe(ticketline.db.ReiheKey comp_id, int startplatz,
			int anzplaetze, boolean sitzplatz,
			ticketline.db.Kategorie kategorie, Set belegungen) {
		this.comp_id = comp_id;
		this.startplatz = startplatz;
		this.anzplaetze = anzplaetze;
		this.sitzplatz = sitzplatz;
		this.kategorie = kategorie;
		this.belegungen = belegungen;
	}

	/** default constructor */
	public Reihe() {
		// do nothing
	}

	/** minimal constructor */
	public Reihe(ticketline.db.ReiheKey comp_id, int startplatz,
			int anzplaetze, boolean sitzplatz, Set belegungen) {
		this.comp_id = comp_id;
		this.startplatz = startplatz;
		this.anzplaetze = anzplaetze;
		this.sitzplatz = sitzplatz;
		this.belegungen = belegungen;
	}

	public ticketline.db.ReiheKey getComp_id() {
		return this.comp_id;
	}

	public void setComp_id(ticketline.db.ReiheKey comp_id) {
		this.comp_id = comp_id;
	}

	/**
	 * erste Platznummer der Reihe
	 */
	public int getStartplatz() {
		return this.startplatz;
	}

	public void setStartplatz(int startplatz) {
		this.startplatz = startplatz;
	}

	/**
	 * Anzahl der Plaetze > 0
	 */
	public int getAnzplaetze() {
		return this.anzplaetze;
	}

	public void setAnzplaetze(int anzplaetze) {
		this.anzplaetze = anzplaetze;
	}

	/**
	 * Sitzplatz (TRUE) oder Stehplatz (FALSE)
	 */
	public boolean isSitzplatz() {
		return this.sitzplatz;
	}

	public void setSitzplatz(boolean sitzplatz) {
		this.sitzplatz = sitzplatz;
	}

	public ticketline.db.Kategorie getKategorie() {
		return this.kategorie;
	}

	public void setKategorie(ticketline.db.Kategorie kategorie) {
		this.kategorie = kategorie;
	}

	public Set getBelegungen() {
		return this.belegungen;
	}

	public void setBelegungen(Set belegungen) {
		this.belegungen = belegungen;
	}

	public String toString() {
		return new ToStringBuilder(this).append("comp_id", getComp_id())
				.toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof Reihe))
			return false;
		Reihe castOther = (Reihe) other;
		return new EqualsBuilder().append(this.getComp_id(),
				castOther.getComp_id()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getComp_id()).toHashCode();
	}

	@Override
	public int compareTo(Object obj) {
		int onumber = new Integer(((Reihe)obj).getComp_id().getBezeichnung().substring(5));
		int number = new Integer(this.getComp_id().getBezeichnung().substring(5));
		
		if(number < onumber){
			return -1;
		}
		if(number > onumber){
			return 1;
		}
		
		return 0;
	}

}
