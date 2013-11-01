package db.interfaces;

import javax.ejb.Local;

import entities.AttributeGroup;

@Local
public interface IAttributeGroupDAO extends IGenericDAO<AttributeGroup, Long>{

	public AttributeGroup findByTrialFormIDandSort(Long tfId, Integer sort);

}
