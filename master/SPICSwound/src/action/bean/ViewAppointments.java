package bean;

import java.io.Serializable;

import javax.ejb.Local;

import util.Resettable;

@Local
public interface ViewAppointments extends Serializable, Resettable {

	/**
	 * Destroys the bean.
	 */
	public void destroy();

	/**
	 * Finds all appointments for the current search string.
	 * @return The redirect string.
	 */
	public String findAppointments();

	/**
	 * Creates a new appointment and redirects to the appointments page.
	 * @return The redirect string.
	 */
	public String createAppointment();

	/**
	 * Edits the selected appointment.
	 * @return The redirect string.
	 */
	public String editAppointment();

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
	 * Opens the selected patient.
	 * @return The redirect string.
	 */
	public String selectPatient();
}
