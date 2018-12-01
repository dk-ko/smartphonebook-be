package com.soda.phonebook.dto.res;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.VO.ContactType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ContactListReadResponseDto {
	private Long id;
	private ContactType type;
	private String name;
	private List<DigitResponseDto> digits = new ArrayList<>();
	
	@Builder
	public ContactListReadResponseDto(Contact contact, List<DigitResponseDto> digits) {
		this.id = contact.getId();
		this.type = contact.getType();
		this.name = contact.getName();
		this.digits = Optional.ofNullable(digits).orElse(this.digits);
	}
}
