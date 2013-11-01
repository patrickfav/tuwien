package swag.gui;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import swag.bl.IBuildingManagement;
import swag.models.BaseSquare;
import swag.models.Building;
import swag.models.BuildingType;
import swag.models.MilitaryBuilding;
import swag.models.ResourceBuilding;
import swag.models.UpgradeBuilding;
import swag.utils.MessageUtil;

@ManagedBean
@RequestScoped
public class BuildingManager implements Serializable {

	private static final long serialVersionUID = -6373381644244318952L;
	
	private static Logger log = Logger.getLogger("BuildingManager");
	
	@EJB
	private IBuildingManagement buildingManagement;
	
	@ManagedProperty("#{sessionManager}")
	private SessionManager sessionManager;
	
	public List<BuildingType> getAllBuildingTypes() {
		return buildingManagement.getAllBuildingTypes();
	}
	
	public void buildBuilding(BaseSquare square, BuildingType type) {
		log.info("Triggered BuildingManager: build " + type.getName());
		if(buildingManagement.build(square, type)) {
			sessionManager.reloadBase();
			sessionManager.reloadBaseSquare();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "build_success", type.getName()),null));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "build_error"),null));
		}
	}
	
	public void upgradeBuilding(BaseSquare square, Building b) {
		log.info("Triggered BuildingManager: upgrade " + b.getType().getName());
		if(buildingManagement.upgrade(square, b)) {
			sessionManager.reloadBase();
			sessionManager.reloadBaseSquare();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "upgrade_success", b.getType().getName()),null));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "upgrade_error"),null));
		}
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
	
	public MilitaryBuilding getMilitaryBuilding(BuildingType type) {
		if(type instanceof MilitaryBuilding) {
			return (MilitaryBuilding) type;
		}
		return null;
	}
	
	public ResourceBuilding getResourceBuilding(BuildingType type) {
		if(type instanceof ResourceBuilding) {
			return (ResourceBuilding) type;
		}
		return null;
	}
	
	public UpgradeBuilding getUpgradeBuilding(BuildingType type) {
		if(type instanceof UpgradeBuilding) {
			return (UpgradeBuilding) type;
		}
		return null;
	}

}
