package exceptions;

public class DatabaseException extends Exception{

	private static final long serialVersionUID = -4775487877515686278L;
	
	public DatabaseException() {
		super();
	}
	public DatabaseException(String msg) {
		super(msg);
	}
	public DatabaseException(String msg,Throwable cause) {
		super(msg,cause);
	}

}
