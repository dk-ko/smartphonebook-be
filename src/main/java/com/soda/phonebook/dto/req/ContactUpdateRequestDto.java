package com.soda.phonebook.dto.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import com.soda.phonebook.domain.Contact;
import com.soda.phonebook.domain.User;
import com.soda.phonebook.domain.VO.ContactType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactUpdateRequestDto {

	@NotEmpty
	private Long id;
	
	@NotEmpty
	private ContactType type;
	
	@NotEmpty
	private String name;
	
	private String memo = null;
	private byte[] photo = null;
	
	private List<DigitUpdateRequestDto> digits = new ArrayList<>();
	private List<UrlUpdateRequestDto> urls = new ArrayList<>();
	private List<EmailUpdateRequestDto> emails = new ArrayList<>();
	private List<DateUpdateRequestDto> dates = new ArrayList<>();
	private List<AddressUpdateRequestDto> addresses = new ArrayList<>();
	
	@Builder
	public ContactUpdateRequestDto(Long id, ContactType type, String name, String memo, byte[] photo, 
			List<DigitUpdateRequestDto> digits, List<UrlUpdateRequestDto> urls,
			List<EmailUpdateRequestDto> emails,List<DateUpdateRequestDto> dates,
			List<AddressUpdateRequestDto> addresses){
		
		this.id = id;
		this.type = type;
		this.name = name;
		// nullable
		this.memo = memo;
		this.photo = photo;
		
		this.digits = Optional.ofNullable(digits).orElse(this.digits);
		this.urls = Optional.ofNullable(urls).orElse(this.urls);
		this.emails = Optional.ofNullable(emails).orElse(this.emails);
		this.dates = Optional.ofNullable(dates).orElse(this.dates);
		this.addresses = Optional.ofNullable(addresses).orElse(this.addresses);
	}
	
	public Contact toEntity(User user) {
		return Contact.builder()
				.id(this.id)
				.user(user)
				.type(this.type)
				.name(this.name)
				.memo(this.memo)
				.photo(this.photo)
				.build();
	}
}
