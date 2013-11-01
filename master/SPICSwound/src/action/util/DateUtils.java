package util;

import java.util.Calendar;
import java.util.Date;

/**
 * Utils class for convenient date/time operations.
 * @author inso
 */
public class DateUtils {

	/**
	 * Get the {@link Date} created with the specified year, the month
	 * (beginning with 1), day of month, hour and minutes. Seconds and
	 * milliseconds are removed from the date.
	 * @param year
	 *            The year for the date.
	 * @param month
	 *            The month for the date (beginning with 1).
	 * @param dayOfMonth
	 *            The day of month for the date.
	 * @param hour
	 *            The hour for the date.
	 * @param minutes
	 *            The minutes for the date.
	 * @return A new date with the information set.
	 */
	public static Date newDateTime(int year, int month, int dayOfMonth,
			int hour, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
