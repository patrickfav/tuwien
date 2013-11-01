package db.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import entities.User;

@Local
public interface IUserDAO extends IGenericDAO<User, String> {

	public List<User> usersNotParticipatingInTrial(Long trialId);

	public List<User> findAll();

	/**
	 * Find all users containing the specified search string within username,
	 * firstname or lastname. The second parameter specifies if only enabled
	 * users should be searched.
	 * @param searchString
	 *            The search string which has to be contained in one of
	 *            username, firstname or lastname
	 * @param searchEnabled
	 *            Flag indicating if only enabled users should be searched.
	 * @param maxResults
	 *            Maximum number of found patients
	 * @return A list of found users.
	 */
	public List<User> find(String searchString, boolean searchEnabled,
			int maxResults);

	/**
	 * Get the last login date (date and time) for the specified user.
	 * @param user
	 *            The user for which the last login should be found.
	 * @return The last login date, or null if no login event was found.
	 */
	public Date getLastLoginDate(User user);
}
