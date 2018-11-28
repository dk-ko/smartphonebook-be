package com.soda.phonebook.dto.req;

import javax.validation.constraints.NotEmpty;

import com.soda.phonebook.domain.VO.Mark;
import com.soda.phonebook.domain.VO.Numbers;

import lombok.Getter;

@Getter
public class DigitSaveRequestDto {
	
	private CategorySaveRequestDto Category;
	
	@NotEmpty
	private Numbers numbers;
	
	private Mark rep = Mark.N;
	
	public DigitSaveRequestDto(CategorySaveRequestDto category, Numbers numbers, Mark rep) {
		
	}
}
