package swag.ejb;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import swag.bl.IBaseManagement;
import swag.bl.IMapManagement;
import swag.bl.IResourceManagement;
import swag.bl.ITickManagement;
import swag.bl.ITroopManagement;
import swag.bl.messaging.IMessageManagement;
import swag.exceptions.MessagingException;
import swag.exceptions.NotEnoughFreeResourcesException;
import swag.models.Base;
import swag.models.GameMap;
import swag.models.LongEntity;
import swag.models.MapSquare;
import swag.models.MilitaryCreateListEntry;
import swag.models.Troop;
import swag.models.TroopMovementWrapper;
import swag.models.User;
import swag.models.enums.MilitaryType;
import swag.models.enums.ResourceType;
import swag.util.ReflectionUtil;

/**
 * Session Bean implementation class TroopManagementBean
 */
@Stateless
public class TroopManagementBean implements ITroopManagement{
	
	public static final int UNIT_SPEED_FACTOR = 1;
	
	@EJB
	private IMapManagement mm;

	@EJB
	private IResourceManagement rm;
	
	@EJB
	private ITickManagement tm;
	
	@EJB
	private IMessageManagement messageManagement;
	
	@PersistenceContext
	public EntityManager em;
	
	private static Logger log = Logger.getLogger("TroopManagementBean");

    public TroopManagementBean() {
        // TODO Auto-generated constructor stub
    }

    @Override
	public boolean trainTroop(GameMap m, Base base, Troop t, int count) {
    	
    	if(rm.sufficientResources(t.getResourceCost(), base.getUser(), m, count)) {
			try {
				rm.takeResourcesFromUser(m, base.getUser(), t.getResourceCost(), count);
			} catch(NotEnoughFreeResourcesException e) {
				log.warning("Not enough resources: "+e.getMessage());
				return false;
			} catch(Exception e) {
				log.warning("ERROR: "+e.getMessage());
				return false;
			}
			base = em.merge(base);
			
			MilitaryCreateListEntry entry = null;
			
			if(base.getTrainingList().containsKey(t)) {
				log.warning("Add more Troops to entry " + t.getName());
				entry = base.getTrainingList().get(t);
				entry.setCount(entry.getCount() + count);
				entry.setEnd_time(tm.calculateEnd(entry.getStart_time(), t.getTrainingTime() * entry.getCount(), mm.getMapForBase(base)));
				em.merge(entry);
			} else {
				log.warning("Create new Troop entry " + t.getName());
				entry = new MilitaryCreateListEntry();
				entry.setTroop(t);
				entry.setStart_time(new Date());
				entry.setCount(count);
				entry.setEnd_time(tm.calculateEnd(entry.getStart_time(), t.getTrainingTime() * count, mm.getMapForBase(base)));
				em.persist(entry);
				base.getTrainingList().put(t, entry);
			}
			
			em.merge(base);
			
			return true;
		} else {
			return false;
		}
    	
	}
	
	@Override
	public void updateTrainingStatus(GameMap m) {
		
		m = mm.getMap(m.getId());
		
		log.info("Updating training status for map " + m.getName() + ".");
		for(MapSquare ms : m.getSquares()){
			if(ms.getBase() == null) continue;
			Base base = ms.getBase();
			Set<Troop> troops = new HashSet<Troop>();
			troops.addAll(ms.getBase().getTrainingList().keySet());
			for(Troop t : troops) {
				MilitaryCreateListEntry entry = ms.getBase().getTrainingList().get(t);
							
				if(entry.getEnd_time().compareTo(new Date()) <= 0){
					log.info("Removing MilitaryCreateListEntry " + entry.getTroop().getName() +" from training list.");
					MilitaryCreateListEntry tmp = em.find(MilitaryCreateListEntry.class, entry.getId());
					
					if(base.getUnitsCount().containsKey(t)) {
						
						LongEntity old = base.getUnitsCount().get(t);
						LongEntity newLong = new LongEntity();
						newLong.setValue(new Long(old.getValue() + entry.getCount()));
						base.getUnitsCount().put(t, newLong);
						log.warning("Add to finished List: " + old.getValue() + entry.getCount());
					} else {
						LongEntity newLong = new LongEntity();
						newLong.setValue(new Long(entry.getCount()));
						base.getUnitsCount().put(t, newLong);
					}
					ms.getBase().getTrainingList().remove(t);
				} else {
					log.info("Update MilitaryCreateListEntry " + entry.getTroop().getName() +".");
					Date now = new Date();
					Date endUnit = tm.calculateEnd(now, t.getTrainingTime(), m);
					long todo = entry.getEnd_time().getTime() - now.getTime();
					long oneUnit = endUnit.getTime() - now.getTime();
					
					long unitsLeft = (todo / oneUnit);
					if(entry.getCount() >= unitsLeft) {
						if(base.getUnitsCount().containsKey(t)) {
							LongEntity old = base.getUnitsCount().get(t);
							LongEntity newLong = new LongEntity();
							newLong.setValue(new Long(old.getValue() + (entry.getCount() - unitsLeft)));
							base.getUnitsCount().put(t, newLong);
							log.warning("Add to finished List: " + (old.getValue() + (entry.getCount() - unitsLeft)));
						} else {
							LongEntity newLong = new LongEntity();
							newLong.setValue(new Long((entry.getCount() - unitsLeft)));
							base.getUnitsCount().put(t, newLong);
							log.warning("Add to finished List: " + (entry.getCount() - unitsLeft));
						}
						log.warning("Units left: " + unitsLeft);
						entry.setCount((int)unitsLeft);
						em.merge(entry);
					}
				}
				
			}
			em.merge(base);
		}
	}

