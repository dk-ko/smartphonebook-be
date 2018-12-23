package com.soda.phonebook.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
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
public class User extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 6312104514380193520L;

	@Column(name="email", nullable=false, unique=true)
	private String email;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="role", nullable=false)
	private String role;
	
	
	@JsonIgnore
	@OneToMany(mappedBy="user", 
				fetch=FetchType.LAZY, 
				cascade = CascadeType.REMOVE,
				orphanRemoval = true)
	private Set<Contact> contacts = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(	mappedBy="user",
				fetch=FetchType.LAZY, 
				cascade = CascadeType.REMOVE,
				orphanRemoval = true)
	private Set<Tag> tags = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="user", 
				fetch=FetchType.LAZY, 
				cascade = CascadeType.REMOVE,
				orphanRemoval = true)
	private Set<Category> categories = new HashSet<>();
	
	
	@OneToMany(fetch=FetchType.LAZY, 
				cascade = CascadeType.REMOVE,
				orphanRemoval = true)
	@JoinTable(name="user_contact",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="contact_id"))
	@OrderBy("id desc")
	private Set<Contact> favorites = new HashSet<Contact>();
	
	
	@Builder
	public User(String email, String role, String name, 
					Set<Contact> contacts, Set<Tag> tags,
					Set<Category> categories, Set<Contact> favorites) {
		this.email = email;
		this.role = role;
		this.name = name;
		
		this.contacts = Optional.ofNullable(contacts).orElse(this.contacts);
		this.tags = Optional.ofNullable(tags).orElse(this.tags);
		this.categories = Optional.ofNullable(categories).orElse(this.categories);
		this.favorites = Optional.ofNullable(favorites).orElse(this.favorites);
	}
	
	
	public void updateName(String name) {
		this.name = name;
	}
	
	public void updateRole(String role) {
		this.role = role;
	}
}
