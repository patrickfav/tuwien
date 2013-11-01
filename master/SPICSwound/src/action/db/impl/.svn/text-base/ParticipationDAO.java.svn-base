package db.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import db.interfaces.IParticipationDAO;
import entities.Participation;
import entities.Trial;
import entities.User;

@Stateless
@Name("participationDAO")
@AutoCreate
public class ParticipationDAO implements IParticipationDAO {

	@In
	private EntityManager entityManager;

	public void persist(Participation p) {
		entityManager.persist(p);

	}

	public Participation findByID(Long id) {
		return entityManager.find(Participation.class, id);

	}

	public Participation merge(Participation p) {
		return entityManager.merge(p);
	}

	public Participation findByUserAndTrial(User user, Trial trial) {
		return findByUsernameAndTrialId(user.getUsername(), trial.getId());
	}
	
	public Participation findByUsernameAndTrialId(String username, Long trialId) {
		Query q = entityManager	.createQuery(
				"from Participation as p where p.user.username = :username AND p.trial.id = :trialId");
		q.setParameter("username", username);
		q.setParameter("trialId", trialId);
		
		try {
			return (Participation) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Participation> findByTrialId(Long trialId, int maxResults) {
		Query q = entityManager
				.createQuery("from Participation p where p.trial.id = :trialId order by p.id");
		q.setParameter("trialId", trialId);
		q.setMaxResults(maxResults);
		return q.getResultList();
	}

	public int getPatientCount(Participation p) {
		Query q = entityManager.createQuery("select count(*) from Patient p where p.participation.id = :participation_id");
		q.setParameter("participation_id", p.getId());

		return ((Number)q.getSingleResult()).intValue();
	}
	
	public int getPatientCount(Trial t) {
		Query q = entityManager.createQuery("select count(*) from Patient p where p.participation.id in (select part.id from Participation part where part.trial.id = :trial_id)");
		q.setParameter("trial_id", t.getId());
		
		return ((Number)q.getSingleResult()).intValue();
	}

	public void refresh(Participation p) {
		entityManager.refresh(p);
	}

	public void remove(Participation p) {
		entityManager.remove(p);
	}

}
