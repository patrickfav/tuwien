package swag.ejb.local;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import swag.bl.IMapManagement;
import swag.bl.IResourceManagement;
import swag.bl.ITestDataManagement;
import swag.bl.ITickManagement;
import swag.exceptions.DatabaseException;
import swag.models.GameMap;
import swag.models.BuildingType;

import swag.models.Message;
import swag.models.User;
import swag.test.TestDataGenerator;

@Stateless
public class TestDataManagementBean implements ITestDataManagement{
	
	private static final int SALT = 567;
	private static final int GENERATED_USER = 40;
	private static final int GENERATED_MAPS = 3;
	
	private static final int MAP_FIELDS = 10;
	
	private static Logger log = Logger.getLogger("TestDataManagementBean");
	
	@PersistenceContext
	public EntityManager em;
	
	@EJB
	public IMapManagement mapManagement;
	
	@EJB
	public IResourceManagement irm;
	
	public static TestDataGenerator tdg = new TestDataGenerator(SALT);
	
	@Override
	public void addTestData() throws DatabaseException {
		List<GameMap> map_list = addMap();
		List<User> user_list = addUser();
		addMessages(user_list.get(0),user_list.get(1),5);
	}

	private List<User> addUser() {
		User u;
		List<User> user_list = new ArrayList<User>();
		
		for(int i=0;i<GENERATED_USER;i++) {
			u = tdg.generateUser();
			
			if(i==0) {
				u.setUsername("direktorin");
				try {
					u.setPassword("pass");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				u.setMail("patrick.favrebulle+swag1@gmail.com");
			}
			
			if(i==1) {
				u.setLogin(true);
				u.setLoginDate(new Date());
				u.setUsername("player");
				try {
					u.setPassword("pass");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				u.setMail("patrick.favrebulle+swag2@gmail.com");
			}
			//u.getUserMaps().add(tdg.generateUserGameMap(map,u));
			em.persist(u);
			user_list.add(u);
			log.info("Persisting User "+u);
			
		}
		
		return user_list;
	}
	
	private List<GameMap> addMap() {
		List<GameMap> list = new ArrayList<GameMap>();
		for(int i=0;i<GENERATED_MAPS;i++) {
			list.add(mapManagement.createMap(MAP_FIELDS));
		}
		return list;
	}
	
	private void addMessages(User sender,User receiver,int count) {
		Message m;
		
		for(int i=0;i<count;i++) {
			m = tdg.generateMessage(sender, receiver);
			em.persist(m);
			log.info("Persisting Message "+m);
		}
	}
}
