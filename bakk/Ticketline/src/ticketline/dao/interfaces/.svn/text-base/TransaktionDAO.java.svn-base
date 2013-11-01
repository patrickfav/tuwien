package ticketline.dao.interfaces;

import java.util.List;

import ticketline.db.Transaktion;
import ticketline.db.TransaktionKey;

/**
 * @author geezmo
 */
public interface TransaktionDAO extends DAO {

	/**
	 * returns the Transaktion identified by the transaktionKey.
	 *
	 * @param transaktionKey
	 * @return Transaktion
	 * @throws RuntimeException
	 */
	public Transaktion get(TransaktionKey transaktionKey)
			throws RuntimeException;

	/**
	 * saves or updates the transaktion.
	 */
	public void save(Transaktion transaktion) throws RuntimeException;

	/**
	 * deletes the transaktion.
	 *
	 * @param transaktion
	 * @throws RuntimeException
	 */
	public void remove(Transaktion transaktion) throws RuntimeException;
	
	/**
	 * searches the Database with the given Key Values as well as whether
	 * to search for all Transaktions or only specific ones
	 * 
	 * isTicket = verkauft is true in the DB and storno is false
	 * isReservation = verkauft is false in the DB and storno is false
	 * 
	 * @param transKey
	 * @param resNr
	 * @param isTicket
	 * @param isReservation
	 * @return
	 * @throws RuntimeException
	 */
	public List<Transaktion> searchTransaktion(TransaktionKey transKey,int resNr, boolean isTicket, boolean isReservation) throws RuntimeException;
	public Integer getNextResNr();
	
	/**
	 * Updates the Transaktion in the Database with the information
	 * contained withing the argument Transaktion
	 * 
	 * @param transaktion
	 * @throws RuntimeException
	 */
	public void update(Transaktion transaktion) throws RuntimeException;
}
