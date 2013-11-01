package db;

import gui.ErrMsgGui;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.management.Query;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

public class HSQLDbHandler {

	private static Connection con = null;	
	private static Logger logger = Logger.getLogger(HSQLDbHandler.class);
	private static ErrMsgGui errmsg;
	
	  private static void openConnection(){		
			try {
				Class.forName("org.hsqldb.jdbcDriver"); 
				con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/cabdriver", "sa", "");
			} catch (Exception e) {
				errmsg  = new ErrMsgGui("Es konnte keine Verbindung mit der Datenbank hergestellt werden. Das Programm wird geschlossen.");
				System.exit(0);
				logger.error("SQL Error: "+e.getMessage());
	        }
			logger.debug("HSQL has successfully connected");
	   }
		
		public static Connection getConnection(){
			try {
				if(con==null || con.isClosed()) openConnection();
			} catch (SQLException e) {
				errmsg  = new ErrMsgGui("Es konnte keine Verbindung mit der Datenbank hergestellt werden. Das Programm wird geschlossen.");
				System.exit(0);
				logger.error("SQL Error: "+e.getMessage());
	        }
			logger.debug("retrieve HSQL connection");
			return con;
		}
		
		public static void closeConnection(){
			try {
				if(con!=null) con.close();
			} catch (SQLException e) {
				logger.error("SQL Error: "+e.getMessage());
			}
			logger.debug("HSQL connection closed");
	}	

	
	
}

