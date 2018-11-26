package com.soda.phonebook.dto.res;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.VO.DataType;

import lombok.Getter;

@Getter
public class CategoryResponseDto {
	private Long id;
	private String name;
	private DataType type;
	
	public CategoryResponseDto (Category category) {
		this.id = category.getId();
		this.name = category.getName();
		this.type = category.getType();
	}
}
