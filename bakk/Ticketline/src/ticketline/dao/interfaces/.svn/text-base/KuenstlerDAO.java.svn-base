package ticketline.dao.interfaces;

import java.util.List;

import ticketline.db.Kuenstler;
import ticketline.db.Kunde;

/**
 * @author geezmo
 */
public interface KuenstlerDAO extends DAO {

	/**
	 * returns the Kuenstler identified by the kuenstlernr.
	 *
	 * @param kuenstlernr
	 * @return Kuenstler
	 * @throws RuntimeException
	 */
	public Kuenstler get(Integer kuenstlernr) throws RuntimeException;

	/**
	 * ssaves or updates the kuenstler.
	 */
	public void save(Kuenstler kuenstler) throws RuntimeException;

	/**
	 * deletes the kuenstler.
	 *
	 * @param kuenstler
	 * @throws RuntimeException
	 */
	public void remove(Kuenstler kuenstler) throws RuntimeException;
	
	public List search(Kuenstler kuenstler) throws RuntimeException;
}
