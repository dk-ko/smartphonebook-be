package com.soda.phonebook.controller;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.soda.phonebook.common.HttpSessionUtils;
import com.soda.phonebook.dto.req.TagSaveRequestDto;
import com.soda.phonebook.dto.res.ContactListReadResponseDto;
import com.soda.phonebook.dto.res.TagResponseDto;
import com.soda.phonebook.service.TagService;

import lombok.AllArgsConstructor;

//@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/api/tags")
@RestController
public class TagController {
	
	private final TagService tagService;
	
	// tag 전체 목록
	@GetMapping(path= {"/", ""})
	@ResponseStatus(value = HttpStatus.OK)
	public Set<TagResponseDto> getAllTags(HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return null;
		return tagService.findAllByUser(HttpSessionUtils.getUserFromSession(session));
	}
	
	// tag 선택시 tag에 속한 연락처 목록 출력
	@GetMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public Set<ContactListReadResponseDto> getContactsByTags(@PathVariable final Long id, HttpSession session){
		if(!HttpSessionUtils.isLoginUser(session)) return null;
		return tagService.findContactsByTag(id);
	}
	
	// tag 삭제
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public boolean deleteTags(@PathVariable final Long id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return false;
		tagService.delete(id);
		return true;
	}
	
	// tag 생성
	@PostMapping(path= {"/", ""})
	@ResponseStatus(value = HttpStatus.CREATED)
	public boolean createContacts(@RequestBody final TagSaveRequestDto dto, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return false;
		return tagService.create(dto, HttpSessionUtils.getUserFromSession(session));
	}
	
	// tag 수정 - tag 이름 수정
	@PutMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public boolean editTags(@PathVariable final Long id, @RequestBody final TagSaveRequestDto dto, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) return false;
		return tagService.update(id, dto);
	}
}
