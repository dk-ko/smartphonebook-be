package com.soda.phonebook.dto.req;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.domain.VO.ContactType;
import com.soda.phonebook.domain.info.Info;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContactSaveRequestDto {
	
	@NotEmpty
	private String name;
	
	private String memo;
	private byte[] photo;
	private ContactType type;
	
	private Set<Digit> digits;
	private Set<Info> infoes;
	private Set<Tag> tags;
	
	@Builder
	public ContactSaveRequestDto(String name, String memo, byte[] photo, 
			Set<Digit> digits, Set<Info> infoes, Set<Tag> tags, ContactType type){
		this.name = name;
		this.memo = memo;
		this.photo = photo;
		this.digits = digits;
		this.infoes = infoes;
		this.tags = tags;
		this.type = type;
	}
	
	public Contact toEntity() {
		return Contact.builder()
				.name(this.name)
				.memo(this.memo)
				.photo(this.photo)
				.digits(this.digits)
				.infoes(this.infoes)
				.tags(this.tags)
				.type(this.type)
				.build();
	}
}
