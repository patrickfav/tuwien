package swag.bl;

import java.util.Map;

import javax.ejb.Remote;

import swag.exceptions.NotEnoughFreeResourcesException;
import swag.models.BaseSquare;
import swag.models.BuildingType;
import swag.models.GameMap;
import swag.models.User;
import swag.models.enums.ResourceType;

@Remote
public interface IResourceManagement {
	
	public long getRessourceStock(ResourceType type, User u, GameMap m);
	
	public boolean sufficientResources(BaseSquare b, BuildingType bt, int level);
	public boolean sufficientResources(Map<ResourceType,Long> needed,User u, GameMap m);
	public boolean sufficientResources(Map<ResourceType,Long> needed,User u, GameMap m, int level);
	
	public void stealResources(GameMap map, User thief, User victim, Map<ResourceType,Long> amount);
	
	public void takeResourcesFromUser(GameMap map, User usr, Map<ResourceType,Long> amount, int level) throws NotEnoughFreeResourcesException;
	public void takeResourcesFromUser(BaseSquare b, BuildingType bt, int level)  throws NotEnoughFreeResourcesException ;
	
	public void updateRessourcesInDB(GameMap m);
}
