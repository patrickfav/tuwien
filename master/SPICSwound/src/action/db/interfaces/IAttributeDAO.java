package db.interfaces;

import java.util.List;

import javax.ejb.Local;

import bean.action.AttributeIdentifier;

import entities.Attribute;

@Local
public interface IAttributeDAO extends IGenericDAO<Attribute, Long>{
	
	public List<AttributeIdentifier> getAttributeIdentifiersForTrialForm(Long trialFormID);

}
