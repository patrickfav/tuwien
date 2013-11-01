package swag.exceptions;

public class NotEnoughFreeResourcesException extends Exception{

	private static final long serialVersionUID = -4775487877515686278L;
	
	public NotEnoughFreeResourcesException() {
		super();
	}
	public NotEnoughFreeResourcesException(String msg) {
		super(msg);
	}
	public NotEnoughFreeResourcesException(String msg,Throwable cause) {
		super(msg,cause);
	}

}