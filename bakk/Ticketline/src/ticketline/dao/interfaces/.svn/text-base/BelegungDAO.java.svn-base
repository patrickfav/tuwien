package ticketline.dao.interfaces;

import java.util.Date;
import java.util.Set;
import java.util.List;

import ticketline.db.Belegung;
import ticketline.db.BelegungKey;
import ticketline.db.Saal;

/**
 * @author geezmo
 */
public interface BelegungDAO extends DAO {

	/**
	 * @param belegungKey
	 * @return Belegung
	 * @throws RuntimeException
	 */
	public Belegung get(BelegungKey belegungKey) throws RuntimeException;

	/**
	 * saves or updates the belegung.
	 */
	public void save(Belegung belegung) throws RuntimeException;
	public void update(Belegung belegung) throws RuntimeException;
	/**
	 * deletes the belegung.
	 *
	 * @param belegung
	 * @throws RuntimeException
	 */
	public void remove(Belegung belegung) throws RuntimeException;
	
	/**
	 * To find Halls for the Hall Load
	 * 
	 * @param hall
	 * @param pastLength
	 * @param from
	 * @param to
	 * @return
	 */
	 
	public List<Belegung> getBelegungen(Saal saal, Date from, Date to) throws RuntimeException;

}
