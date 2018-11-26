package com.soda.phonebook.controller;

import java.util.List;
//import java.util.Optional;

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
import com.soda.phonebook.dto.req.ContactSaveRequestDto;
import com.soda.phonebook.service.ContactService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RequestMapping("/api/contacts")
@RestController
public class ContactController {
	
	private ContactService contactService;
	
	@GetMapping("/")
	@ResponseStatus(value = HttpStatus.OK)
	public List<ContactListReadResponseDto> getAllContacts() {
		return contactService.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ContactResponseDto getContacts(@PathVariable final Long id) {
		log.info("* before service");
		ContactResponseDto dto = contactService.findById(id);
		log.info("* after service");
		return dto;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteContacts(@PathVariable final Long id) {
		contactService.delete(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(value = HttpStatus.CREATED)
	public boolean createContacts(@RequestBody final ContactSaveRequestDto dto) {
		return contactService.create(dto);
	}
	
//	@PutMapping("/{id}")
//	@ResponseStatus(value = HttpStatus.OK)
//	public ContactResponseDto editContacts(@PathVariable final Long id, ) {
//		return new ContactResponseDto();
//	}
}
