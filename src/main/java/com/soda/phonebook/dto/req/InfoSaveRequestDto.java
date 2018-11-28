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
public abstract class InfoSaveRequestDto {
	
	@NotEmpty
	protected CategorySaveRequestDto category;
	
	@NotEmpty
	protected String contents;
	
	public InfoSaveRequestDto(CategorySaveRequestDto category, String contents) {
		this.category = category;
		this.contents = contents;
	}
	
	public abstract <T extends Info> T toEntity(Contact contact, Category category);
}
