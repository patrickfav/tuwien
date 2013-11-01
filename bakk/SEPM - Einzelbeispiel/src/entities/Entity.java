package entities;

public interface Entity {
	
	//a standarzied method for retrievieng the id (primary key) of an objekt
	public Integer getId();
	//clears all datamembers
	public void clear();
	//string representation
	public String toString();
}
