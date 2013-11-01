package swag.gui;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class ProgressManager implements Serializable {

	private static final long serialVersionUID = 1697584531644115473L;

	private static Logger log = Logger.getLogger("ProgressManager");

	@ManagedProperty("#{sessionManager}")
	private SessionManager sessionManager;
	
	public Long getProgress(Date start, Date end) {

		Date now = new Date();

		float total = end.getTime() - start.getTime();
		float done = now.getTime() - start.getTime();

		//log.info("TOTAL: " + total);
		//log.info("DONE: " + done);

		if (done <= total) {
			float tmp = (done / total) * 100;
			//log.info("PROGRESS: " + tmp);
			if (tmp >= 100) {
				onComplete();
				return Long.valueOf(100);
			}
			return Long.valueOf((int)tmp);
		} else {
			//log.info("PROGRESS: " + 100);
			onComplete();			
			return Long.valueOf(100);
		}
	}

	public void onComplete() {
		sessionManager.reloadBase();
		sessionManager.reloadBaseSquare();	
		// FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Progress Completed", null));
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
	
	

}
