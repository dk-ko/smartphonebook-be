package com.soda.phonebook.domain.info;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;

import lombok.Builder;

@Entity
@DiscriminatorValue("U")
public class Url extends Info{
	@Builder
	public Url (Contact contact, String contents, Category category) {
		super(contact, contents, category);
	}
}
