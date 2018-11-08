package com.soda.phonebook.domain.info;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.soda.phonebook.domain.Info;

import lombok.Builder;

@Entity
@DiscriminatorValue("U")
public class Url extends Info{
	@Builder
	public Url (String contents) {
		super(contents);
	}
}
