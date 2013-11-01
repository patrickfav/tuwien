package ticketline.dao.interfaces;

import ticketline.db.Artikel;

/**
 * @author geezmo
 */
public interface ArtikelDAO extends DAO {
	/**
	 * returns the artikel identified by the artikelnummer.
	 *
	 * @param artikelnr
	 * @return artikel
	 * @throws RuntimeException
	 */
	public Artikel get(Integer artikelnr) throws RuntimeException;

	/**
	 * saves or updates the artikel
	 */
	public void save(Artikel artikel) throws RuntimeException;

	/**
	 * deletes the artikel.
	 *
	 * @param artikel
	 * @throws RuntimeException
	 */
	public void remove(Artikel artikel) throws RuntimeException;

}
