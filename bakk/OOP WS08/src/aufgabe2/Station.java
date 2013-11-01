package aufgabe2;

import java.io.Serializable;

public class Station implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1522929742760540509L;
	private String name;
	private String password;
	private int iterator;
	/**
	 * Constructor
	 * @param name
	 * @param password
	 * @param id
	 */
	public Station(String name, String password, int id) {
		this.name = name;
		this.password = password;
		this.iterator = id;
	}
	/**
	 * Generates a new id
	 * @return
	 */
	public int getId() {
		return iterator++;
	}
	/**
	 * Returns the name of the station
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * Change the name of the station
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns the password of the station
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets a new password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
