package com.soda.phonebook.domain;

import com.soda.phonebook.domain.User;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
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
	private Set<Contact> contactList = new HashSet<Contact>();
	
	@Before
	public void setUp() throws Exception{
		user = User.builder().name("user1").build();
		
		contactList.add(Contact.builder().name("A").build());
		contactList.add(Contact.builder().name("B").build());
		contactList.add(Contact.builder().name("C").build());
		
	}
	
	@Test
	public void test_favorite_조인테이블() throws Exception{
		List<Contact> savedContact = contactRepository.save(contactList);
		
		user.addFavorite(savedContact.get(2)); // B
		User savedUser = userRepository.save(user);
		
		Set<Contact> findFavorite = savedUser.getFavorites();

		assertThat(findFavorite.stream().findFirst().get().getName(),is("B"));
	}
	
	@Test
	public void testUserCreate() {
		User savedUser = userRepository.save(user);
		User findedUser = userRepository.findAll().get(0);
		
		assertThat(savedUser.getId(), is(1l));
		assertThat(savedUser.getName(), is("user1"));
		assertThat(findedUser.getName(), is("user1"));
		
	}

}
