package swag.broker;

import java.net.InetAddress;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.Singleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.eclipse.persistence.exceptions.DatabaseException;

import swag.bl.IBaseManagement;
import swag.bl.IBuildingManagement;
import swag.bl.IConfigDBManagement;
import swag.bl.IMapManagement;
import swag.bl.IRankingManagement;
import swag.bl.IResourceManagement;
import swag.bl.ITestDataManagement;
import swag.bl.ITickManagement;
import swag.bl.ITroopManagement;
import swag.bl.IUserManagement;
import swag.bl.IUtilizationManagement;
import swag.dtos.RankingDTO;
import swag.exceptions.NotEnoughFreeResourcesException;
import swag.exceptions.TroopException;
import swag.exceptions.UserManagementException;
import swag.models.Base;
import swag.models.BaseSquare;
import swag.models.Building;
import swag.models.BuildingType;
import swag.models.GameMap;
import swag.models.MapSquare;
import swag.models.Troop;
import swag.models.TroopMovementWrapper;
import swag.models.User;
import swag.models.UserGameMap;
import swag.models.enums.MilitaryType;
import swag.models.enums.ResourceType;


/**
 * Session Bean implementation class DispatchBean
 */
@Singleton
public class Broker implements IBroker {

	private List<GameServerWrapper> gameServerList = new LinkedList<GameServerWrapper>();
	
