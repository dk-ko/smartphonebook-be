package com.soda.phonebook;

import com.soda.phonebook.DB;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;


public class DBConnectTest {
	
	// TYPE : LOCAL, TEST 
	DB db = DB.getInstance(TYPE.TEST);
	
	
	@Test
	public void testConnection() throws Exception{
		Class.forName(db.getDRIVER());
		try(Connection con = DriverManager.
				getConnection(db.getURL(), db.getUSER(), db.getPASSWORD())){
			System.out.println(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
