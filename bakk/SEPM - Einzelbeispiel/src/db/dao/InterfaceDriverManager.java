package db.dao;

import java.util.Vector;
import entities.Driver;

public interface InterfaceDriverManager  extends InterfaceDaoManager{
	//adds the entity to the DB (insert)
	public void create(Driver d);
	//searches for an Entity similiar to the given - returns all matches
	public Vector<Driver> search(Driver d);
	//saves the entity - has to be created first (update)
	public void save(Driver d);
	//loads a single driver
	public Driver load(Integer svnr);
	//deletes an entity from the db
	public void delete(Driver d);
	
}