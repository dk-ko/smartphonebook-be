package com.sode.phonebook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sode.phonebook.domain.Contact;
import com.sode.phonebook.domain.User;

@RestController
public class WebRestController {
	
	@GetMapping("/hello")
	public String hello() {
		return "HelloWorld";
	}
	
	@GetMapping("/test")
	public Contact test() {
		Contact contact = new Contact();
		
		contact.setUser(new User());
		contact.setName("고다경");
		contact.setMemo("memo");
		contact.setBirth("1993-07-30");
		contact.setPhoto(null);
		contact.setFavorite(0);
		contact.setSite(null);
		return contact;
	}
}
