package swag.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import swag.bl.IUserManagement;
import swag.ejb.ClientMessageBean;
import swag.exceptions.UserManagementException;
import swag.models.Message;
import swag.models.User;
import swag.utils.MessageUtil;

@ManagedBean
@RequestScoped
public class MessageManager {
	
	@EJB
	private IUserManagement userManagement;
	
	@EJB
	private ClientMessageBean cmb;
	
	private String subject;
	private String content;
	private User receiver;
	
	
	
	public List<User> getAllUserWithoutCurrent(User current) {
		try {
			List<User> list = userManagement.getAllUser();
			list.remove(current);
			
			return list;
		} catch (UserManagementException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<User>();
	}
	
	
	public String send(User sender) {
		Message msg = new Message();
		
		msg.setReceiver(receiver);
		msg.setSubject(subject);
		msg.setText(content);
		msg.setSender(sender);
		msg.setTimestamp(new Date());
		msg.setSendDate(new Date());
		
		try {
			cmb.send(msg);
		} catch(Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "msg_could_not_sent"), null ));
			return "/game/messages.xhtml";
		}
		
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, MessageUtil.loadMessage(FacesContext.getCurrentInstance(), "msg_successfully_sent"), null ));
		
		clearGuiFields();
		
		return "/game/messages.xhtml";
	}
	
	public void clearGuiFields() {
		subject="";
		content="";
		receiver=null;
	}
	
	public void replyMessage(User u,String subject) {
    	this.subject= "RE: "+subject;
    	this.receiver = u;
    }
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	
	
}
