package dst1.helper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import dst1.model.Membership;
import dst1.model.hardware.Cluster;
import dst1.model.hardware.Computer;
import dst1.model.hardware.Grid;
import dst1.model.tasks.Execution;
import dst1.model.tasks.Job;
import dst1.model.user.Admin;
import dst1.model.user.User;

public class TestDataPersister {
	
	private static Logger log = Logger.getLogger(TestDataPersister.class);
	
	public static List<User> insertUser(TestDataGenerator tdg, EntityManager em,int count) {
		
		User u = null;
		List<User> list= new ArrayList<User>();
		
		try {
			em.getTransaction().begin();
			
			/* persist user */
			for(int i=0;i<count;i++) {
				u = tdg.generateUser();
				em.persist(u);
				list.add(u);
				log.info("Save User: "+u);
			}
			
			
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
		
		return list;
	}
	
	public static List<Admin> insertAdmin(TestDataGenerator tdg, EntityManager em,int count) {
		Admin a = null;
		List<Admin> list= new ArrayList<Admin>();
		
		try {
			em.getTransaction().begin();
			
			/* persist admin */
			for(int i=0;i<count;i++) {
				a = tdg.generateAdmin();
				em.persist(a);
				list.add(a);
				log.info("Save Admin: "+a);
			}
			
			
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
		
		return list;
	}
	
	public static List<Job> insertJobs(TestDataGenerator tdg, EntityManager em,List<User> userList,int countPerUser) {
		Job j = null;
		List<Job> list= new ArrayList<Job>();
		
		try {
			em.getTransaction().begin();
			
			/* persist job */
			for(User u: userList)
				for(int i=0;i<countPerUser;i++) {
					j = tdg.generateJob(u);
					em.persist(j);
					list.add(j);
					log.info("Save Job: "+j);
				}
			
			
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
		
		return list;
	}

	public static List<Execution> insertExecutions(TestDataGenerator tdg, EntityManager em,List<Job> jobList) {
		Execution e = null;
		List<Execution> list= new ArrayList<Execution>();
		
		try {
			em.getTransaction().begin();
			
			/* persist exec */
			for(Job j: jobList) {
				e = tdg.generateExecution(j);
				j.setExecution(e);
				em.persist(j);
				list.add(e);
				log.info("Save Exec.: "+e);
			}
			
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e1) {
			try {
				em.getTransaction().rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e1.printStackTrace();
		}
		
		return list;
	}
	
	
	public static List<Grid> insertGrids(TestDataGenerator tdg, EntityManager em,int count) {
		Grid g = null;
		List<Grid> list= new ArrayList<Grid>();
		
		try {
			em.getTransaction().begin();
			
			/* persist grid */
			for(int i=0;i<count;i++) {
				g = tdg.generateGrid();
				em.persist(g);
				list.add(g);
				log.info("Save Grid: "+g);
			}
			
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e1) {
			try {
				em.getTransaction().rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e1.printStackTrace();
		}
		
		return list;
	}
	
	public static List<Membership> insertMembershipForGrid(TestDataGenerator tdg, EntityManager em,Grid g,List<User> user_list) {
		Membership m = null;
		List<Membership> list= new ArrayList<Membership>();
		
		try {
			em.getTransaction().begin();
			
			/* persist memberships */
			for(User u:user_list) {
				m = tdg.generateMembership(u, g);
				em.persist(m);
				list.add(m);
				log.info("Save Memb.: "+m);
			}
			
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e1) {
			try {
				em.getTransaction().rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e1.printStackTrace();
		}
		
		return list;
	}
	
	public static List<Cluster> insertCluster(TestDataGenerator tdg, EntityManager em,Grid g,Admin a,int count) {
		Cluster c = null;
		List<Cluster> list= new ArrayList<Cluster>();
		
		try {
			em.getTransaction().begin();
			
			/* persist grid */
			for(int i=0;i<count;i++) {
				c = tdg.generateCluster(a,g);
				em.persist(c);
				list.add(c);
				log.info("Save Clus.: "+c);
			}
			
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e1) {
			try {
				em.getTransaction().rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e1.printStackTrace();
		}
		
		return list;
	}
	
	public static void assignChildrenToCluster(TestDataGenerator tdg, EntityManager em,Cluster cl, List<Cluster> child_list) {
		try {
			em.getTransaction().begin();
			
			for(Cluster tmpCl: child_list) {
				tmpCl.getClusterOwner().add(cl);
				cl.getClusterChildren().add(tmpCl);
				em.persist(cl);
				log.info("Upd Clus.: "+cl);
				log.info("Upd Clus.: "+tmpCl);
			}

			em.flush();
			em.getTransaction().commit();
		} catch (Exception e1) {
			try {
				em.getTransaction().rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e1.printStackTrace();
		}
	}
	
	public static List<Computer> insertComputer(TestDataGenerator tdg, EntityManager em,Cluster cl,int count) {
		Computer c = null;
		List<Computer> list= new ArrayList<Computer>();
		
		try {
			em.getTransaction().begin();
			
			/* persist grid */
			for(int i=0;i<count;i++) {
				c = tdg.generateComputer(cl);
				em.persist(c);
				list.add(c);
				log.info("Save Comp.: "+c);
			}
			
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e1) {
			try {
				em.getTransaction().rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e1.printStackTrace();
		}
		
		return list;
	}
	
	public static void assignExecution(TestDataGenerator tdg, EntityManager em,Computer c,List<Execution> exec_list) {
		try {
			em.getTransaction().begin();
			
			for(Execution e:exec_list) {
				c.getExecutions().add(e);
				e.getComputer().add(c);
				em.persist(c);
				log.info("Upd Exec.: "+e);
			}
			log.info("Upd Comp.: "+c);
			
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e1) {
			try {
				em.getTransaction().rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e1.printStackTrace();
		}
	}
	
	/*
	 * STASTICS PRINT OUT
	 */
	
	public static String printUserCounts(EntityManager em) {
		return "User: "+getUserRowCount(em)+" / Admin: "+getAdminRowCount(em)+" / Address: "+getAddressRowCount(em)+" / Memb.: "+getMembershipRowCount(em) ;
	}
	
	public static String printHardwareCounts(EntityManager em) {
		return "Grid: "+getGridRowCount(em)+" / Cluster: "+getClusterRowCount(em)+" / Computer: "+getComputerRowCount(em)+" / Memb.: "+getMembershipRowCount(em) ;
	}
	
	public static String printTaskCounts(EntityManager em) {
		return "Job: "+getJobRowCount(em)+" / Env.: "+getEnvironmentRowCount(em)+" / Execution: "+getExecutionRowCount(em);
	}
	
	/*
	 * ROW COUNT METHODS
	 */
	
	
	public static BigInteger getUserRowCount(EntityManager em) {
		return (BigInteger) em.createNativeQuery("select count(*) as rowcount from User").getSingleResult();
	}
	public static BigInteger getAdminRowCount(EntityManager em) {
		return (BigInteger) em.createNativeQuery("select count(*) as rowcount from Admin").getSingleResult();
	}
	public static BigInteger getAddressRowCount(EntityManager em) {
		return (BigInteger) em.createNativeQuery("select count(*) as rowcount from Address").getSingleResult();
	}
	public static BigInteger getJobRowCount(EntityManager em) {
		return (BigInteger) em.createNativeQuery("select count(*) as rowcount from Job").getSingleResult();
	}
	public static BigInteger getEnvironmentRowCount(EntityManager em) {
		return (BigInteger) em.createNativeQuery("select count(*) as rowcount from Environment").getSingleResult();
	}
	public static BigInteger getExecutionRowCount(EntityManager em) {
		return (BigInteger) em.createNativeQuery("select count(*) as rowcount from Execution").getSingleResult();
	}
	public static BigInteger getGridRowCount(EntityManager em) {
		return (BigInteger) em.createNativeQuery("select count(*) as rowcount from Grid").getSingleResult();
	}
	public static BigInteger getMembershipRowCount(EntityManager em) {
		return (BigInteger) em.createNativeQuery("select count(*) as rowcount from Membership").getSingleResult();
	}
	public static BigInteger getClusterRowCount(EntityManager em) {
		return (BigInteger) em.createNativeQuery("select count(*) as rowcount from Cluster").getSingleResult();
	}
	public static BigInteger getComputerRowCount(EntityManager em) {
		return (BigInteger) em.createNativeQuery("select count(*) as rowcount from Computer").getSingleResult();
	}
}
