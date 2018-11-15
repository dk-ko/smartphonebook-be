package com.soda.phonebook.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tag")
@AttributeOverride(name="id", column=@Column(name="tag_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tag extends BaseEntity{
	
	@Column(name="name", nullable=false)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id",
				foreignKey = @ForeignKey(name="fk_tag_user"),
				nullable = false)
	private User user;
	
	@ManyToMany
	@JoinTable(name="tag_contact",
			joinColumns = @JoinColumn(name="tag_id"),
			inverseJoinColumns = @JoinColumn(name="contact_id"))
	@OrderBy("name asc")
	private Set<Contact> contacts = new HashSet<Contact>();
	
	@Builder
	public Tag (String name, User user){
		this.name = name;
		this.user = user;
	}
	
	public void addContact(Contact contact) {
		this.contacts.add(contact);
		// 
	}
}
