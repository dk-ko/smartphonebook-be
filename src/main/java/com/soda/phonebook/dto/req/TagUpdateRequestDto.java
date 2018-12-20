package com.soda.phonebook.dto.req;

import javax.validation.constraints.NotEmpty;

import com.soda.phonebook.domain.Tag;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TagUpdateRequestDto{ // Contact에 Tag 추가, 삭제 시 
	
	@NotEmpty
	private Long id;
	
	private String name;
	
	@Builder
	public TagUpdateRequestDto(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Tag toEntity() {
		return Tag.builder()
				.id(this.id)
				.name(this.name)
				.build();
	}
}
