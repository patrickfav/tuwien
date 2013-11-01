package util;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import bean.EventManager;
import entities.User;
import entities.event.ErrorEvent;

@Name("ErrorHandler")
@Scope(ScopeType.STATELESS)
public class ErrorHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Logger
	private Log log;
	
	@In(value="EventManager")
	private EventManager eventManager;
	
	@In(required=false)
	private User user;
	
	@Observer("org.jboss.seam.exceptionHandled")
	public void handleException(Exception e) {
		if(user == null)
			return;		// do not log SessionTimeouts!!
		
		log.info("exception handled by seam! type: #0 message #1", e.getClass(), e.getMessage());
		createSeamErrorEvent(e);
	}
	
	@Observer("org.jboss.seam.exceptionNotHandled")
	public void handleUnhandledException(Exception e) {
		log.info("got unhandled exception from seam");
		
		createSeamErrorEvent(e);
	}
	
	private void createSeamErrorEvent(Exception e) {
		ErrorEvent ee = new ErrorEvent();
		ee.setTimestamp(new Date(System.currentTimeMillis()));
		ee.setUser(user);
		ee.setErrorMsg(getStackTraceAsString(e));
		ee.setErrorType(e.getClass().getName());
		
		eventManager.registerEvent(ee);
	}
	
	private static String getStackTraceAsString(Exception e) {
		final Writer result = new StringWriter();
	    final PrintWriter printWriter = new PrintWriter(result);
	    e.printStackTrace(printWriter);
	    return result.toString().substring(0, 2499);

	}

}
