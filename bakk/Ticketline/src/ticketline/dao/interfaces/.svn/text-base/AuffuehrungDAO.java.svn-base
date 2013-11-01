package ticketline.dao.interfaces;

import java.util.Date;
import java.util.List;

import ticketline.db.Auffuehrung;
import ticketline.db.AuffuehrungKey;

/**
 * @author geezmo, AndiS
 */
public interface AuffuehrungDAO extends DAO {

	/**
	 * @param auffuehrungKey
	 * @return Auffuehrung
	 * @throws RuntimeException
	 */
	public Auffuehrung get(AuffuehrungKey auffuehrungKey)
			throws RuntimeException;

	/**
	 * saves or updates the auffuehrung.
	 */
	public void save(Auffuehrung auffuehrung) throws RuntimeException;

	/**
	 * deletes the auffuehrung.
	 *
	 * @param auffuehrung
	 * @throws RuntimeException
	 */
	public void remove(Auffuehrung auffuehrung) throws RuntimeException;
	
	/**
	 * searches for similarities with the given act in the db
	 *
	 * @param auffuehrung
	 * @throws RuntimeException
	 */
	public List search(Auffuehrung auffuehrung) throws RuntimeException;

	/**
	 * searches for similarities with the given act in the db
	 * include checking the act-date:
	 *   FROM and TO date specified => all between FROM and TO
	 *   only FROM date => all after FROM date
	 *   only TO date => all before TO date
	 *
	 * @param auffuehrung
	 * @throws RuntimeException
	 */
	public List searchWithDate(Auffuehrung auffuehrung,Date from,Date to) throws RuntimeException;

}
