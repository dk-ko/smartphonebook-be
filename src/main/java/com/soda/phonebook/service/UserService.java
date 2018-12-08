package com.soda.phonebook.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.dto.res.ContactListReadResponseDto;
import com.soda.phonebook.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	// test
	public User testUser() {
		User findUser = userRepository.findByName("테스트유저");
		if( findUser != null) {
			return findUser;
		}else {
			return save(User.builder().name("테스트유저").build());
		}
	}
	
	public User getCurrentUser() {
		// test
		User savedUser = testUser();
		return savedUser;
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	@Transactional(readOnly = true)
	public List<ContactListReadResponseDto> getFavorites(Long id) {
		User findUser = findById(id);
		
		List<ContactListReadResponseDto> favorites = new ArrayList<>();
		for(Contact contact : findUser.getFavorites())
			favorites.add(ContactListReadResponseDto.builder()
					.contact(contact).build());
		return favorites;
	}
	
	private User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("findById error : wrong id")); 
	}
}
