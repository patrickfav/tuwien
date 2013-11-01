package error;

public class FileServerFileNotFound extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2302831893542507984L;
	private String msg;
	
	public FileServerFileNotFound(String msg) {
		this.msg = msg;
	}
	
	public String toString() {
		return msg;
	}
}
