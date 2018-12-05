package com.soda.phonebook.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="contact_id",
				foreignKey = @ForeignKey(name="fk_digit_contact"),
				nullable = false)
	private Contact contact;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false,
				cascade = CascadeType.PERSIST)
	@JoinColumn(name="category_id",
				foreignKey = @ForeignKey(name="fk_digit_category"),
				nullable = false)
	private Category category;
	
	@Embedded 
	@Column(name="numbers", nullable=false)
	private Numbers numbers;

	
	@Builder
	public Digit(Long id, Contact contact, Category category, Numbers numbers) {
		this.id = id;
		this.contact = contact;
		this.category = category;
		this.numbers = numbers;
	}
	
	
	public void updateNumbers(Numbers numbers) {
		this.numbers = numbers;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id: %d\n")
				.append("contact_id: %d\n")
				.append("numbers: %s\n")
				.append("category_id: %d\n");
		return String.format(builder.toString(), this.id, this.contact.getId(), 
				this.numbers.toString(),this.category.getId());
	}
}
