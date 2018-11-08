package com.soda.phonebook.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@Table(name="tag_contact")
@Entity
public class TagContact extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name="contact_id")
	private Contact contact;
	
	@ManyToOne
	@JoinColumn(name="tag_id")
	private Tag tag;
}
