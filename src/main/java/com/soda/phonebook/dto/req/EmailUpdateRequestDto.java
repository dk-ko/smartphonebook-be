package com.soda.phonebook.dto.req;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.info.Email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailUpdateRequestDto extends InfoUpdateRequestDto{
	public EmailUpdateRequestDto(Long id, CategoryUpdateRequestDto category, String contents) {
		super(id, category, contents);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Email toEntity(Contact contact, Category category) {
		return Email.builder()
				.id(this.id)
				.contact(contact)
				.category(category)
				.contents(this.contents)
				.build();
	}
}
