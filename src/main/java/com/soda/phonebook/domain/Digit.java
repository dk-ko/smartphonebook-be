package com.soda.phonebook.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="contact_id",
				foreignKey = @ForeignKey(name="fk_digit_contact"),
				nullable = false)
	private Contact contact;
	
	@Embedded 
	@Column(name="numbers", nullable=false)
	private Numbers numbers;
	
	@Convert(converter = MarkAttributeConverter.class)
	@Column(name="rep", nullable=false)
	private Mark rep = Mark.N;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category_id",
				foreignKey = @ForeignKey(name="fk_digit_category"),
				nullable = false)
	private Category category;

	
	@Builder
	public Digit(Contact contact, Numbers numbers, Mark rep, Category category) {
		this.contact = contact;
		this.numbers = numbers;
		this.rep = rep;
		this.category = category;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id: %d\n")
				.append("contact_id: %d\n")
				.append("numbers: %s\n")
				.append("rep: %s\n")
				.append("category_id: %d\n");
		return String.format(builder.toString(), this.id, this.contact.getId(), 
				this.numbers.toString(),this.rep,this.category.getId());
	}
	
	public void updateNumbers(Numbers numbers) {
		this.numbers = numbers;
	}
	
	public void updateRep(Mark rep) {
		this.rep = rep;
	}
}
