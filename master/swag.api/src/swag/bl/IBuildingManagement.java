package swag.bl;

import java.util.List;

import javax.ejb.Remote;

import swag.exceptions.DatabaseException;
import swag.models.BaseSquare;
import swag.models.Building;
import swag.models.BuildingType;
import swag.models.GameMap;
import swag.models.UpgradeType;
import swag.models.User;

@Remote
public interface IBuildingManagement {

        public List<Building> getBuildingsForUser(User u);

        public List<BuildingType> getAllBuildingTypes();
        public boolean build(BaseSquare bs, BuildingType bt);
        public boolean upgrade(BaseSquare bs, Building b);
        public void updateBuildStatus(GameMap m);
        
        public Building refreshBuilding(long id);
        public void updateBuilding(Building b);
    	// public void upgrade(User user, GameMap map, UpgradeType type) throws DatabaseException;
}
