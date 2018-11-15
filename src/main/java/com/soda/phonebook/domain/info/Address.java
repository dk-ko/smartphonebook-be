package com.soda.phonebook.domain.info;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;

import lombok.Builder;

@Entity
@DiscriminatorValue("A")
public class Address extends Info{
	
	@Builder
	public Address(Contact contact, String contents, Category category) {
		super(contact, contents, category);
	}
}
