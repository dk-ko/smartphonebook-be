package com.soda.phonebook.dto.res;

import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.VO.Numbers;

import lombok.Getter;

@Getter
public class DigitResponseDto {
	private Long id;
	private CategoryResponseDto category;
	private Numbers numbers;
	
	public DigitResponseDto(Digit digit) {
		this.id = digit.getId();
		this.category = new CategoryResponseDto(digit.getCategory());
		this.numbers = digit.getNumbers(); 
	}
	
}
