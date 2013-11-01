package db.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import entities.Patient;
import entities.Trial;
import entities.User;

@Local
public interface IPatientDAO extends IGenericDAO<Patient, Long>{
	
	public List<Patient> getPatientsByTrialAndUser(Trial trial, User user, String searchString);
	
	public List<Patient> getPatientsByTrial(Trial trial, String searchString, int maxResults);
	
	public Patient findByUserAndKennnummer(User user, String kennnummer);
	
	public Patient findByUsernameAndKennnummer(String username, String kennnummer);
	
	public Date getLastModified(Patient p);
}
