package com.soda.phonebook.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.soda.phonebook.dto.res.ContactListReadResponseDto;
import com.soda.phonebook.dto.res.ContactResponseDto;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.dto.req.ContactSaveRequestDto;
import com.soda.phonebook.service.ContactService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/api/contacts")
@RestController
public class ContactController {
	
	private ContactService contactService;
	
	@GetMapping("/")
	@ResponseStatus(value = HttpStatus.OK)
	public List<ContactListReadResponseDto> getAllContacts() {
		List<Contact> findList = contactService.findAll();
		List<ContactListReadResponseDto> dtoList = new ArrayList<>();
		for(int i = 0; i < findList.size(); i++) 
			dtoList.add(new ContactListReadResponseDto(findList.get(i)));
		return dtoList;
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ContactResponseDto getContacts(@PathVariable final Long id) {
		return new ContactResponseDto(contactService.findById(id));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteContacts(@PathVariable final Long id) {
		contactService.delete(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ContactResponseDto createContacts(@RequestBody final ContactSaveRequestDto dto) {
		return new ContactResponseDto(contactService.create(dto));
	}
	
//	@PutMapping("/{id}")
//	@ResponseStatus(value = HttpStatus.OK)
//	public ContactResponseDto editContacts(@PathVariable final Long id, ) {
//		return new ContactResponseDto();
//	}
}
