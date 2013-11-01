package testdata;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Set;

import model.Company;
import model.Occupation;
import model.Person;
import model.Skill;



public class TestDataGenerator {
	//private static Logger log = Logger.getLogger(TestDataGenerator.class);
	
	private Random r;
	private final Date time = new Date(1301346459176l);
	private final String[] firstNames = new String[]{"Aaron", "Adam", "Adrian", "Alan", "Alejandro", "Alex", "Allen", "Andrew", "Andy", "Anthony", "Art",
	"Arthur", "Barry", "Bart", "Ben", "Benjamin", "Bill", "Bobby", "Brad", "Bradley", "Brendan", "Brett", 
	"Brian", "Bruce", "Bryan", "Carlos", "Chad", "Charles", "Chris", "Christopher", "Chuck", "Clay",
	"Corey", "Craig", "Dan", "Daniel", "Darren", "Dave", "David", "Dean", "Dennis", "Denny", "Derek", "Don", 
	"Doug", "Duane", "Edward", "Eric", "Eugene", "Evan", "Frank", "Fred", "Gary", "Gene", "George", "Gordon", 
	"Greg", "Harry", "Henry", "Hunter", "Ivan", "Jack", "James", "Jamie", "Jason", "Jay", "Jeff", "Jeffrey", 
	"Jeremy", "Jim", "Joe", "Joel", "John", "Jonathan", "Joseph", "Justin", "Keith", "Ken", "Kevin", "Larry", 
	"Logan", "Marc", "Mark", "Matt", "Matthew", "Michael", "Mike", "Nat", "Nathan", "Patrick", "Paul", "Perry", 
	"Peter", "Philip", "Phillip", "Randy", "Raymond", "Ricardo", "Richard", "Rick", "Rob", "Robert", "Rod", 
	"Roger", "Ross", "Ruben", "Russell", "Ryan", "Sam", "Scot", "Scott", "Sean", "Shaun", "Stephen", "Steve", 
	"Steven", "Stewart", "Stuart", "Ted", "Thomas", "Tim", "Toby", "Todd", "Tom", "Troy", "Victor", "Wade",
	"Walter", "Wayne", "William"}; //length = 130
	
	private final String[] surNames = new String[]{"Adams","Adamson", "Adler", "Akers", "Akin", "Aleman", "Alexander", "Allen", "Allison", "Allwood",
			"Anderson", "Andreou", "Anthony", "Appelbaum", "Applegate", "Arbore", "Arenson", "Armold",
			"Arntzen", "Askew", "Athanas", "Atkinson", "Ausman", "Austin", "Averitt", "Avila-Sakar",
			"Badders", "Baer", "Baggerly", "Bailliet", "Baird", "Baker", "Ball", "Ballentine", "Ballew", "Banks",
			"Baptist-Nguyen", "Barbee", "Barber", "Barchas", "Barcio", "Bardsley", "Barkauskas", "Barnes",
			"Barnett", "Barnwell", "Barrera", "Barreto", "Barroso", "Barrow", "Bart", "Barton", "Bass", "Bates"}; //length = 54
	
	private final String[] cities = new String[]{"USA-NYC@10000", "USA-NYC@20000","USA-NYC@30000","USA-NYC@40000","FRA-PAR@1000","FRA-PAR@2000","FRA-PAR@3000","FRA-PAR@4000",
			"FRA-PAR@5000","GER-BER@10000","GER-BER@20000","GER-BER@30000","GER-BER@40000","GER-BER@50000","GER-BER@60000","GER-BER@70000","GER-BER@80000","AUT-VIE@1020", "AUT-VIE@1020","AUT-VIE@1030","AUT-VIE@1040","AUT-VIE@1050",
			"AUT-VIE@1060","AUT-VIE@1070","AUT-VIE@1080","AUT-VIE@1090","AUT-VIE@1100 AUT-VIE@1110","AUT-VIE@1120"};
	
	private final String[] skills = new String[]{"Java","C++","WebServices","Phyton","Ruby","HTML","CSS","PHP","Patterns","Algorithms","Cobol","ADA","SAP","XML","Javascript"};
	
	private final String[] companyPostfix = new String[]{"Inc.","& Co.","Foods","Supplies","and Son"," Developments","and more"};
	private final String[] fieldOfActivities = new String[]{"Research","Development","Aquiring","Manpower","Ideas"};
	
	public TestDataGenerator(long salt) {
		r = new Random(salt);
	}
	
	/*
	 * ************************************************** GENERATING METHODS */
	
	public Person generatePerson(Set<Skill> skill,Set<Occupation>  oc) {
		Person p = new Person();
		
		p.setPrename(firstNames[generateBetween(0,firstNames.length-1)]);
		p.setSurname(surNames[generateBetween(0,surNames.length-1)]);
		p.setDescrption(cities[generateBetween(0,cities.length-1)]);
		p.setSkills(skill);
		p.setOccupations(oc);
		return p;
	}
	
	public Skill generateSkill() {
		Skill s = new Skill();
		s.setName(skills[generateBetween(0,skills.length-1)]);
		
		return s;
	}
	
	public Company generateCompany(Set<Skill> skill) {
		Company c = new Company();
		
		c.setName(surNames[generateBetween(0,surNames.length-1)]+" "+companyPostfix[generateBetween(0,companyPostfix.length-1)]);
		c.setFounded(generateRandomDateInPast(100,600));
		c.setFieldOfActivity(skills[generateBetween(0,skills.length-1)]+" "+fieldOfActivities[generateBetween(0,fieldOfActivities.length-1)]);
		c.setSkills(skill);
		
		return c;
	}
	
	public Occupation generateOccupation(Company c/*, Person p*/) {
		Occupation o = new Occupation();
		o.setCompany(c);
		//o.setPerson(p);
		//o.setId(new OccupationPk(p.getId(), c.getId()));
		
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(c.getFounded());
		cal.add(Calendar.DAY_OF_YEAR, generateBetween(10,50));
		
		o.setFrom(cal.getTime());
		
		/*c.setOccupations(new HashSet<Occupation>());
		p.setOccupations(new HashSet<Occupation>());
		c.getOccupations().add(o);
		p.getOccupations().add(o);*/
		
		return o;
	}
	
	/*
	 * ************************************************** HELPER */
	
	private Date generateRandomDateInPast(int minDays,int maxDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.DAY_OF_YEAR, -(generateBetween(minDays,maxDays)));
		return cal.getTime();
	}
	
	private int generateBetween(int min, int max) {
		return min + (int)(r.nextDouble() * ((max - min) + 1));
	}
	
	
	
	
	
	
	
	
	
}
