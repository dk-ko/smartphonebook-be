package com.soda.phonebook.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AuthenticationException extends RuntimeException{
	
	private static final long serialVersionUID = 2606492291728805614L;
	
	public AuthenticationException(){
		super();
	}
	public AuthenticationException(String string){
		super(string);
	}
	
}
