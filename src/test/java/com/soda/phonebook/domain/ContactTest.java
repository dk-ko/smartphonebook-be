package com.soda.phonebook.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soda.phonebook.repository.ContactRepository;
import com.soda.phonebook.repository.TagRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ContactTest {
	
	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	TagRepository tagRepository;
	
//	private Contact contact;
	private List<Tag> tags = new ArrayList<Tag>();;

	@Before
	public void setUp() {
		tags.add(Tag.builder()
				.name("학교친구")
				.build());
		
		tags.add(Tag.builder()
				.name("동네친구")
				.build());
		
//		contact = Contact.builder()
//				.name("연락처1")
//				.build();
	}
	
	
	
	@Test
	@Transactional
	public void testCreateContact() {
		Contact contact = Contact.builder()
				.name("koda").build();
		
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
