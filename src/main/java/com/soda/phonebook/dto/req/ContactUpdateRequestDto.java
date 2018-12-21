package com.soda.phonebook.dto.req;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Base64.Decoder;
import java.util.HashSet;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ContactUpdateRequestDto {

	@JsonIgnore
	private static final int fileSize = 3145728; //3MB
	
	@NotEmpty
	private Long id;
	
	@NotEmpty
	private ContactType type = ContactType.DEFAULT;
	
	@NotEmpty
	private String name;
	
	private String memo = null;
	
//	@JsonIgnore
//	private byte[] decoded = null;
	
	private String photo = null;
	
//	private byte[] photo = null;
//	@JsonIgnore
//	private MultipartFile photo;
//	
//	@JsonProperty("photo")
//	private String photoName;
	
	private List<DigitUpdateRequestDto> digits = new ArrayList<>();
	private List<UrlUpdateRequestDto> urls = new ArrayList<>();
	private List<EmailUpdateRequestDto> emails = new ArrayList<>();
	private List<DateUpdateRequestDto> dates = new ArrayList<>();
	private List<AddressUpdateRequestDto> addresses = new ArrayList<>();
	
	private Set<TagUpdateRequestDto> tags = new HashSet<>();
	
	@Builder
//	public ContactUpdateRequestDto(Long id, ContactType type, String name, String memo, byte[] photo,
//	public ContactUpdateRequestDto(Long id, ContactType type, String name, String memo, MultipartFile photo,
	public ContactUpdateRequestDto(Long id, ContactType type, String name, String memo, String photo,
			List<DigitUpdateRequestDto> digits, List<UrlUpdateRequestDto> urls,
			List<EmailUpdateRequestDto> emails,List<DateUpdateRequestDto> dates,
			List<AddressUpdateRequestDto> addresses, Set<TagUpdateRequestDto> tags){
		
		this.id = id;
		this.type = Optional.ofNullable(type).orElse(this.type);
		this.name = name;
		// nullable
		this.memo = memo;
		this.photo = Optional.ofNullable(photo).orElse(this.photo);
		
		this.digits = Optional.ofNullable(digits).orElse(this.digits);
		this.urls = Optional.ofNullable(urls).orElse(this.urls);
		this.emails = Optional.ofNullable(emails).orElse(this.emails);
		this.dates = Optional.ofNullable(dates).orElse(this.dates);
		this.addresses = Optional.ofNullable(addresses).orElse(this.addresses);
		this.tags = Optional.ofNullable(tags).orElse(this.tags);
	}
	
	public Contact toEntity(User user) throws IOException {
		return Contact.builder()
				.id(this.id)
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
		Decoder decoder = Base64.getDecoder();
		byte[] result = decoder.decode(photo);
		if(result.length > fileSize) throw new CanNotSaveContact("업로드할 수 있는 파일 크기를 초과하였습니다.");
		return result;
	}
}
