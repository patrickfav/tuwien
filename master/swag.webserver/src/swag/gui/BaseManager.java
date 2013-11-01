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

import swag.bl.IBaseManagement;
import swag.models.Base;
import swag.models.GameMap;
import swag.models.MapSquare;
import swag.models.User;
import swag.utils.MessageUtil;

@ManagedBean
@RequestScoped
public class BaseManager implements Serializable {

	private static final long serialVersionUID = 5414284781471439228L;
	
	private static Logger log = Logger.getLogger("BaseManager");

	@EJB
	private IBaseManagement baseManagement;
	
	@ManagedProperty("#{sessionManager}")
	private SessionManager sessionManager;
	
	public String show() {
		return "/game/base.xhtml";
	}

	public List<Base> getAllBases(long id) {
		return baseManagement.getBasesForUser(id);
	}
	
	public void buildBase(MapSquare ms, User u, GameMap map) {
		log.info("Triggered BaseManager: build base");
		if(baseManagement.createBase(u, map, ms)){
			sessionManager.reloadMap();
			sessionManager.reloadMapSquare();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "base_build_success"),null));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "base_build_error"),null));
		}
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
}
