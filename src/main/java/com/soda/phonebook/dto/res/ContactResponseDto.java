package com.soda.phonebook.dto.res;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.domain.VO.ContactType;
import com.soda.phonebook.domain.info.Info;

import lombok.Getter;

@Getter
public class ContactResponseDto {
	private String name;
	private String memo;
	private byte[] photo;
	private ContactType type;
	
	@JsonIgnore
	private List<Digit> digits;
	
	@JsonIgnore
	private List<Info> infoes;
	
	@JsonIgnore
	private Set<Tag> tags;
	
	public ContactResponseDto(Contact contact) {
		this.name = contact.getName();
		this.memo = contact.getMemo();
		this.photo = contact.getPhoto();
		this.digits = contact.getDigits();
		this.infoes = contact.getInfoes();
		this.tags = contact.getTags();
		this.type = contact.getType();
	}
}
