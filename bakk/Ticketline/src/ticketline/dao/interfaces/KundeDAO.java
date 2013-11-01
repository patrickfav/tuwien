package ticketline.dao.interfaces;

import java.util.List;

import ticketline.db.Kunde;

/**
 * @author geezmo
 */
public interface KundeDAO extends DAO {

	/**
	 * returns the Kunde identified by the kartennr.
	 *
	 * @param kartennr
	 * @return Kunde
	 * @throws RuntimeException
	 */
	public Kunde get(Integer kartennr) throws RuntimeException;

	/**
	 * saves the kunde.
	 *
	 * @param kunde
	 * @throws RuntimeException
	 */
	public void save(Kunde kunde) throws RuntimeException;
	
	/**
	 * updates the kunde.
	 *
	 * @param kunde
	 * @throws RuntimeException
	 */
	public void update(Kunde kunde) throws RuntimeException;
	
	/**
	 * searches for similarities with the given costumer in the db
	 *
	 * @param kunde
	 * @throws RuntimeException
	 */
	public List search(Kunde kunde) throws RuntimeException;
	
	/**
	 * deletes the kunde.
	 *
	 * @param kunde
	 * @throws RuntimeException
	 */
	public void remove(Kunde kunde) throws RuntimeException;
}
