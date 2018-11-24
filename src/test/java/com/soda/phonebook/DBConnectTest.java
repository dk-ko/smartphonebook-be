package com.soda.phonebook;

import com.soda.phonebook.DB;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
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
