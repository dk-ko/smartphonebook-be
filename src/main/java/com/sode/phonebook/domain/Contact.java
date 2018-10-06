package com.sode.phonebook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user; // not null
	
	@Column(nullable = false)
	private String name; // not null
	
	private String memo; // default null
	private String birth; // default null
	
	@Lob
	private byte[] photo;
	
	@Column(nullable = false)
	private int favorite; // not null, default 0
	
	private String site; // default null
	
}
