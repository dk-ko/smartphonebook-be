package com.sode.phonebook.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sode.phonebook.domain.Contact;
import com.sode.phonebook.repository.ContactRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class WebRestController {
	
	private ContactRepository contactRepository;
	
	@GetMapping("/hello")
	public String hello() {
		return "HelloWorld Ver.3";
	}
	
	@GetMapping("/test")
	public Contact test() {
		List<Contact> contactList = contactRepository.findAll();
		return contactList.get(0);
	}
	
}
