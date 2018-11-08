package com.soda.phonebook.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.soda.phonebook.domain.VO.Mark;
import com.soda.phonebook.domain.VO.PhoneNumber;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="digit")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Digit extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name="contact_id")
	private Contact contact;
	
	@Embedded 
	@Column(name="phoneNumber", nullable=false)
	private PhoneNumber phoneNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name="representation", nullable=false)
	private Mark representation = Mark.N;
	
	@ManyToOne
	@JoinColumn(name="data_type_id")
	private DataType dataType;

	
	@Builder
	public Digit(PhoneNumber phoneNumber, Mark representation) {
		this.phoneNumber = phoneNumber;
		this.representation = representation;
	}
	
	public void updatePhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void updateRepresentation(Mark representation) {
		this.representation = representation;
	}
}
