package com.soda.phonebook.converter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.ContactType;


@RunWith(SpringRunner.class)
//@ActiveProfiles("local")
//@SpringBootTest
@DataJpaTest
public class ContactTypeAttributeConverterTest {
	@PersistenceContext
    private EntityManager em;
	
	private Contact contact;
	
	@Before
	public void setUp() {
		User user = User.builder()
				.name("테스트유저")
				.build();
		em.persist(user);
		
		contact = Contact.builder()
				.name("name")
//				.user(user)
				.type(ContactType.FAVORITED)
				.build();
		em.persist(contact);
		em.flush();
		em.clear();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void test_contactType_converter() {
		
		Query query = em.createNativeQuery("select * from contact where contact_type = :contact_type", Contact.class);
		query.setParameter("contact_type", 2); // FAVORITED is 2
		List<Contact> list = query.getResultList();
		
		// confirm
		assertThat(ContactType.FAVORITED, is(list.get(0).getType()));
	}

}
