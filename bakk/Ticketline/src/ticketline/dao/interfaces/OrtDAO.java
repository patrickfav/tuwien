package ticketline.dao.interfaces;

import java.util.List;

import ticketline.db.Ort;
import ticketline.db.OrtKey;
import ticketline.db.Saal;

/**
 * @author geezmo
 */
public interface OrtDAO extends DAO {

	/**
	 * returns the Ort identified by the ortKey.
	 *
	 * @param ortKey
	 * @return Ort
	 * @throws RuntimeException
	 */
	public Ort get(OrtKey ortKey) throws RuntimeException;

	/**
	 * saves or updates the ort.
	 */
	public void save(Ort ort) throws RuntimeException;
	
	/**
	 * searches for similarities with the given Ort in the db
	 *
	 * @param ort
	 * @throws RuntimeException
	 */
	public List search(Ort o) throws RuntimeException;
	
	/**
	 * deletes the ort.
	 *
	 * @param ort
	 * @throws RuntimeException
	 */
	public void remove(Ort ort) throws RuntimeException;
}
