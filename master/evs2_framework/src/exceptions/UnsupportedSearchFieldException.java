package exceptions;

public class UnsupportedSearchFieldException extends Exception{

	private static final long serialVersionUID = -4775487877515686278L;
	
	public UnsupportedSearchFieldException() {
		super();
	}
	public UnsupportedSearchFieldException(String msg) {
		super(msg);
	}
	public UnsupportedSearchFieldException(String msg,Throwable cause) {
		super(msg,cause);
	}

}
