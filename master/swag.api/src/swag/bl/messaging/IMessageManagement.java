package swag.bl.messaging;

import java.util.List;

import swag.exceptions.MessagingException;
import swag.models.Message;
import swag.models.User;

public interface IMessageManagement {
	public void sendSystemMessage(User receiver,String subject, String text) throws MessagingException;
	public List<Message> getAllMessagesByUser(long userId) throws MessagingException;
	public List<Message> getAllMessagesForUser(long userId);
	public void persist(Message msg);
	public void update(Message msg);
	public Message find(long id);
}
