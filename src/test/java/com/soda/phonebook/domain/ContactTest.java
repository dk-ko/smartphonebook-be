package com.soda.phonebook.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import org.junit.Test;

public class ContactTest {

	@Test
	public void testCreateContact() {
		Contact contact = Contact.builder()
				.name("koda").build();
		
		assertThat(contact.getId(), is(nullValue()));
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
	}

}
