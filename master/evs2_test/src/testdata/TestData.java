package testdata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import model.Company;
import model.Occupation;
import model.Person;
import model.Skill;

import persistence.HibernateManager;

public class TestData {
	
	public TestData() {
		EntityManager em = HibernateManager.getInstance().getEm();
		TestDataGenerator tdg = new TestDataGenerator(5);
		Set<Skill> skills = TestDataPersister.insertSkill(tdg, em, 4);
		

		Set<Company> companies = TestDataPersister.insertCompany(tdg, em, 15, skills);
		
		
		List<Company> cList = new ArrayList<Company>(companies);
		
		List<Occupation> occ1 = new ArrayList<Occupation>();
		List<Occupation> occ2 = new ArrayList<Occupation>();
		
		occ1.add(TestDataPersister.insertOccupation(tdg, em, cList.get(0)));
		occ1.add(TestDataPersister.insertOccupation(tdg, em, cList.get(1)));
		occ1.add(TestDataPersister.insertOccupation(tdg, em, cList.get(2)));
		
		occ2.add(TestDataPersister.insertOccupation(tdg, em, cList.get(3)));
		
		TestDataPersister.insertPerson(tdg, em, 2, skills,new HashSet<Occupation>(occ1));
		TestDataPersister.insertPerson(tdg, em, 18, skills,new HashSet<Occupation>(occ2));
		
		TestDataPersister.insertSkill(tdg, em, 15);
		

	}
	
}
