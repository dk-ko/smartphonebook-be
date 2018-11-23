package com.soda.phonebook;

import lombok.Getter;

enum TYPE {LOCAL, TEST};

@Getter
public class DB {
	
	private String DRIVER;
	private String URL;
	private String USER;
	private String PASSWORD;
	
	private DB(TYPE t){
		if(t == TYPE.LOCAL) {
			this.DRIVER = "org.mariadb.jdbc.Driver";
			this.URL = "jdbc:mariadb://localhost:3306/contact";
			this.USER = "root";
			this.PASSWORD = "test123";
		} else if(t == TYPE.TEST) {
			this.DRIVER = "org.h2.Driver";
//			this.URL = "jdbc:h2:mem://localhost/~/contact"; // Embedded DB
			this.URL = "jdbc:h2:tcp://localhost/~/contact"; // External DB
			this.USER = "sa";
			this.PASSWORD = "";
		} 
	}
	
	public static DB getInstance(TYPE type){
		final DB INSTANCE = new DB(type);
		return INSTANCE;
	}

}
