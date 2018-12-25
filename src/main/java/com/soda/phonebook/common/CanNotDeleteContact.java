package com.soda.phonebook.common;

public class CanNotDeleteContact extends RuntimeException{
	
	private static final long serialVersionUID = 8534804818032092906L;
	public CanNotDeleteContact(){
		super();
	}
	public CanNotDeleteContact(String string){
		super(string);
	}
	
}
