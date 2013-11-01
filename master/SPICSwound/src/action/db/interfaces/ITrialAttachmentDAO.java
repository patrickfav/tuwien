package db.interfaces;

import javax.ejb.Local;

import entities.TrialAttachment;

@Local
public interface ITrialAttachmentDAO extends IGenericDAO<TrialAttachment, Long>{
		
	public void deleteById(Long id);
}
