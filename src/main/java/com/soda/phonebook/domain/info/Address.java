package com.soda.phonebook.domain.info;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.soda.phonebook.domain.Info;

import lombok.Builder;

@Entity
@DiscriminatorValue("A")
public class Address extends Info{
	
	@Builder
	public Address(String contents) {
		super(contents);
	}
}
