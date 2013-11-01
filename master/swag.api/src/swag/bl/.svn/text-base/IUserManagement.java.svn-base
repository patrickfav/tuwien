package swag.bl;


import java.util.List;

import javax.ejb.Remote;

import swag.exceptions.UserManagementException;
import swag.models.User;

@Remote
public interface IUserManagement {
	
	public User login(String usr, String password) throws UserManagementException;
	public void logout(long id) throws UserManagementException;
	
	public User refreshUser(long id) throws UserManagementException;
	public void updateUser(User user) throws UserManagementException;
	public List<User> getAllUser()throws UserManagementException;
	
	public boolean quitAccount(User user) throws UserManagementException;
	public void registerUser(User user) throws UserManagementException;
	
	public boolean isLoggedIn(long id) throws UserManagementException;
}
