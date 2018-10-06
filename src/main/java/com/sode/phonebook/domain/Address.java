package com.sode.phonebook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Address {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="contact_id")
	private Contact contact; // not null
	
	@Column(nullable = false)
	private int category; // not null, default 1 (builder)
	
	@Column(nullable = false)
	private String contents; // not null
}