	@Override
	public void register(String ip) 
	{
		System.out.println("Test!");
		
		Properties properties=new Properties();  
		properties.setProperty("java.naming.factory.initial","com.sun.enterprise.naming.impl.SerialInitContextFactory");  
		properties.setProperty("org.omg.CORBA.ORBInitialHost", ip);
		properties.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        
		Context ctx;
		try 
		{
			ctx = new InitialContext(properties);
			
			GameServerWrapper gameServer = new GameServerWrapper();
			
			gameServer.setUserManagement((IUserManagement)ctx.lookup("java:global/swag.deployment/swag.gameserver/UserManagementBean"));
			gameServer.setMapManagement((IMapManagement)ctx.lookup("java:global/swag.deployment/swag.gameserver/MapManagementBean"));		
			gameServer.setBaseManagment((IBaseManagement)ctx.lookup("java:global/swag.deployment/swag.gameserver/BaseManagementBean"));
			gameServer.setBuildingManagement((IBuildingManagement)ctx.lookup("java:global/swag.deployment/swag.gameserver/BuildingManagementBean"));
			gameServer.setConfigDBManagement((IConfigDBManagement)ctx.lookup("java:global/swag.deployment/swag.gameserver/ConfigDBManagementBean"));
			gameServer.setRankingManagement((IRankingManagement)ctx.lookup("java:global/swag.deployment/swag.gameserver/RankingManagementBean"));
			gameServer.setResourceManagement((IResourceManagement)ctx.lookup("java:global/swag.deployment/swag.gameserver/ResourceManagementBean"));
			gameServer.setTestDataManagement((ITestDataManagement)ctx.lookup("java:global/swag.deployment/swag.gameserver/TestDataManagementBean"));
			gameServer.setTickManagement((ITickManagement)ctx.lookup("java:global/swag.deployment/swag.gameserver/TickManagementBean"));
			gameServer.setTroopManagement((ITroopManagement)ctx.lookup("java:global/swag.deployment/swag.gameserver/TroopManagementBean"));
			gameServer.setUtilizationManagement((IUtilizationManagement)ctx.lookup("java:global/swag.deployment/swag.gameserver/UtilizationManagementBean"));
		
			gameServerList.add(gameServer);
			System.out.println("Test2!");
		} 
		catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private GameServerWrapper getGameServer()
	{
		return gameServerList.get((int)Math.floor((Math.random() * gameServerList.size())));
	}

	@Override
	public Base refreshBase(long id) {
		// TODO Auto-generated method stub
		return getGameServer().getBaseManagment().refreshBase(id);
	}

	@Override
	public void updateBase(Base base) {
		// TODO Auto-generated method stub
		 getGameServer().getBaseManagment().updateBase(base);
	}

	@Override
	public BaseSquare refreshBaseSquare(long id) {
		// TODO Auto-generated method stub
		return  getGameServer().getBaseManagment().refreshBaseSquare(id);
	}

	@Override
	public List<Base> getBasesForUser(long userId) {
		// TODO Auto-generated method stub
		return  getGameServer().getBaseManagment().getBasesForUser(userId);
	}

	@Override
	public Base getBaseForSquare(BaseSquare bs) {
		// TODO Auto-generated method stub
		return  getGameServer().getBaseManagment().getBaseForSquare(bs);
	}

	@Override
	public void updateResources() {
		// TODO Auto-generated method stub
		 getGameServer().getBaseManagment().updateResources();
	}

	@Override
	public boolean createBase(User u, GameMap map, MapSquare m) {
		// TODO Auto-generated method stub
		return  getGameServer().getBaseManagment().createBase(u, map, m);
	}

	@Override
	public void createStarterBase(User u, GameMap map, MapSquare m) {
		// TODO Auto-generated method stub
		 getGameServer().getBaseManagment().createStarterBase(u, map, m);
	}

	@Override
	public Building createBuilding(BaseSquare bs, BuildingType type) {
		// TODO Auto-generated method stub
		return  getGameServer().getBaseManagment().createBuilding(bs, type);
	}

	@Override
	public void upgradeBuilding(BaseSquare bs) {
		// TODO Auto-generated method stub
		 getGameServer().getBaseManagment().upgradeBuilding(bs);
	}

	@Override
	public void trainTroop(Troop t, int count) {
		// TODO Auto-generated method stub
		 getGameServer().getBaseManagment().trainTroop(t, count);
	}

	@Override
	public List<Building> getBuildingsForUser(User u) {
		// TODO Auto-generated method stub
		return  getGameServer().getBuildingManagement().getBuildingsForUser(u);
	}

	@Override
	public List<BuildingType> getAllBuildingTypes() {
		// TODO Auto-generated method stub
		return getGameServer().getBuildingManagement().getAllBuildingTypes();
	}

	@Override
	public boolean build(BaseSquare bs, BuildingType bt) {
		// TODO Auto-generated method stub
		return getGameServer().getBuildingManagement().build(bs, bt);
	}

	@Override
	public boolean upgrade(BaseSquare bs, Building b) {
		// TODO Auto-generated method stub
		return getGameServer().getBuildingManagement().upgrade(bs, b);
	}

	@Override
	public void updateBuildStatus(GameMap m) {
		// TODO Auto-generated method stub
		getGameServer().getBuildingManagement().updateBuildStatus(m);
	}

	@Override
	public Building refreshBuilding(long id) {
		// TODO Auto-generated method stub
		return getGameServer().getBuildingManagement().refreshBuilding(id);
	}

	@Override
	public void updateBuilding(Building b) {
		// TODO Auto-generated method stub
		getGameServer().getBuildingManagement().updateBuilding(b);
	}

	@Override
	public void addDataToDB() {
		// TODO Auto-generated method stub
		getGameServer().getConfigDBManagement().addDataToDB();
	}

	@Override
	public GameMap createMap(Integer numberOfSquares) {
		// TODO Auto-generated method stub
		return getGameServer().getMapManagement().createMap(numberOfSquares);
	}

	@Override
	public GameMap getMap(long id) {
		// TODO Auto-generated method stub
		return getGameServer().getMapManagement().getMap(id);
	}

	@Override
	public List<GameMap> getAllMaps() {
		// TODO Auto-generated method stub
		return getGameServer().getMapManagement().getAllMaps();
	}

	@Override
	public void updateMap(GameMap map) {
		// TODO Auto-generated method stub
		getGameServer().getMapManagement().updateMap(map);
	}

	@Override
	public void createBaseOnMapSquare(User u, MapSquare ms) {
		// TODO Auto-generated method stub
		getGameServer().getMapManagement().createBaseOnMapSquare(u, ms);
	}

	@Override
	public void createStartBaseForUser(GameMap m, User u) {
		// TODO Auto-generated method stub
		getGameServer().getMapManagement().createStartBaseForUser(m, u);
	}

	@Override
	public MapSquare refreshMapSquare(long id) {
		// TODO Auto-generated method stub
		return getGameServer().getMapManagement().refreshMapSquare(id);
	}

	@Override
	public void updateMapSquare(MapSquare ms) {
		// TODO Auto-generated method stub
		getGameServer().getMapManagement().updateMapSquare(ms);
	}

	@Override
	public boolean hasBase(GameMap m, User u) {
		// TODO Auto-generated method stub
		return getGameServer().getMapManagement().hasBase(m, u);
	}

	@Override
	public GameMap getMapForSquare(MapSquare ms) {
		// TODO Auto-generated method stub
		return getGameServer().getMapManagement().getMapForSquare(ms);
	}

	@Override
	public GameMap getMapForBase(Base b) {
		// TODO Auto-generated method stub
		return getGameServer().getMapManagement().getMapForBase(b);
	}

	@Override
	public MapSquare getMapSquareForBase(Base b) {
		// TODO Auto-generated method stub
		return getGameServer().getMapManagement().getMapSquareForBase(b);
	}

	@Override
	public GameMap loggonMap(GameMap map, User u) {
		// TODO Auto-generated method stub
		return getGameServer().getMapManagement().loggonMap(map, u);
	}

	@Override
	public UserGameMap getUserGameMap(GameMap m, User u) {
		// TODO Auto-generated method stub
		return getGameServer().getMapManagement().getUserGameMap(m, u);
	}

	@Override
	public List<UserGameMap> getUserGameMaps(User u) {
		// TODO Auto-generated method stub
		return getGameServer().getMapManagement().getUserGameMaps(u);
	}

	@Override
	public List<TroopMovementWrapper> getTroopMovements(GameMap m) {
		// TODO Auto-generated method stub
		return getGameServer().getMapManagement().getTroopMovements(m);
	}

	@Override
	public List<RankingDTO> getRanking(GameMap map) {
		// TODO Auto-generated method stub
		return getGameServer().getRankingManagement().getRanking(map);
	}

	@Override
	public long getRessourceStock(ResourceType type, User u, GameMap m) {
		// TODO Auto-generated method stub
		return getGameServer().getResourceManagement().getRessourceStock(type, u, m);
	}

	@Override
	public boolean sufficientResources(BaseSquare b, BuildingType bt, int level) {
		// TODO Auto-generated method stub
		return getGameServer().getResourceManagement().sufficientResources(b, bt, level);
	}

	@Override
	public boolean sufficientResources(Map<ResourceType, Long> needed, User u,
			GameMap m) {
		// TODO Auto-generated method stub
		return getGameServer().getResourceManagement().sufficientResources(needed, u, m);
	}

	@Override
	public void stealResources(GameMap map, User thief, User victim,
			Map<ResourceType, Long> amount) {
		// TODO Auto-generated method stub
		getGameServer().getResourceManagement().stealResources(map, thief, victim, amount);
	}

	@Override
	public void takeResourcesFromUser(GameMap map, User usr,
			Map<ResourceType, Long> amount, int level)
			throws NotEnoughFreeResourcesException {
		// TODO Auto-generated method stub
		getGameServer().getResourceManagement().takeResourcesFromUser(map, usr, amount, level);
	}

	@Override
	public void takeResourcesFromUser(BaseSquare b, BuildingType bt, int level)
			throws NotEnoughFreeResourcesException {
		// TODO Auto-generated method stub
		getGameServer().getResourceManagement().takeResourcesFromUser(b, bt, level);
	}

	@Override
	public void updateRessourcesInDB(GameMap m) {
		// TODO Auto-generated method stub
		getGameServer().getResourceManagement().updateRessourcesInDB(m);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		getGameServer().getTickManagement().init();
	}

	@Override
	public Date calculateEnd(Date start, Integer duration, GameMap m) {
		// TODO Auto-generated method stub
		return getGameServer().getTickManagement().calculateEnd(start, duration, m);
	}

	@Override
	public void sendTroops(User user, GameMap map, MapSquare dest,
			Map<Troop, Long> troops) throws TroopException {
		// TODO Auto-generated method stub
		getGameServer().getTroopManagement().sendTroops(user, map, dest, troops);
	}

	@Override
	public void troopsArrived(TroopMovementWrapper attack) {
		// TODO Auto-generated method stub
		getGameServer().getTroopManagement().troopsArrived(attack);
	}

	@Override
	public void updateTroopMovement(GameMap m) {
		// TODO Auto-generated method stub
		getGameServer().getTroopManagement().updateTroopMovement(m);
	}

	@Override
	public Map<Troop, Long> getTroopCount(GameMap m, User u) {
		// TODO Auto-generated method stub
		return getGameServer().getTroopManagement().getTroopCount(m, u);
	}

	@Override
	public Integer getTroopCount(GameMap m, User u, Troop t) {
		// TODO Auto-generated method stub
		return getGameServer().getTroopManagement().getTroopCount(m, u, t);
	}

	@Override
	public List<Troop> getAllTroopTypes() {
		// TODO Auto-generated method stub
		return getGameServer().getTroopManagement().getAllTroopTypes();
	}

	@Override
	public List<Troop> getTroopsForType(MilitaryType type) {
		// TODO Auto-generated method stub
		return getGameServer().getTroopManagement().getTroopsForType(type);
	}

	@Override
	public User login(String usr, String password)
			throws UserManagementException {
		// TODO Auto-generated method stub
		return getGameServer().getUserManagement().login(usr, password);
	}

	@Override
	public void logout(long id) throws UserManagementException {
		// TODO Auto-generated method stub
		getGameServer().getUserManagement().logout(id);
	}

	@Override
	public User refreshUser(long id) throws UserManagementException {
		// TODO Auto-generated method stub
		return getGameServer().getUserManagement().refreshUser(id);
	}

	@Override
	public void updateUser(User user) throws UserManagementException {
		// TODO Auto-generated method stub
		getGameServer().getUserManagement().updateUser(user);
	}

	@Override
	public List<User> getAllUser() throws UserManagementException {
		// TODO Auto-generated method stub
		return getGameServer().getUserManagement().getAllUser();
	}

	@Override
	public boolean quitAccount(User user) throws UserManagementException {
		// TODO Auto-generated method stub
		return getGameServer().getUserManagement().quitAccount(user);
	}

	@Override
	public void registerUser(User user) throws UserManagementException {
		// TODO Auto-generated method stub
		getGameServer().getUserManagement().registerUser(user);
	}

	@Override
	public boolean isLoggedIn(long id) throws UserManagementException {
		// TODO Auto-generated method stub
		return getGameServer().getUserManagement().isLoggedIn(id);
	}

	@Override
	public int getUtilization() {
		// TODO Auto-generated method stub
		return getGameServer().getUtilizationManagement().getUtilization();
	}

	@Override
	public boolean sufficientResources(Map<ResourceType, Long> needed, User u,
			GameMap m, int level) {
		// TODO Auto-generated method stub
		return getGameServer().getResourceManagement().sufficientResources(needed, u, m, level);
	}

	@Override
	public void addTestData() throws swag.exceptions.DatabaseException {
		// TODO Auto-generated method stub
		getGameServer().getTestDataManagement().addTestData();
	}

	@Override
	public boolean trainTroop(GameMap m, Base base, Troop t, int count) {
		// TODO Auto-generated method stub
		return getGameServer().getTroopManagement().trainTroop(m, base, t, count);
	}

	@Override
	public List<TroopMovementWrapper> getTroopMovementWrappers(GameMap map,
			User u) {
		// TODO Auto-generated method stub
		return getGameServer().getTroopManagement().getTroopMovementWrappers(map, u);
	}

	@Override
	public List<TroopMovementWrapper> getTroopMovementWrappers(GameMap map) {
		// TODO Auto-generated method stub
		return getGameServer().getTroopManagement().getTroopMovementWrappers(map);
	}

	@Override
	public void updateTrainingStatus(GameMap m) {
		// TODO Auto-generated method stub
		getGameServer().getTroopManagement().updateTrainingStatus(m);
	}


	
}
