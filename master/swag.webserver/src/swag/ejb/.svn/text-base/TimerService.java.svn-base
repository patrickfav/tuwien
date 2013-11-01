package swag.ejb;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

@Stateless
@Local
public class TimerService {
	
	@EJB
	private SessionStorageBean ssb;
	
	@Schedule(second="0",minute="*/15",hour="*",persistent=false)
	public void checkLoginStatus() {
		//checks login status, if timed out
		ssb.checkAllInstances();
	}
}
