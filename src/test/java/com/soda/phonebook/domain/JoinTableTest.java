package com.soda.phonebook.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soda.phonebook.repository.ContactRepository;
import com.soda.phonebook.repository.TagRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JoinTableTest {

	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	TagRepository tagRepository;
	
	private Contact contact;
	private List<Tag> tags = new ArrayList<Tag>();
	
	@Before
	public void setUp() {
		tags.add(Tag.builder()
				.name("학교친구")
				.build());
		
		tags.add(Tag.builder()
				.name("동네친구")
				.build());
		
		contact = Contact.builder()
				.name("연락처1")
				.build();
	}
	
	@Test
	public void test_tag_조인테이블() throws Exception{
		List<Tag> savedTags = new ArrayList<Tag>();
		savedTags.add(tagRepository.save(tags.get(0)));
		savedTags.add(tagRepository.save(tags.get(1)));
		
		// tag - contact 관계 설정 
		savedTags.get(0).addContact(contact);
		savedTags.get(1).addContact(contact);
		
		// contact - tag 관계 설정 
		contact.addTag(savedTags.get(0));
		contact.addTag(savedTags.get(1));
		
		contactRepository.save(contact);
		
		Contact afterContact = contactRepository.findOne(1l);
		Tag afterTag = tagRepository.findOne(1l);
		
		assertThat(afterContact.getTags().size(), is(2));
		assertThat(afterTag.getContacts().size(), is(1));
	}

}
