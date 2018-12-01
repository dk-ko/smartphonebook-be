package com.soda.phonebook;

enum TYPE {LOCAL, TEST};

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
			this.URL = "jdbc:h2:mem://localhost/~/contact"; // Embedded DB
//			this.URL = "jdbc:h2:tcp://localhost/~/contact"; // External DB
			this.USER = "sa";
			this.PASSWORD = "";
		} 
	}
	
	public static DB getInstance(TYPE type){
		final DB INSTANCE = new DB(type);
		return INSTANCE;
	}
	
	public String getDRIVER() {
		return this.DRIVER;
	}
	
	public String getURL() {
		return this.URL;
	}
	
	public String getUSER() {
		return this.USER;
	}
	
	public String getPASSWORD() {
		return this.PASSWORD;
	}

}
