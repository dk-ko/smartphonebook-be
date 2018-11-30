package com.soda.phonebook.dto.req;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.VO.Mark;
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
	
	private Mark rep;
	
	@Builder
	public DigitUpdateRequestDto(Long id,CategoryUpdateRequestDto category, Numbers numbers, Mark rep) {
		this.id = id;
		this.category = category;
		this.numbers = numbers;
		this.rep = Optional.ofNullable(rep).orElse(this.rep);
	}
	
	public Digit toEntity(Contact contact, Category category) {
		return Digit.builder()
				.id(this.id)
				.contact(contact)
				.category(category)
				.numbers(this.numbers)
				.rep(this.rep)
				.build();
	}
}
