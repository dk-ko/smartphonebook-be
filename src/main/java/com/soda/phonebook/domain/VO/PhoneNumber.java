package com.soda.phonebook.domain.VO;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneNumber {
	private String first;
	private String second;
	private String third;
	
	@Builder
	public PhoneNumber(String first, String second, String third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
}
