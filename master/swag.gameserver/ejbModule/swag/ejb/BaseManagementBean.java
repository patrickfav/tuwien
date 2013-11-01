package swag.ejb;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import swag.bl.IBaseManagement;
import swag.bl.IResourceManagement;
import swag.bl.ITroopManagement;
import swag.bl.messaging.IMessageManagement;
import swag.exceptions.MessagingException;
import swag.models.Base;
import swag.models.BaseSquare;
import swag.models.Building;
import swag.models.BuildingType;
import swag.models.GameMap;
import swag.models.LongEntity;
import swag.models.MapSquare;
import swag.models.Resource;
import swag.models.Troop;
import swag.models.User;
import swag.models.UserGameMap;
import swag.models.enums.ResourceType;
import swag.util.ReflectionUtil;

@Stateless
public class BaseManagementBean implements IBaseManagement {
	
	private static final Integer MAX_RESSOURCES = 6;
	
	@PersistenceContext
	public EntityManager em;
	
	@EJB
	private IResourceManagement resourceManagement;
	
	@EJB
	private ITroopManagement troopManagement;
	
	@EJB
	private IMessageManagement messageManagement;
	
	private static Logger log = Logger.getLogger("BaseManagementBean");

	@Override
	public Base refreshBase(long id) {
		log.info("Refreshing base with ID " + id);
		return em.find(Base.class, id);
	}

	@Override
	public void updateBase(Base base) {
		log.info("Updating base with ID " + base.getId());
		em.merge(base);
	}
	
	@Override
	public BaseSquare refreshBaseSquare(long id) {
		log.info("Refreshing BaseSquare with ID " + id);
		return em.find(BaseSquare.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Base> getBasesForUser(long userId) {
		log.info("Get bases for user ID " + userId);
		Query query = em.createQuery("select b from Base b where b.user.id = :id");
		query.setParameter("id", userId);
		List<Base> resultList = query.getResultList();
		return resultList;
	}

	private void createBase(User u, GameMap map, MapSquare m, boolean isStarterBase) {
		Random rand = new Random();
		
		Base base = new Base();
		base.setUser(u);
		base.setMaxRessources(MAX_RESSOURCES);
		base.setStarterBase(isStarterBase);
		
		//debug
		for(Troop t: troopManagement.getAllTroopTypes()) {
			log.info("Check troop: id:"+t.getId()+" name:"+t.getName());
			base.getUnitsCount().put(t, new LongEntity(15l));
		}
		
		int counter = 0;
		ResourceType[] resources = ResourceType.values();
		List<BaseSquare> baseSquares = new LinkedList<BaseSquare>();
		for(int x = 0; x < map.getConfig().getBaseSquares(); x++){
			for(int y = 0; y < map.getConfig().getBaseSquares(); y++){
				counter++;
				BaseSquare baseSquare = new BaseSquare();
				baseSquare.setX(x);
				baseSquare.setY(y);
				
				if(rand.nextDouble()<=map.getConfig().getPrivilegedBaseSquaresPercentage()){
					Resource r = new Resource();
					r.setFactor(new Double(3));
					r.setType(resources[rand.nextInt(3)]);
					em.persist(r);
					baseSquare.setResource(r);
				}
				
				baseSquares.add(baseSquare);
				em.persist(baseSquare);
			}
		}
		
		base.setSquares(baseSquares);
		log.info("Persisting Base.");
		em.persist(base);
		
		m.setBase(base);
		m.setLandscape(null);
		log.info("Adding Base to MapSquare.");
		em.merge(m);
		
		u = em.find(User.class, u.getId());
		/* add base to usergamemap */
		for(int i=0;i<u.getUserMaps().size();i++) {
			if(u.getUserMaps().get(i).getMap().equals(map)) {
				//log.info("debug:"+ReflectionUtil.printObjectDetails(u.getUserMaps().get(i)));
				u.getUserMaps().get(i).getBases().add(base);
				break;
			}
		}
		
		em.merge(u);
		
		try {
			messageManagement.sendSystemMessage(u, "New Base", "A new Base was build at ("+m.getX()+","+m.getY()+")");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean createBase(User u, GameMap map, MapSquare m) {
		if(resourceManagement.sufficientResources(map.getConfig().getBaseCosts(), u, map)){
			createBase(u, map, m, false);
			return true;
		}
		return false;
	}

	@Override
	public void createStarterBase(User u, GameMap map, MapSquare m) {
		createBase(u, map, m, true);
	}

	@Override
	public void updateResources() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Building createBuilding(BaseSquare bs, BuildingType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void upgradeBuilding(BaseSquare bs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trainTroop(Troop t, int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Base getBaseForSquare(BaseSquare bs) {
		Query query = em.createQuery("select b from Base b where :bs member of b.squares");
		query.setParameter("bs", bs);
		log.info("Getting base for base square " + bs.getId() + ".");
		return (Base) query.getSingleResult();
	}
}
