package aufgabe2;

import java.io.Serializable;
import java.util.Date;

/**
 * AdminRecordsManagement reuses the code of RecordsManagement and adds the verification that only
 * an logged in Admin can use special functions.
 * 
 */
public class AdminRecordsManagement extends RecordsManagement implements Serializable{
	transient private BackupThread backT = null;
	private ClassManager cm;
	private static final long serialVersionUID = -5693590669828776550L;
	private Authorisation login;
	private Units units_management;
	
	/**
	 * CONSTRUCTOR
	 * @param units_management - Unit Management
	 * @param login - Instance of the Authorisation Class
	 */
	public AdminRecordsManagement(Units units_management, Authorisation login) {
		super(units_management);		
		this.login = login;
		this.units_management = units_management;
	}
	
	/**
	 * doLogin
	 * 
	 * Expects username and password and returns a validation-hash if the transfered credentials are valid
	 * @param user
	 * @param password
	 * @return validation-hash
	 */
	public String doLogin(String user, String pwd){
		return login.doLogin(user, pwd);
	}
	
	/**
	 * checkLogin
	 * 
	 * checks if the handed over authorisation is valid
	 * @param ticket - hash of the admin
	 * @return if the hash is valid
	 */
	public boolean checkLogin(String ticket){
		return login.checkLogin(ticket);
	}
	
	/**
	 * doLogout
	 * 
	 * loggs an user out
	 * @param ticket - validation-hash
	 * @return if the check out was successful
	 */
	public boolean doLogout(String ticket){
		return login.doLogout(ticket);
	}
	
	/**
	 * getUnitManagement
	 * 
	 * 
	 * @return the management of the units
	 */
	public Units getUnitManagement(){
		return units_management;
	}
	
	/**
	 * startProgram
	 * 
	 * creates and starts the backupthread and makes an instance of the ClassManager
	 */
	public void startProgram(){
		if (backT == null){
			backT = new BackupThread("AdminRecordsManagement", this);
		}
		backT.start();
		cm = new ClassManager();	
	}
	
	/**
	 * stopProgram
	 * 
	 * stops and interrupts(otherwhise the backupthread would run additional 5 Minutes till the prozess terminates) the backupthread
	 * and starts the final backup of the data
	 */
	public void stopProgram(){
		backT.stop();
		backT.interrupt();
		cm.addObjectToFile("AdminRecordsManagement", this);
	}
	
	/*
	 * methodes from recordsmanagment extended with the loginverification
	 */
	public String addNewRecordToFolder(Date timestamp, String station_name, float value, String param, String device, String ticket) {
		if(login.checkLogin(ticket)) return super.addNewRecordToFolder(timestamp, station_name, value, param, device);
		return null;
	}

	public boolean addParameter(String name, float max_value, float min_value,
			float threshold, String sym, String ticket) {
		if(login.checkLogin(ticket))
		return super.addParameter(name, max_value, min_value, threshold, sym);
		return false;
	}

	public String addRecordFolder(Date timestamp, Station station, String ticket) {
		if(login.checkLogin(ticket))
		return super.addRecordFolder(timestamp, station);
		return null;
	}

	public boolean addStation(String name, String password, int id, String ticket) {
		if(login.checkLogin(ticket))
		return super.addStation(name, password, id);
		return false;
	}

	public boolean deleteParameter(String name, String ticket) {
		if(login.checkLogin(ticket))
		return super.deleteParameter(name);
		return false;
	}

	public boolean deleteStation(String name, String ticket) {
		if(login.checkLogin(ticket))
		return super.deleteStation(name);
		return false;
	}

	public String getBaseUnitSymbolByParamter(String name, String ticket) {
		if(login.checkLogin(ticket))
		return super.getBaseUnitSymbolByParamter(name);
		return null;
	}

	public Parameter getParameter(String name, String ticket) {
		if(login.checkLogin(ticket))
		return super.getParameterByName(name);
		return null;
	}

	public RecordFolder getRecordFolder(Date timestamp, String station_name, String ticket) {
		if(login.checkLogin(ticket))
		return super.getRecordFolder(timestamp, station_name);
		return null;
	}

	public Station getStation(String name, String ticket) {
		if(login.checkLogin(ticket))
		return super.getStation(name);
		return null;
	}

	public String StationtoString(String station_name, String ticket) {
		if(login.checkLogin(ticket))
		return super.StationToString(station_name);
		return null;
	}
	
	
	
}
