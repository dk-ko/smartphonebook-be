package com.soda.phonebook.domain;

import com.soda.phonebook.domain.User;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soda.phonebook.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {

	@Autowired
	private UserRepository userRepository;
	
	private User user;
	
	@Before
	public void setUp() throws Exception{
		user = User.builder().name("user1").build();
	}
	
	@Test
	public void testUserCreate() {
		User savedUser = userRepository.save(user);
		User findedUser = userRepository.findAll().get(0);
		
		assertThat(savedUser.getName(), is("user1"));
		assertThat(findedUser.getName(), is("user1"));
		
	}
}
