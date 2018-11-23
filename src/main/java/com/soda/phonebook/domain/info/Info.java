package com.soda.phonebook.domain.info;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.soda.phonebook.domain.BaseEntity;
import com.soda.phonebook.domain.Category;
import com.soda.phonebook.domain.Contact;

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
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="contact_id",
				foreignKey = @ForeignKey(name="fk_info_contact"),
				nullable = false)
	private Contact contact;
	
	@Column(name="contents", nullable=false)
	private String contents;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false, 
				cascade = CascadeType.PERSIST)
	@JoinColumn(name="category_id",
				foreignKey = @ForeignKey(name="fk_info_category"),
				nullable = false)
	private Category category;
	
	
	public Info(Contact contact, String contents, Category category) {
		this.contact = contact;
		this.contents = contents;
		this.category = category;
	}
	
	//
	public void updateCategory(Category category) {
		this.category = category;
	}
}
