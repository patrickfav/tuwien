package ticketline.dao.interfaces;

import ticketline.db.Reihe;
import ticketline.db.ReiheKey;

/**
 * @author geezmo
 */
public interface ReiheDAO extends DAO {

	/**
	 * returns the Reihe identified by the reiheKey.
	 *
	 * @param reiheKey
	 * @return Reihe
	 * @throws RuntimeException
	 */
	public Reihe get(ReiheKey reiheKey) throws RuntimeException;

	/**
	 * saves or updates the reihe.
	 */
	public void save(Reihe reihe) throws RuntimeException;

	/**
	 * deletes the reihe.
	 *
	 * @param reihe
	 * @throws RuntimeException
	 */
	public void remove(Reihe reihe) throws RuntimeException;
}
