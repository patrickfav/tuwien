package dst1;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.log4j.Logger;

import dst1.helper.ComputerExecutionWrapper;
import dst1.helper.TestDataPersister;
import dst1.helper.TestDataGenerator;
import dst1.interceptor.SQLInterceptor;
import dst1.listener.DefaultListener;
import dst1.model.Membership;
import dst1.model.hardware.Cluster;
import dst1.model.hardware.Computer;
import dst1.model.hardware.Grid;
import dst1.model.tasks.Execution;
import dst1.model.tasks.Job;
import dst1.model.user.Admin;
import dst1.model.user.User;
import dst1.query.HibernateCriteriaTask;


public class Main {
	
	private static Logger log = Logger.getLogger(Main.class);
	private static final long SALT = 5l;
	
	@PersistenceUnit
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	@Resource
	private static UserTransaction utx;
	
	private static TestDataGenerator tdg;
	
	private static List<Cluster> cluster_list;
	private static List<Computer> computer_list;
	private static List<User> user_list;
	private static List<Admin> admin_list;
	private static List<Job> job_list;
	private static List<Execution> exec_list;
	private static List<Grid> grid_list;
	private static List<Membership> memb_list;
	
	private Main() {
		super();
	}

	public static void main(String[] args) {
		
		log.info("Deploying scheme to Database dst");
		emf = Persistence.createEntityManagerFactory( "grid" );  
		em = emf.createEntityManager();
		tdg = new TestDataGenerator(SALT);

		log.info("Time: "+(new Date()).getTime());
		log.info("=================================");
		log.info("| EXERCISES START               |");
		log.info("=================================");
		
		log.info("");
		log.info("---------------------------------");
		log.info("| Exercise 1                    |");
		log.info("---------------------------------");
		dst01();
		
		log.info("");
		log.info("---------------------------------");
		log.info("| Exercise 2a                   |");
		log.info("---------------------------------");
		dst02a();
		
		log.info("");
		log.info("---------------------------------");
		log.info("| Exercise 2b                   |");
		log.info("---------------------------------");
		dst02b();
		
		log.info("");
		log.info("---------------------------------");
		log.info("| Exercise 2c                   |");
		log.info("---------------------------------");
		dst02c();
		
		log.info("");
		log.info("---------------------------------");
		log.info("| Exercise 3                    |");
		log.info("---------------------------------");
		dst03();
		
		log.info("");
		log.info("---------------------------------");
		log.info("| Exercise 4a                   |");
		log.info("---------------------------------");
		dst04a();
		
		log.info("");
		log.info("---------------------------------");
		log.info("| Exercise 4b                   |");
		log.info("---------------------------------");
		dst04b();
		
		log.info("");
		log.info("---------------------------------");
		log.info("| Exercise 4c                   |");
		log.info("---------------------------------");
		dst04c();
		
		log.info("");
		log.info("---------------------------------");
		log.info("| Exercise 4d                   |");
		log.info("---------------------------------");
		dst04d();
		
		log.info("");
		log.info("=================================");
		log.info("| EXERCISES END                 |");
		log.info("=================================");
		
		//em.close();
		//emf.close();
	}

