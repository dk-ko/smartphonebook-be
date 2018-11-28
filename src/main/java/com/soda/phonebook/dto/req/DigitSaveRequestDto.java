package com.soda.phonebook.dto.req;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.VO.Mark;
import com.soda.phonebook.domain.VO.Numbers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DigitSaveRequestDto {
	
	@NotEmpty
	private CategorySaveRequestDto category;
	
	@NotEmpty
	private Numbers numbers;
	
	private Mark rep = Mark.N;
	
	public DigitSaveRequestDto(CategorySaveRequestDto category, Numbers numbers, Mark rep) {
		this.category = category;
		this.numbers = numbers;
		this.rep = Optional.ofNullable(rep).orElse(this.rep);
	}
	
	public Digit toEntity(Contact contact, Category category) {
		return Digit.builder()
				.contact(contact)
				.category(category)
				.numbers(this.numbers)
				.rep(this.rep)
				.build();
	}
	
}
