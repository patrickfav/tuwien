package exceptions;

public class HttpContentException extends Exception{

	private static final long serialVersionUID = -4775487877515686278L;
	
	public HttpContentException() {
		super();
	}
	public HttpContentException(String msg) {
		super(msg);
	}
	public HttpContentException(String msg,Throwable cause) {
		super(msg,cause);
	}

}
