package db.interfaces;

import java.io.Serializable;

public interface IGenericDAO<T,ID extends Serializable> extends Serializable {
	
	public void persist(T element);
	
	public T merge (T element);
	
	public void remove(T element);
	
	public void refresh(T element);
	
	public T findByID(ID id);
}
