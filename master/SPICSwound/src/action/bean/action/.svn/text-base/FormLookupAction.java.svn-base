package bean.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.log.Log;

import db.interfaces.IAttributeDAO;
import db.interfaces.ITrialDAO;
import db.interfaces.ITrialFormDAO;
import entities.Attribute;
import entities.Trial;
import entities.TrialForm;

/**
 * @author inso
 * @author inso
 */
@Name("formLookupAction")
@Scope(ScopeType.CONVERSATION)
public class FormLookupAction {

	@Logger
	private Log log;

	@In
	private ITrialDAO trialDAO;

	@In
	private ITrialFormDAO trialFormDAO;

	@In
	private IAttributeDAO attributeDAO;

	/**
	 * Map for trial-form-names-to-id mapping
	 */
	private Map<String, Long> trialFormIdCache;

	/**
	 * Map for attribute-names-to-id mapping
	 */
	private Map<Long, Map<String, Long>> attributeNameCache;

	private Long trialId;

	public Long getTrialId() {
		return trialId;
	}

	/**
	 * Get the trial with the specified id.
	 * @param trialId
	 *            The id of the trial
	 * @return The found trial, or null if no trial with that id exists.
	 */
	public Trial getTrial(Long trialId) {
		return trialDAO.findByID(trialId);
	}

	/**
	 * Checks if the trial with the specified id exists.
	 * @param trialId
	 *            The id of the trial.
	 * @return True if the trial exists, otherwise false.
	 */
	public boolean trialExists(Long trialId) {
		return getTrial(trialId) != null;
	}

	/**
	 * Sets the trialId and starts a new conversation.
	 * @param trialId
	 *            The trialId.
	 */
	@Begin(join=true)
	public void setTrialId(Long trialId) {
		log.info("setting trialId to #0, starting new conversation with id #1",
				trialId, Conversation.instance().getId());
		this.trialId = trialId;
		init();
	}

	/**
	 * Get the trial form with the specified id.
	 * @param trialFormId
	 *            The id of the trial form.
	 * @return The found trial form, or null if no trial form with that id
	 *         exists.
	 */
	public TrialForm getTrialForm(Long trialFormId) {
		return trialFormDAO.findByID(trialFormId);
	}

	/**
	 * Checks if the trial form with the specified id exists.
	 * @param trialFormId
	 *            The id of the trial form.
	 * @return True if the trial form exists, otherwise false.
	 */
	public boolean trialFormExists(Long trialFormId) {
		return getTrialForm(trialFormId) != null;
	}

	/**
	 * Makes a lookup for the specified trial form name. The trial form
	 * name-to-id mapping is added to the cache if the trial form exists.
	 * @param trialFormName
	 *            The name of the trial form.
	 * @return The id of the trial form.
	 */
	public Long lookupTrialForm(String trialFormName) {
		if (trialId == null) {
			log.error("trialId not set,"
					+ " first set trialId to lookup for trial forms");
			return null;
		}

		log.info("looking up trialform for trial with id #0"
				+ " with name #1 conversationid #2", trialId, trialFormName,
				Conversation.instance().getId());

		if (trialFormIdCache.containsKey(trialFormName)) {
			log.info("cache hit for trialform with name #0", trialFormName);
			return trialFormIdCache.get(trialFormName);
		}

		Long tfId = trialFormDAO.lookupTrialForm(trialFormName, trialId);
		// only add the mapping if the trial form is valid
		if (tfId != null) {
			trialFormIdCache.put(trialFormName, tfId);
		}

		return tfId;
	}

	private void init() {
		trialFormIdCache = new HashMap<String, Long>();
		attributeNameCache = new HashMap<Long, Map<String, Long>>();
	}

	/**
	 * Makes a lookup for an attribute with the specified attribute group name
	 * and attribute name.
	 * @param trialFormId
	 *            The trial form id.
	 * @param attributeGroupName
	 *            The attribute group name.
	 * @param attributeName
	 *            The attribute name.
	 * @return The id of the found attribute.
	 */
	public Long lookupAttribute(Long trialFormId, String attributeGroupName,
			String attributeName) {
		if (trialId == null) {
			log.error("trialId not set,"
					+ " first set trialId to lookup for attributes");
			return null;
		}

		if (attributeNameCache == null)
			init();

		log.info("looking up Attribute for trialForm #0"
				+ " attributeGroup #1 attribute #2", trialFormId,
				attributeGroupName, attributeName);

		if (attributeNameCache.get(trialFormId) == null) {
			log.info("initializing attributecache for trialform #0",
					trialFormId);
			List<AttributeIdentifier> attIdents = attributeDAO
					.getAttributeIdentifiersForTrialForm(trialFormId);
			HashMap<String, Long> cache = new HashMap<String, Long>();

			for (AttributeIdentifier attIdent : attIdents) {
				cache
						.put(concatNames(attIdent.getAttributeGroupName(),
								attIdent.getAttributeName()), attIdent
								.getAttributeId());
			}

			attributeNameCache.put(trialFormId, cache);
		} else {
			log.info("cache hit - juhuu");
		}

		return attributeNameCache.get(trialFormId).get(
				concatNames(attributeGroupName, attributeName));
	}

	private static String concatNames(String attributeGroupName,
			String attributeName) {
		return attributeGroupName + "_" + attributeName;
	}

	/**
	 * Get the attribute for the specified id.
	 * @param attributeId
	 *            The attribute id
	 * @return The attribute if any exists, or null.
	 */
	public Attribute getAttribute(Long attributeId) {
		return attributeDAO.findByID(attributeId);
	}

	/**
	 * Checks if the attribute with the specified id exists.
	 * @param attributeId
	 *            The attribute id
	 * @return True if the attribute exists, otherwise false.
	 */
	public boolean attributeExists(Long attributeId) {
		return getAttribute(attributeId) != null;
	}
}
