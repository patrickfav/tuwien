package swag.test;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import swag.models.GameMap;
import swag.models.Message;
import swag.models.User;
import swag.models.UserGameMap;
import swag.models.enums.ResourceType;

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
	
	private final String[] streets = new String[]{"Alfred-Bock-Strasse", "Alicenstrasse","Alte Schulstrasse","Alten Busecker Strasse","Altenfeldsweg",
			"Alter Steinbacher Weg","Alter Wetzlarer Weg","Am Bergwerkswald","Am Eichelbaum","Am Steg","Am Zollstock","Anneröder Weg","Asterweg","Aulweg",
			"Bachweg","Bahnhofstrasse","Berliner Platz","Bernhardtstrasse","Bismarkstrasse","Bleichstrasse","Bootshausstrasse","Carl-Franz-Strasse",
			"Carlo-Mierendorff-Strasse","Crednerstrasse","Curtmannstrasse","Dammstrasse","Diezstrasse","Dürerstrass"};
	
	private final String[] words = new String[]{"Lorem","ipsum","dolor","sit","amet,","consetetur","sadipscing","elitr","sed","diam","nonumy","eirmod",
			"tempor","invidunt","ut","labore","et","dolore","magna","aliquyam","erat","sed","diam","voluptua","At","vero","eos","et","accusam","et","justo",
			"duo","dolores","et","ea","rebum",",",".","!"};
	
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
		try {
			u.setPassword(UUID.randomUUID().toString().substring(1,8));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		u.setAddress(streets[generateBetween(0,streets.length-1)]+" "+generateBetween(1,120));
		u.setLogin(false);
		u.setMail(u.getFirstName()+"@"+u.getLastName()+".com");
		return u;
	}
	
	public Message generateMessage(User sender, User receiver) {
		Message m = new Message();
		m.setSendDate(new Date());
		m.setSubject(words[generateBetween(0,words.length-1)]);
		m.setText(generateText(generateBetween(15,30)));
		m.setReceiver(receiver);
		m.setSender(sender);
		m.setTimestamp(new Date());
		m.setAlreadySent(true);
		return m;
	}
	
	public UserGameMap generateUserGameMap(GameMap map,User u) {
		UserGameMap ugm = new UserGameMap();
		ugm.getResourceStock().put(ResourceType.STONE, (long) generateBetween(100,9999));
		ugm.getResourceStock().put(ResourceType.IRON, (long) generateBetween(100,9999));
		ugm.getResourceStock().put(ResourceType.WOOD, (long) generateBetween(100,9999));
		ugm.setMap(map);
		ugm.setUser(u);
		
		return ugm;
	}
	
	/*
	 * ************************************************** HELPER */
	
	private int generateBetween(int min, int max) {
		return min + (int)(r.nextDouble() * ((max - min) + 1));
	}
	private String generateText(int wordCount) {
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<wordCount;i++) {
			sb.append(words[generateBetween(0,words.length-1)]);
			sb.append(" ");
		}
		return sb.toString();
	}
	
}
