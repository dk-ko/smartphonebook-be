package com.soda.phonebook.domain.VO;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Numbers {
	private String first;
	private String second;
	private String third;
	
	@Builder
	public Numbers(String first, String second, String third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	@Override
	public String toString() {
		return String.format("%s-%s-%s", this.first, this.second, this.third);
	}
}
