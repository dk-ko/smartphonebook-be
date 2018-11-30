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
public class DateUpdateRequestDto extends InfoUpdateRequestDto{

	public DateUpdateRequestDto(Long id, CategoryUpdateRequestDto category, String contents) {
		super(id, category, contents);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Date toEntity(Contact contact, Category category) {
		return Date.builder()
				.id(this.id)
				.contact(contact)
				.category(category)
				.contents(this.contents)
				.build();
	}

}
