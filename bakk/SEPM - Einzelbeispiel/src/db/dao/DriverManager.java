package db.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

import org.apache.log4j.Logger;

import db.HSQLDbHandler;

import entities.Driver;
import gui.ErrMsgGui;

public class DriverManager implements InterfaceDriverManager {
	
	private static Logger logger = Logger.getLogger(DriverManager.class);
	private Connection con = HSQLDbHandler.getConnection();
	private Statement stmt;
	private String query;
	private ResultSet rs;
	private ErrMsgGui errmsg;
	private Driver driver;
	
	public DriverManager() {
		logger.debug("DriverManager ->Constructor()");
	}
	
	private void prepareStatement() {

		stmt = null;
		query = null;
		rs = null;
		driver = new Driver();
		
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			errmsg  = new ErrMsgGui("Es ist ein Fehler bei einer Datenbank-Aktion aufgetreten. Die letzte Aktion wurde womöglich nicht ausgeführt.");
			logger.error("SQL Error: "+e.getMessage());
		}
	}
	private void executeStatement() {
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			errmsg  = new ErrMsgGui("Es ist ein Fehler bei einer Datenbank-Aktion aufgetreten. Die letzte Aktion wurde womöglich nicht ausgeführt.");
			logger.error("SQL Error: "+e.getMessage());
		}
	}
	private void assignResult() {
		try {
			while(rs.next()){
				driver.setSvnr((Integer)rs.getObject(1));
				driver.setFirstname((String)rs.getObject(2));
				driver.setSurname((String)rs.getObject(3));
				driver.setTel((String)rs.getObject(4));
				driver.setVehicle((Integer)rs.getObject(5));
				driver.setCovers((Integer)rs.getObject(6));
				driver.setMale((Boolean)rs.getObject(7));
				driver.setUpdate((Timestamp)rs.getObject(8));
				driver.setCreate((Date)rs.getObject(9));
			}
		} catch (SQLException e) {
			errmsg  = new ErrMsgGui("Es ist ein Fehler bei einer Datenbank-Aktion aufgetreten. Die letzte Aktion wurde womöglich nicht ausgeführt.");
			logger.error("SQL Error: "+e.getMessage());
		}
	}
	
	public void save(Driver d) {
		prepareStatement();
	
		query = "UPDATE DRIVER SET firstname='" + d.getFirstname() + "', surname='" + 
		         d.getSurname()+ "', tel='" + d.getTel() + "', vehicle=" + d.getVehicle() + ", covers=" + d.getCovers() +", male='" 
		         + d.getMale() +"', update= NOW() WHERE svnr= '" + d.getSvnr() + "';";		
		
		logger.debug("Driver ->save()");
		logger.debug(query);
		
		executeStatement();
			
	}
	
	@Override
	public Driver load(Integer id) {
		
		prepareStatement();
		
		query = "SELECT * FROM Driver WHERE svnr="+id;
		
		logger.debug("Driver ->load("+ id +")");
		logger.debug(query);
		
		executeStatement();
		
		assignResult();
		
		return driver;
	}
	
	public Driver getLastInserted() {
		
		prepareStatement();
		
		query = "SELECT * FROM DRIVER ORDER BY update DESC LIMIT 1";
		
		logger.debug("Driver ->getLastInserted()");
		logger.debug(query);
		
		executeStatement();
		
		assignResult();
		
		return driver;
	}
	
	public Vector<Driver> search(Driver d) {
		
		Vector<Driver> foundDrivers = new Vector<Driver>();
		
		prepareStatement();
	
		// SELECT * FROM BUCH WHERE 1=1 AND ...
		
		query = "SELECT * FROM Driver WHERE 1=1 ";
		
		if(d.getSvnr() != null) query += "AND svnr = '" + d.getSvnr() + "' ";
		if(d.getFirstname() != null) query += "AND firstname like '%" + d.getFirstname() + "%' ";
		if(d.getSurname() != null) query += "AND surname like '%" + d.getSurname() + "%' ";
		if(d.getVehicle() != null) query += "AND vehicle =" + d.getVehicle() + " ";
		if(d.getCovers() != null) query += "AND covers =" + d.getCovers() + " ";
		if(d.getMale() != null) query += "AND male =" + d.getMale() + " ";
		
		query += " ORDER BY surname, firstname ASC;";
		
		logger.debug("Driver ->search()");
		logger.debug(query);
		
		executeStatement();

		try {
			while(rs.next()){
				Driver driver = new Driver();
				driver.setSvnr((Integer)rs.getObject(1));
				driver.setFirstname((String)rs.getObject(2));
				driver.setSurname((String)rs.getObject(3));
				driver.setTel((String)rs.getObject(4));
				driver.setVehicle((Integer)rs.getObject(5));
				driver.setCovers((Integer)rs.getObject(6));
				driver.setMale((Boolean)rs.getObject(7));
				driver.setUpdate((Timestamp)rs.getObject(8));
				driver.setCreate((Date)rs.getObject(9));
				foundDrivers.add(driver);
			}
		} catch (SQLException e) {
			errmsg  = new ErrMsgGui("Es ist ein Fehler bei einer Datenbank-Aktion aufgetreten. Die letzte Aktion wurde womöglich nicht ausgeführt.");
			logger.error("SQL Error: "+e.getMessage());
		}
			
		return foundDrivers;
			
	}


	@Override
	public void create(Driver d) {
		prepareStatement();
		
		//if its the first insert
		if(d.getCovers() == null || d.getCovers().equals(""))
			d.setCovers(d.getSvnr());
		
		query = "INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create)" +
				"VALUES('" + d.getSvnr() + "','" + d.getFirstname() + "','" + 
		         d.getSurname()+ "','" + d.getTel() + "'," + d.getVehicle() +"," 
		         + d.getCovers() + ",'" + d.getMale() + "',NOW(),NOW());";		
		
		logger.debug("Driver ->create()");
		logger.debug(query);
		
		executeStatement();
		
	}


	@Override
	public void delete(Driver d) {
		prepareStatement();
		
		//keeps the constraints - if a driver is deleted all covers will be updated and covers = self
		query = "UPDATE DRIVER SET covers=DRIVER.svnr WHERE covers=" + d.getSvnr() + ";";
		query += "DELETE FROM DRIVER WHERE svnr=" + d.getSvnr() + ";";
		
		logger.debug("Driver ->delete()");
		logger.debug(query);
		
		executeStatement();
		
	}
	
	/**
	 * EXISTS
	 */
	public boolean exists(Integer id) {
		Driver d = load(id);
		
		logger.debug("Driver ->exists()");
		
		if(d.getId() != null)
			return true;
		
		return false;
	}
}
