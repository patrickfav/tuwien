package swag.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import swag.bl.IBaseManagement;
import swag.bl.IBuildingManagement;
import swag.bl.IMapManagement;
import swag.bl.IResourceManagement;
import swag.bl.ITickManagement;
import swag.exceptions.DatabaseException;
import swag.exceptions.NotEnoughFreeResourcesException;
import swag.models.Base;
import swag.models.BaseSquare;
import swag.models.BuildListEntry;
import swag.models.Building;
import swag.models.BuildingType;
import swag.models.GameMap;
import swag.models.MapSquare;
import swag.models.ResourceBuilding;
import swag.models.Upgrade;
import swag.models.UpgradeListEntry;
import swag.models.UpgradeType;
import swag.models.User;
import swag.models.enums.ResourceType;

@Stateless
public class BuildingManagementBean implements IBuildingManagement {

	private static Logger log = Logger.getLogger("BuildingManagementBean");

	@PersistenceContext
	public EntityManager em;
	
	@EJB
	private IResourceManagement resourceManagement;
	
	@EJB
	private IBaseManagement bm;
	
	@EJB
	private IMapManagement mm;
	
	@EJB
	private ITickManagement tm;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BuildingType> getAllBuildingTypes() {
		Query query = em.createQuery("select b from BuildingType b order by b.name asc");
		List<BuildingType> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<Building> getBuildingsForUser(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean build(BaseSquare b, BuildingType bt) {
		int startLevel = 1;
		if (resourceManagement.sufficientResources(b,bt,startLevel)) {
			try {
				resourceManagement.takeResourcesFromUser(b, bt, startLevel);
			} catch(NotEnoughFreeResourcesException e) {
				log.warning("Not enough resources: "+e.getMessage());
				return false;
			}
			
			Building building = new Building();
            building.setLevel(1);
            building.setType(bt);
            building.setUnderConstruction(true);
            log.info("Persiting building " + bt);
            em.persist(building);
            
            b.setBuilding(building);
            em.merge(b);
            
            Base base = bm.getBaseForSquare(b);
            
            BuildListEntry ble = new BuildListEntry();
            ble.setBuilding(building);
            ble.setIsBuilding(true);
            ble.setSquare(b);
            ble.setStart_time(new Date());
            ble.setEnd_time(tm.calculateEnd(ble.getStart_time(), bt.getBuildingTime(), mm.getMapForBase(base)));
            log.info("Persisting BuildListEntry for building " + bt);
            em.persist(ble);
            
           if(base.getBuildingList() == null) {
        	   base.setBuildingList(new ArrayList<BuildListEntry>());
           }
          
           base.getBuildingList().add(ble);
           log.info("Updating build list for base " + b.getId());
           em.merge(base);

           User user = base.getUser(); 
           user.setPoints( base.getUser().getPoints() + bt.getPoints());
           em.merge(user);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean upgrade(BaseSquare bs, Building b) {
		int nextLevel = b.getLevel() + 1;
		if(resourceManagement.sufficientResources(bs, b.getType(), nextLevel)) {
			try {
				resourceManagement.takeResourcesFromUser(bs, b.getType(), nextLevel);
			} catch(NotEnoughFreeResourcesException e) {
				log.warning("Not enough resources: "+e.getMessage());
				return false;
			}
			
			b.setLevel(nextLevel);
			b.setUnderConstruction(true);
			
			 em.merge(b);
	            
	            Base base = bm.getBaseForSquare(bs);
	            
	            BuildListEntry ble = new BuildListEntry();
	            ble.setBuilding(b);
	            ble.setIsBuilding(true);
	            ble.setSquare(bs);
	            ble.setStart_time(new Date());
	            ble.setEnd_time(tm.calculateEnd(ble.getStart_time(), b.getType().getBaseUpgradeTime(), mm.getMapForBase(base)));
	            log.info("Persisting BuildListEntry for building " + b);
	            em.persist(ble);
	            
	           if(base.getBuildingList() == null) {
	        	   base.setBuildingList(new ArrayList<BuildListEntry>());
	           }
	           base.getBuildingList().add(ble);
	           log.info("Updating build list for base " + base.getId());
	           em.merge(base);

	           User user = base.getUser(); 
	           // user.setPoints( base.getUser().getPoints() + bt.getPoints());
	           em.merge(user);
			
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Building refreshBuilding(long id) {
		log.info("Refreshing building with ID " + id);
		return em.find(Building.class, id);
	}

	@Override
	public void updateBuilding(Building b) {
		log.info("Updating building with ID " + b.getId());
		em.merge(b);
	}

	
	/* OLD UPGRADE TROOP ???
	@SuppressWarnings("unchecked")
	@Override
	public void upgrade(User user, GameMap map, UpgradeType type)
			throws DatabaseException {
		log.info("Upgrading" + type.getType().toString() + " at user "
				+ user.getId());

		List<Upgrade> list = null;
		try {
			list = em
					.createQuery(
							"select u from upgrade u where u.user = :usr AND u.type = :type AND map = :mp")
					.setParameter("usr", user.getId())
					.setParameter("type", type.getId())
					.setParameter("mp", map.getId()).getResultList();
		} catch (Exception e) {
			throw new DatabaseException(
					"Could not retrieve users upgrade list from db", e);
		}

		UpgradeListEntry ule = new UpgradeListEntry();
		ule.setStart_time(new Date());
		ule.setType(type);
		long end_date = (long) (ule.getStart_time().getTime() + map.getConfig()
				.getTickTime() );
		ule.setEnd_time(new Date(end_date));

		if (list == null || list.size() == 0)
			ule.setLevelTo(1);
		else
			ule.setLevelTo(list.get(0).getLevel());

		log.info("Persisting UpgradeListEntry for upgrade "
				+ type.getType().toString());
		em.persist(ule);
		
		user.setPoints( user.getPoints() + type.getPointsPerLevel());
		em.merge(user);
	}
	*/

	@Override
	public void updateBuildStatus(GameMap m) {
		log.info("Updating build status for map " + m.getName() + ".");
		for(MapSquare ms : m.getSquares()){
			if(ms.getBase() == null) continue;
			List<BuildListEntry> tempList = new ArrayList<BuildListEntry>();
			tempList.addAll(ms.getBase().getBuildingList());
			for(BuildListEntry ble : tempList) {
				if(ble.getEnd_time().compareTo(new Date()) <= 0){
					log.info("Removing building " + ble.getBuilding().getId() +" from build list.");
					BuildListEntry tmp = em.find(BuildListEntry.class, ble.getId());
					Building building = tmp.getBuilding();
					building.setUnderConstruction(false);
					em.merge(building);
					ms.getBase().getBuildingList().remove(ble);
					em.remove(tmp);
					
					if(ble.getBuilding().getType() instanceof ResourceBuilding){
						ResourceBuilding rb = (ResourceBuilding) ble.getBuilding().getType();
						if(ms.getBase().getRessourcesPerTick() == null) ms.getBase().setRessourcesPerTick(new HashMap<ResourceType,Long>());
						if(ms.getBase().getRessourcesPerTick().containsKey(rb.getRessource())){
							if(building.getLevel() > 1){
								ms.getBase().getRessourcesPerTick().put(rb.getRessource(), (long) (ms.getBase().getRessourcesPerTick().get(rb.getRessource()) + (rb.getBaseProduction() * rb.getLevelFactor())));
							}else{
								if(nextToResourceField(ble.getSquare(), ms.getBase(), rb.getRessource())){
									ms.getBase().getRessourcesPerTick().put(rb.getRessource(), (long) (ms.getBase().getRessourcesPerTick().get(rb.getRessource()) + (rb.getBaseProduction() * rb.getNextToResourceFactor())));
								}else{
									ms.getBase().getRessourcesPerTick().put(rb.getRessource(), ms.getBase().getRessourcesPerTick().get(rb.getRessource()) + rb.getBaseProduction());
								}
							}
						}else{
							ms.getBase().getRessourcesPerTick().put(rb.getRessource(), rb.getBaseProduction());
						}
					}
				}
			}
			em.merge(ms.getBase());
		}
	}
	
	private boolean nextToResourceField(BaseSquare bs, Base b, ResourceType rt){
		
		for(BaseSquare s : b.getSquares()){
			if(s.getX().equals(bs.getX()) && s.getY().equals(bs.getY())) continue;
			if(s.getResource() == null) continue;
			
			if((s.getX() >= (bs.getX() -1)) && (s.getX() <= (bs.getX() + 1)) &&
			   (s.getY() >= (bs.getY() -1)) && (s.getY() <= (bs.getY() + 1)) &&
			   s.getResource().getType().equals(rt)){
				return true;
			}
		}
		
		return false;
	}
}