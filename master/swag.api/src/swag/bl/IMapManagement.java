package swag.bl;

import java.util.List;

import javax.ejb.Remote;

import swag.models.Base;
import swag.models.GameMap;
import swag.models.MapSquare;
import swag.models.TroopMovementWrapper;
import swag.models.User;
import swag.models.UserGameMap;

@Remote
public interface IMapManagement {
	
	public GameMap createMap(Integer numberOfSquares);
	
	public GameMap getMap(long id);
	public List<GameMap> getAllMaps();
	public void updateMap(GameMap map);
	
	public void createBaseOnMapSquare(User u, MapSquare ms);
	public void createStartBaseForUser(GameMap m, User u);
	
	public MapSquare refreshMapSquare(long id);
	public void updateMapSquare(MapSquare ms);
	
	public boolean hasBase(GameMap m, User u);
	public GameMap getMapForSquare(MapSquare ms);
	public GameMap getMapForBase(Base b);
	public MapSquare getMapSquareForBase(Base b);
	
	public GameMap loggonMap(GameMap map, User u);
	
	public UserGameMap getUserGameMap(GameMap m, User u);
	public List<UserGameMap> getUserGameMaps(User u);
	public List<TroopMovementWrapper> getTroopMovements(GameMap m);
}