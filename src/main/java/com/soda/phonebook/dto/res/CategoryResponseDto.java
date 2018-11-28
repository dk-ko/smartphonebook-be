package com.soda.phonebook.dto.res;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.VO.DataType;
import com.soda.phonebook.domain.VO.Mark;

import lombok.Getter;

@Getter
public class CategoryResponseDto {
	private Long id;
	private String name;
	private DataType type;
	private Mark isDefault;
	
	public CategoryResponseDto (Category category) {
		this.id = category.getId();
		this.name = category.getName();
		this.type = category.getType();
		this.isDefault = category.getIsDefault();
	}
}
