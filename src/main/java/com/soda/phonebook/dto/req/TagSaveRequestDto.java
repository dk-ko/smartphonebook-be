package com.soda.phonebook.dto.req;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TagSaveRequestDto {
	@NotEmpty
	private String name;
	
	public TagSaveRequestDto(String name) {
		this.name = name;
	}
}
