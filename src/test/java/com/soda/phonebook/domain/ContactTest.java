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
import com.soda.phonebook.domain.VO.DataType;
import com.soda.phonebook.domain.VO.Numbers;
import com.soda.phonebook.domain.info.Address;
import com.soda.phonebook.repository.TagRepository;
import com.soda.phonebook.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ContactTest {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	private Contact contact;
	private User user, savedUser;
	private Numbers numbers = Numbers.builder()
			.first("010").second("1234").third("5678").build();
	
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
		assertThat(contact.getId(), is(nullValue())); // JPA ID 생성 규약 
		assertThat(contact.getName(), is("koda"));
	}
	
	@Test
	@Transactional
	public void test_Contact_cascade_저장및삭제() {
		
		// digit, info, tag 생성 후 add		
		Category category1 = Category.builder()
				.user(savedUser)
				.type(DataType.DIGIT)
				.name("개인").build();
		Category category2 = Category.builder()
				.user(savedUser)
				.type(DataType.ADDRESS)
				.name("집").build();
		Digit digit = Digit.builder()
				.numbers(numbers)
				.category(category1)
				.contact(contact)
				.build();
		Address address = Address.builder()
				.contents("서울시 구로구 ...")
				.category(category2)
				.contact(contact)
				.build();
		Tag tag = Tag.builder().name("태그저장").user(savedUser).build();
		
		contact.getDigits().add(digit);
		contact.getInfoes().add(address);
		contact.getTags().add(tag);
		
		// contact만 persist
		em.persist(contact);
		em.flush();
		
		assertNotNull(em.find(Digit.class, 1l));
		assertNotNull(em.find(Address.class, 1l));
		assertNotNull(tagRepository.findById(1l));
		
		em.remove(contact);
		em.flush();
		
		// 삭제 후 null
		assertNull(em.find(Digit.class, 1l));
		assertNull(em.find(Address.class, 1l));
		
		em.clear();
	}
}
