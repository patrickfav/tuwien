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

import entities.Taxi;
import gui.ErrMsgGui;

public class TaxiManager implements InterfaceTaxiManager {
	
	private static Logger logger = Logger.getLogger(TaxiManager.class);
	private Connection con = HSQLDbHandler.getConnection();
	private Statement stmt = null;
	private String query = null;
	private ResultSet rs = null;
	private ErrMsgGui errmsg;
	private Taxi taxi;
	
	public TaxiManager() {
		logger.debug("TaxiManager ->Constructor()");
	}
	
	private void prepareStatement() {
		stmt = null;
		query = null;
		rs = null;
		taxi = new Taxi();
		
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
				taxi.setId((Integer)rs.getObject(1));
				taxi.setBrand((String)rs.getObject(2));
				taxi.setType((String)rs.getObject(3));
				taxi.setColor((String)rs.getObject(4));
				taxi.setPlate((String)rs.getObject(5));
				taxi.setSeats((Integer)rs.getObject(6));
				taxi.setConsumption((Double)rs.getObject(7));
				taxi.setDisabled((Boolean)rs.getObject(8));
				taxi.setUpdate((Timestamp)rs.getObject(9));
				taxi.setCreate((Date)rs.getObject(10));
			}
		} catch (SQLException e) {
			errmsg  = new ErrMsgGui("Es ist ein Fehler bei einer Datenbank-Aktion aufgetreten. Die letzte Aktion wurde womöglich nicht ausgeführt.");
			logger.error("SQL Error: "+e.getMessage());
		}
	}
	
	/**
	 * SAVE
	 */
	public void save(Taxi t) {		
		prepareStatement();
	
		query = "UPDATE TAXI SET brand='" + t.getBrand() + "', type='" + 
		         t.getType()+ "', color='" + t.getColor() + "', plate='" + t.getPlate() +"', seats="
		         + t.getSeats() +", consumption=" + t.getConsumption() +", disabled='" + t.getDisabled() + "', update=NOW() WHERE id= '" + t.getId() + "';";		
		
		logger.debug("Taxi ->save()");
		logger.debug(query);
		
		executeStatement();	
	}
	
	/**
	 * LOAD
	 */
	public Taxi load(Integer id) {
		
		prepareStatement();
		
		query = "SELECT * FROM TAXI WHERE id="+id;
		
		logger.debug("Taxi ->load("+id+")");
		logger.debug(query);
		
		executeStatement();	
		
		assignResult();
		
		return taxi;
	}
	
	/**
	 * getLastInserted
	 * returns the last inserted db entry
	 */
	public Taxi getLastInserted() {
		
		prepareStatement();
		
		query = "SELECT * FROM TAXI ORDER BY update DESC LIMIT 1";
		
		logger.debug("Taxi ->getLastInserted()");
		logger.debug(query);
		
		executeStatement();	
		
		assignResult();
		
		return taxi;
	}
	
	/**
	 * SEARCH
	 */
	public Vector<Taxi> search(Taxi t) {		
		Vector<Taxi> foundTaxis = new Vector<Taxi>();
				
		prepareStatement();
		
		query = "SELECT * FROM TAXI WHERE 1=1 ";
		
		if(t.getId() != null) query += "AND id = " + t.getId() + " ";
		if(t.getBrand() != null) query += "AND brand like '%" + t.getBrand() + "%' ";
		if(t.getType() != null) query += "AND type like '%" + t.getType() + "%' ";
		if(t.getPlate() != null) query += "AND plate like '%" + t.getPlate() + "%' ";
		if(t.getSeats() != null) query += "AND seats = " + t.getSeats() + " ";
		if(t.getColor() != null) query += "AND color ='" + t.getColor() + "' ";
		if(t.getConsumption() != null) query += "AND consumption =" + t.getConsumption() + " ";
		if(t.getDisabled() != null) query += "AND disabled =" + t.getDisabled() + " ";
		
		query += " ORDER BY brand,update DESC;";
		
		logger.debug("Taxi ->search()");
		logger.debug(query);
		
		executeStatement();	
	
		try {
			while(rs.next()){
				Taxi taxi = new Taxi();
				taxi.setId((Integer)rs.getObject(1));
				taxi.setBrand((String)rs.getObject(2));
				taxi.setType((String)rs.getObject(3));
				taxi.setColor((String)rs.getObject(4));
				taxi.setPlate((String)rs.getObject(5));
				taxi.setSeats((Integer)rs.getObject(6));
				taxi.setConsumption((Double)rs.getObject(7));
				taxi.setDisabled((Boolean)rs.getObject(8));
				taxi.setUpdate((Timestamp)rs.getObject(9));
				taxi.setCreate((Date)rs.getObject(10));
				foundTaxis.add(taxi);
			}
		} catch (SQLException e) {
			errmsg  = new ErrMsgGui("Es ist ein Fehler bei einer Datenbank-Aktion aufgetreten. Die letzte Aktion wurde womöglich nicht ausgeführt.");
			logger.error("SQL Error: "+e.getMessage());
		}
		return foundTaxis;
	}


	/**
	 * CREATE
	 */
	public void create(Taxi t) {
		prepareStatement();
	
		query = "INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) " +
				" VALUES('" + t.getBrand() + "','" + t.getType()+ "','" + t.getColor() + "','" + t.getPlate() + "'," 
				+ t.getSeats() +","+ t.getConsumption() +",'" + t.getDisabled() +"',NOW(),NOW());";		
		
		logger.debug("Taxi ->create()");
		logger.debug(query);
		
		executeStatement();
	}


	/**
	 * DELETE
	 */
	public void delete(Taxi t) {
		prepareStatement();
		
		//keeps the constraints - if taxi is driven by a driver, another vehicle is choosen
		query = "UPDATE DRIVER SET vehicle=(SELECT id FROM TAXI WHERE id != " + t.getId() + " LIMIT 1) WHERE vehicle=" + t.getId() + "; ";
		query += "DELETE FROM TAXI WHERE id=" + t.getId() + "; ";
		
		logger.debug("Taxi ->delete()");
		logger.debug(query);
		
		try {
			stmt.execute(query);
		} catch (SQLException e) {
			errmsg  = new ErrMsgGui("Es ist ein Fehler bei einer Datenbank-Aktion aufgetreten. Die letzte Aktion wurde womöglich nicht ausgeführt.");
			logger.error("SQL Error: "+e.getMessage());
		}
		
	}
	
	/**
	 * EXISTS
	 */
	public boolean exists(Integer id) {
		Taxi t = load(id);
		logger.debug("Taxi ->exists("+id+")");
		if(t.getId() != null)
			return true;
		
		return false;
	}
}