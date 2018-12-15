package com.soda.phonebook.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.soda.phonebook.dto.res.ContactListReadResponseDto;
import com.soda.phonebook.dto.res.ContactResponseDto;
import com.soda.phonebook.dto.res.TagResponseDto;
import com.google.common.net.HttpHeaders;
import com.soda.phonebook.domain.VO.Mark;
import com.soda.phonebook.dto.req.ContactSaveRequestDto;
import com.soda.phonebook.dto.req.ContactUpdateRequestDto;
import com.soda.phonebook.service.ContactService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/api/contacts")
//@RestController
@Controller
public class ContactController {
	
	private final ContactService contactService;
	
	@GetMapping(path = {"/", ""})
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<ContactListReadResponseDto> getAllContacts() {
		return contactService.getAllContacts();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public ContactResponseDto getContacts(@PathVariable final Long id) {
		log.info("* before service");
		ContactResponseDto dto = contactService.getContacts(id);
		log.info("* after service");
//		if(dto.getPhoto() != Mark.N) dto.updatePhotoPath(makeDownloadUri(dto.getId()));
		if(dto.getPhoto() != null) dto.updatePhotoPath(makeDownloadUri(dto.getId()));
		return dto;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteContacts(@PathVariable final Long id) {
		contactService.delete(id);
	}
	
//	@PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(path = {"/", ""})
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CREATED)
//	public boolean createContacts(@RequestBody final ContactSaveRequestDto dto) {
	public String createContacts(@ModelAttribute final ContactSaveRequestDto dto) throws IOException {
		log.info("* post controller");
		Long savedContactId = contactService.create(dto);
		log.info("* create 이후");
		
		if(dto.getPhoto() == null) return "saved";
		
		String fileDownloadUri = makeDownloadUri(savedContactId);
		log.info("* download uri 생성");
//		return contactService.create(dto);
		return fileDownloadUri;
	}
	
//	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PutMapping(path = "/{id}")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
//	public boolean editContacts(@PathVariable final Long id, @RequestBody final ContactUpdateRequestDto dto) {
	public String editContacts(@PathVariable final Long id, @ModelAttribute final ContactUpdateRequestDto dto) throws IOException {
		log.info("* put controller");
		Long savedContactId = contactService.update(id, dto);
		log.info("* update 이후");
		
		if(dto.getPhoto() == null) return "saved";
		
		String fileDownloadUri = makeDownloadUri(savedContactId);
		log.info("* download uri 생성");
		return fileDownloadUri != null ? fileDownloadUri : "saved";
//		return contactService.update(id, dto);
	}
	
	// 사진 불러오기 
	@GetMapping("/{id}/downloadFile")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long id){
		ContactResponseDto dto = contactService.getContacts(id);
		byte[] file = dto.getPhoto();
		
		if(file == null) return ResponseEntity.noContent().build();
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dto.getId() + "\"")
				.body(new ByteArrayResource(file));
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
	
	private String makeDownloadUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/contacts/")
				.path(id.toString())
				.path("/downloadFile")
				.toUriString();
	}
}
