package entities;

import java.sql.Date;
import java.sql.Timestamp;


public class Driver implements Entity{

	private Integer svnr = null;
	private String firstname = null;
	private String surname = null;
	private String tel = null;
	private Integer vehicle = null;
	private Integer covers = null;
	private Boolean male = null;
	private Timestamp update = null;
	private Date create = null;
	
	/*
	 * EXTERN CONSTRUCTOR 
	 * (no update and create)
	 */
	
	public Driver(Integer svnr, String firstname, String surname, String tel,
			Integer vehicle, Integer covers, Boolean male) {
		this.svnr = svnr;
		this.firstname = firstname;
		this.surname = surname;
		this.tel = tel;
		this.vehicle = vehicle;
		this.covers = covers;
		this.male = male;
	}
	
	/*
	 * MAKE NEW DRIVER CONSTRUCTOR 
	 */
	
	public Driver(Integer svnr) {
		this.svnr = svnr;
		this.firstname = "";
		this.surname = "";
		this.tel = "";
		this.vehicle = 0;
		this.covers = 0;
		this.male = true;
		this.update = new Timestamp(0);
		this.create = new Date(0);
	}
	
	/*
	 * Empty CONSTRUCTOR 
	 */
	
	public Driver() {
		//do nothing
	}
	
	/*
	 * clears all Data - empty Objekt afterwards, like when initialized
	 * (non-Javadoc)
	 * @see entities.Entity#clear()
	 */
	
	public void clear() {
		svnr = null;
		firstname = null;
		surname = null;
		tel = null;
		vehicle = null;
		covers = null;
		male = null;
		update = null;
		create = null;
	}
	
	/*
	 * toString
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String output = new String();
		
		if(surname != null)
			output += surname;
		
		if(firstname != null && !firstname.equals(""))
			output += ", "+firstname;
		
		if(svnr != null && svnr != 0)
			output += " (" + svnr.toString() + ")";
			
		return  output;
	}
	
	/*
	 * retrieves the primary key
	 */
	
	public Integer getId() {
		return svnr;
	}
	
	/*
	 *  GETTER AND SETTER 
	 */
	
	public Integer getSvnr() {
		return svnr;
	}
	public void setSvnr(Integer svnr) {
		this.svnr = svnr;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String fistname) {
		this.firstname = fistname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getVehicle() {
		return vehicle;
	}
	public void setVehicle(Integer vehicle) {
		this.vehicle = vehicle;
	}
	public Integer getCovers() {
		return covers;
	}
	public void setCovers(Integer covers) {
		this.covers = covers;
	}
	public Boolean getMale() {
		return male;
	}
	public void setMale(Boolean male) {
		this.male = male;
	}
	public Timestamp getUpdate() {
		return update;
	}
	public void setUpdate(Timestamp update) {
		this.update = update;
	}
	public Date getCreate() {
		return create;
	}
	public void setCreate(Date create) {
		this.create = create;
	}

}
