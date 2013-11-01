package swag.ejb.local;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import swag.bl.IBuildingManagement;
import swag.bl.IMapManagement;
import swag.bl.IResourceManagement;
import swag.bl.ITickManagement;
import swag.bl.ITroopManagement;
import swag.models.GameMap;
import swag.models.TroopMovementWrapper;

@Singleton
public class TickManagementBean implements ITickManagement {
	
	private static final Integer TICK_FACTOR = 1;

	private static Logger log = Logger.getLogger("TickManagementBean");
	
	@PersistenceContext
	public EntityManager em;
	
	@EJB
	private IResourceManagement rm;
	
	@EJB
	private IBuildingManagement bm;
	
	@EJB
	private IMapManagement mm;
	
	@EJB
	private ITroopManagement tm;
	
	@Resource
    public TimerService timerService;
	
	private List<Timer> timers;

	@Override
	public void init(){
		timers = new ArrayList<Timer>();
		
		List<GameMap> maps = mm.getAllMaps();
		if(maps == null) return;
		
		for(GameMap m : maps){
			long duration = m.getConfig().getTickTime() * TICK_FACTOR;
			Timer timer = timerService.createIntervalTimer(new Date(), duration, new TimerConfig(m.getId(), true));
			timers.add(timer);
		}		
		
	}
	
	@Timeout
	public void tick(Timer timer){
		GameMap m = mm.getMap((Long) timer.getInfo());

		tm.updateTroopMovement(m);
		rm.updateRessourcesInDB(m);
		bm.updateBuildStatus(m);
		tm.updateTrainingStatus(m);
		log.info("Tick for Map " + m.getName() + ".");
	}

	@Override
	public Date calculateEnd(Date start, Integer duration, GameMap m) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		Integer milliseconds = (int) (duration * m.getConfig().getTickTime() * TICK_FACTOR);
		cal.add(Calendar.MILLISECOND, milliseconds);
		return cal.getTime();
	}
}
