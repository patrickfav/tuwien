package swag.ejb;

import java.util.Date;
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
import swag.bl.IMapManagement;
import swag.models.Base;
import swag.models.Config;
import swag.models.GameMap;
import swag.models.MapSquare;
import swag.models.TroopMovementWrapper;
import swag.models.User;
import swag.models.UserGameMap;
import swag.models.enums.Landscape;
import swag.models.enums.ResourceType;

@Stateless
public class MapManagementBean implements IMapManagement {
	
	private static final int BASE_SQUARES = 5;
	private static final long TICK_TIME = 10*1000;
	private static final long START_RESOURCES_WOOD = 2000;
	private static final long START_RESOURCES_IRON = 500;
	private static final long START_RESOURCES_STONE = 1000;
	private static final long BASE_PRODUCTION_WOOD = 200;
	private static final long BASE_PRODUCTION_IRON = 70;
	private static final long BASE_PRODUCTION_STONE = 120;
	private static final double BASE_RESOURCE_PERCENTAGE = 0.3;
	private static final double MAP_RESOURCE_PERCENTAGE = 0.35;
	private static final long BASE_COSTS_WOOD = 100l;
	private static final long BASE_COSTS_IRON = 100l;
	private static final long BASE_COSTS_STONE = 100l;
	
	private static String[] mapNames = {"Camelot","Burgund","Middlearth","Hordforest","Terosa","Niterus","Benela","Carthago","Delphi","Lavandia"}; 
	
	private static Random r = new Random(1);
	
	@PersistenceContext
	public EntityManager em;
	
	@EJB
	public IBaseManagement baseManagement;
	
	private static Logger log = Logger.getLogger("MapManagementBean");

