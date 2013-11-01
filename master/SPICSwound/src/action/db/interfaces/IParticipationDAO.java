package db.interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Participation;
import entities.Trial;
import entities.User;

@Local
public interface IParticipationDAO extends IGenericDAO<Participation, Long> {
	
	public Participation findByUserAndTrial(User user, Trial trial);
	
	public Participation findByUsernameAndTrialId(String username, Long trialId);
	
	public List<Participation> findByTrialId(Long trialId, int maxResults);
	
	public int getPatientCount(Participation p);
	
	public int getPatientCount(Trial t);
	
}
