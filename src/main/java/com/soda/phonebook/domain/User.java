package com.soda.phonebook.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
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
	private List<DataType> dataTypes;
}