	public static void dst01() {
		
		/* ******************************* USER */
		user_list = TestDataPersister.insertUser(tdg, em,10);
		
		/* ******************************* ADMINS */
		admin_list = TestDataPersister.insertAdmin(tdg,em,10);
		
		log.info(TestDataPersister.printUserCounts(em));
		try {
			/* change user */
			em.getTransaction().begin();
			user_list.get(0).setFirstName("Philip_New");
			user_list.get(0).getAddress().setStreet("NewStreet 1");
			em.persist(user_list.get(0));
			em.flush();
			em.getTransaction().commit();
			
			/* get user */
			em.getTransaction().begin();
			log.info("Update: "+(User)em.find(User.class, user_list.get(0).getId()));
			em.flush();
			em.getTransaction().commit();
			
			/* remove user */
			em.getTransaction().begin();
			log.info("Remove: "+(User)em.find(User.class, user_list.get(user_list.size()-1).getId()));
			em.remove(user_list.get(user_list.size()-1));
			user_list.remove(user_list.size()-1);
			em.flush();
			em.getTransaction().commit();
			
			log.info(TestDataPersister.printUserCounts(em));
			
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		/* ******************************* JOBS */
		job_list = TestDataPersister.insertJobs(tdg, em, user_list.subList(0, 6), 5);
		
		log.info(TestDataPersister.printTaskCounts(em));
		try {
			/* check params */
			em.getTransaction().begin();
			em.persist(job_list.get(0));
			log.info("Check param: "+em.find(Job.class, job_list.get(0).getId()));
			job_list.get(0).getEnvironment().getParams().add("lastParam");
			em.persist(job_list.get(0));
			log.info("Check param: "+em.find(Job.class, job_list.get(0).getId()));
			Job tmpJob = em.find(Job.class, job_list.get(0).getId());
			tmpJob.getEnvironment().getParams().remove(1);
			em.persist(tmpJob);
			log.info("Check param: "+em.find(Job.class, job_list.get(0).getId()));
			em.flush();
			em.getTransaction().commit();
			
			/* del user, should del jobs & execs */
			em.getTransaction().begin();
			log.info("Remove: "+(User)em.find(User.class, user_list.get(5).getId()));
			em.remove(user_list.get(5));
			user_list.remove(5);
			em.flush();
			em.getTransaction().commit();
			
			log.info("UserCount:"+TestDataPersister.getUserRowCount(em)+" / AdminCount:"+TestDataPersister.getAdminRowCount(em)+" / AddressCount:"+TestDataPersister.getAddressRowCount(em));
			log.info("JobCount:"+TestDataPersister.getJobRowCount(em)+" / EnvCount:"+TestDataPersister.getEnvironmentRowCount(em));
			
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		/* ******************************* EXECUTIONS */
		exec_list = TestDataPersister.insertExecutions(tdg, em, job_list.subList(0, 10));
		
		/* ******************************* GRIDS */
		grid_list = TestDataPersister.insertGrids(tdg, em, 5);
		log.info(TestDataPersister.printHardwareCounts(em));
		
		/* ******************************* MEMBERSHIPS */
		memb_list = TestDataPersister.insertMembershipForGrid(tdg, em, grid_list.get(0), user_list.subList(0, Math.round(user_list.size()/2)));
		memb_list.addAll(TestDataPersister.insertMembershipForGrid(tdg, em, grid_list.get(1), user_list.subList(0, Math.round(user_list.size()/2))));
		memb_list.addAll(TestDataPersister.insertMembershipForGrid(tdg, em, grid_list.get(3), user_list.subList(0, 1)));
		
		/* ******************************* CLUSTER */
		cluster_list = TestDataPersister.insertCluster(tdg, em, grid_list.get(0), admin_list.get(0), 3);
		cluster_list.addAll(TestDataPersister.insertCluster(tdg, em, grid_list.get(0), admin_list.get(1), 3));
		cluster_list.addAll(TestDataPersister.insertCluster(tdg, em, grid_list.get(1), admin_list.get(1), 3));
		
		/* ******************************* CLUSTER TO CLUSTER*/
		TestDataPersister.assignChildrenToCluster(tdg,em,cluster_list.get(1),cluster_list.subList(2, 4));
		
		/* ******************************* COMPUTER */
		computer_list = TestDataPersister.insertComputer(tdg, em, cluster_list.get(0), 4);
		computer_list.addAll(TestDataPersister.insertComputer(tdg, em, cluster_list.get(1), 2));
		log.info(TestDataPersister.printHardwareCounts(em));
		
		/* ******************************* ASSIGN EXECS */
		TestDataPersister.assignExecution(tdg, em, computer_list.get(0), exec_list.subList(0, 3));
		TestDataPersister.assignExecution(tdg, em, computer_list.get(1), exec_list.subList(3, 6));
		TestDataPersister.assignExecution(tdg, em, computer_list.get(2), exec_list.subList(6, 9));
		
		log.info("*****************************************");
		log.info("Print all row-counts:");
		log.info(TestDataPersister.printUserCounts(em));
		log.info(TestDataPersister.printTaskCounts(em));
		log.info(TestDataPersister.printHardwareCounts(em));
		log.info("*****************************************");
		
		log.info("Test: Delete User with Jobs");
		
		/* ******************************* TEST DELETE USER */
		try {
			
			/* del user, should del jobs & execs */
			em.getTransaction().begin();
			log.info("Remove: "+user_list.get(2));
			//em.remove(user_list.get(2));
			user_list.remove(2);
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void dst02a() {
		
		log.info("Get All Users with certain Membership (GRID-2219) and more than 3 jobs");
		List<User> result_2a_1 = (List<User>) em.createNamedQuery("findUserWithActiveMemberShipWithAtLeastXJobs").setParameter("name", "GRID-2219").setParameter("count", 3).getResultList();
		log.info("Resultset Query1: ("+result_2a_1.size()+")"+result_2a_1.toString());
		
		log.info("Find User with Max Jobs");
		User result_2a_2 =  (User) em.createNamedQuery("findMaxJobUser").getSingleResult();
		log.info("Resultset Query2: "+result_2a_2);
	}

	@SuppressWarnings("unchecked")
	public static void dst02b() {
		log.info("Get Usage of all AUT-VIE Server");
		List<ComputerExecutionWrapper> result_2b_1 = em.createNamedQuery("getTotalUsageOfAllComputers").setParameter("loc", "AUT-VIE%").getResultList();
		log.info("Resultset Query: ("+result_2b_1.size()+")"+result_2b_1.toString());
	}

	public static void dst02c() {
		log.info("Get All Jobs by Phillip9507 with workflow workflow-3");
		log.info(HibernateCriteriaTask.getAllJobsCreatedByUser(em, "workflow-3", "Phillip9507"));
		
		log.info("Get All finished jobs with start:1300969599176 and end:1300972675414");
		log.info("ResultQuery:"+ HibernateCriteriaTask.getFinishedJobsByExample(em, new Date(1300969599176l), new Date(1300972675414l)));
	}

	public static void dst03() {
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		

		log.info("Inserting Correct Computer");
		// Validation - generator generates always valid object
		Set<ConstraintViolation<Computer>> violationsComp = validator.validate(tdg.generateComputer(cluster_list.get(0)));
		if(violationsComp.size() > 0) {
			for(ConstraintViolation<Computer> cv : violationsComp) {
				log.info("Validation errors: " + cv.getMessage());
			}
		} else { log.info("No validation errors!"); }
		
		
		log.info("Inserting Computer with all possible faults");
		// Validation
		Computer c = tdg.generateComputer(cluster_list.get(0));
		c.setLocation("ABC-AB@1234");
		c.setName("ABC");
		c.setLastUpdate(new Date(new Date().getTime()+9999999));
		c.setCreation(new Date(new Date().getTime()+9999999));
		//c.setCpus(128);
		
		violationsComp = validator.validate(c);
		if(violationsComp.size() > 0) {
			for(ConstraintViolation<Computer> cv : violationsComp) {
				log.info("Validation errors: " + cv.getMessage());
			}
		} else { log.info("No validation errors!"); }
		
		log.info("Inserting Grid with Computer with too many CPUs");
		Cluster cl = cluster_list.get(cluster_list.size()-1);
		c = tdg.generateComputer(cl);
		c.setName("ABCD_1");
		c.setCpus(256);
		cl.getComputer().add(c);
		Grid g = grid_list.get(grid_list.size()-1);
		g.getCluster().add(cl);
		
		Set<ConstraintViolation<Grid>> violationsGrid = validator.validate(g);
		if(violationsComp.size() > 0) {
			for(ConstraintViolation<Grid> cv : violationsGrid) {
				log.info("Validation error: " + cv.getMessage());
			}
		} else { log.info("No validation errors!"); }
	}

	public static void dst04a() {
		log.info("See Sourcecode...");
		EntityTransaction t = em.getTransaction();
		try {
			/* begin transaction */
			t.begin();
			
			/* generate job */
			Job j = tdg.generateJob(user_list.get(4));
			/* persist job - is not saved yet -just managed*/
			em.persist(j);
			/* commit transaction - tring to persist to db*/
			t.commit();
		} catch (Exception e) {
			/* smth. went wrong */
			try {
				/* trying to roll back the whole conversation */
				em.getTransaction().rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			/* if transaction still active smth went wrong - rollback */
			if (t.isActive()) em.getTransaction().rollback();
		}
	}

	public static void dst04b() {
		try {
			log.info("Testing Update");
			em.getTransaction().begin();
			log.info("Before Upd Comp.: "+computer_list.get(0));
			log.info("Waiting 3 seconds...");
			Thread.sleep(3000);
			em.persist(computer_list.get(0));
			em.flush();
			em.getTransaction().commit();
			log.info("Upd Comp.: "+computer_list.get(0));
			
			
			log.info("Testing Persist");
			em.getTransaction().begin();
			Computer c = tdg.generateComputer(cluster_list.get(0));
			log.info("Before persist: "+c);
			em.persist(c);
			em.flush();
			em.getTransaction().commit();
			log.info("After persist: "+c);
			
		} catch (Exception e1) {
			try {
				em.getTransaction().rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e1.printStackTrace();
		}
	}

	public static void dst04c() {
		log.info(DefaultListener.generateReport());
		log.info("Reset Report...");
		DefaultListener.reset();
		
		log.info("Generate Users...");
		TestDataPersister.insertUser(tdg, em,10);
		
		log.info(DefaultListener.generateReport());
	}

	public static void dst04d() {
		log.info("Print log:");
		log.info(SQLInterceptor.printLog());
		
		log.info("resetting...");
		SQLInterceptor.reset();

		log.info("Get Usage of all AUT-VIE Server");
		em.createNamedQuery("getTotalUsageOfAllComputers").setParameter("loc", "AUT-VIE%").getResultList();
		
		log.info(SQLInterceptor.printLog());
	}

	public static UserTransaction getUtx() {
		return utx;
	}

	public static void setUtx(UserTransaction utx) {
		Main.utx = utx;
	}
	
	
	
}
