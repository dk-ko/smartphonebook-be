package com.soda.phonebook.dto.req;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.info.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DateSaveRequestDto extends InfoSaveRequestDto{
	
	public DateSaveRequestDto(CategorySaveRequestDto category, String contents) {
		super();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Date toEntity(Contact contact, Category category) {
		return Date.builder()
				.contact(contact)
				.category(category)
				.contents(this.contents)
				.build();
	}
}
