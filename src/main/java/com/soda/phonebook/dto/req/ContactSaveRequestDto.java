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
public class ContactSaveRequestDto {
	
	private ContactType type = ContactType.DEFAULT;
	
	@NotEmpty
	private String name;
	
	private String memo = null;
	private byte[] photo = null;
	
	private List<DigitSaveRequestDto> digits = new ArrayList<>();
	private List<UrlSaveRequestDto> urls = new ArrayList<>();
	private List<EmailSaveRequestDto> emails = new ArrayList<>();
	private List<DateSaveRequestDto> dates = new ArrayList<>();
	private List<AddressSaveRequestDto> addresses = new ArrayList<>();
	
	@Builder
	public ContactSaveRequestDto(ContactType type, String name, String memo, byte[] photo, 
			List<DigitSaveRequestDto> digits, List<UrlSaveRequestDto> urls,
			List<EmailSaveRequestDto> emails,List<DateSaveRequestDto> dates,
			List<AddressSaveRequestDto> addresses){
		this.type = Optional.ofNullable(type).orElse(this.type);
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
	
	public ContactSaveRequestDto(Contact contact) {
		this.type = Optional.ofNullable(contact.getType()).orElse(this.type);
		this.name = contact.getName();
		this.memo = contact.getMemo();
		this.photo = contact.getPhoto();
	}
	
	public Contact toEntity(User user) {
		return Contact.builder()
				.user(user)
				.type(this.type)
				.name(this.name)
				.memo(this.memo)
				.photo(this.photo)
				.build();
	}
}
