package com.soda.phonebook.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="info")
@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE")
public abstract class Info extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name="contact_id",foreignKey = @ForeignKey(name="fk_info_contact"))
	private Contact contact;
	
	@Column(name="contents", nullable=false)
	private String contents;
	
	@ManyToOne
	@JoinColumn(name="category_id",foreignKey = @ForeignKey(name="fk_info_category"))
	private Category category;
	
	public Info(Contact contact, String contents, Category category) {
		this.contact = contact;
		this.contents = contents;
		this.category = category;
	}
}
