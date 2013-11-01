package ticketline.dao.interfaces;

import ticketline.db.Bestellung;
import ticketline.db.BestellungKey;

/**
 * @author geezmo
 */
public interface BestellungDAO extends DAO {

	/**
	 * @param bestellungKey
	 * @return Bestellung
	 * @throws RuntimeException
	 */
	public Bestellung get(BestellungKey bestellungKey) throws RuntimeException;

	/**
	 * saves or updates the bestellung.
	 */
	public void save(Bestellung bestellung) throws RuntimeException;

	/**
	 * deletes the bestellung.
	 *
	 * @param bestellung
	 * @throws RuntimeException
	 */
	public void remove(Bestellung bestellung) throws RuntimeException;
}
