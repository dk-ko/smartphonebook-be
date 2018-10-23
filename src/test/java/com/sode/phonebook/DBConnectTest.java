package com.sode.phonebook;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class DBConnectTest {
	// local 
	private static final String DRIVER = "org.mariadb.jdbc.Driver";
	private static final String URL = "jdbc:mariadb://localhost:3306/contact-test";
	private static final String USER = "root";
	private static final String PASSWORD = "test123";
	
	// server 
//	private static final String DRIVER = "org.mariadb.jdbc.Driver";
//	private static final String URL = 
//	private static final String USER = 
//	private static final String PASSWORD = 
	
	@Test
	public void testConnection() throws Exception {
		Class.forName(DRIVER);
		try(Connection con = DriverManager.getConnection(URL, USER, PASSWORD)){
			System.out.println(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
