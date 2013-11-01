package dst2.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst1.helper.TestDataGenerator;
import dst1.model.Membership;
import dst1.model.hardware.Cluster;
import dst1.model.hardware.Computer;
import dst1.model.hardware.Grid;
import dst1.model.tasks.Environment;
import dst1.model.tasks.Execution;
import dst1.model.tasks.Execution.JobStatus;
import dst1.model.tasks.Job;
import dst1.model.user.Admin;
import dst1.model.user.User;
import dst2.ejb.interfaces.Testing;

@Stateless
public class TestingBean implements Testing{
	
	private static Logger log = Logger.getLogger("TestingBean");
	private static final long SALT = 5l;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void insertTestData() {
		TestDataGenerator tdg = new TestDataGenerator(SALT);
		
		/* insert User */
		User u;
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < 4; i++) {
			u = tdg.generateUser();
			
			if(i==0) {
				u.setUsername("Phillip9507");
				u.setPassword("9f3797");
			}
			if(i==1) {
				u.setUsername("Ivan9476");
				u.setPassword("51ad7ef");
			}	
			
			
			u.setId(i+1);
			em.persist(u);
			userList.add(u);
			log.info("Save User: " + u);
		}
		
		/* insert admin */
		Admin a = tdg.generateAdmin();
		a.setId(100);
		em.persist(a);
		log.info("Save Admin: "+a);

		
		/* insert grids */
		Grid g;
		List<Grid> gridList = new ArrayList<Grid>();
		for (int i = 0; i < 2; i++) {
			g = tdg.generateGrid();
			g.setId(i+1);
			em.persist(g);
			gridList.add(g);
			log.info("Save Grid: " + g);
		}
		
		/* insert clusters */
		Cluster c = null;
		List<Cluster> clusterList = new ArrayList<Cluster>();

		for (int i = 0; i < 2; i++) {
			c = tdg.generateCluster(a, gridList.get(0));
			c.setId((long) ((i+1)));
			em.persist(c);
			clusterList.add(c);
			log.info("Save Clus.: " + c);
		}
		for (int i = 0; i < 2; i++) {
			c = tdg.generateCluster(a, gridList.get(1));
			c.setId((long) ((i+3)));
			em.persist(c);
			clusterList.add(c);
			log.info("Save Clus.: " + c);
		}
		
		
		/* save job */
		
		Environment env = new Environment();
		//env.setId(1l);
		env.setWorkflow("workflow");
		env.setParams(new ArrayList<String>());
		
		Job job = tdg.generateJob(userList.get(0));
		//job.setId(1l);
		job.setEnvironment(env);
		job.setPaid(false);
		em.persist(job);
		log.info("Save Job: " + job);
		
		Execution exec = tdg.generateExecution(job);
		//exec.setId(1l);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, -30);
		exec.setStart(cal.getTime());
		exec.setEnd(null);
		exec.setStatus(JobStatus.RUNNING);
		
		/* insert Computer */
		Computer comp = null;
		List<Computer> computerList = new ArrayList<Computer>();
		
		for (int i = 0; i < 2; i++) {
			comp = tdg.generateComputer(clusterList.get(0));
			
			if(i==0)
				comp.getExecutions().add(exec);
			
			comp.setId((long) (i+1));
			em.persist(comp);
			computerList.add(comp);
			log.info("Save Comp.: " + comp);
		}
		for (int i = 0; i < 2; i++) {
			comp = tdg.generateComputer(clusterList.get(1));
			comp.setId((long) (i+3));
			em.persist(comp);
			computerList.add(comp);
			log.info("Save Comp.: " + comp);
		}
		for (int i = 0; i < 2; i++) {
			comp = tdg.generateComputer(clusterList.get(2));
			comp.setId((long) (i+5));
			em.persist(comp);
			computerList.add(comp);
			log.info("Save Comp.: " + comp);
		}
		for (int i = 0; i < 2; i++) {
			comp = tdg.generateComputer(clusterList.get(3));
			comp.setId((long) (i+7));
			em.persist(comp);
			computerList.add(comp);
			log.info("Save Comp.: " + comp);
		}
		
		
		Membership m = null;
		List<Membership> memberList = new ArrayList<Membership>();

		/* persist memberships */	
		m = tdg.generateMembership(userList.get(0), gridList.get(0));
		m.setId(1l);
		em.persist(m);
		memberList.add(m);
		log.info("Save Memb.: " + m);
		m = tdg.generateMembership(userList.get(0), gridList.get(1));
		m.setId(2l);
		em.persist(m);
		memberList.add(m);
		log.info("Save Memb.: " + m);
		
		m = tdg.generateMembership(userList.get(1), gridList.get(0));
		m.setId(3l);
		em.persist(m);
		memberList.add(m);
		log.info("Save Memb.: " + m);
		m = tdg.generateMembership(userList.get(1), gridList.get(1));
		m.setId(4l);
		em.persist(m);
		memberList.add(m);
		log.info("Save Memb.: " + m);
		
		m = tdg.generateMembership(userList.get(2), gridList.get(0));
		m.setId(5l);
		em.persist(m);
		memberList.add(m);
		log.info("Save Memb.: " + m);
		m = tdg.generateMembership(userList.get(2), gridList.get(1));
		m.setId(6l);
		em.persist(m);
		memberList.add(m);
		log.info("Save Memb.: " + m);
		
		
		
		}
	

}
