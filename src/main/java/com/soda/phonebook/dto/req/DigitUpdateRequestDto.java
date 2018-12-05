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
public class DigitUpdateRequestDto implements ContactDataRequestDto{
	
	@NotEmpty
	private Long id;
	
	@NotEmpty
	private CategoryUpdateRequestDto category;
	
	@NotEmpty
	private Numbers numbers;
	
	
	@Builder
	public DigitUpdateRequestDto(Long id,CategoryUpdateRequestDto category, Numbers numbers) {
		this.id = id;
		this.category = category;
		this.numbers = numbers;
	}
	
	public Digit toEntity(Contact contact, Category category) {
		return Digit.builder()
				.id(this.id)
				.contact(contact)
				.category(category)
				.numbers(this.numbers)
				.build();
	}
}
