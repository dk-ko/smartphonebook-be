package com.soda.phonebook.dto.req;

import javax.validation.constraints.NotEmpty;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.VO.Numbers;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DigitSaveRequestDto implements ContactDataRequestDto{
	
	@NotEmpty
	private CategorySaveRequestDto category;
	
	@NotEmpty
	private Numbers numbers;
	
	
	@Builder
	public DigitSaveRequestDto(CategorySaveRequestDto category, Numbers numbers) {
		this.category = category;
		this.numbers = numbers;
	}
	
	public Digit toEntity(Contact contact, Category category) {
		return Digit.builder()
				.contact(contact)
				.category(category)
				.numbers(this.numbers)
				.build();
	}
	
}
