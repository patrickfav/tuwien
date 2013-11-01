package bean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.faces.model.SelectItem;

import entities.Participation;

@Local
public interface ParticipationManager extends Serializable {

	public void queryParticipations();
	
	public void remove();
	
	public List<SelectItem> getAllUsers();

	public String getSelectedUsername();

	public void setSelectedUsername(String selectedUsername);
	
	public void addParticipation();
	
	public void aktivateParticipation(Participation participation);
	
	public void deaktivateParticipation(Participation participation);
	
	public void toggleViewAllPatients(Participation p);
	
	public int getPatientCount(Participation p);
	
	public int getPage();

	public void setPage(int page);

	public int getMAX_PAGES();

	public int getMAX_ROWS();
}