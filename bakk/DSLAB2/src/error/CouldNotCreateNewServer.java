package error;

public class CouldNotCreateNewServer extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2302831893542507984L;
	private String msg;
	
	public CouldNotCreateNewServer(String msg) {
		this.msg = msg;
	}
	
	public String toString() {
		return msg;
	}
}
