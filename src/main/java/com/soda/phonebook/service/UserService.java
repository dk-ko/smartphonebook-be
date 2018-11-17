package com.soda.phonebook.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.User;
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
			return create(User.builder().name("테스트유저").build());
		}
	}
	
	public User getCurrentUser() {
		// test
		User savedUser = testUser();
		return savedUser;
	}
	
	public User create(User user) {
		return userRepository.save(user);
	}
}
