package com.soda.phonebook.common;

public class CanNotFindCategory extends RuntimeException{

	private static final long serialVersionUID = -5663761209233840957L;

	public CanNotFindCategory(){
		super();
	}
	public CanNotFindCategory(String string){
		super(string);
	}
	
}
