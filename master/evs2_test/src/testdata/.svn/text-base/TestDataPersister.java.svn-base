package testdata;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import model.Company;
import model.Occupation;
import model.Person;
import model.Skill;

import org.apache.log4j.Logger;

public class TestDataPersister {
	
	private static Logger log = Logger.getLogger(TestDataPersister.class);
	
	public static Set<Person> insertPerson(TestDataGenerator tdg, EntityManager em,int count,Set<Skill> skills,Set<Occupation> occ) {
		
		Person p = null;
		Set<Person> list= new HashSet<Person>();
		
		try {
			em.getTransaction().begin();
			
			/* persist */
			for(int i=0;i<count;i++) {
				p = tdg.generatePerson(skills,occ);
				em.persist(p);
				list.add(p);
				log.debug("Save Person: "+p);
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
	
	public static Set<Company> insertCompany(TestDataGenerator tdg, EntityManager em,int count,Set<Skill> skills) {
		
		Company c = null;
		Set<Company> list= new HashSet<Company>();
		
		try {
			em.getTransaction().begin();
			
			/* persist */
			for(int i=0;i<count;i++) {
				c = tdg.generateCompany(skills);
				em.persist(c);
				list.add(c);
				log.debug("Save Company: "+c);
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
	
	public static Occupation insertOccupation(TestDataGenerator tdg, EntityManager em, Company c) {
		
		Occupation o = null;
		
		try {
			em.getTransaction().begin();
			
			/* persist */
			
			o = tdg.generateOccupation(c);
			em.persist(o);
			log.debug("Save Company: "+o);
			
			
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
		
		return o;
	}
	
	public static Set<Skill> insertSkill(TestDataGenerator tdg, EntityManager em,int count) {
		
		Skill s = null;
		Set<Skill> list= new HashSet<Skill>();
		
		try {
			em.getTransaction().begin();
			
			/* persist */
			for(int i=0;i<count;i++) {
				s = tdg.generateSkill();
				em.persist(s);
				list.add(s);
				log.debug("Save Skill: "+s);
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
	
	
	/*
	 * ROW COUNT METHODS
	 */
	
	
	public static BigInteger getPresonRowCount(EntityManager em) {
		return (BigInteger) em.createNativeQuery("select count(*) as rowcount from Person").getSingleResult();
	}

}
