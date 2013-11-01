package swag.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import swag.bl.IBaseManagement;
import swag.bl.IMapManagement;
import swag.bl.IUserManagement;
import swag.bl.messaging.IMessageManagement;
import swag.exceptions.MessagingException;
import swag.exceptions.UserManagementException;
import swag.models.Base;
import swag.models.Message;
import swag.models.User;
import swag.models.UserGameMap;
import swag.util.HashUtil;

@Stateless
public class UserManagementBean implements IUserManagement {

	private static Logger log = Logger.getLogger("UserManagementBean");
	
	@PersistenceContext
	public EntityManager em;
	
	@EJB
	public IBaseManagement baseManagement;
	
	@EJB
	public IMapManagement mapManagement;
	
	@EJB
	public IMessageManagement messageManagement;
	
	@SuppressWarnings("unchecked")
	@Override
	public User login(String username, String password) throws UserManagementException {
		List<User> list = null;
		
		try {
			list = em.createQuery("select u from swag_user u where u.username = :usr AND u.password = :psw")
		.setParameter("usr", username).setParameter("psw", HashUtil.sha256(password, username)).getResultList();
		} catch (Exception e) {
			throw new UserManagementException("Could not retrieve user from db",e);
		}
		
		if(list == null || list.size() == 0)
			return null;
		
		
		log.info("Login successful "+username);
		
		/* update login status */
		User u = list.get(0);
		u.setLogin(true);
		u.setLoginDate(new Date());
		
		updateUser(u);
		
		return u;
	}

	@Override
	public void logout(long id) throws UserManagementException {
		
		log.info("Logout "+id);
		
		User u = em.find(User.class, id);
		
		if(u != null) {
			/* update login status */
			u.setLogin(false);
			
			updateUser(u);
		}
		
		
	}

	@Override
	public User refreshUser(long id) throws UserManagementException {
		return em.find(User.class, id);
	}

	@Override
	public void updateUser(User user) throws UserManagementException {
		em.merge(user);
	}

	@Override
	public boolean quitAccount(User user) throws UserManagementException {
		
		List<Base> userBases = baseManagement.getBasesForUser(user.getId());
		for(Base b : userBases){
			b.setUser(null);
			if(b.getStarterBase()) b.setStarterBase(false);
			em.merge(b);
		}
		
		List<UserGameMap> userGameMaps = mapManagement.getUserGameMaps(user);
		for(UserGameMap ugm : userGameMaps){
			UserGameMap tmp = em.find(UserGameMap.class, ugm.getId());
			em.remove(tmp);
		}
		
		List<Message> userMessages = messageManagement.getAllMessagesForUser(user.getId());
		for(Message m : userMessages){
			Message tmp = em.find(Message.class, m.getId());
			em.remove(tmp);
		}
		
		User tmp = em.find(User.class, user.getId());
		em.remove(tmp);
		
		return true;
		
	}

	@Override
	public void registerUser(User user) throws UserManagementException {
		user.setLogin(false);
		em.persist(user);
		
	}

	@Override
	public boolean isLoggedIn(long id) throws UserManagementException {
		User u = null;
		
		try {
			u = em.find(User.class,id);
		} catch (Exception e) {
			throw new UserManagementException("Could not retrieve user from db for getting login status",e);
		}
		
		if(u == null)
			return false;
		
		return u.getLogin();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUser() throws UserManagementException {
		return (List<User>) em.createQuery("select u from swag_user u order by u.username asc").getResultList();
	}

	
}
