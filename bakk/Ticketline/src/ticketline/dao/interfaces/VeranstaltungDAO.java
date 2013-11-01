package ticketline.dao.interfaces;

import java.util.List;

import ticketline.db.Saal;
import ticketline.db.Veranstaltung;
import ticketline.db.VeranstaltungKey;

/**
 * @author geezmo
 */
public interface VeranstaltungDAO extends DAO {

	/**
	 * returns the Veranstaltung identified by the veranstaltungKey.
	 *
	 * @param veranstaltungKey
	 * @return Veranstaltung
	 * @throws RuntimeException
	 */
	public Veranstaltung get(VeranstaltungKey veranstaltungKey)
			throws RuntimeException;

	/**
	 * saves or updates the veranstaltung.
	 */
	public void save(Veranstaltung veranstaltung) throws RuntimeException;
	
	/**
	 * searches for similarities with the given Veranstaltung in the db
	 *
	 * @param veranstaltung
	 * @throws RuntimeException
	 */
	public List search(Veranstaltung veranstatung) throws RuntimeException;
	
	/**
	 * deletes the veranstaltung.
	 *
	 * @param veranstaltung
	 * @throws RuntimeException
	 */
	public void remove(Veranstaltung veranstaltung) throws RuntimeException;

}
