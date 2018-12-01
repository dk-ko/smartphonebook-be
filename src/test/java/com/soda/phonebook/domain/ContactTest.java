package com.soda.phonebook.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soda.phonebook.domain.VO.ContactType;
import com.soda.phonebook.repository.UserRepository;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class ContactTest {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private UserRepository userRepository;
	
	private Contact contact;
	private User user, savedUser;
	
	@Before
	public void setUp() {
		
		user = User.builder().name("testuser").build();
		savedUser = userRepository.save(user);
		
		contact = Contact.builder()
				.user(savedUser)
				.type(ContactType.DEFAULT)
				.name("koda").build();
	}
	
	@Test
	@Transactional
	public void test_Contact_빌더패턴_생성() {
		
//		log.info(contact.toString());
		
		assertThat(contact.getId(), is(nullValue())); // JPA ID 생성 규약 
		assertThat(contact.getName(), is("koda"));
	}
	/*
	@Test
	@Transactional
	public void test_Contact_cascade_저장() {
		
		// digit, info, tag 생성 후 add		
		// contact만 persist
		
		assertThat(contact.getId(), is(1l));
		
		// is not empty
		assertFalse(contact.getDigits().isEmpty());
		assertFalse(contact.getInfoes().isEmpty());
		assertFalse(contact.getTags().isEmpty()); 
		
	}*/
}
