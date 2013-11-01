package aufgabe3;

import java.io.Serializable;
import java.util.Date;

/**
 * A bin class for one logentry.
 * 
 * @author Parick Favre-Bulle
 *
 */
public class Log implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8944618812067599762L;
	private Date timestamp;
	private int id;
	private String description;
	private String flag;
	
	
	/**
	 *  CONSTRUCTOR 
	 *  
	 * @param timestamp
	 * @param description
	 * @param flag
	 */
	
	public Log(Date timestamp, int id, String description, String flag) {
		this.timestamp = timestamp;
		this.description = description;
		this.flag = flag;
		this.id = id;
	}
	/**
	 * EMPTY CONSTRUCTOR
	 */
	public Log() {
		
	}
	/**
	 *  GETTER AND SETTER METHODS
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
	
}

