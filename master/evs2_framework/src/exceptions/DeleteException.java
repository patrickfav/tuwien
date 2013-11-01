package exceptions;

public class DeleteException extends Exception{

	private static final long serialVersionUID = -4775487877515686278L;
	
	public DeleteException() {
		super();
	}
	public DeleteException(String msg) {
		super(msg);
	}
	public DeleteException(String msg,Throwable cause) {
		super(msg,cause);
	}

}
