package db.interfaces;

import javax.ejb.Local;

import entities.value.Value;
import entities.value.Value.ValueId;

@Local
public interface IValueDAO extends IGenericDAO<Value, ValueId> {

	/**
	 * Get the value for the specified attribute id and the trial data id.
	 * @param attributeId
	 *            The attribute id
	 * @param trialDataId
	 *            The trial data id
	 * @return The value, or null if no value exists.
	 */
	public Value findByAttributeAndTrialData(Long attributeId, Long trialDataId);
}
