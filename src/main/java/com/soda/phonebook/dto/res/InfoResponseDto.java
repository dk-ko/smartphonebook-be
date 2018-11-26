package com.soda.phonebook.dto.res;

import com.soda.phonebook.domain.info.Info;

import lombok.Getter;

@Getter
public class InfoResponseDto {
	private Long id;
	private CategoryResponseDto category;
	private String contents;
	
	public InfoResponseDto(Info info) {
		this.id = info.getId();
		this.category = new CategoryResponseDto(info.getCategory());
		this.contents = info.getContents();
	}
}
