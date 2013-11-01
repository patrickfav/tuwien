package db.interfaces;

import java.util.List;

import javax.ejb.Local;

import org.hibernate.ScrollableResults;

import entities.Patient;
import entities.Trial;
import entities.TrialData;

@Local
public interface ITrialDataDAO extends IGenericDAO<TrialData, Long>{

	public boolean attributeGroupHasTrialData(Long agId);
	
	public boolean attributeHasTrialData(Long attId);
	
	public boolean trialFormHasTrialData(Long tfId);
	
	public long getTrialDataCount(Trial trial);
	
	public List<TrialData> getTrialDatasForPersonalExport(Long trialId, String username);

	public List<TrialData> getTrialDatasForFullExport(Long trialId);
	
	/* added */
	public ScrollableResults getScrollableResultTrailDatasForFullExport(Long trialId);
	public ScrollableResults getScrollableResultsTrialDatasForPersonalExport(Long trialId, String username);
	
	public int getCountByPatient(Patient p);
	
	public void flush();
	
}
