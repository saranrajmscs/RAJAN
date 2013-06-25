/**
 * 
 */
package com.sep;

import java.sql.*;
import com.google.appengine.api.rdbms.AppEngineDriver;

/**
 * @author Saravanarajan
 * This class is used to create Database Connections
 */
public class SEP_DB_Manager {
	static Connection c = null;
	
	public SEP_DB_Manager(){
		
	    try {
	    	
	      DriverManager.registerDriver(new AppEngineDriver());
	      c = DriverManager.getConnection("jdbc:google:rdbms://soceveplnr:dbsep/sepdb");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	      }
	}
	
	public static Connection getConnection() 
	{
		return c;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SEP_DB_Manager();
		System.out.println(SEP_DB_Manager.getConnection());

	}

}
