package ticketline.dao.interfaces;

import java.util.List;

import ticketline.db.Kunde;
import ticketline.db.Saal;
import ticketline.db.SaalKey;

/**
 * @author geezmo
 */
public interface SaalDAO extends DAO {

	/**
	 * returns the Saal identified by the key.
	 *
	 * @param key
	 * @return Saal
	 * @throws RuntimeException
	 */
	public Saal get(SaalKey key) throws RuntimeException;
	
	/**
	 * searches for similarities with the given Saal in the db
	 *
	 * @param saal
	 * @throws RuntimeException
	 */
	public List search(Saal saal) throws RuntimeException;
	
	/**
	 * saves or updates the saal.
	 */
	public void save(Saal saal) throws RuntimeException;

	/**
	 * returns the void identified by the saal.
	 *
	 * @param saal
	 * @throws RuntimeException
	 */
	public void remove(Saal saal) throws RuntimeException;
}
