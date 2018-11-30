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
public class UrlUpdateRequestDto extends InfoUpdateRequestDto{
	public UrlUpdateRequestDto(Long id,CategoryUpdateRequestDto category, String contents) {
		super(id, category, contents);
	}
	
	@SuppressWarnings("unchecked")
	public Url toEntity(Contact contact, Category category) {
		return Url.builder()
				.id(this.id)
				.contact(contact)
				.category(category)
				.contents(this.contents)
				.build();
	}
}
