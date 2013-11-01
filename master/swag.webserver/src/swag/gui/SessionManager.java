package swag.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import swag.bl.IBaseManagement;
import swag.bl.IMapManagement;
import swag.bl.IResourceManagement;
import swag.bl.IUserManagement;
import swag.bl.messaging.IMessageManagement;
import swag.ejb.SessionStorageBean;
import swag.exceptions.MessagingException;
import swag.exceptions.UserManagementException;
import swag.gui.comparator.MessageComparator;
import swag.models.Base;
import swag.models.BaseSquare;
import swag.models.GameMap;
import swag.models.MapSquare;
import swag.models.Message;
import swag.models.User;
import swag.models.enums.ResourceType;
import swag.models.UserGameMap;
import swag.utils.MessageUtil;

@ManagedBean
@SessionScoped
public class SessionManager implements Serializable {
	
	private static final long serialVersionUID = -6694632853246559497L;

	private static Logger log = Logger.getLogger("SessionManager");
	
	@EJB
	private IUserManagement userManagement;
	
	@EJB
	private IMapManagement mapManagement;
	
	@EJB
	private IBaseManagement baseManagement;
	
	@EJB
	private SessionStorageBean sessionStorageBean;
	
	@EJB
	private IMessageManagement messageManagement;
	
	@EJB
	public IResourceManagement resourceManagement;
	
	private Map<Message,Boolean> userMessages;
	
	private User user = null;
	private UserGameMap userMap = null;
	private GameMap map = null;
	private Base base = null;
	
	private boolean loggedIn = false;
	
	private String username;
	private String password;
	
	private Message selectedMessage;
	public MapSquare selectedMapSquare;
	public BaseSquare selectedBaseSquare;
	
	private Date lastActivity;
	
	@PostConstruct
	public void indexInstance() {
		userMessages = Collections.synchronizedMap(new HashMap<Message,Boolean>());

		registerActivity();
	}
	
	@PreDestroy
	public void beforeDestroy() {
		if(user != null)
    		sessionStorageBean.removeInstance(user.getId());
	}
	
