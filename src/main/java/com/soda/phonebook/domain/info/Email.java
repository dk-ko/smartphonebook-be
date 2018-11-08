package com.soda.phonebook.domain.info;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.soda.phonebook.domain.Info;

import lombok.Builder;

@Entity
@DiscriminatorValue("E")
public class Email extends Info{
	@Builder
	public Email(String contents) {
		super(contents);
	}
}
