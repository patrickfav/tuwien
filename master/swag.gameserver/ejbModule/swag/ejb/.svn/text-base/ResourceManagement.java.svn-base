package swag.ejb;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import swag.bl.IResourceManagement;
import swag.exceptions.NotEnoughFreeResourcesException;
import swag.models.Base;
import swag.models.BaseSquare;
import swag.models.BuildingType;
import swag.models.GameMap;
import swag.models.User;
import swag.models.UserGameMap;
import swag.models.enums.ResourceType;
import swag.util.ReflectionUtil;

@Stateless
public class ResourceManagement implements IResourceManagement{
	
	private static Logger log = Logger.getLogger("ResourceManagement");
	
	@PersistenceContext
	public EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public long getRessourceStock(ResourceType type, User u, GameMap m) {
		List<UserGameMap> ugm = null;
		
		try {
			ugm = (List<UserGameMap>) em.createQuery("select gm from swag_user u join u.userMaps as gm where u.id = :uid and gm.map.id = :mid").setParameter("uid", u.getId())
			.setParameter("mid", m.getId()).getResultList();
			
		
		} catch(Exception e) {
			//e.printStackTrace();
			return 0;
		} finally {
			if(ugm == null || ugm.size() <= 0)
				return 0;
		}
		
		
		return ugm.get(0).getResourceStock().get(type);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean sufficientResources(BaseSquare b, BuildingType bt, int level) {
		List<UserGameMap> ugm = null;
		
		try {
			ugm = (List<UserGameMap>) em.createQuery("select gm from UserGameMap gm join gm.bases as gbase join gbase.squares as gsquare where gsquare.id = :sid")
			.setParameter("sid", b.getId()).getResultList();
			
		} catch(Exception e) {
			log.info("Suff res: false0");
			return false;
		} finally {
			if(ugm == null || ugm.size()<=0) {
				log.info("Suff res: false1");
				return false;
			}
		}
		
		
		for(ResourceType type: bt.getResourceCost().keySet()) {
			if(ugm.get(0).getResourceStock().containsKey(type)) {
				if(bt.getResourceCost().get(type)*level > ugm.get(0).getResourceStock().get(type)) {
					log.info("Suff res: false2");
					return false;
				}
			} else {
				log.info("Suff res: false3");
				return false;
			}
		}
		
		log.info("Suff res: true");
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean sufficientResources(Map<ResourceType, Long> needed,User u, GameMap m) {
		List<UserGameMap> ugm = null;
		
		try {
			ugm = (List<UserGameMap>) em.createQuery("select gm from swag_user u join u.userMaps as gm where u.id = :uid and gm.map.id = :mid").setParameter("uid", u.getId())
			.setParameter("mid", m.getId()).getResultList();
			
		} catch(Exception e) {
			return false;
		} finally {
			if(ugm == null || ugm.size()<=0)
				return false;
		}
		
		for(ResourceType type: needed.keySet()) {
			if(ugm.get(0).getResourceStock().containsKey(type)) {
				if(needed.get(type) > ugm.get(0).getResourceStock().get(type))
					return false;
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean sufficientResources(Map<ResourceType, Long> needed,User u, GameMap m, int level) {
		List<UserGameMap> ugm = null;
		
		try {
			ugm = (List<UserGameMap>) em.createQuery("select gm from swag_user u join u.userMaps as gm where u.id = :uid and gm.map.id = :mid").setParameter("uid", u.getId())
			.setParameter("mid", m.getId()).getResultList();
			
		} catch(Exception e) {
			return false;
		} finally {
			if(ugm == null || ugm.size()<=0)
				return false;
		}
		
		for(ResourceType type: needed.keySet()) {
			if(ugm.get(0).getResourceStock().containsKey(type)) {
				if((needed.get(type)*level) > ugm.get(0).getResourceStock().get(type))
					return false;
			} else {
				return false;
			}
		}
		
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateRessourcesInDB(GameMap m) {
		//log.info("Update Resource Stock for map "+m.getName());
		List<UserGameMap> ugm_list = null;
		
		try {
			ugm_list = em.createQuery("select ugm from UserGameMap ugm where ugm.map.id = :mid").setParameter("mid", m.getId()).getResultList();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(ugm_list == null)
			return;
		
		for(UserGameMap ugm:ugm_list) {
			Map<ResourceType,Double> resMap = new HashMap<ResourceType,Double>();
			
			long duration = (new Date()).getTime() - ugm.getLastResourceUpdate().getTime();
			double ticks = new Double(duration) / new Double(m.getConfig().getTickTime());
			
			/* add base production */
			for(ResourceType type: ResourceType.values()) {
				if(resMap.containsKey(type)) {
					resMap.put(type, (resMap.get(type)+(ticks*ugm.getMap().getConfig().getBaseProductionPerTick().get(type))));
				} else {
					resMap.put(type, ((ticks*ugm.getMap().getConfig().getBaseProductionPerTick().get(type))));
				}
			}
			
			/* per base */
			for(Base base: ugm.getBases()) {
				/* per type get res per tick */
				for(ResourceType type: base.getRessourcesPerTick().keySet()) {
					if(resMap.containsKey(type)) {
						resMap.put(type, (resMap.get(type)+(ticks*base.getRessourcesPerTick().get(type))));
					} else {
						resMap.put(type, ticks*base.getRessourcesPerTick().get(type));
					}
				}
			}
			
			/* add the new resources */
			for(ResourceType type: resMap.keySet()) {
				if(ugm.getResourceStock().containsKey(type)) {
					ugm.getResourceStock().put(type, ugm.getResourceStock().get(type)+Math.round(resMap.get(type)));
				} else {
					ugm.getResourceStock().put(type, Math.round(resMap.get(type)));
				}
			}
			ugm.setLastResourceUpdate(new Date());
			
			//log.info("Adding:"+resMap.values());
			
			em.persist(ugm);
		}
		
	}

	@Override
	public void stealResources(GameMap map, User thief, User victim,Map<ResourceType, Long> amount) {
		UserGameMap thiefUgm = null;
		UserGameMap victimUgm = null;
		
		/* get the UserGameMaps */
		for(UserGameMap ugm:thief.getUserMaps()) {
			if(ugm.getMap().equals(map)) {
				thiefUgm = ugm;
				break;
			}
		}
		
		for(UserGameMap ugm:victim.getUserMaps()) {
			if(ugm.getMap().equals(map)) {
				victimUgm = ugm;
				break;
			}
		}
		
		if(thiefUgm == null || victimUgm == null) {
			log.warning("Error in steal res method. Could not find UserGameMaps");
			return;
		}
		
		for(ResourceType type: amount.keySet()) {
			if(victimUgm.getResourceStock().containsKey(type)) {
				/* if victim has less than claimed */
				if(victimUgm.getResourceStock().get(type)< amount.get(type)) {
					thiefUgm.getResourceStock().put(type, thiefUgm.getResourceStock().get(type)+victimUgm.getResourceStock().get(type));
					victimUgm.getResourceStock().put(type, 0l);
				} else {
					thiefUgm.getResourceStock().put(type, thiefUgm.getResourceStock().get(type)+amount.get(type));
					victimUgm.getResourceStock().put(type, victimUgm.getResourceStock().get(type)-amount.get(type));
				}
			}
		}
		
		/* set the UserGameMaps */
		for(int i=0;i<thief.getUserMaps().size();i++) {
			if(thief.getUserMaps().get(i).getMap().equals(map)) {
				thief.getUserMaps().set(i, thiefUgm);
				break;
			}
		}
		
		for(int i=0;i<victim.getUserMaps().size();i++) {
			if(victim.getUserMaps().get(i).getMap().equals(map)) {
				victim.getUserMaps().set(i, victimUgm);
				break;
			}
		}
		
		em.persist(thief);
		em.persist(victim);
	}
	
	@SuppressWarnings("unchecked")
	public void takeResourcesFromUser(BaseSquare b, BuildingType bt, int level)  throws NotEnoughFreeResourcesException {
		List<UserGameMap> ugm = null;
		try {
			ugm = (List<UserGameMap>) em.createQuery("select gm from UserGameMap gm join gm.bases as gbase join gbase.squares as gsquare where gsquare.id = :sid")
			.setParameter("sid", b.getId()).getResultList();
			
		} catch(Exception e) {
			throw new NotEnoughFreeResourcesException("Could not get UserGameMap (exception in jpql)");
		} finally {
			if(ugm == null || ugm.size()<=0)
				throw new NotEnoughFreeResourcesException("Could not get UserGameMap (null or empty list)");
		}
		
		takeResourcesFromUser(ugm.get(0).getMap(),ugm.get(0).getUser(),bt.getResourceCost(), level);
	}
	
	@Override
	public void takeResourcesFromUser(GameMap map, User usr, Map<ResourceType, Long> amount, int level)  throws NotEnoughFreeResourcesException {
		UserGameMap userUgm = null;
		
		/* get the UserGameMaps */
		for(UserGameMap ugm:usr.getUserMaps()) {
			if(ugm.getMap().equals(map)) {
				userUgm = ugm;
				//log.info("before:"+ReflectionUtil.printObjectDetails(ugm));
				break;
			}
		}
		
		if(userUgm == null) {
			log.warning("Error in take res method. Could not find UserGameMaps");
			return;
		}
		
		for(ResourceType type: amount.keySet()) {
			if(userUgm.getResourceStock().containsKey(type)) {
				/* if user has less than claimed */
				if(userUgm.getResourceStock().get(type) < amount.get(type)) {
					throw new NotEnoughFreeResourcesException("ResourceType not in the needed amount");
					
				} else {
					userUgm.getResourceStock().put(type, userUgm.getResourceStock().get(type) - (amount.get(type) * level));
					//log.info("Subtracting resources");
				}
			} else {
				throw new NotEnoughFreeResourcesException("ResourceType not present");
			}
		}
		
		
		/* set the UserGameMaps */
		for(int i=0;i<usr.getUserMaps().size();i++) {
			if(usr.getUserMaps().get(i).getMap().equals(map)) {
				usr.getUserMaps().set(i, userUgm);
				//log.info("after:"+ReflectionUtil.printObjectDetails(usr.getUserMaps().get(i)));
				break;
			}
		}
		
		em.merge(usr);
	}

}
