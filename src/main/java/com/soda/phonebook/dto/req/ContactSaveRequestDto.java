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

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactSaveRequestDto {
	
	@NotEmpty
	private ContactType type;
	
	@NotEmpty
	private String name;
	
	private String memo = null;
	private byte[] photo = null;
	
	private List<Digit> digits = new ArrayList<>();
	private List<Info> infoes = new ArrayList<>();
	private Set<Tag> tags = new HashSet<>();
	
	@Builder
	public ContactSaveRequestDto(ContactType type, String name, String memo,  
			byte[] photo, List<Digit> digits, List<Info> infoes, Set<Tag> tags){
		this.type = type;
		this.name = name;
		// nullable
		this.memo = memo;
		this.photo = photo;
		this.digits.addAll(Optional.ofNullable(digits).orElse(this.digits));
		this.infoes.addAll(Optional.ofNullable(infoes).orElse(this.infoes));
		this.tags.addAll(Optional.ofNullable(tags).orElse(this.tags));
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
