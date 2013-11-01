package swag.ejb.local;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import swag.bl.IConfigDBManagement;
import swag.bl.ITestDataManagement;
import swag.bl.ITickManagement;
import swag.exceptions.DatabaseException;

@Singleton
@Startup
public class StartUpBean {
	
	private static Logger log = Logger.getLogger("StartUpBean");
	
	@EJB
	public ITickManagement tm;
	
	@EJB
	public ITestDataManagement tdm;
	
	@EJB
	public IConfigDBManagement cdbm;
	
	@PostConstruct
	public void setup() {
		log.info("Starting: Adding Config DB Data");
		cdbm.addDataToDB();
		
		log.info("Starting: Adding TestData");
		try {
			tdm.addTestData();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("Starting: Initializing Timer");
		tm.init();
	}
}
