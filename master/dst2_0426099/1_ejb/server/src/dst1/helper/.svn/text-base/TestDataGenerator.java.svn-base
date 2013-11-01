package dst1.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import dst1.model.Membership;
import dst1.model.hardware.Cluster;
import dst1.model.hardware.Computer;
import dst1.model.hardware.Grid;
import dst1.model.tasks.Environment;
import dst1.model.tasks.Execution;
import dst1.model.tasks.Job;
import dst1.model.tasks.Execution.JobStatus;
import dst1.model.user.Address;
import dst1.model.user.Admin;
import dst1.model.user.User;

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
	
	private final String[] workflows = new String[]{"workflow-0","workflow-1","workflow-2","workflow-3","workflow-4","workflow-5","workflow-6","workflow-7","workflow-8"}; //9
	private final String[] params = new String[]{"1","0","-1","true","false","null","stringParam1","stringParam2","stringParam3","1.0","1l","array1","array2","array3"}; //14
	private final JobStatus[] jobStatus = new JobStatus[]{JobStatus.FINISHED,JobStatus.FAILED,JobStatus.RUNNING,JobStatus.SCHEDULED}; //4
	
	
	public TestDataGenerator(long salt) {
		r = new Random(salt);
	}
	
	/*
	 * ************************************************** GENERATING METHODS */
	
	public User generateUser() {
		User u = new User();
		
		u.setFirstName(firstNames[generateBetween(0,firstNames.length-1)]);
		u.setLastName(surNames[generateBetween(0,surNames.length-1)]);
		u.setUsername(u.getFirstName()+r.nextInt(9999));
		u.setPassword(UUID.randomUUID().toString().substring(1,8));
		u.setAccountNo(String.valueOf(generateBetween(10000000,99999999)));
		u.setBankCode(String.valueOf(generateBetween(1000,9999)));
		u.setAddress(generateAddress());
		
		return u;
	}
	
	public Admin generateAdmin() {
		Admin a = new Admin();
		
		a.setFirstName(firstNames[generateBetween(0,129)]);
		a.setLastName(surNames[generateBetween(0,53)]);
		a.setAddress(generateAddress());
		
		return a;
	}
	
	public Address generateAddress() {
		Address a= new Address();
		a.setStreet(firstNames[generateBetween(0,129)]+"street "+generateBetween(1,199));
		a.setCity(cities[generateBetween(0,cities.length-1)]);
		a.setZipCode(String.valueOf(generateBetween(1000,7999)));
		return a;
	}
	
	public Job generateJob(User u) {
		Job j = new Job();
		j.setPaid(r.nextBoolean());
		j.setCreator(u);
		j.setEnvironment(generateEnvironment());
		u.getJobs().add(j);
		return j;
	}
	
	public Environment generateEnvironment() {
		Environment e = new Environment();
		e.setWorkflow(workflows[generateBetween(0,workflows.length-1)]);
		
		int paramC = r.nextInt(6);
		e.setParams(new ArrayList<String>(paramC));
		
		
		for(int i=0;i<paramC;i++) {
			e.getParams().add(params[generateBetween(0,params.length-1)]);
		}
		
		return e;
	}
	
	public Execution generateExecution(Job j) {
		Execution e = new Execution();
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.MINUTE, -(generateBetween(60,60*24*30)));
		e.setStart(cal.getTime());
		cal.add(Calendar.MILLISECOND, generateBetween(2500,1*60*60*1000));
		e.setEnd(cal.getTime());
		e.setJob(j);
		e.setStatus(jobStatus[generateBetween(0,jobStatus.length-1)]);
		j.setExecution(e);
		return e;
	}
	
	public Grid generateGrid() {
		Grid g = new Grid();
		g.setName("GRID-"+generateBetween(1,9999));
		g.setLocation(cities[generateBetween(0,cities.length-1)]);
		g.setCostsPerCPUMinute(new BigDecimal(r.nextDouble()*35));
		return g;
	}
	
	public Membership generateMembership(User u,Grid g) {
		Membership m = new Membership();
		m.setGrid(g);
		m.setUser(u);
		m.setDiscount(r.nextDouble()*0.5);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.DAY_OF_YEAR, -(generateBetween(7,365)));
		m.setRegistration(cal.getTime());
		
		u.getMemberships().add(m);
		g.getMemberships().add(m);
		
		return m;
	}
	
	public Cluster generateCluster(Admin a,Grid g) {
		Cluster c = new Cluster();
		c.setName("CLUSTER-"+generateBetween(1,9999));
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.DAY_OF_YEAR, -(generateBetween(2,364)));
		c.setLastService(cal.getTime());
		cal.add(Calendar.DAY_OF_YEAR,365);
		c.setNextService(cal.getTime());
		
		c.setGrid(g);
		c.setAdmin(a);
		
		g.getCluster().add(c);
		a.getCluster().add(c);
		
		return c;
	}
	
	public Computer generateComputer(Cluster cl) {
		Computer c = new Computer();
		c.setName("COMP-"+generateBetween(1,9999));
		c.setCpus(generateBetween(1,64));
		c.setLocation(cities[generateBetween(0,cities.length-1)]);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.DAY_OF_YEAR, -(generateBetween(2,364)));
		c.setCreation(cal.getTime());
		cal.setTime(time);
		cal.add(Calendar.DAY_OF_YEAR,-(generateBetween(1,30)));
		c.setLastUpdate(cal.getTime());
	
		cl.getComputer().add(c);
		c.setCluster(cl);
		
		return c;
	}
	
	/*
	 * ************************************************** HELPER */
	
	private int generateBetween(int min, int max) {
		return min + (int)(r.nextDouble() * ((max - min) + 1));
	}
	
	
	
	
	
	
	
	
	
}
