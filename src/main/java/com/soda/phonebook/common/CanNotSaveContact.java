package com.soda.phonebook.common;

public class CanNotSaveContact extends RuntimeException{

	private static final long serialVersionUID = 1211377727628790293L;

	public CanNotSaveContact(){
		super();
	}
	public CanNotSaveContact(String string){
		super(string);
	}
}