	/* *********************************************************************************** PATRICK */
	
	private long getStealCapacity(Map<Troop, Long> troops) {
		long capacity = 0;
		for(Troop t: troops.keySet()) {
			capacity += troops.get(t) * 10;
	    }
	    return capacity;
	}
	
	private double getStrength(Map<Troop, Long> troops) {
		double strength = 0;
		for(Troop t: troops.keySet()) {
	    	strength += troops.get(t) * (t.getArmor()*1.2 + t.getStrength());
	    }
	    return strength;
	}
	
	private double getStrength2(Map<Troop, LongEntity> troops) {
		double strength = 0;
		for(Troop t: troops.keySet()) {
	    	strength += troops.get(t).getValue() * (t.getArmor()*1.2 + t.getStrength());
	    }
	    return strength;
	}
	
	private void fight(Base b, Map<Troop, Long> attackers, User attacker,GameMap m) {
		
		/* attacker wins */
		if(getStrength(attackers) >= getStrength2(b.getUnitsCount())) {
			if(!b.getStarterBase()) {
				b.setUser(attacker);
			} else {
				/* if starter base, just steal */
				Map<ResourceType,Long> resMap = new HashMap<ResourceType,Long>();
				for(ResourceType rs:ResourceType.values()) {
					resMap.put(rs, getStealCapacity(attackers));
				}
				
				rm.stealResources(m, attacker, b.getUser(), resMap);
			}
			b.setUnitsCount(new HashMap<Troop,LongEntity>());
			
			for(Troop t: attackers.keySet()) {
				b.getUnitsCount().put(t, new LongEntity(attackers.get(t)));
			}
		}
		/* attacker loses */
		else {
			// do nothing all attackers dead
		}
		
		em.merge(b);
		
	}
	
	
	private void station(Base b, Map<Troop, Long> attackers) {
		//b = em.find(Base.class,b.getId());
		log.info("in station");
		//log.info("before"+b.getUnitsCount().values());
		
		for(Troop t:attackers.keySet()) {
			if(b.getUnitsCount().containsKey(t)) {
				LongEntity le = em.find(LongEntity.class, b.getUnitsCount().get(t).getId());
				le.setValue(b.getUnitsCount().get(t).getValue()+attackers.get(t));
				log.info("Debug: (contains) "+ReflectionUtil.printObjectDetails(le));
				em.merge(le);
				//b.getUnitsCount().put(t, new LongEntity(b.getUnitsCount().get(t).getValue()+attackers.get(t)));
			} else {
				b.getUnitsCount().put(t, new LongEntity(attackers.get(t)));
				log.info("Debug: (none) "+ReflectionUtil.printObjectDetails(b));
			}
			
			
		}
		
		//log.info("before merge");
		//log.info(ReflectionUtil.printObjectDetails(b));
		//log.info("after"+b.getUnitsCount().values());
		em.merge(b);
	}
	
