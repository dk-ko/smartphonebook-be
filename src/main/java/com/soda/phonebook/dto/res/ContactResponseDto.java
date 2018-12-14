package com.soda.phonebook.dto.res;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.VO.ContactType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ContactResponseDto {
	private Long id;
	private ContactType type;
	private String name;
	
	private String memo;
	private byte[] photo;
	
	private List<DigitResponseDto> digits = new ArrayList<>();
	private List<InfoResponseDto> infoes = new ArrayList<>();
	private Set<TagResponseDto> tags = new HashSet<>();
	
	@Builder
	public ContactResponseDto(Contact contact, 
			List<DigitResponseDto> digits, List<InfoResponseDto> infoes, Set<TagResponseDto> tags) {
		this.id = contact.getId();
		this.type = contact.getType();
		this.name = contact.getName();
		
		// nullable
		this.memo = contact.getMemo();
		this.photo = contact.getPhoto();
		
		this.digits = Optional.ofNullable(digits).orElse(this.digits);
		this.infoes = Optional.ofNullable(infoes).orElse(this.infoes);
		this.tags = Optional.ofNullable(tags).orElse(this.tags);
	}
	
	public ContactResponseDto(Contact contact) {
		this.id = contact.getId();
		this.type = contact.getType();
		this.name = contact.getName();
		
		// nullable
		this.memo = contact.getMemo();
		this.photo = contact.getPhoto();
	}
}
