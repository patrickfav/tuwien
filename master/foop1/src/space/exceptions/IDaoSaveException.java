package space.exceptions;

public class IDaoSaveException extends Exception{
	private static final long serialVersionUID = 8041577551538125989L;
	
	public IDaoSaveException() {
		super();
	}
	public IDaoSaveException(String msg) {
		super(msg);
	}
	public IDaoSaveException(String msg,Throwable cause) {
		super(msg,cause);
	}

}

