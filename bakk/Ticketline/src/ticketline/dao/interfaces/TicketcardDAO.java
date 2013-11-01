package ticketline.dao.interfaces;

import ticketline.db.Ticketcard;

/**
 * @author geezmo
 */
public interface TicketcardDAO extends DAO {

	/**
	 * 
	 * returns the Ticketcard identified by the kartennr.
	 * 
	 * @param kartennr
	 * @return Ticketcard
	 * @throws RuntimeException
	 */
	public Ticketcard get(Integer kartennr) throws RuntimeException;

	/**
	 * saves or updates the ticketcard.
	 */
	public void save(Ticketcard ticketcard) throws RuntimeException;

	/**
	 * deletes the ticketcard.
	 * 
	 * @param ticketcard
	 * @throws RuntimeException
	 */
	public void remove(Ticketcard ticketcard) throws RuntimeException;
}
