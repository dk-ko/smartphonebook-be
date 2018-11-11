package com.soda.phonebook.domain;

import com.soda.phonebook.domain.User;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soda.phonebook.repository.ContactRepository;
import com.soda.phonebook.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	private User user;
	Contact c1, c2, c3;
	
	@Before
	public void setUp() throws Exception{
		user = User.builder().name("user1").build();
		
		c1 = Contact.builder().name("A").build();
		c2 = Contact.builder().name("B").build();
		c3 = Contact.builder().name("C").build();
		
	}
	
	@Test
	public void testUserCreate() {
		User savedUser = userRepository.save(user);
		User findedUser = userRepository.findAll().get(0);
		
		assertThat(savedUser.getName(), is("user1"));
		assertThat(findedUser.getName(), is("user1"));
		
	}
	
	@Test
	public void test_favorite_조인테이블() throws Exception{
		Contact savedContact = contactRepository.save(c3);
		
		user.addFavorite(savedContact); // C
		User savedUser = userRepository.save(user);
		
		Set<Contact> findFavorite = savedUser.getFavorites();
		assertThat(findFavorite.stream().findFirst().get().getName(),is("C"));
	}

}
