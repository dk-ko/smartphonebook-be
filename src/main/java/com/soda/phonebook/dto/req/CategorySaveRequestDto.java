package com.soda.phonebook.dto.req;

import javax.validation.constraints.NotEmpty;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.DataType;
import com.soda.phonebook.domain.VO.Mark;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategorySaveRequestDto {
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private DataType type;
	
	private Mark isDefault = Mark.N;
	
	@Builder
	public CategorySaveRequestDto(String name, DataType type) {
		this.name = name;
		this.type = type;
	}
	
	public Category toEntity(User user) {
		return Category.builder()
				.name(this.name)
				.type(this.type)
				.isDefault(this.isDefault)
				.user(user)
				.build();
	}
}
