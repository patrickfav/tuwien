package swag.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import swag.bl.IMapManagement;
import swag.bl.ITroopManagement;
import swag.helper.TroopCountWrapper;
import swag.models.Base;
import swag.models.GameMap;
import swag.models.MapSquare;
import swag.models.Troop;
import swag.models.TroopMovementWrapper;
import swag.models.User;
import swag.models.enums.MilitaryType;
import swag.utils.MessageUtil;

@ManagedBean
@RequestScoped
public class TroopManager {
	private static Logger log = Logger.getLogger("TroopManager");
	
	@EJB
	private ITroopManagement troopManagement;
	
	@EJB
	private IMapManagement mapManagement;
	
	@ManagedProperty("#{sessionManager}")
	private SessionManager sessionManager;
	
	private List<TroopCountWrapper> troopList;
	
	private int troopCount;

	public void trainTroops(Base b, Troop t) {
		log.info("Triggered TroopManager: train" + t.getName());
		GameMap m = mapManagement.getMapForBase(b);
		if(troopManagement.trainTroop(m, b, t, troopCount)) {
			sessionManager.reloadBase();
			FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "build_success", t.getName()),null));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "build_error"),null));
		}
	}
	
	public void sendTroops( GameMap map,User user,MapSquare dest) {
		Map<Troop,Long> troops = new HashMap<Troop, Long>();
		
		
		for(TroopCountWrapper tcw: troopList) {
			troops.put(tcw.getTroop(), tcw.getCount());
		}
		try {
			troopManagement.sendTroops(user, map, dest, troops);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "send_troop_error"),null));
		
			e.printStackTrace();
		}
		
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "successfully_send_troop"),null));
	}
	
	public int getTroopCount(GameMap m, User u, Troop t) {
		return troopManagement.getTroopCount(m,u,t);
	}
	
	public List<TroopMovementWrapper> getTroopMovementWrapper(GameMap m, User u) {
		try {
			return troopManagement.getTroopMovementWrappers(m, u);
		} catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<TroopMovementWrapper>();
		}
	}
	
	public List<TroopCountWrapper> getGlobalTroopCountWrapperList(GameMap m, User u) {
		List<TroopCountWrapper> list = new ArrayList<TroopCountWrapper>();
		TroopCountWrapper tcw;
		Map<Troop,Long> map = troopManagement.getTroopCount(m, u);
		for(Troop t:map.keySet()) {
			tcw = new TroopCountWrapper();
			tcw.setCount(map.get(t));
			tcw.setTroop(t);
			list.add(tcw);
		}
		return list;
	}
	
	private List<TroopCountWrapper> getTroopCountWrapperList() {
		List<TroopCountWrapper> list = new ArrayList<TroopCountWrapper>();
		TroopCountWrapper tcw;
		
		for(Troop t:troopManagement.getAllTroopTypes()) {
			tcw = new TroopCountWrapper();
			tcw.setCount(0);
			tcw.setTroop(t);
			list.add(tcw);
		}
		return list;
	}
	
	public List<Troop> getTroopsByType(MilitaryType type) {
		return troopManagement.getTroopsForType(type);
	}

	public void setTroopList(List<TroopCountWrapper> troopList) {
		this.troopList = troopList;
	}

	public List<TroopCountWrapper> getTroopList() {
		troopList = getTroopCountWrapperList();
		return troopList;
	}
	
	public int getTroopCount() {
		return troopCount;
	}

	public void setTroopCount(int troopCount) {
		this.troopCount = troopCount;
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
	
}
