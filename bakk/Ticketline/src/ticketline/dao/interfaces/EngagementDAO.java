package ticketline.dao.interfaces;

import ticketline.db.Engagement;
import ticketline.db.EngagementKey;

/**
 * @author geezmo
 */
public interface EngagementDAO extends DAO {

	/**
	 * returns the Engagement identified by the engagementKey.
	 *
	 * @param engagementKey
	 * @return Engagement
	 * @throws RuntimeException
	 */
	public Engagement get(EngagementKey engagementKey) throws RuntimeException;

	/**
	 * saves or updates the engagement.
	 */
	public void save(Engagement engagement) throws RuntimeException;

	/**
	 * deletes the engagement.
	 *
	 * @param engagement
	 * @throws RuntimeException
	 */
	public void remove(Engagement engagement) throws RuntimeException;
}
