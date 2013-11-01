package ticketline.db;

import java.io.Serializable;
import java.util.Date;

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
public class BelegungKey extends Entity implements Serializable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 4397160081398320747L;

	/** identifier field */
	private String reihebez;

	/** identifier field */
	private String kategoriebez;

	/** identifier field */
	private String saalbez;

	/** identifier field */
	private String ortbez;

	/** identifier field */
	private String ort;

	/** identifier field */
	private Date datumuhrzeit;

	/** full constructor */
	public BelegungKey(String reihebez, String kategoriebez, String saalbez,
			String ortbez, String ort, Date datumuhrzeit) {
		this.reihebez = reihebez;
		this.kategoriebez = kategoriebez;
		this.saalbez = saalbez;
		this.ortbez = ortbez;
		this.ort = ort;
		this.datumuhrzeit = datumuhrzeit;
	}

	/** default constructor */
	public BelegungKey() {
		// do nothing
	}

	/**
	 * ~Reihe.Bezeichnung
	 */
	public String getReihebez() {
		return this.reihebez;
	}

	public void setReihebez(String reihebez) {
		this.reihebez = reihebez;
	}

	/**
	 * ~Kategorie.Bezeichnung
	 */
	public String getKategoriebez() {
		return this.kategoriebez;
	}

	public void setKategoriebez(String kategoriebez) {
		this.kategoriebez = kategoriebez;
	}

	/**
	 * ~Saal.Bezeichnung
	 */
	public String getSaalbez() {
		return this.saalbez;
	}

	public void setSaalbez(String saalbez) {
		this.saalbez = saalbez;
	}

	/**
	 * ~Ort.Bezeichnung
	 */
	public String getOrtbez() {
		return this.ortbez;
	}

	public void setOrtbez(String ortbez) {
		this.ortbez = ortbez;
	}

	/**
	 * ~Ort.Ort
	 */
	public String getOrt() {
		return this.ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	/**
	 * ~Auffuehrung.DatumUhrzeit
	 */
	public Date getDatumuhrzeit() {
		return this.datumuhrzeit;
	}

	public void setDatumuhrzeit(Date datumuhrzeit) {
		this.datumuhrzeit = datumuhrzeit;
	}

	public String toString() {
		return new ToStringBuilder(this).append("reihebez", getReihebez())
				.append("kategoriebez", getKategoriebez()).append("saalbez",
						getSaalbez()).append("ortbez", getOrtbez()).append(
						"ort", getOrt()).append("datumuhrzeit",
						getDatumuhrzeit()).toString();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if (!(other instanceof BelegungKey))
			return false;
		BelegungKey castOther = (BelegungKey) other;
		return new EqualsBuilder().append(this.getReihebez(),
				castOther.getReihebez()).append(this.getKategoriebez(),
				castOther.getKategoriebez()).append(this.getSaalbez(),
				castOther.getSaalbez()).append(this.getOrtbez(),
				castOther.getOrtbez())
				.append(this.getOrt(), castOther.getOrt()).append(
						this.getDatumuhrzeit(), castOther.getDatumuhrzeit())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getReihebez()).append(
				getKategoriebez()).append(getSaalbez()).append(getOrtbez())
				.append(getOrt()).append(getDatumuhrzeit()).toHashCode();
	}

	public BelegungKey clone(){
		BelegungKey bgk = new BelegungKey();
		bgk.setDatumuhrzeit(new Date());
		bgk.setKategoriebez(this.getKategoriebez());
		bgk.setOrt(this.getOrt());
		bgk.setOrtbez(this.getOrtbez());
		bgk.setReihebez(this.getReihebez());
		bgk.setSaalbez(this.getSaalbez());
		return bgk;
	}
}
