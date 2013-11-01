package dst2.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class ComputerNotFreeAnyMore extends Exception{

	private static final long serialVersionUID = 7747763955855177342L;
	
	public ComputerNotFreeAnyMore() {
		super();
	}
	public ComputerNotFreeAnyMore(String msg) {
		super(msg);
	}
	public ComputerNotFreeAnyMore(String msg,Throwable cause) {
		super(msg,cause);
	}

}