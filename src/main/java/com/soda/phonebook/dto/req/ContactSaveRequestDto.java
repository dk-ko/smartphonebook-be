package com.soda.phonebook.dto.req;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.Digit;
import com.soda.phonebook.domain.Tag;
import com.soda.phonebook.domain.VO.ContactType;
import com.soda.phonebook.domain.info.Info;
import com.soda.phonebook.dto.res.DigitResponseDto;
import com.soda.phonebook.dto.res.InfoResponseDto;
import com.soda.phonebook.dto.res.TagResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactSaveRequestDto {
	
	private ContactType type = ContactType.DEFAULT;
	
	@NotEmpty
	private String name;
	
	private String memo = null;
	private byte[] photo = null;
	
	private List<DigitResponseDto> digits = new ArrayList<>();
	private List<InfoResponseDto> infoes = new ArrayList<>();
	private Set<TagResponseDto> tags = new HashSet<>();
	
	@Builder
	public ContactSaveRequestDto(ContactType type, String name, String memo,  
			byte[] photo, List<DigitResponseDto> digits, List<InfoResponseDto> infoes, Set<TagResponseDto> tags){
		this.type = Optional.ofNullable(type).orElse(this.type);
		this.name = name;
		// nullable
		this.memo = memo;
		this.photo = photo;
		this.digits = Optional.ofNullable(digits).orElse(this.digits);
		this.infoes = Optional.ofNullable(infoes).orElse(this.infoes);
		this.tags = Optional.ofNullable(tags).orElse(this.tags);
	}
	
	public Contact toEntity() {
		return Contact.builder()
				.type(this.type)
				.name(this.name)
				.memo(this.memo)
				.photo(this.photo)
//				.digits(this.digits)
//				.infoes(this.infoes)
//				.tags(this.tags)
				.build();
	}
}
