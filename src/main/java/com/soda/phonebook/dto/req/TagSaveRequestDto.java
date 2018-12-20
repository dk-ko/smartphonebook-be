package com.soda.phonebook.dto.req;

import javax.validation.constraints.NotEmpty;

import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TagSaveRequestDto { // Tag 생성, 삭제 시
	
	@NotEmpty
	private String name;
	
	@Builder
	public TagSaveRequestDto(String name) {
		this.name = name;
	}
	
	public Tag toEntity(User user) {
		return Tag.builder()
				.name(name)
				.user(user)
				.build();
	}
}
