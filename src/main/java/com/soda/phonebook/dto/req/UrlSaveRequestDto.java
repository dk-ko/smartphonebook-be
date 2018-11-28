package com.soda.phonebook.dto.req;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.info.Url;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UrlSaveRequestDto extends InfoSaveRequestDto{
	
	public UrlSaveRequestDto(CategorySaveRequestDto category, String contents) {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public Url toEntity(Contact contact, Category category) {
		return Url.builder()
				.contact(contact)
				.category(category)
				.contents(this.contents)
				.build();
	}
}
