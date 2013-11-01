package db.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.apache.log4j.Logger;

import db.HSQLDbHandler;

import gui.ErrMsgGui;

public class GeneralManager{
	
	private static Logger logger = Logger.getLogger(GeneralManager.class);
	private Connection con = HSQLDbHandler.getConnection();
	private Statement stmt;
	private String query;
	private ResultSet rs;
	private ErrMsgGui errmsg;
	private String[] result;
	private Vector<String[]> rvector;
	
	public GeneralManager() {
		logger.debug("GeneralManager ->Constructor()");
	}
	
	private void prepareStatement() {

		stmt = null;
		query = null;
		rs = null;
		
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
	
	public Vector<String[]> getDamagedCarDriver() {
		rvector = new Vector<String[]>();
		
		prepareStatement();
		
		query = "SELECT svnr,firstname,surname,brand,type,plate " +
				"FROM Driver d LEFT JOIN Taxi t ON d.vehicle = t.id WHERE t.disabled = true;";
		
		logger.debug("General ->getDamagedCarDriver()");
		logger.debug(query);
		
		executeStatement();
		
		try {
			while(rs.next()){
				result= new String[6];
				result[0] = (String)rs.getObject(1).toString();
				result[1] = (String)rs.getObject(2);
				result[2] = (String)rs.getObject(3);
				result[3] = (String)rs.getObject(4);
				result[4] = (String)rs.getObject(5);
				result[5] = (String)rs.getObject(6);
				rvector.add(result);
			}
		} catch (SQLException e) {
			errmsg  = new ErrMsgGui("Es ist ein Fehler bei einer Datenbank-Aktion aufgetreten. Die letzte Aktion wurde womöglich nicht ausgeführt.");
		}
		
		return rvector;
	}
	
	public Vector<String[]> getCheapestCarDriver() {
		rvector = new Vector<String[]>();
		
		prepareStatement();
		
		query = "SELECT svnr,firstname,surname,brand,type,plate,consumption " +
				"FROM Driver d JOIN Taxi t ON d.vehicle = t.id " +
				"ORDER BY t.consumption ASC LIMIT 7;";
		
		logger.debug("General ->getCheapestCarDriver()");
		logger.debug(query);
		
		executeStatement();
		
		try {
			while(rs.next()){
				result= new String[7];
				result[0] = (String)rs.getObject(1).toString();
				result[1] = (String)rs.getObject(2);
				result[2] = (String)rs.getObject(3);
				result[3] = (String)rs.getObject(4);
				result[4] = (String)rs.getObject(5);
				result[5] = (String)rs.getObject(6);
				result[6] = (String)rs.getObject(7).toString();
				rvector.add(result);
			}
		} catch (SQLException e) {
			errmsg  = new ErrMsgGui("Es ist ein Fehler bei einer Datenbank-Aktion aufgetreten. Die letzte Aktion wurde womöglich nicht ausgeführt.");
		}
		
		return rvector;
	}
	
	public Vector<String[]> getDoubleCarDriver() {
		rvector = new Vector<String[]>();
		
		prepareStatement();
		
		query = "SELECT svnr,firstname,surname,brand,type,plate " +
				"FROM Driver d LEFT JOIN Taxi t ON d.vehicle = t.id " +
				"WHERE d.vehicle IN (SELECT DISTINCT vehicle FROM Driver d2 WHERE d2.svnr != d.svnr) " +
				"ORDER BY brand,type,plate ASC";
		
		logger.debug("General ->getDoubleCarDriver()");
		logger.debug(query);
		
		executeStatement();
		
		try {
			while(rs.next()){
				result= new String[6];
				result[0] = (String)rs.getObject(1).toString();
				result[1] = (String)rs.getObject(2);
				result[2] = (String)rs.getObject(3);
				result[3] = (String)rs.getObject(4);
				result[4] = (String)rs.getObject(5);
				result[5] = (String)rs.getObject(6);
				rvector.add(result);
			}
		} catch (SQLException e) {
			errmsg  = new ErrMsgGui("Es ist ein Fehler bei einer Datenbank-Aktion aufgetreten. Die letzte Aktion wurde womöglich nicht ausgeführt.");
		}
		
		return rvector;
	}
	
	public Vector<String[]> getDriverCovering(Integer id) {
		rvector = new Vector<String[]>();
		
		prepareStatement();
		
		query = "SELECT svnr,firstname,surname " +
				"FROM Driver WHERE covers = "+id;
		
		logger.debug("General ->getDriverCovering("+id+")");
		logger.debug(query);
		
		executeStatement();
		
		try {
			while(rs.next()){
				result= new String[3];
				result[0] = (String)rs.getObject(1).toString();
				result[1] = (String)rs.getObject(2);
				result[2] = (String)rs.getObject(3);
				rvector.add(result);
			}
		} catch (SQLException e) {
			errmsg  = new ErrMsgGui("Es ist ein Fehler bei einer Datenbank-Aktion aufgetreten. Die letzte Aktion wurde womöglich nicht ausgeführt.");
		}
		
		return rvector;
	}
	
	public Vector<String[]> getNoCoveredDriver() {
		rvector = new Vector<String[]>();
		
		prepareStatement();
		
		query = "SELECT svnr,firstname,surname " +
				"FROM Driver d " +
				"WHERE svnr NOT IN(SELECT covers FROM Driver d2 WHERE d.svnr != d2.svnr);"; 
		
		logger.debug("General ->getNoCoveredDriver()");
		logger.debug(query);
		
		executeStatement();
		
		try {
			while(rs.next()){
				result= new String[3];
				result[0] = (String)rs.getObject(1).toString();
				result[1] = (String)rs.getObject(2);
				result[2] = (String)rs.getObject(3);
				rvector.add(result);
			}
		} catch (SQLException e) {
			errmsg  = new ErrMsgGui("Es ist ein Fehler bei einer Datenbank-Aktion aufgetreten. Die letzte Aktion wurde womöglich nicht ausgeführt.");
		}
		
		return rvector;
	}
	
	public Vector<String[]> getUnusedCars() {
		rvector = new Vector<String[]>();
		
		prepareStatement();
		
		query = "SELECT id,brand,type,plate " +
				"FROM Taxi t " +
				"WHERE id NOT IN(SELECT vehicle FROM Driver);"; 
		
		logger.debug("General ->getUnusedCars()");
		logger.debug(query);
		
		executeStatement();
		
		try {
			while(rs.next()){
				result= new String[4];
				result[0] = (String)rs.getObject(1).toString();
				result[1] = (String)rs.getObject(2);
				result[2] = (String)rs.getObject(3);
				result[3] = (String)rs.getObject(4);
				rvector.add(result);
			}
		} catch (SQLException e) {
			errmsg  = new ErrMsgGui("Es ist ein Fehler bei einer Datenbank-Aktion aufgetreten. Die letzte Aktion wurde womöglich nicht ausgeführt.");
		}
		
		return rvector;
	}
	
	public void clearDB(){
		prepareStatement();
		
		query = getCreateQuery();
		
		logger.debug("GeneralManger -> clearDB()");
		logger.debug(query);
		
		executeStatement();
	}
	
	public void resetDB(){
		prepareStatement();
		
		query = getCreateQuery();
		query += getinsertQuery();
		query += getMinimalInsertQuery();
		
		logger.debug("GeneralManger -> clearDB()");
		logger.debug(query);
		
		executeStatement();
	}

	private String getCreateQuery() {
		String query = 
		"DROP TABLE Driver IF EXISTS CASCADE; " +
		"DROP TABLE Taxi IF EXISTS CASCADE; " +
		"CREATE TABLE Taxi( " +
			"id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 0, INCREMENT BY 1) PRIMARY KEY," +
			"brand VARCHAR (30) NOT NULL," +
			"type VARCHAR (30) NOT NULL," +
			"color VARCHAR (20) NOT NULL," +
			"plate VARCHAR (15) NOT NULL," +
			"seats INTEGER NOT NULL," +
			"consumption FLOAT NOT NULL," +
			"disabled BOOLEAN DEFAULT FALSE NOT NULL," +
			"update DATETIME NOT NULL," +
			"create DATE NOT NULL," +
			"CHECK ( color IN ('white','black','silver','red','yellow','orange','blue','green','violette','brown'))," +
			"CHECK ( brand IN ('Audi','Opel','VW','Mercedes','BMW','Mazda','Ford','Fiat','Toyota','Renault','Citroen','Porsche','Hummer','Suzuki','Honda','Aston Martin','Chevrolet'))," +
			"CHECK ( seats IN (3,4,5,6,7,8,9,10))" +
		"); " +
		"" +
		"CREATE TABLE Driver (" +
			"svnr INTEGER PRIMARY KEY," +
			"firstname VARCHAR (30) NOT NULL," +
			"surname VARCHAR (30) NOT NULL," +
			"tel VARCHAR (30) NULL," +
			"vehicle INTEGER NOT NULL," +
			"covers INTEGER NOT NULL," +
			"male BOOLEAN NOT NULL," +
			"update DATETIME NOT NULL," +
			"create DATE NOT NULL," +
			"FOREIGN KEY (vehicle) REFERENCES Taxi(id)," +
			"FOREIGN KEY (covers) REFERENCES Driver(svnr)" +
		"); ";
		return query;
	}
	
	private String getMinimalInsertQuery() {
		query  =
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Mazda','323','green','W 9854F',4,5.7,false,NOW(),NOW()); " +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1503210384,'Patrick','Maierhofer','+43 699 12635',0,1503210384,true,NOW(),NOW());";
		
		return query;
	}
	
	private String getinsertQuery() {
		String query = 
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Mercedes','SLR','silver','W 465G7',5,10.8,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Mercedes','SLR','black','W 3897R',5,10.8,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Mercedes','A-Klasse','yellow','W 9G474',4,8.3,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Mercedes','A-Klasse','green','W F8763',4,8.3,true,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Mercedes','A-Klasse','green','W 657D3',4,8.3,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Audi','TT','black','W A745D',3,12.5,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Audi','A4','red','W 8JT67',5,10.1,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Audi','A4','orange','W L687D',5,10.1,true,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Audi','A3','orange','W QF77D',3,7.8,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Opel','Zafira','silver','W FG876',6,8.3,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Opel','Zafira','black','W T687D',6,8.3,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Opel','Insignia','blue','W FG876',4,6.3,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Opel','Insignia','white','W ZZ546',4,6.3,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('BMW','M5','white','W I886G',5,7.9,true,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('BMW','M5','brown','W K886G',5,7.9,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('BMW','M3','violette','W JH776',4,8.9,true,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('BMW','M3','yellow','W JQ765',4,8.9,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('BMW','X4','black','W TT573',3,12.4,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Mazda','6','green','W LO998',5,9.4,true,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Mazda','6','silver','W JE998',5,9.4,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Mazda','5','black','W O5998',4,8.7,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Mazda','5','red','W 55678',4,8.7,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Ford','Focus','red','W O5998',6,6.7,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Ford','Mondeo','white','W CV457',4,8.4,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Fiat','Grande Punto5T','silver','W 86754',4,6.4,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Fiat','Croma','black','W 9872',6,8.9,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Porsche','911 GT','black','W 2347',6,13.3,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Hummer','H2','yellow','W 5674R',6,15.4,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Renault','Scenic','black','W GT4487',4,7.9,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Renault','Scenic','silver','W FHG67',4,7.9,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Honda','Civic','blue','W LK887',4,6.2,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Toyota','Prius','red','W HH123',5,3.9,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Toyota','Avensis','violette','W 54FG8',7,7.3,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Aston Martin','DB9','green','W 5DB9A8',5,12.1,false,NOW(),NOW());" +
			"INSERT INTO TAXI (brand, type,color, plate, seats, consumption, disabled, update, create) VALUES('Chevrolet','Camaro 09','yellow','W 88ZH2',3,13.8,false,NOW(),NOW());" +
			
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1874170785,'Peter','Berhman','+43 1234897',19,1874170785,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1675110373,'Frank','Mayer','+43 1234897',0,1874170785,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1276070265,'Patrick','Müller','+43 1234897',2,1874170785,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1877130769,'Florian','Pannte','+43 1234897',3,1874170785,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1878170885,'Franz','Tusgel','+43 1234897',18,1874170785,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1179170885,'Alexander','Reman','+43 1234897',4,1877130769,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1880170777,'Benno','Zuckermann','+43 1234897',17,1874170785,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1481030775,'Eugen','Grünabum','+43 1234897',13,1276070265,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1882200883,'Simon','Leisim','+43 1234897',28,1874170785,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1583191085,'Rene','Zange','+43 1234897',16,1276070265,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1084070985,'Daniel','Lustlos','+43 1234897',15,1878170885,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1885171069,'Detlef','Wehrding','+43 1234897',11,1276070265,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1886170785,'Bernhard','Hippotert','+43 1234897',17,1874170785,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1687171123,'Andreas','Lungenschwan','+43 1234897',20,1084070985,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1888070745,'Clemens','Cumcu','+43 1234897',0,1276070265,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1889190285,'Markus','Straussen','+43 1234897',12,1276070265,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1690170756,'Franz','Erdmaenchen','+43 1234897',14,1687171123,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1891171266,'Quinibert','Rasputin','+43 1234897',9,1084070985,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1292180781,'Susanne','Bohne','+43 1234897',4,1276070265,false,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1393070186,'Petra','Erbse','+43 1234897',13,1874170785,false,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1465190788,'Lisa','Zitrone','+43 1234897',25,1084070985,false,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1688100182,'Traude','Melone','+43 1234897',13,1889190285,false,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1194110784,'Jan','Beckinger','+43 1234897',29,1084070985,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1123170286,'Julian','Karlsen','+43 1234897',28,1687171123,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1745190781,'Günther','Zitrone','+43 1234897',25,1688100182,true,NOW(),NOW());" +
			"INSERT INTO DRIVER (svnr,firstname, surname, tel, vehicle, covers, male, update, create) VALUES(1252110382,'Harald','Jukic','+43 1234897',22,1583191085,true,NOW(),NOW());";
			
		return query;
	}

}