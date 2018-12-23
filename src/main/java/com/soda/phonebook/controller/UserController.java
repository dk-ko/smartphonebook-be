package com.soda.phonebook.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soda.phonebook.dto.res.ContactListReadResponseDto;
import com.soda.phonebook.security.GoogleUser;
import com.soda.phonebook.security.SessionConstants;
import com.soda.phonebook.service.UserService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {
	
	private final UserService userService;
	private HttpServletRequest request;
	
	
	@GetMapping("/{id}/favorites")
	public List<ContactListReadResponseDto> getFavorites(@PathVariable final Long id) {
		return userService.getFavorites(id);
	}
}
