package db.interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Trial;
import entities.User;

@Local
public interface ITrialDAO extends IGenericDAO<Trial, Long> {
			
	public List<Trial> findActiveByUser(User u);
	
	public List<Trial> findAll();
}