	@Override
	public GameMap getMap(long id) {
		log.info("Getting Map with ID " + id);
		return em.find(GameMap.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GameMap> getAllMaps() {
		Query query = em.createQuery("select m from GameMap m");
		List<GameMap> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public void createBaseOnMapSquare(User u, MapSquare ms) {
		GameMap m = getMapForSquare(ms);
		if(m != null){
			log.info("Creating base for user " + u.getUsername() + " on map " + m.getName() + ".");
			baseManagement.createBase(u, m, ms);
		}
	}

	@Override
	public void createStartBaseForUser(GameMap m, User u) {
		//TODO implement rules for base creation
		log.info("Creating starter base for user " + u.getUsername() + " on map " + m.getName() + ".");
		Random r = new Random();
		int offset = r.nextInt(m.getSquares().size());
		
		for(int i=offset;i<m.getSquares().size();i++) {
			
			if(m.getSquares().get(i).getLandscape().equals(Landscape.EMPTY) && m.getSquares().get(i).getBase() == null){
				m.getSquares().get(i).setLandscape(null);
				baseManagement.createStarterBase(u, m, m.getSquares().get(i));
				break;
			}
			
			if(i == (offset-1))
				break;
			
			if((i+1) == m.getSquares().size())
				i=0;
		}
	}

	@Override
	public MapSquare refreshMapSquare(long id) {
		log.info("Refreshing MapSquare with ID " + id);
		return em.find(MapSquare.class, id);
	}

	@Override
	public void updateMapSquare(MapSquare ms) {
		log.info("Updating MapSquare with ID " + ms.getId());
		em.merge(ms);
	}
	
	@Override
	public void updateMap(GameMap map) {
		log.info("Updating Map with ID " + map.getId());
		em.merge(map);
	}

	@Override
	public GameMap createMap(Integer numberOfSquares) {
		
		Landscape[] landscapes = Landscape.values();
		int[] landscape_weights = {3,3,3,3,10};
		int weightSum = sum(landscape_weights);
		Random random = new Random();
		
		int emptyFields = 0;
			
		GameMap map = new GameMap();
		
		List<MapSquare> mapSquares = new LinkedList<MapSquare>();
		for(int i = 0; i < numberOfSquares; i++){
			for(int j = 0; j < numberOfSquares; j++){
				MapSquare square = new MapSquare();
				square.setX(i);
				square.setY(j);
				
				int currentLimit = random.nextInt(weightSum+1);
				int currentSum = 0;
	            for(int z = 0; z< landscapes.length;z++){
	                currentSum += landscape_weights[z];
	                if(currentSum >= currentLimit){
	                	square.setLandscape(landscapes[z]);     
	                	if(square.getLandscape().equals(Landscape.EMPTY)) emptyFields++;
	                    break;
	                }
	            }
				
	            log.info("Creating MapSquare with Landscape " + square.getLandscape());
				
	            em.persist(square);
				mapSquares.add(square);
			}
		}
		
		Config config = new Config();
		config.setMaxUsers(emptyFields);
		config.setNumberOfSquares(numberOfSquares);
		config.setBaseSquares(BASE_SQUARES);
		config.setTickTime(TICK_TIME);
		config.getStartResourceStock().put(ResourceType.IRON, START_RESOURCES_IRON);
		config.getStartResourceStock().put(ResourceType.WOOD, START_RESOURCES_WOOD);
		config.getStartResourceStock().put(ResourceType.STONE, START_RESOURCES_STONE);
		config.setPrivilegedBaseSquaresPercentage(BASE_RESOURCE_PERCENTAGE);
		config.setPrivilegedMapSquaresPercentage(MAP_RESOURCE_PERCENTAGE);
		config.getBaseProductionPerTick().put(ResourceType.IRON, BASE_PRODUCTION_IRON);
		config.getBaseProductionPerTick().put(ResourceType.WOOD, BASE_PRODUCTION_WOOD);
		config.getBaseProductionPerTick().put(ResourceType.STONE, BASE_PRODUCTION_STONE);
		config.getBaseCosts().put(ResourceType.IRON, BASE_COSTS_IRON);
		config.getBaseCosts().put(ResourceType.WOOD, BASE_COSTS_WOOD);
		config.getBaseCosts().put(ResourceType.STONE, BASE_COSTS_STONE);
		em.persist(config);
		
		map.setSquares(mapSquares);
		map.setConfig(config);
		map.setName(mapNames[r.nextInt(mapNames.length)]);
		log.info("Persisting Map " + map.getName());
		em.persist(map);
		
		return map;
	}

	private static int sum(int[] values) {
        int result = 0;
        
        for(int value : values){
            result += value;
        }
        
        return result;
    }

	@Override
	public boolean hasBase(GameMap m, User u) {
		for(MapSquare s : m.getSquares()){
			if(s.getBase() != null && s.getBase().getUser().getId().equals(u.getId())){
				return true;
			}
		}
		return false;
	}

	@Override
	public GameMap getMapForSquare(MapSquare ms) {
		log.info("Searching map for MapSquare with id " + ms.getId());
		Query query = em.createQuery("select m from GameMap m where :ms member of m.squares");
		query.setParameter("ms", ms);
		GameMap m = (GameMap) query.getSingleResult();
		return m;
	}

	@Override
	public GameMap loggonMap(GameMap map, User u) {
		for(UserGameMap ugm: u.getUserMaps()) {
			if(ugm.getMap().equals(map)) {
				return map;
			}
		}
		
		/* if first time login to map */
		log.info("No map data for user "+u.getUsername()+". Creating.");
		
		UserGameMap newUgm = new UserGameMap();
		newUgm.setLastResourceUpdate(new Date());
		newUgm.setMap(map);
		newUgm.setUser(u);
		newUgm.getResourceStock().put(ResourceType.STONE, map.getConfig().getStartResourceStock().get(ResourceType.STONE));
		newUgm.getResourceStock().put(ResourceType.IRON, map.getConfig().getStartResourceStock().get(ResourceType.IRON));
		newUgm.getResourceStock().put(ResourceType.WOOD, map.getConfig().getStartResourceStock().get(ResourceType.WOOD));
		
		u.getUserMaps().add(newUgm);
		
		em.merge(u);
		
		createStartBaseForUser(map, u);
		
		return map;
	}

	@Override
	public UserGameMap getUserGameMap(GameMap m, User u) {
		Query query = em.createQuery("select ugm from UserGameMap ugm where ugm.map = :map and ugm.user = :user");
		query.setParameter("map", m);
		query.setParameter("user", u);
		log.info("Getting map data for user " + u.getUsername() + " on map " + m.getName() + ".");
		return (UserGameMap) query.getSingleResult();
	}

	@Override
	public GameMap getMapForBase(Base b) {
		Query query = em.createQuery("select m from GameMap m join m.squares s where s.base = :base");
		query.setParameter("base", b);
		log.info("Getting map for base " + b.getId() + ".");
		return (GameMap) query.getSingleResult();
	}

	@Override
	public List<TroopMovementWrapper> getTroopMovements(GameMap m) {
		Query query = em.createQuery("select tmw from TroopMovementWrapper tmw where tmw.map=:map ");
		query.setParameter("map", m);
		List<TroopMovementWrapper> resultList = query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MapSquare getMapSquareForBase(Base b) {
		List<MapSquare> resultList = em.createQuery("select ms from MapSquare ms join ms.base as mbase where mbase.id = :mid")
		.setParameter("mid", b.getId()).getResultList();
		
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserGameMap> getUserGameMaps(User u) {
		Query query = em.createQuery("select ugm from UserGameMap ugm where ugm.user = :user");
		query.setParameter("user", u);
		log.info("Getting map data for user " + u.getUsername() + ".");
		return query.getResultList();
	}
}
