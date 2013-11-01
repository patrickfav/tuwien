package db.interfaces;

import javax.ejb.Local;

import entities.UserPermission;

@Local
public interface IUserPermissionDAO extends IGenericDAO<UserPermission, Integer>{
	
}