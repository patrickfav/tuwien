package db.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import db.interfaces.ITrialDataDAO;
import entities.Patient;
import entities.Trial;
import entities.TrialData;
import entities.User;

@Stateless
@AutoCreate
@Name("trialDataDAO")
public class TrialDataDAO implements ITrialDataDAO {

	private static final long serialVersionUID = -4329683517755199334L;
	
	@Logger
	private Log log;
	
	@In
	private EntityManager entityManager;

	public void persist(TrialData td) {
		entityManager.persist(td);
	}

	public TrialData findByID(Long id) {
		return entityManager.find(TrialData.class, id);
	}

	public TrialData merge(TrialData td) {
		return entityManager.merge(td);
	}
	
	public void refresh(TrialData td) {
		entityManager.refresh(td);
	}

	public void remove(TrialData td) {
		entityManager.createQuery(
				"delete from TrialData where TRIALDATA_ID = " + td.getId())
				.executeUpdate();
	}
	
	public boolean trialFormHasTrialData(Long tfId) {
		Query q = entityManager.createQuery("SELECT count(*) FROM TrialData AS td WHERE td.trialform.id = :tfId");
		
		q.setParameter("tfId", tfId);
		
		int result = ((Long)q.getSingleResult()).intValue();
		
		return result != 0;
	}

	public boolean attributeGroupHasTrialData(Long agId) {

		Query q = entityManager
				.createQuery("SELECT count(*) FROM Value AS v, Attribute AS a WHERE a.id = v.attribute.id AND a.attributeGroup.id = :ag_id");

		Long result = (Long) q.setParameter("ag_id", agId)
				.getSingleResult();

		return result.intValue() != 0;
	}

	public boolean attributeHasTrialData(Long attId) {
		Query q = entityManager
				.createQuery("SELECT count(*) FROM Value AS v WHERE v.attribute.id = :a_id");

		Long result = (Long) q.setParameter("a_id", attId)
				.getSingleResult();

		return result.intValue() != 0;

	}
	
	/*
	 * select count(*) from trialdata where trialform_id in (select trialform_id from trialform where trial_id = 1)
	 */
	public long getTrialDataCount(Trial trial) {
		Query q = entityManager.createQuery("SELECT count(*) FROM TrialData AS td where td.trialform.id in (select id from TrialForm AS tf where tf.trial.id = :trial_id)");
		q.setParameter("trial_id", trial.getId());
		return (Long)q.getSingleResult(); 
	}

	/* probably deprecated */
	public List<TrialData> getTrialDatas(Trial trial) {
		return getTrialDatas(trial.getId());
	}
	
	public List<TrialData> getTrialDatas(Long trialId) {
		return getTrialDatas(trialId, null);
	}
	
	public List<TrialData> getTrialDatas(Trial trial, User user) {
		return getTrialDatas(trial.getId(), user.getUsername());
	}
	
	@SuppressWarnings("unchecked")
	public List<TrialData> getTrialDatas(Long trialId, String username) {
		Query q = entityManager.createQuery("SELECT td FROM TrialData AS td where" +
				(username == null ? "" : " td.patient.savedBy.username = :username AND") +
				" td.trialform.id in (select id from TrialForm AS tf where tf.trial.id = :trial_id)");
		q.setParameter("trial_id", trialId);
		if(username != null) q.setParameter("username", username);
		return q.getResultList();
	}
	/* end probably deprecated */
	
	@SuppressWarnings("unchecked")
	public List<TrialData> getTrialDatasForPersonalExport(Long trialId, String username) {
		Query q2 = entityManager.createQuery("SELECT td FROM TrialData AS td where td.patient.id in" +
				"(select p.id from Patient p where p.export = true AND " +
				"p.savedBy.username = :username AND p.participation.trial.id = :trial_id)");
			 q2.setParameter("username", username);
			 q2.setParameter("trial_id", trialId);
		return q2.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TrialData> getTrialDatasForFullExport(Long trialId) {
		Query q2 = entityManager.createQuery("SELECT td FROM TrialData AS td where td.patient.id in" +
				"(select p.id from Patient p where p.myExport = true AND " +
				"p.participation.trial.id = :trial_id)");
			 q2.setParameter("trial_id", trialId);
		return q2.getResultList();
	}
	
	public ScrollableResults getScrollableResultTrailDatasForFullExport(Long trialId) {
		Session session = (Session) entityManager.getDelegate();
		
		org.hibernate.Query q = session.createQuery("SELECT td FROM TrialData AS td where td.patient.id in" +
				"(select p.id from Patient p where p.myExport = true AND " +
				"p.participation.trial.id = :trial_id)").setParameter("trial_id", trialId)
				.setCacheMode(CacheMode.IGNORE).setReadOnly(true);
		
		return q.scroll(ScrollMode.FORWARD_ONLY);
		
	}
	
	public ScrollableResults getScrollableResultsTrialDatasForPersonalExport(Long trialId, String username) {
		Session session = (Session) entityManager.getDelegate();
		
		org.hibernate.Query q = session.createQuery("SELECT td FROM TrialData AS td where td.patient.id in" +
				"(select p.id from Patient p where p.export = true AND " +
		"p.savedBy.username = :username AND p.participation.trial.id = :trial_id)").setParameter("username", username).setParameter("trial_id", trialId)
				.setCacheMode(CacheMode.IGNORE).setReadOnly(true);
		
		return q.scroll(ScrollMode.FORWARD_ONLY);
		
	}
	
	public int getCountByPatient(Patient p) {
		Query q = entityManager.createQuery("select count(*) from TrialData td where td.patient.id = :patient_id");
		q.setParameter("patient_id", p.getId());
		return ((Number)q.getSingleResult()).intValue();
	}

	public void flush() {
		entityManager.flush();
	}

}
