package com.soda.phonebook.common;

public class CanNotUpdateContact extends RuntimeException{

	private static final long serialVersionUID = 1211377727628790293L;

	public CanNotUpdateContact(){
		super();
	}
	public CanNotUpdateContact(String string){
		super(string);
	}
}
