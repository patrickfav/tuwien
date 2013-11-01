package db.dao;

import java.util.Vector;

import entities.Taxi;

public interface InterfaceTaxiManager extends InterfaceDaoManager{
	
	//adds the entity to the DB (insert)
	public void create(Taxi t);
	//searches for an Entity similiar to the given - returns all matches	
	public Vector<Taxi> search(Taxi t);
	//saves the entity - has to be created first (update)
	public void save(Taxi t);
	//loads a single driver
	public Taxi load(Integer svnr);
	//deletes an entity from the db
	public void delete(Taxi t);
}