package com.soda.phonebook.dto.req;

import javax.validation.constraints.NotEmpty;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.info.Info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class InfoUpdateRequestDto implements ContactDataRequestDto{
	@NotEmpty
	protected Long id;
	
	@NotEmpty
	protected CategoryUpdateRequestDto category;
	
	@NotEmpty
	protected String contents;
	
	public InfoUpdateRequestDto(Long id, CategoryUpdateRequestDto category, String contents) {
		this.id = id;
		this.category = category;
		this.contents = contents;
	}
	
	public abstract <T extends Info> T toEntity(Contact contact, Category category);
}
