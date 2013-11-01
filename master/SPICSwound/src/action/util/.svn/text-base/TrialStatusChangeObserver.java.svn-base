package util;

import java.io.Serializable;

import javax.ejb.Local;

import entities.event.StatusChangeEvent;

@Local
public interface TrialStatusChangeObserver extends Serializable{
	
	public void handleStatusChange(StatusChangeEvent e);

}
