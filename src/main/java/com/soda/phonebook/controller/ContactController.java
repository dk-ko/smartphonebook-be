package com.soda.phonebook.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.soda.phonebook.dto.res.ContactListReadResponseDto;
import com.soda.phonebook.dto.res.ContactResponseDto;
import com.soda.phonebook.dto.res.TagResponseDto;
import com.soda.phonebook.dto.req.ContactSaveRequestDto;
import com.soda.phonebook.dto.req.ContactUpdateRequestDto;
import com.soda.phonebook.service.ContactService;
//import com.soda.phonebook.service.DBFileStorageService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/api/contacts")
@RestController
public class ContactController {
	
	private final ContactService contactService;
	
	@GetMapping("/")
	@ResponseStatus(value = HttpStatus.OK)
	public List<ContactListReadResponseDto> getAllContacts() {
		return contactService.getAllContacts();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ContactResponseDto getContacts(@PathVariable final Long id) {
		log.info("* before service");
		ContactResponseDto dto = contactService.getContacts(id);
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
	
	@PutMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public boolean editContacts(@PathVariable final Long id, @RequestBody final ContactUpdateRequestDto dto) {
		return contactService.update(id, dto);
	}
	
	// Contact에 Tag 추가, 삭제 
	@PostMapping("/{id}/tags/{tagId}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public boolean addTagToContact(@PathVariable final Long id, @PathVariable final Long tagId) {
		return contactService.addTagToContact(id, tagId);
	}
	
	@DeleteMapping("/{id}/tags/{tagId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public boolean deleteTagToContact(@PathVariable final Long id, @PathVariable final Long tagId) {
		return contactService.deleteTagToContact(id, tagId);
	}
	
	@GetMapping("{id}/tags")
	@ResponseStatus(value = HttpStatus.OK)
	public List<TagResponseDto> getAllTagsByContact(@PathVariable final Long id){
		return contactService.getAllTagsByContact(id);
	}
	
	// 즐겨찾기 
	@PostMapping("/{id}/favorites")
	@ResponseStatus(value = HttpStatus.CREATED)
	public boolean addToFavorites(@PathVariable final Long id) {
		return contactService.addToFavorites(id);
	}
	
	@DeleteMapping("/{id}/favorites")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public boolean deleteToFavorites(@PathVariable final Long id) {
		return contactService.deleteToFavorites(id);
	}
	/*
	// 사진 업로드 / 삭제
	@PostMapping("{id}/photo")
	@ResponseStatus(value = HttpStatus.OK)
	public boolean uploadPhoto(@PathVariable final Long id, @RequestParam("file") MultipartFile file) {
		DBFile dbFile = DBFileStorageService.storeFile(file);
		
		String
	}
	
	@DeleteMapping("{id}/photo")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public boolean deletePhoto() {
		
	}*/
}
