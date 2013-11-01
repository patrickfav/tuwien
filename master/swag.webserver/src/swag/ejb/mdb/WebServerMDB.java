package swag.ejb.mdb;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import swag.dtos.MessageDTO;
import swag.ejb.ClientMessageBean;
import swag.ejb.SessionStorageBean;
import swag.util.ReflectionUtil;

@MessageDriven(mappedName = "topic.swag.MessagesTopic" )
public class WebServerMDB implements MessageListener{
	
	private static Logger log = Logger.getLogger("WebServerMDB");
	
	@EJB
	private SessionStorageBean sessionStorageBean;
	
	@EJB
	private ClientMessageBean cmb;
	
	@Override
	public void onMessage(Message message) {
		try {
			if(message instanceof ObjectMessage) {
				ObjectMessage objMsg = (ObjectMessage) message;
				
				if(objMsg.getObject() instanceof MessageDTO) {
				
					MessageDTO dto = (MessageDTO) objMsg.getObject();
					log.info("Received Message (Webserver): "+ReflectionUtil.printObjectDetails(dto));
					
					if(sessionStorageBean.deliverMessage(dto.getReciever().getId(), dto.getAsMessage())) {
						log.info("Message delivered. Sending Ack.");
						cmb.sendReceivedAck(dto.getId());
					} else {
						log.info("User seems to be offline by now. Sending fail.");
						cmb.sendReceivedFail(dto.getId());
					}
					
					
				} else {
					log.warning("Wrong type of Message: "+objMsg.getObject().getClass());
				}
			}
			
		} catch (Exception e) {
			log.warning("ERROR: "+e.toString());
			e.printStackTrace();
		}
		
	}

}
