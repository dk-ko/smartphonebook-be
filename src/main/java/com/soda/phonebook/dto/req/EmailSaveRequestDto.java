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
public class EmailSaveRequestDto extends InfoSaveRequestDto{
	
	public EmailSaveRequestDto(CategorySaveRequestDto category, String contents) {
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public  Email toEntity(Contact contact, Category category) {
		return Email.builder()
				.contact(contact)
				.category(category)
				.contents(this.contents)
				.build();
	}
}
