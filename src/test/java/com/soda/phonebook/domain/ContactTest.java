package com.soda.phonebook.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import org.junit.Test;

import com.soda.phonebook.domain.VO.Mark;

public class ContactTest {

	@Test
	public void testCreateContact() {
		Contact contact = Contact.builder()
				.id(MockEntity.mock(Contact.class, 1l).id)
				.name("koda").build();
		
		assertThat(contact.getName(), is("koda"));
	}
	
	@Test
	public void testUpdateContactName() {
		Contact contact = MockEntity.mock(Contact.class, 1l);
		
		contact.updateName("koda");
		
		assertThat(contact.getId(), is(1l));
		assertThat(contact.getName(), is("koda"));
		assertThat(contact.getMemo(), is(nullValue()));
		assertThat(contact.getPhoto(), is(nullValue()));
		assertThat(contact.getFavorite(), is(Mark.N));
	}

}
