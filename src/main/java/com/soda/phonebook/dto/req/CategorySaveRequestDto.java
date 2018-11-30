package com.soda.phonebook.dto.req;

import javax.validation.constraints.NotEmpty;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.DataType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategorySaveRequestDto implements CategoryRequestDto{
	@NotEmpty
	private Long id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private DataType type;
	
	
	@Builder
	public CategorySaveRequestDto(Long id,String name, DataType type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	public Category toEntity(User user) {
		return Category.builder()
				.name(this.name)
				.type(this.type)
				.user(user)
				.build();
	}
}
