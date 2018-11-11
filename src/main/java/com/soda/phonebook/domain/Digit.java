package com.soda.phonebook.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.soda.phonebook.converter.MarkAttributeConverter;
import com.soda.phonebook.domain.VO.Mark;
import com.soda.phonebook.domain.VO.Numbers;

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
	@JoinColumn(name="contact_id",foreignKey = @ForeignKey(name="fk_digit_contact"))
	private Contact contact;
	
	@Embedded 
	@Column(name="numbers", nullable=false)
	private Numbers numbers;
	
	@Convert(converter = MarkAttributeConverter.class)
	@Enumerated(EnumType.STRING)
	@Column(name="representation")
	private Mark representation = Mark.N;
	
	@ManyToOne
	@JoinColumn(name="category_id",foreignKey = @ForeignKey(name="fk_digit_category"))
	private Category category;

	
	@Builder
	public Digit(Contact contact, Numbers numbers, Mark representation, Category category) {
		this.contact = contact;
		this.numbers = numbers;
		this.representation = representation;
		this.category = category;
	}
	
	public void updateNumbers(Numbers numbers) {
		this.numbers = numbers;
	}
	
	public void updateRepresentation(Mark representation) {
		this.representation = representation;
	}
}
