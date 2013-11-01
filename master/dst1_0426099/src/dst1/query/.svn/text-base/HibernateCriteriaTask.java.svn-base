package dst1.query;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import dst1.model.tasks.Execution;
import dst1.model.tasks.Execution.JobStatus;
import dst1.model.tasks.Job;

public class HibernateCriteriaTask {
	
	
	@SuppressWarnings("unchecked")
	public static List<Job> getAllJobsCreatedByUser(EntityManager em,String workflow, String username) {
		Session session = (Session) em.getDelegate();
		return session.createCriteria(Job.class)
			.createAlias("environment","env")
			.add(Restrictions.eq("env.workflow", workflow))
			.createAlias("creator","crt")
			.add(Restrictions.eq("crt.username", username)).list();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Job> getFinishedJobsByExample(EntityManager em,Date start, Date end) {
		Session session = (Session) em.getDelegate();
		
		//example object
		Job j = new Job();
		j.setExecution(new Execution());
		j.getExecution().setStatus(JobStatus.FINISHED);
		j.getExecution().setStart(start);
		j.getExecution().setEnd(end);

		//make and config example
		Example jobExample = Example.create(j);
		jobExample.excludeProperty("id");
		jobExample.excludeProperty("isPaid");
		jobExample.excludeProperty("environment");
		jobExample.excludeZeroes();

		
		return session.createCriteria(Job.class)
				.add(jobExample)
				.createCriteria("execution")
					.add( Example.create( j.getExecution() ) )
					.list();
	}
}
