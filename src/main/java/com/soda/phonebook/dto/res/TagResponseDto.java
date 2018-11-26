package com.soda.phonebook.dto.res;

import com.soda.phonebook.domain.Tag;

import lombok.Getter;

@Getter
public class TagResponseDto {
	private Long id;
	private String name;
	
	public TagResponseDto(Tag tag) {
		this.id = tag.getId();
		this.name = tag.getName();
	}
}
