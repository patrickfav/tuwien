package util;

import java.util.Iterator;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.log.Log;

/**
 * Just for debugging purposes...
 * 
 * @author inso
 *
 */

@Stateless
@Name("ValidationFailedObserver")
public class ValidationFailedObserverBean implements ValidationFailedObserver {

	@Logger
	private Log log;
	
	@Observer("org.jboss.seam.validationFailed")
	public void observeFailedValidation() {
		log.info("validation of JSF component failed");
		Iterator<FacesMessage> it = FacesContext.getCurrentInstance().getMessages();
		
		while(it.hasNext()) {
			FacesMessage fm = it.next();
			log.info("Found FacesMessage: #0 #1 #2", fm.getSeverity(), fm.getSummary(), fm.getDetail());
		}

	}

}
