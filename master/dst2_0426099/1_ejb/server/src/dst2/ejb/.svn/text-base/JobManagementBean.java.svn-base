package dst2.ejb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst1.model.hardware.Computer;
import dst1.model.tasks.Environment;
import dst1.model.tasks.Execution;
import dst1.model.tasks.Execution.JobStatus;
import dst1.model.tasks.Job;
import dst1.model.user.User;
import dst2.ejb.interfaces.JobManagement;
import dst2.exceptions.ComputerNotFreeAnyMore;
import dst2.exceptions.LoginRequired;
import dst2.exceptions.NotEnoughFreeComputers;

@Stateful
@Interceptors( { AuditInterceptor.class })
public class JobManagementBean implements JobManagement {
	
	private static Logger log = Logger.getLogger("JobManagementBean");
	private boolean login;
	private User user;
	private Map<Long,List<Job>> tempJobList;
	
	@PersistenceContext
	private EntityManager em;
	
	@PostConstruct
	public void setup() {
		tempJobList = new HashMap<Long,List<Job>>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean login(String usr, String psw) {
		List<User> list = em.createQuery("select u from User u where u.username = :usr AND u.password = :psw")
			.setParameter("usr", usr).setParameter("psw", psw).getResultList();
		
		if(list == null || list.size() == 0)
			return false;
		
		em.detach(list.get(0));
		user = list.get(0);
		login = true;
		
		log.info("Login successful "+user);
		
		return login;
	}

	@Override
	public void addJob(Long gridId,int cpus, String workflow, List<String> param) throws NotEnoughFreeComputers {
		log.info("needed so far:"+(cpus+getNeedCPUsFromJobList(gridId)));
		
		if(checkSufficientFreeCPUs(gridId,cpus+getNeedCPUsFromJobList(gridId))) {
			if(!tempJobList.containsKey(gridId)) {
				tempJobList.put(gridId, new ArrayList<Job>());
			}
			
			
			Environment env = new Environment();
			env.setWorkflow(workflow);
			env.setParams(param);
			env.setWorkflow(workflow);
			
			Job j = new Job();
			j.setEnvironment(env);
			j.setPaid(false);
			
			Execution exec = new Execution();
			int execCpus = 0;
			
			/* assign computers until enough cpus */
			for(Computer c: getFreeComputers(gridId,tempJobList.get(gridId))) {
				exec.getComputer().add(c);
				execCpus+= c.getCpus();
				
				log.info("Added cpu:"+c);
				
				if(execCpus >= cpus)
					break;
			}
			
			j.setExecution(exec);
			
			tempJobList.get(gridId).add(j);
			
			log.info("Adding job "+j+" with exec "+exec);
			log.info("Temp Job List for grid "+gridId+" is "+tempJobList.get(gridId));
		} else {
			throw new NotEnoughFreeComputers("Cannot add to temp queue. "+cpus+" cpus are too much.");
		}
	}

	@Override
	@Remove(retainIfException=true)
	public void buyAndStartJobs() throws LoginRequired, ComputerNotFreeAnyMore {
		if(!login)
			throw new LoginRequired("Cannot proceed without login");
		
		//log.info("Debug user null?: "+user);
		
		for(Long gridId: tempJobList.keySet()) {
			for(Job j:tempJobList.get(gridId)) {
				/* check if computers can still be scheduled */
				for(Computer c: j.getExecution().getComputer()) {
					if(!isCPUFree(c.getId()))
						throw new ComputerNotFreeAnyMore("Computer "+c+" is not free anymore.");
				}

				j.setCreator(user);
				
				j.getExecution().setStatus(JobStatus.SCHEDULED);
				j.getExecution().setStart(new Date());
				j.getExecution().setJob(j);
				
				em.persist(j);
				
				user.getJobs().add(j);
				
				em.merge(user);
				
				
				
				/* make managed */
				for(Computer c: j.getExecution().getComputer()) {
					c.getExecutions().add(j.getExecution());
					em.merge(c);
				}
				
				log.info("Debug job: "+j);
				log.info("Debug Exec: "+j.getExecution());
			}
		}

	}

	@Override
	public void removeJobsFromList(Long gridId) {
		if(tempJobList.containsKey(gridId)) {
			tempJobList.put(gridId, new ArrayList<Job>());
		}
	}

	@Override
	public int getJobAmount(Long gridId) {
		if(tempJobList.containsKey(gridId)) {
			return tempJobList.get(gridId).size();
		}
		
		return 0;
	}
	
	@Override
	@Remove
	public void logout() {
		login = false;
	}
	
	
	/* ********************************************** PRIVATES */
	
	private boolean checkSufficientFreeCPUs(long gridId, int neededCPUs) {
		Long count = (Long) em.createQuery("select sum(comp.cpus) from Grid g join g.cluster as cl join cl.computer as comp where g.id = :id " +
				"AND comp.id not in (select cmp.id from Computer cmp join cmp.executions as exec where exec.start is not null AND exec.end is null)")
				.setParameter("id", gridId).getSingleResult();
		
		log.info("SQL DEBUG FOR COMPUTER-CPUs COUNT:  "+count);
		
		return (neededCPUs <= count);
	}
	
	@SuppressWarnings("unchecked")
	private List<Computer> getFreeComputers(long gridId,List<Job> tempJobs) {
		
		List<Computer> compList = em.createQuery("select comp from Grid g join g.cluster as cl join cl.computer as comp where g.id = :id " +
		"AND comp.id not in (select cmp.id from Computer cmp join cmp.executions as exec where exec.start is not null AND exec.end is null)")
		.setParameter("id", gridId).getResultList();
		
		List<Computer> resultList = new ArrayList<Computer>(compList);
		Collections.copy(resultList, compList);
		for(Computer c:compList) {
			for(Job j : tempJobs)
				for(Computer cc: j.getExecution().getComputer())
					if(c.getId().equals(cc.getId()))
						resultList.remove(c);
		}
		
		for(Computer c: resultList) {
			em.detach(c);
		}
		
		log.info("Get free CPUS: "+resultList);
		
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	private boolean isCPUFree(Long cpuId) {
		List<Computer> cpus = em.createQuery("select comp from Computer comp where comp.id = :id AND" +
				" comp.id not in (select cmp.id from Computer cmp join cmp.executions as exec where exec.start is not null AND exec.end is null)")
			.setParameter("id", cpuId).getResultList();
		
		if(cpus.size() == 0)
			return false;
		
		return true;
	}
	
	private int getNeedCPUsFromJobList(long gridId) {
		int count = 0;
		
		if(tempJobList.containsKey(gridId)) {
			for(Job j:tempJobList.get(gridId)) {
				//log.info("getNeedCPUsFromJobList add "+j.getNumCPUs() +" to "+count);
				count += j.getNumCPUs();
			}
		}
		
		return count;
	}
	
	
	
	
}
