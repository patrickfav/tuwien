package db.interfaces;

import javax.ejb.Local;

import entities.TrialForm;

@Local
public interface ITrialFormDAO extends IGenericDAO<TrialForm, Long> {
	
	/**
	 * this query checks if a certain trialform can be filled or not. it does not 
	 * however take into consideration the current "status" of the enclosing trial
	 * it just checks if for a trialform that is only fillable once and a specific
	 * patient (kennnummer) if another trialdata can be created or not!
	 * 
	 * @param tfId - the id of the trialform
	 * @param kennnummer - the kennnummer of the patient
	 * @return true if another trialform can be created, false otherwise
	 */
	public boolean canFillTrialForm(Long tfId, String kennnummer);
	
	/**
	 * has to be an exact match
	 */
	public Long lookupTrialForm(String formName, Long trialId);
	
}
