package swag.bl;

import java.util.List;

import javax.ejb.Remote;

import swag.models.Base;
import swag.models.GameMap;
import swag.models.MapSquare;
import swag.models.User;
import swag.models.BaseSquare;
import swag.models.Building;
import swag.models.BuildingType;
import swag.models.Troop;
import swag.models.enums.ResourceType;

@Remote
public interface IBaseManagement {

	public Base refreshBase(long id);
	public void updateBase(Base base);
	
	public BaseSquare refreshBaseSquare(long id);
	
	public List<Base> getBasesForUser(long userId);
	public Base getBaseForSquare(BaseSquare bs);
	
	public void updateResources();
	
	public boolean createBase(User u, GameMap map, MapSquare m);
	public void createStarterBase(User u, GameMap map, MapSquare m);
	
	public Building createBuilding(BaseSquare bs, BuildingType type);
	public void upgradeBuilding(BaseSquare bs);
	
	public void trainTroop(Troop t, int count);
}
