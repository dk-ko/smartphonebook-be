package com.soda.phonebook.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.soda.phonebook.dto.res.ContactListReadResponseDto;
import com.soda.phonebook.dto.res.ContactResponseDto;
import com.soda.phonebook.dto.res.TagResponseDto;
import com.google.common.net.HttpHeaders;
import com.soda.phonebook.common.HttpSessionUtils;
import com.soda.phonebook.dto.req.ContactSaveRequestDto;
import com.soda.phonebook.dto.req.ContactUpdateRequestDto;
import com.soda.phonebook.service.ContactService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/api/contacts")
@RestController
public class ContactController {
	
	private final ContactService contactService;
	
	@GetMapping(path = {"/", ""})
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<ContactListReadResponseDto> getAllContacts(HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return null;//new ArrayList<ContactListReadResponseDto>();
		return contactService.getAllContacts(HttpSessionUtils.getUserFromSession(session));
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public ContactResponseDto getContacts(@PathVariable final Long id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return null;
			
		log.info("* before service");
		ContactResponseDto dto = contactService.getContacts(id);
		log.info("* after service");
		if(dto.getPhoto().length != 0) dto.updatePhotoPath(makeDownloadUri(dto.getId()));
		return dto;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public boolean deleteContacts(@PathVariable final Long id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return false;
		contactService.delete(id, HttpSessionUtils.getUserFromSession(session));
		return true;
	}
	
	@PostMapping(path = {"/", ""}, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CREATED)
	public String createContacts(@RequestBody final ContactSaveRequestDto dto, HttpSession session) throws IOException {
//	public String createContacts(@ModelAttribute final ContactSaveRequestDto dto) throws IOException {
		if(!HttpSessionUtils.isLoginUser(session)) return "save fail";
		
		log.info("* post controller");
		Long savedContactId = contactService.create(dto, HttpSessionUtils.getUserFromSession(session));
		log.info("* create 이후");
		
		if(dto.getPhoto().length() == 0) return "saved";
		
		String fileDownloadUri = makeDownloadUri(savedContactId);
		log.info("* download uri 생성");
//		return contactService.create(dto);
		return fileDownloadUri;
	}
	
//	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PutMapping(path = {"/{id}", "/{id}/"})
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public String editContacts(@PathVariable final Long id, @RequestBody final ContactUpdateRequestDto dto, HttpSession session) throws IOException {
//	public String editContacts(@PathVariable final Long id, @ModelAttribute final ContactUpdateRequestDto dto) throws IOException {
		if(!HttpSessionUtils.isLoginUser(session)) return "edit fail";
		
		log.info("* put controller");
		Long savedContactId = contactService.update(id, dto, HttpSessionUtils.getUserFromSession(session));
		log.info("* update 이후");
		
		if(dto.getPhoto().length() == 0) return "saved";
		
		String fileDownloadUri = makeDownloadUri(savedContactId);
		log.info("* download uri 생성");
		return fileDownloadUri != null ? fileDownloadUri : "saved";
//		return contactService.update(id, dto);
	}
	
	// 사진 불러오기 
	@GetMapping("/{id}/downloadFile/{date}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long id, HttpSession session){
		if(!HttpSessionUtils.isLoginUser(session)) return ResponseEntity.noContent().build();
		
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
	public boolean addTagToContact(@PathVariable final Long id, @PathVariable final Long tagId, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return false;
		return contactService.addTagToContact(id, tagId);
	}
	
	@DeleteMapping("/{id}/tags/{tagId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public boolean deleteTagToContact(@PathVariable final Long id, @PathVariable final Long tagId, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return false;
		return contactService.deleteTagToContact(id, tagId);
	}
	
	@GetMapping(path = {"/{id}/tags", "/{id}/tags/"})
	@ResponseStatus(value = HttpStatus.OK)
	public List<TagResponseDto> getAllTagsByContact(@PathVariable final Long id, HttpSession session){
		if(!HttpSessionUtils.isLoginUser(session)) return null;
		log.info("contact에 속한 tag 출력 ");
		return contactService.getAllTagsByContact(id);
	}
	
	// 즐겨찾기 
	@PostMapping("/{id}/favorites")
	@ResponseStatus(value = HttpStatus.CREATED)
	public boolean addToFavorites(@PathVariable final Long id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return false;
		return contactService.addToFavorites(id, HttpSessionUtils.getUserFromSession(session));
	}
	
	@DeleteMapping("/{id}/favorites")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public boolean deleteToFavorites(@PathVariable final Long id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return false;
		return contactService.deleteToFavorites(id, HttpSessionUtils.getUserFromSession(session));
	}
	
	private String makeDownloadUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/contacts/")
				.path(id.toString())
				.path("/downloadFile/")
				.path(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")))
				.toUriString();
	}
}
