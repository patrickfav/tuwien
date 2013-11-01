package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import entities.User;

@Local
public interface Administration extends Serializable {

	public void destroy();

	public String cancelEditing();

	public String editUser(String username);

	public String createUser();

	public String saveUser();

	public String getNewPassword();

	public void setNewPassword(String newPassword);

	public String getNewPassword2();

	public void setNewPassword2(String newPassword2);

	public List<String> getRoles();

	public void setRoles(List<String> roles);

	public void saveAdditionalFields(User user);

	/**
	 * Get the search string.
	 * @return The search string, if any, or null
	 */
	public String getSearchString();

	/**
	 * Set the search string.
	 * @param searchString
	 *            The new search string, or null to reset the search.
	 */
	public void setSearchString(String searchString);

	/**
	 * Returns if only enabled users should be searched.
	 * @return True, if only enabled users should be included in the search
	 *         result, false for all users.
	 */
	public boolean getSearchEnabled();

	/**
	 * Enabled or disable the filter for enabled users.
	 * @param searchEnabled
	 *            True to search only enabled users, false for all users.
	 */
	public void setSearchEnabled(boolean searchEnabled);

	/**
	 * Searches for users with the current search filters.
	 * @return A string describing a JFace navigation constant.
	 */
	public String findUsers();

	/**
	 * Resets the search string to null and triggers a re-search.
	 */
	public void resetSearchString();

	/**
	 * Get the current page of the datatable.
	 * @return The current page.
	 */
	public int getPage();

	/**
	 * Set the current page of the datatable.
	 * @param page
	 *            The new current page.
	 */
	public void setPage(int page);

	/**
	 * Get the maximum page count for the datatable.
	 * @return The maximum page count.
	 */
	public int getMAX_PAGES();

	/**
	 * Get the maximum number of rows per page.
	 * @return The maximum number of rows.
	 */
	public int getROW_COUNT();
	
	/**
	 * Get the last login date for the specified user.
	 * @param user
	 *            The user for which the last login should be found.
	 * @return The last login date, or null if no login event was found.
	 */
	public Date getLastLoginDate(User user);
}
