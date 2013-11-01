package db.interfaces;

import javax.ejb.Local;

import entities.OrgGroup;

@Local
public interface IGroupDAO extends IGenericDAO<OrgGroup, Long>{
	
	public OrgGroup findByName(String name);

}
