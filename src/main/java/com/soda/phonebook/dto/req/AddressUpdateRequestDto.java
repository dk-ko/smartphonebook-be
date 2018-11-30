package com.soda.phonebook.dto.req;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.info.Address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressUpdateRequestDto extends InfoUpdateRequestDto{
	
	public AddressUpdateRequestDto(Long id, CategoryUpdateRequestDto category, String contents) {
		super(id, category, contents);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Address toEntity(Contact contact, Category category) {
		return Address.builder()
				.id(this.id)
				.contact(contact)
				.category(category)
				.contents(this.contents)
				.build();
	}
}
