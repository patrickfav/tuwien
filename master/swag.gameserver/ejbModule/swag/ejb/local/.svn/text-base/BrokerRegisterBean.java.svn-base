package swag.ejb.local;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import swag.bl.IMapManagement;
import swag.bl.ITestDataManagement;
import swag.bl.IUserManagement;
import swag.broker.IBroker;
import swag.models.Message;
import swag.models.User;
import swag.test.TestDataGenerator;

@Singleton
@Startup
public class BrokerRegisterBean
{
	private final String brokerIP = "localhost";
	
	@PostConstruct
	public void init() 
	{
		Properties properties=new Properties();  
		properties.setProperty("java.naming.factory.initial","com.sun.enterprise.naming.impl.SerialInitContextFactory");  
		properties.setProperty("org.omg.CORBA.ORBInitialHost", brokerIP);
		properties.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        
		Context ctx;
		try 
		{
			ctx = new InitialContext(properties);
			IBroker broker = (IBroker)ctx.lookup("java:global/swag.deployment/swag.broker/Broker");
			
			broker.register("localhost");
		} 
		catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
