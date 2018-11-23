package com.soda.phonebook.dto.res;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.domain.VO.ContactType;
import com.soda.phonebook.domain.info.Info;

import lombok.Getter;

@Getter
public class ContactListReadResponseDto {
	private Long id;
	private String name;
	private String memo;
	private byte[] photo;
	private ContactType type;
	
	@JsonIgnore
	private Set<Digit> digits;
	
	@JsonIgnore
	private Set<Info> infoes;
	
	@JsonIgnore
	private Set<Tag> tags;
	
	public ContactListReadResponseDto(Contact contact) {
		this.id = contact.getId();
		this.name = contact.getName();
		this.memo = contact.getMemo();
		this.photo = contact.getPhoto();
		this.digits = contact.getDigits();
		this.infoes = contact.getInfoes();
		this.tags = contact.getTags();
		this.type = contact.getType();
	}
}
