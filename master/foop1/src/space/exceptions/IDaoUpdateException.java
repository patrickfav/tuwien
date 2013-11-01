package space.exceptions;

public class IDaoUpdateException extends Exception{
	private static final long serialVersionUID = -4527999859731411818L;
	
	public IDaoUpdateException() {
		super();
	}
	public IDaoUpdateException(String msg) {
		super(msg);
	}
	public IDaoUpdateException(String msg,Throwable cause) {
		super(msg,cause);
	}

}

