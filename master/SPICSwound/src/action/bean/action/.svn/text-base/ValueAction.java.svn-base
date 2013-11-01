package bean.action;

import java.io.Serializable;

import javax.ejb.EJB;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import util.ValueFactory;
import util.ValueFactoryBean;

import db.interfaces.IAttributeDAO;
import db.interfaces.ITrialDataDAO;
import db.interfaces.IValueDAO;

import entities.Attribute;
import entities.TrialData;
import entities.value.Value;

/**
 * @author inso
 * @author inso
 */
@Name("valueAction")
@Scope(ScopeType.STATELESS)
@AutoCreate
public class ValueAction {

	@In
	private IValueDAO valueDAO;

	@In
	private IAttributeDAO attributeDAO;

	@In(create = true)
	private ITrialDataDAO trialDataDAO;

	// TODO Injection of valueFactor not working #?(@&@!
	// @In(create = true)
	private ValueFactory valueFactory = new ValueFactoryBean();

	@Logger
	private Log log;

	public Value createValue(Attribute attribute, TrialData trialData,
			Serializable valueObject) {
		// TODO do we have to check the correct data-type for value-object?
		Value value = valueFactory.getValueObject(attribute.getFormElement()
				.getDataType());
		value.setAttribute(attribute);
		value.setTrialData(trialData);
		value.setValueObject(valueObject);
		return value;
	}

	/**
	 * Creates and persists the value for the specified attribute id and trial
	 * data id.
	 * @param attributeId
	 *            The attribute id
	 * @param trialDataId
	 *            The trial data id
	 * @param value
	 *            The serializable value
	 * @return The created value, or null if something went wrong.
	 */
	public Value createAndPersistValue(Long attributeId, Long trialDataId,
			Serializable value) {
		log.info("creating and persisting Value for attribute #0"
				+ " and trialData #1 with value #2 of class #3", attributeId,
				trialDataId, value, value.getClass());

		Attribute attribute = attributeDAO.findByID(attributeId);
		if (attribute == null) {
			return null;
		}

		TrialData trialData = trialDataDAO.findByID(trialDataId);
		if (trialData == null) {
			return null;
		}

		// value already exists
		if (valueExists(attributeId, trialDataId)) {
			return null;
		}

		Value v = createValue(attribute, trialData, value);
		valueDAO.persist(v);

		return v;
	}

	/**
	 * Checks if the value with the specified attribute id and trial data id
	 * exists.
	 * @param attributeId
	 *            The attribute id
	 * @param trialDataId
	 *            The trial data id
	 * @return True if the value exists, otherwise false
	 */
	public boolean valueExists(Long attributeId, Long trialDataId) {
		return getValue(attributeId, trialDataId) != null;
	}

	/**
	 * Get the value for the specified attribute id and trial data id.
	 * @param attributeId
	 *            The attribute id
	 * @param trialDataId
	 *            The trial data id
	 * @return A value if exists, otherwise null
	 */
	public Value getValue(Long attributeId, Long trialDataId) {
		return valueDAO.findByAttributeAndTrialData(attributeId, trialDataId);
	}
}
