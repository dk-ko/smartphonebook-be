package com.soda.phonebook.dto.res;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.domain.VO.ContactType;
import com.soda.phonebook.domain.info.Info;

import lombok.Getter;

@Getter
public class ContactListReadResponseDto {
	private Long id;
	private ContactType type;
	private String name;
	
	private String memo;
	private byte[] photo;
	
	private List<Digit> digits = new ArrayList<>();
	private List<Info> infoes = new ArrayList<>();
	private Set<Tag> tags = new HashSet<>();
	
	public ContactListReadResponseDto(Contact contact) {
		this.id = contact.getId();
		this.type = contact.getType();
		this.name = contact.getName();
		// nullable 
		this.memo = contact.getMemo();
		this.photo = contact.getPhoto();
		this.digits = contact.getDigits();
		this.infoes = contact.getInfoes();
		this.tags = contact.getTags();
	}
}
