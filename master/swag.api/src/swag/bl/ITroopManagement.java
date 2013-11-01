package swag.bl;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import swag.exceptions.TroopException;
import swag.models.GameMap;
import swag.models.User;
import swag.models.Base;
import swag.models.MapSquare;
import swag.models.Troop;
import swag.models.TroopMovementWrapper;
import swag.models.enums.MilitaryType;

@Remote
public interface ITroopManagement {
	
	public boolean trainTroop(GameMap m, Base base, Troop t, int count);
	
	public void sendTroops(User user, GameMap map, MapSquare dest, Map<Troop, Long> troops)  throws TroopException;
	
	public void troopsArrived(TroopMovementWrapper attack);	
		
	public Map<Troop, Long> getTroopCount(GameMap m, User u);
	public Integer getTroopCount(GameMap m, User u,Troop t);
	public List<Troop> getAllTroopTypes();
	public List<Troop> getTroopsForType(MilitaryType type);
	
	public List<TroopMovementWrapper> getTroopMovementWrappers(GameMap map,User u);
	public List<TroopMovementWrapper> getTroopMovementWrappers(GameMap map);
	
	public void updateTroopMovement(GameMap m);
	public void updateTrainingStatus(GameMap m);
}