	@Override
	public void troopsArrived(TroopMovementWrapper attack) {
		log.info("in arrived user:"+attack.getUser().getId());
		
		Map<Troop, Long> attackers = attack.getTroops();
		
		if(attack.getUser().equals(attack.getDestination().getBase().getUser())) {
			station(attack.getDestination().getBase(),attackers);
		} else {
			fight(attack.getDestination().getBase(), attackers,attack.getUser(),attack.getMap());
		}
		
		try {
			messageManagement.sendSystemMessage(attack.getUser(), "Troops arrived", "Your troops have arrived at ("+attack.getDestination().getX()+","+attack.getDestination().getY()+")");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		em.remove(attack);
		
		
	}
	
	
	
	@Override
	public void updateTroopMovement(GameMap m) {
		long now = new Date().getTime();
		for(TroopMovementWrapper tm : getTroopMovementWrappers(m))
		{
			if(tm.getEnd_time().getTime() <= now)
				troopsArrived(tm);
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer getTroopCount(GameMap m, User u, Troop t) {
		int count = 0;
		
		List<Base> resultList = em.createQuery("select gbases from UserGameMap gm join gm.bases as gbases where gm.map.id = :mid and gm.user.id = :uid")
		.setParameter("uid", u.getId()).setParameter("mid", m.getId()).getResultList();
		
		log.info("Found Bases for :"+resultList.toArray()+" size:"+resultList.size());
		
		for(Base b: resultList) {
			if(b.getUnitsCount().containsKey(t)) {
				count += b.getUnitsCount().get(t).getValue();
				log.info("The count: "+count);
			}
		}
		
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Troop, Long> getTroopCount(GameMap m, User u) {
		Map<Troop, Long> countMap = new HashMap<Troop, Long>();
		
		List<Base> resultList = em.createQuery("select gbases from UserGameMap gm join gm.bases as gbases where gm.map.id = :mid and gm.user.id = :uid")
		.setParameter("uid", u.getId()).setParameter("mid", m.getId()).getResultList();
		
		for(Base b: resultList) {
			for(Troop t:b.getUnitsCount().keySet()) {
				if(countMap.containsKey(t)) {
					countMap.put(t,countMap.get(t)+b.getUnitsCount().get(t).getValue());
				} else {
					countMap.put(t,b.getUnitsCount().get(t).getValue());
				}
			}
				
		}
		
		return countMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Troop> getAllTroopTypes() {
		return em.createQuery("select t from Troop t order by t.name asc").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void sendTroops(User user, GameMap map, MapSquare dest, Map<Troop, Long> troops) {
		log.info("Entering sendTroops");
		List<Base> resultList = em.createQuery("select gbases from UserGameMap gm join gm.bases as gbases where gm.map.id = :mid and gm.user.id = :uid")
		.setParameter("uid", user.getId()).setParameter("mid", map.getId()).getResultList();
		
		
		Map<Troop, Long> gatheredTroops = new HashMap<Troop, Long>();
		for(Troop t:troops.keySet()) {
			gatheredTroops.put(t, 0l);
		}
		
		MapSquare ms;
		long max_distance = 0;
		
		boolean done;
		
		for(Base b: resultList) {
			ms = mm.getMapSquareForBase(b);
			max_distance = Math.max(max_distance, calculateDistance(ms.getX(), ms.getY(), dest.getX(), dest.getY())) ;
			log.info("distance: "+max_distance);
			
			for(Troop t:gatheredTroops.keySet()) {
				if(b.getUnitsCount().containsKey(t)) {
					if(troops.containsKey(t))
					
						if(gatheredTroops.get(t) < troops.get(t)) {
							if((gatheredTroops.get(t)+b.getUnitsCount().get(t).getValue()) < troops.get(t)) {
								gatheredTroops.put(t, gatheredTroops.get(t)+b.getUnitsCount().get(t).getValue());
								//b.getUnitsCount().put(t,new LongEntity(0l));//has not enough
								LongEntity le = em.find(LongEntity.class, b.getUnitsCount().get(t).getId());
								le.setValue(0l);
							} else {
								//b.getUnitsCount().put(t,new LongEntity(b.getUnitsCount().get(t).getValue()-(troops.get(t)-gatheredTroops.get(t))));
								LongEntity le = em.find(LongEntity.class, b.getUnitsCount().get(t).getId());
								le.setValue(b.getUnitsCount().get(t).getValue()-(troops.get(t)-gatheredTroops.get(t)));
								gatheredTroops.put(t, troops.get(t));
							}
							//em.merge(b);
						}
					
				}
			}
			
			
			/*check if done */
			done = true;
			for(Troop t:troops.keySet()) {
				if(troops.get(t) > gatheredTroops.get(t)) {
					done = false;
				}
			}
			
			if(done)
				break;
			
		}
		
		int min_speed = Integer.MAX_VALUE;
		/* get slowest memeber */
		for(Troop t:gatheredTroops.keySet()) {
			min_speed = Math.min(min_speed, t.getSpeed());
		}
		
		long timeUnitsPerSquare = Math.max(1, Math.round(((new Double(1))/(new Double(min_speed)))*(new Double(UNIT_SPEED_FACTOR))));
		
		log.info("timeUnitsPerSquare: "+timeUnitsPerSquare);
		
		TroopMovementWrapper tmw = new TroopMovementWrapper();
		tmw.setDestination(dest);
		tmw.setStart_time(new Date());
		tmw.setUser(user);
		tmw.setMap(map);
		tmw.setEnd_time(new Date((new Date()).getTime() + (map.getConfig().getTickTime()*timeUnitsPerSquare*max_distance)));
		tmw.setTroops(gatheredTroops);
		em.persist(tmw);
	}
	
	
	private long calculateDistance(int xSrc,int ySrc, int xDest, int yDest) {
		long dist = 0;
		
		dist += Math.abs(xSrc - xDest);
		dist += Math.abs(ySrc - yDest);
		
		return dist;
	}
	
	@Override
	public List<Troop> getTroopsForType(MilitaryType type) {
		Query query = em.createQuery("select t from Troop t where t.type = :type order by t.name asc");
		query.setParameter("type", type);
		List<Troop> resultList = query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TroopMovementWrapper> getTroopMovementWrappers(GameMap map, User u) {
		return em.createQuery("select tmw from TroopMovementWrapper tmw where tmw.user.id = :uid and tmw.map.id = :mid")
		.setParameter("uid", u.getId()).setParameter("mid", map.getId()).getResultList();

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TroopMovementWrapper> getTroopMovementWrappers(GameMap map) {
		return em.createQuery("select tmw from TroopMovementWrapper tmw where tmw.map.id = :mid")
		.setParameter("mid", map.getId()).getResultList();

	}
}
