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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class ContactTest {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private UserRepository userRepository;
	
	private User savedUser;
	
	@Before
	public void setUp() {
		
		User user = User.builder().name("테스트유저").build();
		savedUser = userRepository.save(user);
	}
	
	@Test
	@Transactional
	public void test_Contact_빌더패턴_생성() {
		Contact contact = Contact.builder()
				.user(savedUser)
				.type(ContactType.DEFAULT)
				.name("koda").build();
		
		log.info(contact.toString());
		
		assertThat(contact.getId(), is(nullValue()));
		assertThat(contact.getName(), is("koda"));
	}
	
	@Test
	@Transactional
	public void testUpdateContactName() {
		Contact contact = MockEntity.mock(Contact.class, 1l);
		
		contact.updateName("koda");
		
		assertThat(contact.getId(), is(1l));
		assertThat(contact.getName(), is("koda"));
		assertThat(contact.getMemo(), is(nullValue()));
		assertThat(contact.getPhoto(), is(nullValue()));
	}

}
