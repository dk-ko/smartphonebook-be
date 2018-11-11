package com.soda.phonebook.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@AttributeOverride(name="id", column=@Column(name="user_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity{
	
	@Column(name="name", nullable=false)
	private String name;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	@JsonIgnore
	private Set<Contact> contacts;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	@JsonIgnore
	private Set<Tag> tags;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Category> categories;
	
	@OneToMany
	@JoinTable(name="user_contact",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="contact_id"))
	@OrderBy("id desc")
	private List<Contact> favorites = new ArrayList<Contact>();
	
	@Builder
	public User(String name) {
		this.name = name;
	}
	
	public void updateName(String name) {
		this.name = name;
	}
}
