package dst2.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst1.model.tasks.Execution;
import dst1.model.tasks.Execution.JobStatus;

@Stateless
public class TimerSessionBean {
	
	private static Logger log = Logger.getLogger("TimerSessionBean");
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Schedule(second="*/10",minute="*",hour="*",persistent=false)
	public void timerService() {
		List<Execution> execList = (List<Execution>) em.createQuery("select e from Execution e where e.start is not null AND e.end is null").getResultList();
		
		for(Execution e:execList) {
			e.setEnd(new Date());
			e.setStatus(JobStatus.FINISHED);
		}
		
		log.info("Scheduled update @ "+(new Date())+". "+execList.size()+" Executions updated.");
	}
}