    public String login() {   	
    	registerActivity();
    	
		try {
			user = userManagement.login(username, password);
	
			if(user == null) {
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_WARN, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "login_wrong"), null ));
				return "/public/login.xhtml";
			}
		
		
			map = mapManagement.loggonMap(map, user);
			reloadMap();
			reloadUser();
			reloadUserMap();
		
		} catch (UserManagementException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "login_error"),null));
			return "/public/login.xhtml";
		}
		
    	FacesContext.getCurrentInstance().addMessage(null, 
    			new FacesMessage(FacesMessage.SEVERITY_INFO, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "login_success"),null));
    	loggedIn = true;
    	

		sessionStorageBean.addInstance(user.getId(), this);
		
		fetchMessages();
		
		return "/game/map.xhtml";
    }

	public String logout() {
    	registerActivity();
    	
    	
    	if(user != null)
    		sessionStorageBean.removeInstance(user.getId());
    	
    	/* already logged out */
    	if(user== null || !loggedIn ) {
    		FacesContext.getCurrentInstance().addMessage(null, 
        			new FacesMessage(FacesMessage.SEVERITY_WARN, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "logout_alredydone"),null));
    		
    		return "/public/home.xhtml";
    	}
    	
    	loggedIn = false;
    	userMessages = Collections.synchronizedMap(new HashMap<Message,Boolean>());

    	/* try to log out */
    	try {
    		userManagement.logout(user.getId());
		} catch (UserManagementException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
        			new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "logout_error"),null));
    		
			return "/public/home.xhtml";
		} finally {
			user = null;
		}
		
		/* success */
		FacesContext.getCurrentInstance().addMessage(null, 
    			new FacesMessage(FacesMessage.SEVERITY_INFO, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "logout_success"),null));
		return "/public/home.xhtml";
    }
    
	/**
	 * Logout for the session storage manager
	 * @throws UserManagementException
	 */
	public void logoutInternal() {
    	if(user != null)
    		sessionStorageBean.removeInstance(user.getId());
    	
    	loggedIn = false;
    	userMessages = Collections.synchronizedMap(new HashMap<Message,Boolean>());

    	/* try to log out */
    	try {
			userManagement.logout(user.getId());
		} catch (UserManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user = null;
    }
    
    /**
     * Sets the activity timestamp for login status garbage collection
     */
    public void registerActivity() {
    	lastActivity =new Date();
    }
    
    
    /* *********************************************************  MESSAGES */
    
    /**
     * To be called from the MDB to set a new Message
     * for a user
     * @param msg
     */
    public void addNewDeliveredMessage(Message msg) {
    	//newMessages.add(msg);
    	userMessages.put(msg, false);
    }
    
  
    /**
     * Retuns new messages received throguh MQ and the fetch from the db
     */
    public List<Message> getAllMessages() {
    	registerActivity();
    	List<Message> list = new ArrayList<Message>(userMessages.keySet());
    	Collections.sort(list, new MessageComparator<Message>());
    	return list;
    }
    
    public void fetchMessages() {
    	registerActivity();
    	try {
			for(Message m: messageManagement.getAllMessagesByUser(user.getId())) {
				userMessages.put(m, true);
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }
    
    public int getNewMessagesCount() {
    	int i=0;
    	for(Boolean bool:userMessages.values()) {
    		if(!bool)
    			i++;
    	}
    	return i;
    }
    
    /**
     * Checked by the gui
     */
    public boolean isNewMessage(Message msg) {
    	if(userMessages.containsKey(msg)) {
    		if(!userMessages.get(msg)) {
    			//userMessages.put(msg,true);
    			//log.info("new returns true");
    			return true;
    		}
    	}
    	//log.info("new returns false");
    	return false;
    }
    
    /* *********************************************************  RESOURCES */
   
    public long getIronStock() {
    	return resourceManagement.getRessourceStock(ResourceType.IRON, user, map);
    }
    
    public long getWoodStock() {
    	return resourceManagement.getRessourceStock(ResourceType.WOOD, user, map);
    }
    
    public long getStoneStock() {
    	return resourceManagement.getRessourceStock(ResourceType.STONE, user, map);
    }
    
    /* *********************************************************  RELOAD MAP/BASE */
  
    public void reloadUser() throws UserManagementException {
		user = userManagement.refreshUser(user.getId());
    }
    
    public void reloadUserMap() throws UserManagementException {
    	for(UserGameMap ugm: user.getUserMaps()) {
			if(ugm.getMap().equals(map)) {
				userMap = ugm;
				break;
			}
		}
    }
    
    public void reloadMap() {
    	map = mapManagement.getMap(map.getId());
    }
    
    public void reloadBase() {
    	if(base != null) {
    		base = baseManagement.refreshBase(base.getId());
    	}
    }
    
	public void reloadBaseSquare() {
		if(selectedBaseSquare != null) {
    		selectedBaseSquare = baseManagement.refreshBaseSquare(selectedBaseSquare.getId());
    	}
	}
	
	public void reloadMapSquare() {
		if(selectedMapSquare != null) {
    		selectedMapSquare = mapManagement.refreshMapSquare(selectedMapSquare.getId());
    	}
	}
    
    /* *********************************************************  GETTER/SETTER */
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public Date getLastActivity() {
		return lastActivity;
	}

	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}
	
	public String goToBase(Base base) {
		log.info("Go to Base " + base.getId());
		this.base = base;
		return "/game/base.xhtml";
	}

	public void setSelectedMessage(Message selectedMessage) {
		if(userMessages.containsKey(selectedMessage)) {
			userMessages.put(selectedMessage, true);
		}
		this.selectedMessage = selectedMessage;
	}

	public Message getSelectedMessage() {
		return selectedMessage;
	}

	public UserGameMap getUserMap() {
		return userMap;
	}

	public void setUserMap(UserGameMap userMap) {
		this.userMap = userMap;
	}

	public MapSquare getSelectedMapSquare() {
		return selectedMapSquare;
	}

	public void setSelectedMapSquare(MapSquare selectedMapSquare) {
		this.selectedMapSquare = selectedMapSquare;
		reloadMapSquare();
	}

	public BaseSquare getSelectedBaseSquare() {
		return selectedBaseSquare;
	}

	public void setSelectedBaseSquare(BaseSquare selectedBaseSquare) {
		this.selectedBaseSquare = selectedBaseSquare;
	}

	public String quit(){
		try {
			sessionStorageBean.removeInstance(user.getId());
			userManagement.quitAccount(user);
			FacesContext.getCurrentInstance().addMessage(null, 
	    			new FacesMessage(FacesMessage.SEVERITY_INFO, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "quit_success"),null));
			loggedIn = false;
			user = null;
			return "/public/home.xhtml";
		} catch (UserManagementException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
        			new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "quit_error"),null));
			return null;
		}
	}
}
