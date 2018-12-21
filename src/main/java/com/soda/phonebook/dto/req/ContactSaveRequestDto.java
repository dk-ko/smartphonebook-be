package com.soda.phonebook.dto.req;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Set;
import java.util.HashSet;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.soda.phonebook.common.CanNotSaveContact;
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

	@JsonIgnore
	private static final int fileSize = 3145728; //3MB

	private ContactType type = ContactType.DEFAULT;
	
	@NotEmpty
	private String name;
	
	private String memo = null;
	
//	@JsonIgnore
//	private byte[] photo = null;
	
	private String photo = null;
	
//	@JsonIgnore
//	private MultipartFile photo = null;
//	
//	@JsonProperty("photo")
//	private String photoName;
	
	private List<DigitSaveRequestDto> digits = new ArrayList<>();
	private List<UrlSaveRequestDto> urls = new ArrayList<>();
	private List<EmailSaveRequestDto> emails = new ArrayList<>();
	private List<DateSaveRequestDto> dates = new ArrayList<>();
	private List<AddressSaveRequestDto> addresses = new ArrayList<>();
	private Set<TagUpdateRequestDto> tags = new HashSet<>();
	
	@Builder
//	public ContactSaveRequestDto(ContactType type, String name, String memo, byte[] photo,
//	public ContactSaveRequestDto(ContactType type, String name, String memo, MultipartFile photo,
	public ContactSaveRequestDto(ContactType type, String name, String memo, String photo,
			List<DigitSaveRequestDto> digits, List<UrlSaveRequestDto> urls,
			List<EmailSaveRequestDto> emails,List<DateSaveRequestDto> dates,
			List<AddressSaveRequestDto> addresses, Set<TagUpdateRequestDto> tags){
		this.type = Optional.ofNullable(type).orElse(this.type);
		this.name = name;
		// nullable
		this.memo = memo;
//		this.photo = photo;
		this.photo = Optional.ofNullable(photo).orElse(this.photo);
		
		this.digits = Optional.ofNullable(digits).orElse(this.digits);
		this.urls = Optional.ofNullable(urls).orElse(this.urls);
		this.emails = Optional.ofNullable(emails).orElse(this.emails);
		this.dates = Optional.ofNullable(dates).orElse(this.dates);
		this.addresses = Optional.ofNullable(addresses).orElse(this.addresses);
		this.tags = Optional.ofNullable(tags).orElse(this.tags);
	}
	
	public Contact toEntity(User user) throws IOException{
		return Contact.builder()
				.user(user)
				.type(this.type)
				.name(this.name)
				.memo(this.memo)
//				.photo(this.photo != null ? this.photo.getBytes() : null)
//				.photo(this.photo)
				.photo(this.photo.length() != 0 ? photoDecoder(this.photo) : "".getBytes())
				.build();
	}
	
	private byte[] photoDecoder(String photo) {
		byte[] result = Base64.getDecoder().decode(photo);
		if(result.length > fileSize) throw new CanNotSaveContact("업로드할 수 있는 파일 크기를 초과하였습니다.");
		return result;
	}
}
