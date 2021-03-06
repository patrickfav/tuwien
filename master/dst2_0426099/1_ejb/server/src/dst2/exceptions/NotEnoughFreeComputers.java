package dst2.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class NotEnoughFreeComputers extends Exception{

	private static final long serialVersionUID = 7747763955855177342L;
	
	public NotEnoughFreeComputers() {
		super();
	}
	public NotEnoughFreeComputers(String msg) {
		super(msg);
	}
	public NotEnoughFreeComputers(String msg,Throwable cause) {
		super(msg,cause);
	}

}