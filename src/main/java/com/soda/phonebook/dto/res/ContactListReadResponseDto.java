package com.soda.phonebook.dto.res;

import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.VO.ContactType;

import lombok.Getter;

@Getter
public class ContactListReadResponseDto {
	private Long id;
	private ContactType type;
	private String name;
	
	public ContactListReadResponseDto(Contact contact) {
		this.id = contact.getId();
		this.type = contact.getType();
		this.name = contact.getName();
	}
}
